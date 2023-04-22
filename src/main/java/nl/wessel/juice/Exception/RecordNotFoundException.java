package nl.wessel.juice.Exception;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException(Object object) {
        super("Could not find that object of type: " + "' " + object.getClass().getSimpleName() + " '");
    }
}
