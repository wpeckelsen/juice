package nl.wessel.juice.Security.Payload;

public class AuthenticationRequest {


    private String username;
    private String password;
    private String type;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

}