package nl.wessel.juice.DTO.Customer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nl.wessel.juice.Model.Authority;

import java.util.List;
import java.util.Set;

public class PublicCustomerDto {

    private String username;
    private List<Long> bidIDs;
    private List<Long> dealIDs;
    private Set<Authority> authorities;


    public List<Long> getDealIDs() {
        return dealIDs;
    }

    public void setDealIDs(List<Long> dealIDs) {
        this.dealIDs = dealIDs;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Long> getBidIDs() {
        return bidIDs;
    }

    public void setBidIDs(List<Long> bidIDs) {
        this.bidIDs = bidIDs;
    }
}
