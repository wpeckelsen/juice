package nl.wessel.juice.B.BusinessLogic.DTO.Publisher;

import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreatedDeal;
import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreatedDomain;

import java.util.List;

public class CreatedPublisher {
    private Long publisherID;
    private String name;
    private String country;
    private String niche;

//    private List<CreatedDomain> domains;
//    private List<CreatedDeal> deals;


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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNiche() {
        return niche;
    }

    public void setNiche(String niche) {
        this.niche = niche;
    }

//    public List<CreatedDomain> getDomains() {
//        return domains;
//    }
//
//    public void setDomains(List<CreatedDomain> domains) {
//        this.domains = domains;
//    }
//
//    public List<CreatedDeal> getDeals() {
//        return deals;
//    }
//
//    public void setDeals(List<CreatedDeal> deals) {
//        this.deals = deals;
//    }
}