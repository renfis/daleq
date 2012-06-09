package de.brands4friends.daleq;

public class DaleqBuildException extends RuntimeException {

    public DaleqBuildException(final String message) {
        super(message);
    }

    public DaleqBuildException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DaleqBuildException(final Throwable cause) {
        super(cause);
    }
}
