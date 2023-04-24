package nl.wessel.juice.Model;


import javax.persistence.*;

@Entity
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dealID;

    private int price;
    private String deadline;
    private String payment;
    private String terms;

    @ManyToOne
    @JoinColumn(name = "customer_username")
    private Customer customer;

    @OneToOne(mappedBy = "deal")
    private Bid bid;

    @ManyToOne
    @JoinColumn(name = "publisher_username")
    private Publisher publisher;

    @OneToOne(mappedBy = "deal")
    private Domain domain;

    private boolean done;

    private String principal;


    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
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

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Bid getBid() {
        return bid;
    }

    public void setBid(Bid bid) {
        this.bid = bid;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }
}
