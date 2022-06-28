package nl.wessel.juice.B.BusinessLogic.Model;


import javax.persistence.*;
import java.util.Date;

@Entity
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dealID;
    private int price;
    private Date dueDate;

    @Column(length = 50)
    private String paymentType;

    @Column(length = 2000)
    private String terms;


    @OneToOne
    private Domain domain;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_clientID")
    @MapsId(value = "clientID")
    private Client client;

    @ManyToOne
    @MapsId("publisherID")
    @JoinColumn(name = "publisher_publisherID")
    private Publisher publisher;


    public Long getDealID() {
        return dealID;
    }

    public void setDealID(Long dealID) {
        this.dealID = dealID;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
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
