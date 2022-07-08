package nl.wessel.juice.B.BusinessLogic.DTO.Publisher;

public class CreatePublisher {
    private String name;
    private String country;
    private String niche;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNiche() {
        return niche;
    }

    public void setNiche(String niche) {
        this.niche = niche;
    }

}