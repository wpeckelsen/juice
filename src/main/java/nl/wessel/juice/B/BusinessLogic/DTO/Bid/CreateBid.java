package nl.wessel.juice.B.BusinessLogic.DTO.Bid;

public class CreateBid {

    private int words;
    private String deadline;
    private String topic;
    private String anchor;
    private String vernacular;

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
