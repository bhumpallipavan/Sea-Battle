/*
 * Represents a Battleship in the Sea Battle game.
 * A Battleship occupies 4 consecutive cells on the board.
 */

package ships;

import console.ConsoleColors;

public class BattleShip extends Ship {
	public BattleShip() {
        super(4);
    }

    @Override
    public String getSymbol() {
        return ConsoleColors.BRIGHT_BLUE+'■'+ConsoleColors.RESET;
    }

}
