package nl.wessel.juice.Exception;

public class ExpiredJWT extends RuntimeException {
    public ExpiredJWT() {
        super("Your JWT token has expired.");
    }
}