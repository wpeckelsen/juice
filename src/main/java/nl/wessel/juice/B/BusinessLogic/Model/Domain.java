package nl.wessel.juice.B.BusinessLogic.Model;

import javax.persistence.*;

@Entity
public class Domain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long domainID;

    private String name;
    private String TLD;
    private String category;
    private int price;

    @ManyToOne
    @JoinColumn(name = "owner_username")
    private Owner owner;

    @OneToOne
    private Deal deal;

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Long getDomainID() {
        return domainID;
    }

    public void setDomainID(Long domainID) {
        this.domainID = domainID;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        if (price >= 0){
            this.price = price;
        } else {
            this.price = 0;
        }
    }

//    public Publisher getPublisher() {
//        return publisher;
//    }
//
//    public void setPublisher(Publisher publisher) {
//        this.publisher = publisher;
//    }

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }
}
