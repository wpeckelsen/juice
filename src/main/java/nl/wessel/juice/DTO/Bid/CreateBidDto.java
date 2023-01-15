package nl.wessel.juice.DTO.Bid;

public class CreateBidDto {

    public int words;
    public String deadline;
    public String topic;
    public String anchor;
    public String vernacular;

    public int getWords() {
        return words;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getTopic() {
        return topic;
    }

    public String getAnchor() {
        return anchor;
    }

    public String getVernacular() {
        return vernacular;
    }
}
