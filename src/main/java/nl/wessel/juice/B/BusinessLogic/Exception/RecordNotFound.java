package nl.wessel.juice.B.BusinessLogic.Exception;

public class RecordNotFound extends RuntimeException {

    public RecordNotFound(Object object) {
        super("Could not find that object of type: " + "' " + object.getClass().getSimpleName() + " '");
    }
}
