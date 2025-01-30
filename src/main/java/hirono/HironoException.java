package hirono;

/**
 * Represents an exception specific to the Hirono application.
 * This exception is thrown when an error occurs during user input processing
 * or command execution within the application.
 */
public class HironoException extends Exception {

    /**
     * Constructs a new HironoException with the specified error message.
     *
     * @param message The detail message describing the nature of the error.
     */
    public HironoException(String message) {
        super(message); // Pass the error message to the superclass constructor
    }
}
