package nl.wessel.juice.B.BusinessLogic.DTO.Deal;

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


//    TODO
    private Client client;

    private Publisher publisher;
    private Domain domain;


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

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
