package nl.wessel.juice.DTO.Bid;

import nl.wessel.juice.DTO.Photo.PhotoDto;

import java.time.ZonedDateTime;

public class CreatedBidDto {





    public Long bidID;
    public int words;
    public String deadline;

    public String topic;
    public String anchor;
    public String vernacular;
    public ZonedDateTime creationTime;
    public PhotoDto photo;

    public Long getBidID() {
        return bidID;
    }
//
//    public int getWords() {
//        return words;
//    }
//
//    public String getDeadline() {
//        return deadline;
//    }

//    public String getTopic() {
//        return topic;
//    }
//
//    public String getAnchor() {
//        return anchor;
//    }
//
//    public String getVernacular() {
//        return vernacular;
//    }
//
//    public ZonedDateTime getCreationTime() {
//        return creationTime;
//    }
//
//    public PhotoDto getPhoto() {
//        return photo;
//    }

    public void setBidID(Long bidID) {
        this.bidID = bidID;
    }

    public void setWords(int words) {
        this.words = words;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }

    public void setVernacular(String vernacular) {
        this.vernacular = vernacular;
    }

    public void setCreationTime(ZonedDateTime creationTime) {
        this.creationTime = creationTime;
    }

//    public void setPhoto(PhotoDto photo) {
//        this.photo = photo;
//    }
}
