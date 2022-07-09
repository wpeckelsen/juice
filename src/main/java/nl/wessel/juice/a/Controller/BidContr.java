package nl.wessel.juice.a.Controller;


import nl.wessel.juice.B.BusinessLogic.DTO.Bid.CreateBid;
import nl.wessel.juice.B.BusinessLogic.DTO.Bid.CreatedBid;
import nl.wessel.juice.B.BusinessLogic.Service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "juice/bid")
public class BidContr {


    @Autowired
    BidService bidService;




//        CREATE
//    @PostMapping("/new")
//    public ResponseEntity<CreatedBid> newBid(@RequestBody CreateBid bid) {
//        final CreatedBid createdBid = bidService.newBid(bid);
//        return ResponseEntity.ok().body(createdBid);
//    }


    //    READ
//    @GetMapping("/list")
//    public ResponseEntity<List<CreatedBid>> getList() {
//        List<CreatedBid> createdBidList;
//        createdBidList = bidService.getList();
//        return ResponseEntity.ok().body(createdBidList);
//    }
//
//    @GetMapping("/{bidID}")
//    public ResponseEntity<CreatedBid> getByID(@PathVariable("bidID") Long bidID) {
//        CreatedBid createdBid = bidService.getByID(bidID);
//        return ResponseEntity.ok().body(createdBid);
//    }


    //    update
//    @PutMapping("/update/{bidID}")
//    public ResponseEntity<Object> update(@PathVariable Long bidID, @RequestBody CreateBid createBid) {
//        CreatedBid createdBid = bidService.update(bidID, createBid);
//        return ResponseEntity.ok().body(createdBid);
//    }


    //    delete
//    @DeleteMapping("/delete/{bidID}")
//    public ResponseEntity<Object> deleteById(@PathVariable Long bidID) {
//        bidService.deleteById(bidID);
//        return ResponseEntity.noContent().build();
//    }
}