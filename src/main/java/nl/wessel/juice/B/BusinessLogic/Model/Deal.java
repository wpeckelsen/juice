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
    private String paymentType;
    private String terms;


    @ManyToOne
    @JoinColumn(name = "client_clientID")
    private Client client;

    @OneToOne(mappedBy = "deal"/*, fetch = FetchType.EAGER*/)
    private Order order;


    @ManyToOne
    @JoinColumn(name = "publisher_publisherID")
    private Publisher publisher;

    @OneToOne(mappedBy = "deal"/*, fetch = FetchType.EAGER*/)
    private Domain domain;


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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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
}
