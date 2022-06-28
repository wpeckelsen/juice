package nl.wessel.juice.B.BusinessLogic.Service;


import nl.wessel.juice.C.Repository.DealRepo;
import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreateDeal;
import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreatedDeal;
import nl.wessel.juice.B.BusinessLogic.Exception.RecordNotFound;
import nl.wessel.juice.B.BusinessLogic.Model.Client;
import nl.wessel.juice.B.BusinessLogic.Model.Deal;
import nl.wessel.juice.B.BusinessLogic.Model.Domain;
import nl.wessel.juice.B.BusinessLogic.Model.Publisher;
import nl.wessel.juice.C.Repository.DealRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DealService {

    private final DealRepo dealRepo;

    public DealService(DealRepo dealRepo) {
        this.dealRepo = dealRepo;
    }

    public static Deal dealMaker(CreateDeal createDeal) {
        Deal deal = new Deal();
        deal.setDueDate(createDeal.getDueDate());
        deal.setPaymentType(createDeal.getPaymentType());
        deal.setTerms(createDeal.getTerms());
        deal.setPrice(createDeal.getPrice());
        return deal;
    }

    public static CreatedDeal dealDtoMaker(Deal deal) {
        CreatedDeal createdDeal = new CreatedDeal();
        createdDeal.setDealID(deal.getDealID());
        createdDeal.setDueDate(deal.getDueDate());
        createdDeal.setPrice(deal.getPrice());
        createdDeal.setPaymentType(deal.getPaymentType());
        createdDeal.setTerms(deal.getTerms());
        return createdDeal;




    }

    //    CREATE
    public CreatedDeal newDeal(CreateDeal createDeal) {
        Deal deal = dealMaker(createDeal);
        dealRepo.save(deal);
        return dealDtoMaker(deal);
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

    public List<CreatedDeal> getListByName(String name) {
        List<Deal> dealList = dealRepo.findDealsByName(name);
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
    public CreatedDeal update(Long identityCode, CreateDeal createDeal) {
        if (dealRepo.findById(identityCode).isPresent()) {
            Deal deal = dealRepo.findById(identityCode).get();
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
    public void deleteById(Long identityCode) {
        dealRepo.deleteById(identityCode);
    }
}
