package nl.wessel.juice.Model;

import javax.persistence.*;

@Entity
public class Photo {


    @Id
    @GeneratedValue
    private Long photoID;

    private String fileName;

    @Lob
    private byte[] docFile;


    @OneToOne
    private Bid bid;


    public Long getPhotoID() {
        return photoID;
    }

    public void setPhotoID(Long photoID) {
        this.photoID = photoID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getDocFile() {
        return docFile;
    }

    public void setDocFile(byte[] docFile) {
        this.docFile = docFile;
    }

    public Bid getBid() {
        return bid;
    }

    public void setBid(Bid bid) {
        this.bid = bid;
    }
}
