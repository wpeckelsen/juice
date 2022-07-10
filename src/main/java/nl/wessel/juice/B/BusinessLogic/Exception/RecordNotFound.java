package nl.wessel.juice.B.BusinessLogic.Exception;

public class RecordNotFound extends RuntimeException {

    public RecordNotFound(Object object) {
        super("Error: could not find that object of type: " + "' " + object.getClass().getSimpleName() + " '");
    }
}
