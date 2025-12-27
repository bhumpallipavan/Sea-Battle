/*
 * Thrown when a ship cannot be placed on the board
 * due to invalid position, collision, or direction.
 */

package exceptions;

public class ShipPlacementException extends Exception {

    public ShipPlacementException(String message) {
        super(message);
    }
}
