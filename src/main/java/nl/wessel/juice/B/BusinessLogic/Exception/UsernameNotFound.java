package nl.wessel.juice.B.BusinessLogic.Exception;

public class UsernameNotFound extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UsernameNotFound(String username) {
        super("Error: could not find that " + username);
    }

}