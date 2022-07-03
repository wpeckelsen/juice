package nl.wessel.juice.B.BusinessLogic.DTO.Deal;

import nl.wessel.juice.B.BusinessLogic.DTO.Client.CreatedClient;
import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreatedDomain;
import nl.wessel.juice.B.BusinessLogic.DTO.Order.CreatedOrder;
import nl.wessel.juice.B.BusinessLogic.DTO.Publisher.CreatedPublisher;
import nl.wessel.juice.B.BusinessLogic.Model.Client;
import nl.wessel.juice.B.BusinessLogic.Model.Domain;
import nl.wessel.juice.B.BusinessLogic.Model.Publisher;

import java.util.Date;

public class CreatedDeal {


    private Long dealID;
    private Date dueDate;
    private int price;
    private String paymentType;
    private String terms;

    private CreatedOrder order;
    private CreatedDomain domain;
    private CreatedClient client;
    private CreatedPublisher publisher;

    public CreatedClient getClient() {
        return client;
    }

    public void setClient(CreatedClient client) {
        this.client = client;
    }

    public CreatedPublisher getPublisher() {
        return publisher;
    }

    public void setPublisher(CreatedPublisher publisher) {
        this.publisher = publisher;
    }

    public CreatedOrder getOrder() {
        return order;
    }

    public void setOrder(CreatedOrder order) {
        this.order = order;
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

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
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
