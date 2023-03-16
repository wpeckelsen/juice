package nl.wessel.juice.Service;

import nl.wessel.juice.DTO.Bid.CreateBidDto;
import nl.wessel.juice.DTO.Bid.CreatedBidDto;
import nl.wessel.juice.Exception.RecordNotFound;
import nl.wessel.juice.Model.Bid;
import nl.wessel.juice.Repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BidService {

    private final BidRepository bidRepository;

    @Autowired
    public BidService(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
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

    public static CreatedBidDto bidDtoMaker(Bid bid) {
        CreatedBidDto createdBidDTO = new CreatedBidDto();
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

    public List<Long> getList() {

        List<Bid> bidList = bidRepository.findAll();

        if (bidList.isEmpty()) {
            Bid bid = new Bid();
            throw new RecordNotFound(bid);
        } else {
            List<Long> bidIDs = new ArrayList<>();

            for (Bid bid : bidList) {
                CreatedBidDto createdBidDTO = bidDtoMaker(bid);
                bidIDs.add(createdBidDTO.bidID);
            }
            return bidIDs;
        }
    }

    public CreatedBidDto getByID(Long idBid) {
        if (bidRepository.findById(idBid).isPresent()) {
            Bid bid = bidRepository.findById(idBid).get();
            return bidDtoMaker(bid);
        } else {
            Bid bid = new Bid();
            throw new RecordNotFound(bid);
        }
    }


    public CreatedBidDto update(Long bidID, CreateBidDto createBidDto) {


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
