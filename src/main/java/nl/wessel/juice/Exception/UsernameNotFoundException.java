package nl.wessel.juice.Exception;

public class UsernameNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UsernameNotFoundException(String username) {
        super("Could not find this username: " + username);
    }

}