/*
 * Represents a Submarine ship in the Sea Battle game.
 * A Submarine occupies 3 consecutive cells on the board.
 */

package ships;

import console.ConsoleColors;

public class Submarine extends Ship{
	public Submarine() {
        super(3);
    }

    @Override
    public String getSymbol() {
        return ConsoleColors.BRIGHT_GREEN+'■'+ConsoleColors.RESET;
    }

}
