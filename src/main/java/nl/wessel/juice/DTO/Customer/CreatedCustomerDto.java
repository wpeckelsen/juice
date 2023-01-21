package nl.wessel.juice.DTO.Customer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nl.wessel.juice.DTO.Deal.CreatedDealDto;
import nl.wessel.juice.Model.Authority;

import java.util.List;
import java.util.Set;

public class CreatedCustomerDto {

    private String username;
    private String password;

    @JsonSerialize
    private Set<Authority> authorities;

    private List<CreatedDealDto> deals;
    private List<Long> bidIDs;


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

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public List<CreatedDealDto> getDeals() {
        return deals;
    }

    public void setDeals(List<CreatedDealDto> deals) {
        this.deals = deals;
    }

    public List<Long> getBidIDs() {
        return bidIDs;
    }

    public void setBidIDs(List<Long> bidIDs) {
        this.bidIDs = bidIDs;
    }
}