package nl.wessel.juice.DTO.Publisher;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nl.wessel.juice.Model.Authority;

import java.util.List;
import java.util.Set;

public class PublisherDto {

    public String username;
    public String password;

    @JsonSerialize
    private Set<Authority> authorities;


    private List<Long> dealIDs;
    private List<Long> domainIDs;


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

    public List<Long> getDealIDs() {
        return dealIDs;
    }

    public void setDealIDs(List<Long> dealIDs) {
        this.dealIDs = dealIDs;
    }

    public List<Long> getDomainIDs() {
        return domainIDs;
    }

    public void setDomainIDs(List<Long> domainIDs) {
        this.domainIDs = domainIDs;
    }
}
