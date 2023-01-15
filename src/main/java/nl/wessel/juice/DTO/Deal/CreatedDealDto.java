package nl.wessel.juice.DTO.Deal;

import nl.wessel.juice.DTO.Domain.CreatedDomainDto;
import nl.wessel.juice.DTO.Bid.CreatedBidDTO;
import nl.wessel.juice.DTO.Customer.CreatedCustomerDto;
import nl.wessel.juice.DTO.Publisher.CreatedPublisherDto;

// CreatedDealDto
public class CreatedDealDto {


    private Long dealID;
    private String deadline;
    private int price;
    private String paymentType;
    private String terms;

    private CreatedBidDTO bid;
    private CreatedDomainDto domain;
    private CreatedCustomerDto customer;
    private CreatedPublisherDto publisher;



    public CreatedPublisherDto getPublisher() {
        return publisher;
    }

    public void setPublisher(CreatedPublisherDto publisher) {
        this.publisher = publisher;
    }

    public CreatedCustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CreatedCustomerDto customer) {
        this.customer = customer;
    }


    public CreatedBidDTO getBid() {
        return bid;
    }

    public void setBid(CreatedBidDTO bid) {
        this.bid = bid;
    }

    public CreatedDomainDto getDomain() {
        return domain;
    }

    public void setDomain(CreatedDomainDto domain) {
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
