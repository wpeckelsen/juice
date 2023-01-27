package nl.wessel.juice.DTO.Deal;


public class CreateDealDto {


    private String deadline;
    private String paymentType;
    private String terms;
    private int price;


    public String getDeadline() {
        return deadline;
    }

//    public void setDeadline(String deadline) {
//        this.deadline = deadline;
//    }

    public String getPaymentType() {
        return paymentType;
    }

//    public void setPaymentType(String paymentType) {
//        this.paymentType = paymentType;
//    }

    public String getTerms() {
        return terms;
    }

//    public void setTerms(String terms) {
//        this.terms = terms;
//    }

    public int getPrice() {
        return price;
    }

//    public void setPrice(int price) {
//        this.price = price;
//    }
}
