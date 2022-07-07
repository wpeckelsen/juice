package nl.wessel.juice.B.BusinessLogic.DTO.Deal;

import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreatedDomain;
import nl.wessel.juice.B.BusinessLogic.DTO.Bid.CreatedBid;
import nl.wessel.juice.B.BusinessLogic.DTO.Customer.CustomerDto;

public class CreatedDeal {


    private Long dealID;
    private String deadline;
    private int price;
    private String paymentType;
    private String terms;

    private CreatedBid bid;
    private CreatedDomain domain;
    private CustomerDto dto;


    public CustomerDto getDto() {
        return dto;
    }

    public void setDto(CustomerDto dto) {
        this.dto = dto;
    }


    public CreatedBid getBid() {
        return bid;
    }

    public void setBid(CreatedBid bid) {
        this.bid = bid;
    }

    public CreatedDomain getDomain() {
        return domain;
    }

    public void setDomain(CreatedDomain domain) {
        this.domain = domain;
    }

    public Long getDealID() {
        return dealID;
    }

    public void setDealID(Long dealID) {
        this.dealID = dealID;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

}
