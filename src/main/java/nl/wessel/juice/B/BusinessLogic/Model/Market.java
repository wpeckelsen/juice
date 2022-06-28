package nl.wessel.juice.B.BusinessLogic.Model;


import javax.persistence.*;
import java.util.List;

@Entity
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long marketID;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String TLD;

    @Column(length = 50)
    @ManyToMany
    private List<Client> clients;

    @ManyToMany
    private List<Publisher> publishers;


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

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Publisher> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<Publisher> publishers) {
        this.publishers = publishers;
    }
}
