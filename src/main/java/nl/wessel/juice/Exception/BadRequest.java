package nl.wessel.juice.Exception;

public class BadRequest extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BadRequest() {
        super();
    }

    public BadRequest(String message) {
        super(message);
    }
}
