/*
 * Abstract base class representing a ship in the Sea Battle game.
 *
 * Each ship:
 *  - Has a fixed size
 *  - Tracks the number of hits taken
 *  - Determines when it is destroyed
 *
 * Concrete ship types define their own size and display symbol.
 */

package ships;

public abstract class Ship {
	
	protected final int size;
    protected int hitsTaken;

    protected Ship(int size) {
        this.size = size;
        this.hitsTaken = 0;
    }

    public int getSize() {
        return size;
    }

    public void registerHit() {
        hitsTaken++;
    }
    
    public int getHitCount()
    {
    	return hitsTaken;
    }

    public boolean isDestroyed() {
        return hitsTaken >= size;
    }

    // Each ship will have its own display symbol
    public abstract String getSymbol();

}
