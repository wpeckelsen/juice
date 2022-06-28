package nl.wessel.juice.B.BusinessLogic.Service;


import nl.wessel.juice.B.BusinessLogic.DTO.Order.CreateOrder;
import nl.wessel.juice.B.BusinessLogic.DTO.Order.CreatedOrder;


import nl.wessel.juice.B.BusinessLogic.Exception.RecordNotFound;
import nl.wessel.juice.B.BusinessLogic.Model.Order;
import nl.wessel.juice.C.Repository.OrderRepo;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepo orderRepo;

    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    public static Order orderMaker(CreateOrder createOrder){
        Order order = new Order();
        order.setWordCount(createOrder.getWordCount());
        order.setDueDate(createOrder.getDueDate());
        order.setTopic(createOrder.getTopic());
        order.setAnchorLink(createOrder.getAnchorLink());
        order.setLanguage(createOrder.getLanguage());
        return order;
    }
    public static CreatedOrder orderDtoMaker(Order order){
        CreatedOrder createdOrder = new CreatedOrder();
        createdOrder.setOrderID(order.getOrderID());
        createdOrder.setDueDate(order.getDueDate());
        createdOrder.setTopic(order.getTopic());
        createdOrder.setAnchorLink(order.getAnchorLink());
        createdOrder.setLanguage(order.getLanguage());
        return createdOrder;
    }



//    CREATE

    public CreatedOrder newOrder(CreateOrder createOrder){
        Order order = orderMaker(createOrder);
        orderRepo.save(order);
        return orderDtoMaker(order);
    }
//    READ

public List<CreatedOrder> getList() {
    List<Order> orderList = orderRepo.findAll();
    List<CreatedOrder> createdOrderList = new ArrayList<>();

    for (Order order : orderList) {
        CreatedOrder createdOrder = orderDtoMaker(order);
        createdOrderList.add(createdOrder);
    }
    return createdOrderList;
}


    public List<CreatedOrder> getListByName(String name) {
        List<Order> orderList = orderRepo.findOrdersByName(name);
        List<CreatedOrder> createdOrderList = new ArrayList<>();

        for (Order order : orderList) {
            CreatedOrder createdOrder = orderDtoMaker(order);
            createdOrderList.add(createdOrder);
        }
        return createdOrderList;
    }

    public CreatedOrder getByID(Long idOrder) {
        if (orderRepo.findById(idOrder).isPresent()) {
            Order order = orderRepo.findById(idOrder).get();
            return orderDtoMaker(order);
        } else {
            Order order = new Order();
            throw new RecordNotFound(order);
        }
    }



    //    update
    public CreatedOrder update(Long orderID, CreateOrder createOrder) {
        if (orderRepo.findById(orderID).isPresent()) {
            Order order = orderRepo.findById(orderID).get();
            Order order1 = orderMaker(createOrder);

            order1.setOrderID(order.getOrderID());
            orderRepo.save(order1);
            return orderDtoMaker(order1);
        } else {
            Order order = new Order();
            throw new RecordNotFound(order);
        }
    }

    //    delete

    public void deleteById(Long orderID) {
        orderRepo.deleteById(orderID);
    }






}
