package pl.warczynski.jedrzej.backend.Exceptions.signedUp;

public abstract class AbstractSignedUpException extends IllegalStateException{

    public AbstractSignedUpException(String message) {
        super(message);
    }
}
