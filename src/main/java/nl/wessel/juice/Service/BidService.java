package nl.wessel.juice.Service;

import nl.wessel.juice.DTO.Bid.CreateBidDto;
import nl.wessel.juice.DTO.Bid.CreatedBidDTO;
import nl.wessel.juice.Exception.RecordNotFound;
import nl.wessel.juice.Model.Bid;
import nl.wessel.juice.Repository.BidRepository;
import nl.wessel.juice.Repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BidService {


    private final BidRepository bidRepository;
    private final PhotoRepository photoRepository;


    @Autowired
    public BidService(BidRepository bidRepository, PhotoRepository photoRepository) {
        this.bidRepository = bidRepository;
        this.photoRepository = photoRepository;

    }

    public static Bid bidMaker(CreateBidDto createBidDto) {
        Bid bid = new Bid();
        bid.setWords(createBidDto.getWords());
        bid.setDeadline(createBidDto.getDeadline());
        bid.setTopic(createBidDto.getTopic());
        bid.setAnchor(createBidDto.getAnchor());
        bid.setVernacular(createBidDto.getVernacular());
        return bid;
    }




    public static CreatedBidDTO bidDtoMaker(Bid bid) {
        CreatedBidDTO createdBidDTO = new CreatedBidDTO();
        ZonedDateTime rightNow = ZonedDateTime.now();

        createdBidDTO.setCreationTime(rightNow);
        createdBidDTO.setBidID(bid.getBidID());
        createdBidDTO.setWords(bid.getWords());
        createdBidDTO.setDeadline(bid.getDeadline());
        createdBidDTO.setTopic(bid.getTopic());
        createdBidDTO.setAnchor(bid.getAnchor());
        createdBidDTO.setVernacular(bid.getVernacular());

        return createdBidDTO;
    }


//    this method gives an error:
//    non-transient entity has a null id: nl.wessel.juice.B.BusinessLogic.Model.Photo
//    corresponding method in CustomerController is commented out as well


    @Transactional
    public CreatedBidDTO bidAndPhoto(Long bidID, String name, CreateBidDto createBidDto){
        Bid foundBid = bidRepository.findById(bidID).get();
        var photo = photoRepository.findPhotoByFileName(name);
        Bid bid = bidMaker(createBidDto);
        bid.setBidID(foundBid.getBidID());
        bid.setPhoto(photo);
        bidRepository.save(bid);
        return bidDtoMaker(bid);
    }

    //    READ
    public List<CreatedBidDTO> getList() {

        List<Bid> bidList = bidRepository.findAll();

        if (bidList.isEmpty()) {
            Bid bid = new Bid();
            throw new RecordNotFound(bid);
        } else {
            List<CreatedBidDTO> createdBidDTOList = new ArrayList<>();

            for (Bid bid : bidList) {
                CreatedBidDTO createdBidDTO = bidDtoMaker(bid);
                createdBidDTOList.add(createdBidDTO);
            }
            return createdBidDTOList;
        }
    }

    public CreatedBidDTO getByID(Long idBid) {
        if (bidRepository.findById(idBid).isPresent()) {
            Bid bid = bidRepository.findById(idBid).get();
            return bidDtoMaker(bid);
        } else {
            Bid bid = new Bid();
            throw new RecordNotFound(bid);
        }
    }


    public CreatedBidDTO update(Long bidID, CreateBidDto createBidDto) {


        if (bidRepository.findById(bidID).isPresent()) {
            Bid bid = bidRepository.findById(bidID).get();
            Bid bid1 = bidMaker(createBidDto);

            bid1.setBidID(bid.getBidID());
            bidRepository.save(bid1);

            return bidDtoMaker(bid1);
        } else {
            Bid bid = new Bid();
            throw new RecordNotFound(bid);
        }
    }

    public void delete(Long bidID) {
        bidRepository.deleteById(bidID);
    }
}
