package nl.wessel.juice.B.BusinessLogic.DTO.Order;

import nl.wessel.juice.B.BusinessLogic.Model.Market;

import java.net.URL;
import java.util.Date;

public class CreateOrder {


//    private Long orderID;
    private int wordCount;
    private Date dueDate;
    private String topic;
    private URL anchorLink;
    private String language;

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
