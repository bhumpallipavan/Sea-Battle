/*
 * Represents a single cell on the game board.
 * A cell may contain a ship and tracks whether it has been hit.
 *
 * This class acts as a lightweight wrapper around ship state
 * and does not contain game logic.
 */

package board;

import ships.Ship;

public class Cell {
	private Ship ship;      
    private boolean isHit;

    public Cell() {
        this.ship = null;
        this.isHit = false;
    }

    public boolean hasShip() {
        return ship != null;
    }

    public Ship getShip() {
        return ship;
    }

    public void placeShip(Ship ship) {
        this.ship = ship;
    }

    public boolean isHit() {
        return isHit;
    }

    public void markHit() {
        this.isHit = true;
    }

}
