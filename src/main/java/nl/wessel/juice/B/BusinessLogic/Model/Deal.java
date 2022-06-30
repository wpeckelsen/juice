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


    @OneToOne(mappedBy = "deal", fetch = FetchType.EAGER)
//    @JoinColumn(name = "domain_domainID")
    private Domain domain;

    @OneToOne(mappedBy = "deal", fetch = FetchType.EAGER)
    private Order order;








    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

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

//    public Publisher getPublisher() {
//        return publisher;
//    }

//    public void setPublisher(Publisher publisher) {
//        this.publisher = publisher;
//    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

//    public Client getClient() {
//        return client;
//    }

//    public void setClient(Client client) {
//        this.client = client;
//    }
}
