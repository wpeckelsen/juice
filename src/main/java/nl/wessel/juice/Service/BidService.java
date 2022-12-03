package nl.wessel.juice.Service;

import nl.wessel.juice.DTO.Bid.CreateBid;
import nl.wessel.juice.DTO.Bid.CreatedBid;
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

    public static Bid bidMaker(CreateBid createBid) {

        Bid bid = new Bid();
        bid.setWords(createBid.getWords());
        bid.setDeadline(createBid.getDeadline());
        bid.setTopic(createBid.getTopic());
        bid.setAnchor(createBid.getAnchor());
        bid.setVernacular(createBid.getVernacular());
        return bid;
    }




    public static CreatedBid bidDtoMaker(Bid bid) {
        CreatedBid createdBid = new CreatedBid();
        ZonedDateTime rightNow = ZonedDateTime.now();

        createdBid.setCreationTime(rightNow);
        createdBid.setBidID(bid.getBidID());
        createdBid.setWords(bid.getWords());
        createdBid.setDeadline(bid.getDeadline());
        createdBid.setTopic(bid.getTopic());
        createdBid.setAnchor(bid.getAnchor());
        createdBid.setVernacular(bid.getVernacular());

        return createdBid;
    }


//    this method gives an error:
//    non-transient entity has a null id: nl.wessel.juice.B.BusinessLogic.Model.Photo
//    corresponding method in CustomerController is commented out as well


    @Transactional
    public CreatedBid bidAndPhoto(Long bidID, String name, CreateBid createBid){
        Bid foundBid = bidRepository.findById(bidID).get();
        var photo = photoRepository.findPhotoByFileName(name);
        Bid bid = bidMaker(createBid);
        bid.setBidID(foundBid.getBidID());
        bid.setPhoto(photo);
        bidRepository.save(bid);
        return bidDtoMaker(bid);
    }

    //    READ
    public List<CreatedBid> getList() {

        List<Bid> bidList = bidRepository.findAll();

        if (bidList.isEmpty()) {
            Bid bid = new Bid();
            throw new RecordNotFound(bid);
        } else {
            List<CreatedBid> createdBidList = new ArrayList<>();

            for (Bid bid : bidList) {
                CreatedBid createdBid = bidDtoMaker(bid);
                createdBidList.add(createdBid);
            }
            return createdBidList;
        }
    }

    public CreatedBid getByID(Long idBid) {
        if (bidRepository.findById(idBid).isPresent()) {
            Bid bid = bidRepository.findById(idBid).get();
            return bidDtoMaker(bid);
        } else {
            Bid bid = new Bid();
            throw new RecordNotFound(bid);
        }
    }


    //    update
    public CreatedBid update(Long bidID, CreateBid createBid) {


        if (bidRepository.findById(bidID).isPresent()) {
            Bid bid = bidRepository.findById(bidID).get();
            Bid bid1 = bidMaker(createBid);

            bid1.setBidID(bid.getBidID());
            bidRepository.save(bid1);

            return bidDtoMaker(bid1);
        } else {
            Bid bid = new Bid();
            throw new RecordNotFound(bid);
        }
    }

    //    delete
    public void deleteById(Long bidID) {
        bidRepository.deleteById(bidID);
    }


}
