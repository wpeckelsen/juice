package nl.wessel.juice.B.BusinessLogic.DTO.Client;

import nl.wessel.juice.B.BusinessLogic.Model.Deal;
import nl.wessel.juice.B.BusinessLogic.Model.Market;
import nl.wessel.juice.B.BusinessLogic.Model.Order;

import java.util.Collection;
import java.util.List;

public class CreatedClient {
    private Long clientID;
    private String name;
    private String email;
    private String password;

//    TODO variables
    private Collection<Deal> deals;

    private List<Order> orders;
    private List<Market> markets;






    public Long getClientID() {
        return clientID;
    }
    public void setClientID(Long clientID) {
        this.clientID = clientID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Collection<Deal> getDeals() {
        return deals;
    }
    public void setDeals(Collection<Deal> deals) {
        this.deals = deals;
    }
    public List<Order> getOrders() {
        return orders;
    }
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    public List<Market> getMarkets() {
        return markets;
    }
    public void setMarkets(List<Market> markets) {
        this.markets = markets;
    }
}
