package nl.wessel.juice.DTO.Domain;

public class CreateDomainDto {


    private String name;
    private String TLD;
    private String category;
    private int price;




    public String getName() {
        return name;
    }

//    public void setName(String name) {
//        this.name = name;
//    }

    public String getTLD() {
        return TLD;
    }



    public String getCategory() {
        return category;
    }

//    public void setCategory(String category) {
//        this.category = category;
//    }

    public int getPrice() {
        return price;
    }

//    public void setPrice(int price) {
//        this.price = price;
//    }
}
