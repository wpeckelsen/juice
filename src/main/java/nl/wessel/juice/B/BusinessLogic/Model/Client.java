package nl.wessel.juice.B.BusinessLogic.Model;

import javax.persistence.*;
import java.util.List;


@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientID;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String email;

    @Column(length = 500)
    private String password;


    @OneToMany(mappedBy = "client"/*, fetch = FetchType.EAGER*/, cascade = CascadeType.ALL)
    private List<Bid> bids;

    @OneToMany(mappedBy = "client"/*, fetch = FetchType.EAGER*/)
    private List<Deal> deals;




    public Long getClientID() {
        return clientID;
    }
    public void setClientID(Long clientID) {
        this.clientID = clientID;
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
    public List<Bid> getBids() {
        return bids;
    }
    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

}
