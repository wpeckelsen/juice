package nl.wessel.juice.B.BusinessLogic.DTO.Market;

import nl.wessel.juice.B.BusinessLogic.Model.Client;
import nl.wessel.juice.B.BusinessLogic.Model.Publisher;

import java.util.List;

public class CreateMarket {

    private String name;
    private String TLD;

//    TODO
//    private List<Client> clients;
//    private List<Publisher> publishers;


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
//
//    public List<Client> getClients() {
//        return clients;
//    }
//
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
