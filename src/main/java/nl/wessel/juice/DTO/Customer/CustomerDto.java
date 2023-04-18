package nl.wessel.juice.DTO.Customer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nl.wessel.juice.Model.Authority;

import java.util.List;
import java.util.Set;

public class CustomerDto {

    private String username;
    private String password;

    @JsonSerialize
    private Set<Authority> authorities;

    private List<Long> bidIDs;

    private List<Long> dealIDs;

    public List<Long> getDealIDs() {
        return dealIDs;
    }

    public void setDealIDs(List<Long> dealIDs) {
        this.dealIDs = dealIDs;
    }

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

    public List<Long> getBidIDs() {
        return bidIDs;
    }

    public void setBidIDs(List<Long> bidIDs) {
        this.bidIDs = bidIDs;
    }
}