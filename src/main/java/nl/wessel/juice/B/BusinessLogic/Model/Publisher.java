package nl.wessel.juice.B.BusinessLogic.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long publisherID;


    private String name;
    private String email;
    private String password;

//    @OneToMany(mappedBy = "client")
//    @JsonIgnore
//    private Collection<Deal> deals;

    @OneToMany(mappedBy = "publisher", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Domain> domains;


//
//    @ManyToMany
//    private List<Market> markets;


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

    public List<Domain> getDomains() {
        return domains;
    }

    public void setDomains(List<Domain> domains) {
        this.domains = domains;
    }

//    public List<Market> getMarkets() {
//        return markets;
//    }

//    public void setMarkets(List<Market> markets) {
//        this.markets = markets;
//    }

}
