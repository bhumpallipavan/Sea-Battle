/*
 * Represents a Destroyer ship in the Sea Battle game.
 * A Destroyer occupies 2 consecutive cells on the board.
 */

package ships;

import console.ConsoleColors;

public class DestroyerShip extends Ship{
	
	public DestroyerShip() {
        super(2);
    }

    @Override
    public String getSymbol() {
        return ConsoleColors.BRIGHT_CYAN+'■'+ConsoleColors.RESET;
    }

}
