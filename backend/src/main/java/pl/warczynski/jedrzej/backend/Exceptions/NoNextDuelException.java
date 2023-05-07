package pl.warczynski.jedrzej.backend.Exceptions;

import java.util.NoSuchElementException;

public class NoNextDuelException extends NoSuchElementException {
    public NoNextDuelException() {
        super("There is no next duel. Probably it was final");
    }
}
