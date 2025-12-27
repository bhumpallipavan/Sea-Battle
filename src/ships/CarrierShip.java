/*
 * Represents a Carrier ship in the Sea Battle game.
 * A Carrier occupies 5 consecutive cells on the board.
 */

package ships;

import console.ConsoleColors;

public class CarrierShip extends Ship{
	public CarrierShip() {
        super(5);
    }

    @Override
    public String getSymbol() {
        return ConsoleColors.BRIGHT_PURPLE+'■'+ConsoleColors.RESET;
    }

}
