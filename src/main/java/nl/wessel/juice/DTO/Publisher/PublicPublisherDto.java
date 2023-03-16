package nl.wessel.juice.DTO.Publisher;
import nl.wessel.juice.Model.Authority;

import java.util.List;
import java.util.Set;

public class PublicPublisherDto {

    public String username;
    private Set<Authority> authorities;
    private List<Long> dealIDs;
    private List<Long> domainIDs;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
