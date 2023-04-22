package nl.wessel.juice.Exception;

public class ExpiredJWTException extends RuntimeException {
    public ExpiredJWTException() {
        super("Your JWT token has expired.");
    }
}