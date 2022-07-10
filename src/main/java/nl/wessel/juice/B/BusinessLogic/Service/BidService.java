package nl.wessel.juice.B.BusinessLogic.Service;


import nl.wessel.juice.B.BusinessLogic.DTO.Bid.CreateBid;
import nl.wessel.juice.B.BusinessLogic.DTO.Bid.CreatedBid;
import nl.wessel.juice.B.BusinessLogic.Exception.RecordNotFound;
import nl.wessel.juice.B.BusinessLogic.Model.Bid;
import nl.wessel.juice.B.BusinessLogic.Model.Customer;
import nl.wessel.juice.C.Repository.BidRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BidService {



    private BidRepo bidRepo;

    @Autowired
    private CustomerService customerService;

    @Autowired
    public BidService(BidRepo bidRepo) {
        this.bidRepo = bidRepo;
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


//    CREATE

//    public CreatedBid newBid(CreateBid createBid){
//        Bid bid = bidMaker(createBid);
//        bidRepo.save(bid);
//        return bidDtoMaker(bid);
//    }

    //    READ
    public List<CreatedBid> getList() {

        List<Bid> bidList = bidRepo.findAll();

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


//    public List<CreatedBid> getListByName(String name) {
//        List<Bid> bidList = bidRepo.findBidsByName(name);
//        List<CreatedBid> createdBidList = new ArrayList<>();
//
//        for (Bid bid : bidList) {
//            CreatedBid createdBid = bidDtoMaker(bid);
//            createdBidList.add(createdBid);
//        }
//        return createdBidList;
//    }

    public CreatedBid getByID(Long idBid) {
        if (bidRepo.findById(idBid).isPresent()) {
            Bid bid = bidRepo.findById(idBid).get();
            return bidDtoMaker(bid);
        } else {
            Bid bid = new Bid();
            throw new RecordNotFound(bid);
        }
    }


    //    update
    public CreatedBid update(Long bidID, CreateBid createBid) {


        if (bidRepo.findById(bidID).isPresent()) {
            Bid bid = bidRepo.findById(bidID).get();
            Bid bid1 = bidMaker(createBid);

            bid1.setBidID(bid.getBidID());
            bidRepo.save(bid1);

            return bidDtoMaker(bid1);
        } else {
            Bid bid = new Bid();
            throw new RecordNotFound(bid);
        }
    }

    //    delete

    public void deleteById(Long bidID) {
        bidRepo.deleteById(bidID);
    }


}
