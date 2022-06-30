package nl.wessel.juice.B.BusinessLogic.DTO.Market;

import nl.wessel.juice.B.BusinessLogic.Model.Client;
import nl.wessel.juice.B.BusinessLogic.Model.Publisher;

import java.util.List;

public class CreatedMarket {

    private Long marketID;
    private String name;

//    TODO
    private String TLD;
//    private List<Client> clients;
//    private List<Publisher> publishers;


    public Long getMarketID() {
        return marketID;
    }

    public void setMarketID(Long marketID) {
        this.marketID = marketID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTLD() {
        return TLD;
    }

    public void setTLD(String TLD) {
        this.TLD = TLD;
    }

//    public List<Client> getClients() {
//        return clients;
//    }

//    public void setClients(List<Client> clients) {
//        this.clients = clients;
//    }
//
//    public List<Publisher> getPublishers() {
//        return publishers;
//    }
//
//    public void setPublishers(List<Publisher> publishers) {
//        this.publishers = publishers;
//    }
}
