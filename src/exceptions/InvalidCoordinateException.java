/*
 * Thrown when provided board coordinates
 * are outside the valid board range.
 */

package exceptions;

public class InvalidCoordinateException extends Exception {

    public InvalidCoordinateException(String message) {
        super(message);
    }
}
