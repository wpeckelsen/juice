package nl.wessel.juice.B.BusinessLogic.Service;


import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreateDeal;
import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreatedDeal;
import nl.wessel.juice.B.BusinessLogic.Exception.BadRequest;
import nl.wessel.juice.B.BusinessLogic.Exception.RecordNotFound;
import nl.wessel.juice.B.BusinessLogic.Model.Deal;
import nl.wessel.juice.C.Repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DealService {

    private final DealRepo dealRepo;
    private final OrderRepo orderRepo;
    private final OrderService orderService;
    private final DomainRepo domainRepo;
    private final DomainService domainService;
    private final ClientRepo clientRepo;
    private final ClientService clientService;
    private final PublisherRepo publisherRepo;
    private final PublisherService publisherService;

    public DealService(DealRepo dealRepo, OrderRepo orderRepo, DomainRepo domainRepo, ClientRepo clientRepo, PublisherRepo publisherRepo, DomainService domainService, OrderService orderService, ClientService clientService, PublisherService publisherService) {
        this.dealRepo = dealRepo;
        this.orderRepo = orderRepo;
        this.domainRepo = domainRepo;
        this.clientRepo = clientRepo;
        this.publisherRepo = publisherRepo;
        this.domainService = domainService;
        this.orderService = orderService;
        this.clientService = clientService;
        this.publisherService = publisherService;
    }

    public static Deal dealMaker(CreateDeal createDeal) {
        Deal deal = new Deal();
        deal.setDueDate(createDeal.getDueDate());
        deal.setPaymentType(createDeal.getPaymentType());
        deal.setTerms(createDeal.getTerms());
        deal.setPrice(createDeal.getPrice());
        return deal;
    }

    public CreatedDeal dealDtoMaker(Deal deal) {
        CreatedDeal createdDeal = new CreatedDeal();
        createdDeal.setDealID(deal.getDealID());
        createdDeal.setDueDate(deal.getDueDate());
        createdDeal.setPrice(deal.getPrice());
        createdDeal.setPaymentType(deal.getPaymentType());
        createdDeal.setTerms(deal.getTerms());

        createdDeal.setOrder(OrderService.orderDtoMaker(deal.getOrder()));
        createdDeal.setDomain(DomainService.domainDtoMaker(deal.getDomain()));
        createdDeal.setClient(ClientService.clientDtoMaker(deal.getClient()));
        createdDeal.setPublisher(PublisherService.publisherDtoMaker(deal.getPublisher()));
//        set client
//        set publisher
        return createdDeal;
    }

    //    CREATE
    public CreatedDeal newDeal(CreateDeal createDeal) {
        Deal deal = dealMaker(createDeal);
        dealRepo.save(deal);
        return dealDtoMaker(deal);
    }
    public CreatedDeal newDeal1(CreateDeal createDeal, Long orderID, Long domainID, Long clientID, Long publisherID) {
        var optionalOrder = orderRepo.findById(orderID);
        var optionalDomain = domainRepo.findById(domainID);
        var optionalClient = clientRepo.findById(clientID);
        var optionalPublisher = publisherRepo.findById(publisherID);

        if (optionalOrder.isPresent()
                && optionalDomain.isPresent()
                && optionalClient.isPresent()
                && optionalPublisher.isPresent())
        {
            Deal deal = dealMaker(createDeal);
            dealRepo.save(deal);
            return dealDtoMaker(deal);
        } else{
            throw new BadRequest(" A deal cannot be made unless a Client, Order, Publisher and Domain is present. " +
                    "Make these first before making a new Deal. ");
        }

    }


    //    READ
    public List<CreatedDeal> getList() {
        List<Deal> dealList = dealRepo.findAll();
        List<CreatedDeal> createdDealList = new ArrayList<>();

        for (Deal deal : dealList) {
            CreatedDeal createdDeal = dealDtoMaker(deal);
            createdDealList.add(createdDeal);
        }
        return createdDealList;
    }


    public CreatedDeal getByID(Long dealID) {
        if (dealRepo.findById(dealID).isPresent()) {
            Deal deal = dealRepo.findById(dealID).get();
            return dealDtoMaker(deal);
        } else {
            Deal deal = new Deal();
            throw new RecordNotFound(deal);
        }
    }


    //    update
    public CreatedDeal update(Long dealID, CreateDeal createDeal) {
        if (dealRepo.findById(dealID).isPresent()) {
            Deal deal = dealRepo.findById(dealID).get();
            Deal deal1 = dealMaker(createDeal);


            deal1.setDealID(deal.getDealID());
            dealRepo.save(deal1);
            return dealDtoMaker(deal1);
        } else {
            Deal deal = new Deal();
            throw new RecordNotFound(deal);
        }
    }

    //    delete
    public void deleteById(Long dealID) {
        dealRepo.deleteById(dealID);
    }


    //    assign
    public CreatedDeal assignOrderAndDomain(Long dealID, Long orderID, Long domainID) {
        var optionalOrder = orderRepo.findById(orderID);
        var optionalDeal = dealRepo.findById(dealID);
        var optionalDomain = domainRepo.findById(domainID);

        if (optionalDeal.isPresent() && optionalOrder.isPresent() && optionalDomain.isPresent()) {
            var order = optionalOrder.get();
            var deal = optionalDeal.get();
            var domain = optionalDomain.get();

            deal.setOrder(order);
            deal.setDomain(domain);

            dealRepo.save(deal);
            return dealDtoMaker(deal);
        } else {
            Deal deal = new Deal();
            throw new RecordNotFound(deal);
        }
    }


    public CreatedDeal makeDeal(Long dealID, Long orderID, Long domainID, Long clientID, Long publisherID) {
        var optionalOrder = orderRepo.findById(orderID);
        var optionalDeal = dealRepo.findById(dealID);
        var optionalDomain = domainRepo.findById(domainID);
        var optionalClient = clientRepo.findById(clientID);
        var optionalPublisher = publisherRepo.findById(publisherID);

        if (optionalDeal.isPresent()
                && optionalOrder.isPresent()
                && optionalDomain.isPresent()
                && optionalClient.isPresent()
                && optionalPublisher.isPresent()
        ) {
            var order = optionalOrder.get();
            var deal = optionalDeal.get();
            var domain = optionalDomain.get();
            var client = optionalClient.get();
            var publisher = optionalPublisher.get();

            deal.setOrder(order);
            deal.setDomain(domain);
            deal.setClient(client);
            deal.setPublisher(publisher);

            dealRepo.save(deal);
            return dealDtoMaker(deal);

        } else {
            Deal deal = new Deal();
            throw new RecordNotFound(deal);
        }
    }

}
