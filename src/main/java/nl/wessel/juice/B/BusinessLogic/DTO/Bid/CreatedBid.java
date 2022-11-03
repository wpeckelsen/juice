package nl.wessel.juice.B.BusinessLogic.DTO.Bid;

import nl.wessel.juice.B.BusinessLogic.DTO.Photo.PhotoDto;
import nl.wessel.juice.B.BusinessLogic.Model.Photo;

import java.time.Instant;
import java.time.ZonedDateTime;

public class CreatedBid {





    private Long bidID;
    private int words;
    private String deadline;

    private String topic;
    private String anchor;
    private String vernacular;
    private ZonedDateTime creationTime;
    private PhotoDto photo;

    public PhotoDto getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoDto photo) {
        this.photo = photo;
    }

    public ZonedDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(ZonedDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public Long getBidID() {
        return bidID;
    }

    public void setBidID(Long bidID) {
        this.bidID = bidID;
    }

    public int getWords() {
        return words;
    }

    public void setWords(int words) {
        this.words = words;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAnchor() {
        return anchor;
    }

    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }

    public String getVernacular() {
        return vernacular;
    }

    public void setVernacular(String vernacular) {
        this.vernacular = vernacular;
    }
}
