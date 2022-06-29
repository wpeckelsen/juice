package nl.wessel.juice.B.BusinessLogic.Model;

import javax.persistence.*;
import java.net.URL;
import java.util.Date;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderID;
    private int wordCount;
    private Date dueDate;

    @Column(length = 50)
    private String topic;

    @Column(length = 1000)
    private URL anchorLink;

    @Column(length = 50)
    private String language;

    @ManyToOne
    @JoinColumn(name = "client_clientID")
    Client client;


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public URL getAnchorLink() {
        return anchorLink;
    }

    public void setAnchorLink(URL anchorLink) {
        this.anchorLink = anchorLink;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }




}
