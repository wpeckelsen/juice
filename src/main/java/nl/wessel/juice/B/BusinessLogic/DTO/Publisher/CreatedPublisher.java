package nl.wessel.juice.B.BusinessLogic.DTO.Publisher;

import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreatedDeal;
import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreatedDomain;
import nl.wessel.juice.B.BusinessLogic.Model.Deal;
import nl.wessel.juice.B.BusinessLogic.Model.Market;
import nl.wessel.juice.B.BusinessLogic.Model.Order;

import java.util.List;

public class CreatedPublisher {
    private Long publisherID;
    private String name;
    private String email;
    private String password;

    private List<CreatedDomain> domains;
    private List<CreatedDeal> deals;


    public Long getPublisherID() {
        return publisherID;
    }
    public void setPublisherID(Long publisherID) {
        this.publisherID = publisherID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<CreatedDomain> getDomains() {
        return domains;
    }
    public void setDomains(List<CreatedDomain> domains) {
        this.domains = domains;
    }
    public List<CreatedDeal> getDeals() {
        return deals;
    }
    public void setDeals(List<CreatedDeal> deals) {
        this.deals = deals;
    }
}