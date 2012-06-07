package de.brands4friends.daleq.jdbc.dbunit;

public class DaleqException extends RuntimeException {

    public DaleqException() {
    }

    public DaleqException(final String message) {
        super(message);
    }

    public DaleqException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DaleqException(final Throwable cause) {
        super(cause);
    }
}
