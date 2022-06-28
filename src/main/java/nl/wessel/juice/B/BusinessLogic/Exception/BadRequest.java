package nl.wessel.juice.B.BusinessLogic.Exception;

public class BadRequest extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BadRequest() {
        super();
    }

    public BadRequest(String message) {
        super(message);
    }
}
