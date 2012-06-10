package de.brands4friends.daleq.internal.dbunit;

public class DaleqException extends RuntimeException {

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
