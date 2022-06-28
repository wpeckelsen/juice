package nl.wessel.juice.a.Controller;


import nl.wessel.juice.B.BusinessLogic.DTO.Order.CreateOrder;
import nl.wessel.juice.B.BusinessLogic.DTO.Order.CreatedOrder;
import nl.wessel.juice.B.BusinessLogic.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/order")
public class OrderContr {


    @Autowired
    OrderService orderService;



    //    CREATE
    @PostMapping("/new")
    public ResponseEntity<CreatedOrder> newOrder(@RequestBody CreateOrder order) {
        final CreatedOrder createdOrder = orderService.newOrder(order);
        return ResponseEntity.ok().body(createdOrder);


    }


    //    READ
    @GetMapping("/list")
    public ResponseEntity<List<CreatedOrder>> getList() {
        List<CreatedOrder> createdOrderList;
        createdOrderList = orderService.getList();
        return ResponseEntity.ok().body(createdOrderList);
    }

    @GetMapping("/{orderID}")
    public ResponseEntity<CreatedOrder> getByID(@PathVariable("orderID") Long orderID) {
        CreatedOrder createdOrder = orderService.getByID(orderID);
        return ResponseEntity.ok().body(createdOrder);
    }


    //    update
    @PutMapping("/update/{orderID}")
    public ResponseEntity<Object> update(@PathVariable Long orderID, @RequestBody CreateOrder createOrder) {
        CreatedOrder createdOrder = orderService.update(orderID, createOrder);
        return ResponseEntity.ok().body(createdOrder);
    }


    //    delete
    @DeleteMapping("/delete/{orderID}")
    public ResponseEntity<Object> deleteById(@PathVariable Long orderID) {
        orderService.deleteById(orderID);
        return ResponseEntity.noContent().build();
    }
}