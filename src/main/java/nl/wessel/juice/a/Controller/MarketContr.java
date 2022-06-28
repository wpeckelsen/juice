package nl.wessel.juice.a.Controller;


import nl.wessel.juice.B.BusinessLogic.DTO.Market.CreateMarket;
import nl.wessel.juice.B.BusinessLogic.DTO.Market.CreatedMarket;
import nl.wessel.juice.B.BusinessLogic.Service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/market")
public class MarketContr {


    @Autowired
    MarketService marketService;


    //    CREATE
    @PostMapping("/new")
    public ResponseEntity<CreatedMarket> newMarket(@RequestBody CreateMarket market) {
        final CreatedMarket createdMarket = marketService.newMarket(market);
        return ResponseEntity.ok().body(createdMarket);


    }


    //    READ
    @GetMapping("/list")
    public ResponseEntity<List<CreatedMarket>> getList() {
        List<CreatedMarket> createdMarketList;
        createdMarketList = marketService.getList();
        return ResponseEntity.ok().body(createdMarketList);
    }

    @GetMapping("/{marketID}")
    public ResponseEntity<CreatedMarket> getByID(@PathVariable("marketID") Long marketID) {
        CreatedMarket createdMarket = marketService.getByID(marketID);
        return ResponseEntity.ok().body(createdMarket);
    }


    //    update
    @PutMapping("/update/{marketID}")
    public ResponseEntity<Object> update(@PathVariable Long marketID, @RequestBody CreateMarket createMarket) {
        CreatedMarket createdMarket = marketService.update(marketID, createMarket);
        return ResponseEntity.ok().body(createdMarket);
    }


    //    delete
    @DeleteMapping("/delete/{marketID}")
    public ResponseEntity<Object> deleteById(@PathVariable Long marketID) {
        marketService.deleteById(marketID);
        return ResponseEntity.noContent().build();
    }
}
