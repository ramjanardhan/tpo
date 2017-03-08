package com.mss.tpo.util;

/**
 *
 * @author Narendar
 */
public class ServiceLocatorException extends Exception {

    private static final long serialVersionUID = 1L;

    public ServiceLocatorException() {
        super();
    }

    /**
     * @param message
     */
    public ServiceLocatorException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public ServiceLocatorException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause
     */
    public ServiceLocatorException(Throwable cause) {
        super(cause);
    }
}
