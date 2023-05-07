package pl.warczynski.jedrzej.backend.Exceptions;

public class InconsistentWinnerException extends IllegalStateException {
    public InconsistentWinnerException() {
        super("Results provided by users are inconsistent");
    }
}
