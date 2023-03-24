package nl.wessel.juice.Exception;

public class UsernameNotFound extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UsernameNotFound(String username) {
        super("Could not find this username: " + username);
    }

}