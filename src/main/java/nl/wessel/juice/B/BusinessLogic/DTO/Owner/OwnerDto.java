package nl.wessel.juice.B.BusinessLogic.DTO.Owner;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreatedDeal;
import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreatedDomain;
import nl.wessel.juice.B.BusinessLogic.Model.Authority;

import java.util.List;
import java.util.Set;

public class OwnerDto {

    public String username;
    public String password;
    public Boolean enabled;
    public String apikey;
    public String email;
    @JsonSerialize
    public Set<Authority> authorities;


    private List<CreatedDeal> deals;
    private List<CreatedDomain> domains;

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

    public List<CreatedDomain> getDomains() {
        return domains;
    }

    public void setDomains(List<CreatedDomain> domains) {
        this.domains = domains;
    }
}
