package nl.wessel.juice.B.BusinessLogic.DTO.Deal;

import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreatedDomain;
import nl.wessel.juice.B.BusinessLogic.DTO.Order.CreatedOrder;
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

    private CreatedOrder createdOrder;
    private CreatedDomain createdDomain;

    public CreatedOrder getCreatedOrder() {
        return createdOrder;
    }

    public void setCreatedOrder(CreatedOrder createdOrder) {
        this.createdOrder = createdOrder;
    }

    public CreatedDomain getCreatedDomain() {
        return createdDomain;
    }

    public void setCreatedDomain(CreatedDomain createdDomain) {
        this.createdDomain = createdDomain;
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
