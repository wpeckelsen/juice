//package nl.wessel.juice.B.BusinessLogic.DTO.User;
//
//
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import nl.wessel.juice.B.BusinessLogic.Model.Authority;
//
//import java.util.Set;
//
//public class UserDto {
//
//    public String username;
//    public String password;
//    public Boolean enabled;
//    public String apikey;
//    public String email;
//    @JsonSerialize
//    public Set<Authority> authorities;
//
//    public String getUsername() {
//        return username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public Boolean getEnabled() {
//        return enabled;
//    }
//
//    public String getApikey() {
//        return apikey;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public Set<Authority> getAuthorities() {
//        return authorities;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public void setEnabled(Boolean enabled) {
//        this.enabled = enabled;
//    }
//
//    public void setApikey(String apikey) {
//        this.apikey = apikey;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void setAuthorities(Set<Authority> authorities) {
//        this.authorities = authorities;
//    }
//}