package nl.wessel.juice.Exception;

public class RecordNotFound extends RuntimeException {

    public RecordNotFound(Object object) {
        super("Could not find that object of type: " + "' " + object.getClass().getSimpleName() + " '");
    }
}
