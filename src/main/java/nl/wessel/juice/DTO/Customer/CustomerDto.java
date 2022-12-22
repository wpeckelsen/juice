package nl.wessel.juice.DTO.Customer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nl.wessel.juice.DTO.Bid.CreatedBid;
import nl.wessel.juice.DTO.Deal.CreatedDeal;
import nl.wessel.juice.Model.Authority;
import nl.wessel.juice.Model.Bid;
import nl.wessel.juice.Model.Deal;

import java.util.List;
import java.util.Set;

public class CustomerDto {

    public String username;
    public String password;
    public Boolean enabled;
    public String apikey;
    public String email;
    @JsonSerialize
    public Set<Authority> authorities;


    public List<CreatedDeal> deals;
    public List<CreatedBid> bids;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public List<CreatedDeal> getDeals() {
        return deals;
    }

    public void setDeals(List<CreatedDeal> deals) {
        this.deals = deals;
    }

    public List<CreatedBid> getBids() {
        return bids;
    }

    public void setBids(List<CreatedBid> bids) {
        this.bids = bids;
    }
}