/*
 * Thrown when a player attempts to attack a cell
 * that has already been attacked.
 */


package exceptions;

public class CellAlreadyHitException  extends Exception{
	
	public CellAlreadyHitException(String message)
	{
		super(message);
	}

}
