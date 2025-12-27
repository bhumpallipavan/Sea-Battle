/*
 * Thrown when user input does not match
 * the expected format (e.g., column or direction input).
 */

package exceptions;

public class InvalidInputFormatException extends Exception {

    public InvalidInputFormatException(String message) {
        super(message);
    }
}
