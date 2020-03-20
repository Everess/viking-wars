package org.viking.wars.exception;

/**
 * Exception for a non-existent island.
 */
public class IslandNotFoundException extends Exception {

    private String message;

    public IslandNotFoundException(String message) {
        super(message);
    }
}
