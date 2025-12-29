/*
 * Represents a player in the Sea Battle game.
 * Responsible for:
 *  - Managing the player's board and fleet
 *  - Handling user input for ship placement and attacks
 *  - Tracking hits and destroyed ships
 *
 * Game flow control is handled by Main,
 * and board rules are enforced by Board.
 */


package player;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import board.Board;
import console.ConsoleSymbols;
import exceptions.CellAlreadyHitException;
import exceptions.InvalidCoordinateException;
import exceptions.InvalidInputFormatException;
import exceptions.ShipPlacementException;
import ships.Ship;

public class Player {

    private String name;
    private Board board;
    private List<Ship> ships;
    private int hitCount;
    private int shipsDestroyed;
    private Scanner sc;

    public Player(String name, int boardSize) {
        this.name = name;
        this.board = new Board(boardSize);
        this.ships = new ArrayList<>();
        this.hitCount = 0;
        this.shipsDestroyed = 0;
        this.sc = new Scanner(System.in);
    }

//===================== BASIC GETTERS SETTERS NEEDED =================

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name=name;
    }
    public int getHitCount() {
        return hitCount;
    }

    public int getShipsDestroyed() {
        return shipsDestroyed;
    }

    public boolean hasWon() {
        return shipsDestroyed == ships.size();
    }

    public void addShip(Ship ship) {
        ships.add(ship);
    }

//=======SHIPS SETUP==============
    public void placeAllShips() {
        System.out.println("\n" + name + " - Place your ships");

        for (Ship ship : ships) {

        	displayOwnBoard();
        	//while true takes input until the inputs are valid
            while (true) {
                

                System.out.println("Enter the starting correct coordinates for Placing " + ship.getClass().getSimpleName() +" (size " + ship.getSize() + ")");

                try
                {
                	int row = getRowInput();
                    int col = getColumnInput() - 'A';
                    char direction = getDirectionInput();
                    
                	board.placeShip(ship, row, col, direction);
                	break;
                }
                catch(ShipPlacementException e)
                {
                	System.out.println(e.getMessage());
                	System.out.println("Please Try Again ");
                }
                catch(InputMismatchException e)
                {
                	System.out.println("Enter integers between 0 and 9 please");
                	sc.nextLine();
                }
                catch(InvalidInputFormatException e)
                {
                	System.out.println(e.getMessage());
                }
            }
        }
    }


//========================= ATTACK FLOW ====================

    public void takeAttackTurn(Player opponent) {
    	printTurnBanner(name);
        //opponent.displayBoardToOpponent();
    	displayBoardsSideBySide(opponent);
        Ship hitShip;
      //while true takes input until the inputs are valid
        while(true)
        {
        	try
        	{
        		int row = getRowInput();
                int col = getColumnInput()-'A';

                hitShip = opponent.receiveAttack(row, col);

                
                break;
                
        	}
        	catch(CellAlreadyHitException | InvalidCoordinateException e)
        	{
        		printMessageForException(e);
        	}
        	catch(InputMismatchException e)
        	{
        		System.out.println("Enter integers between 0 and 9 please");
        		//to free the taken input and not go into infinite loop
        		sc.nextLine();
        	}
        	catch(InvalidInputFormatException e)
            {
            	System.out.println(e.getMessage());
            }
        }
        //opponent.displayBoardToOpponent();
        
        displayBoardsSideBySide(opponent);
     
        if (hitShip != null) {

            handleSuccessfulHit(hitShip);
            System.out.println();
            System.out.println("    "+ ConsoleSymbols.FULL_BLOCK.repeat(31));
            System.out.println("       "+ ConsoleSymbols.EXPLOSION + "  HIT CONFIRMED  "+ ConsoleSymbols.EXPLOSION);
            System.out.println("    "+ ConsoleSymbols.FULL_BLOCK.repeat(31));

        } else {

            System.out.println();
            System.out.println("    "+ ConsoleSymbols.HORIZONTAL.repeat(31));
            System.out.println("             "+ ConsoleSymbols.CROSS_MARK + "  MISS  "+ ConsoleSymbols.CROSS_MARK);
            System.out.println("    "+ ConsoleSymbols.HORIZONTAL.repeat(31));
        }

        displayPoints();
    }

    // Opponent attacks THIS player
    public Ship receiveAttack(int row, int col) throws CellAlreadyHitException, InvalidCoordinateException{
        return board.attackCell(row, col);
    }

    private void handleSuccessfulHit(Ship ship) {
        hitCount++;

        if (ship.isDestroyed()) {
            shipsDestroyed++;
            System.out.println("You destroyed a " +
                    ship.getClass().getSimpleName());
        }
    }

  //======================== DISPLAY METHODS ================================

    // Only the owner can see full board
    public void displayOwnBoard() {
        board.displayOwnBoard();
    }

    // What opponent is allowed to see
    public void displayBoardToOpponent() {
        board.displayHiddenBoard();
    }
    
    public void displayPoints() {
        System.out.println("    ┌───────────────────────────────┐");
        System.out.println("    │            SCOREBOARD         │");
        System.out.println("    ├─────────────────────┬─────────┤");
        System.out.println("    │ Total Hits          │   " + hitCount + "     │");
        System.out.println("    │ Ships Destroyed     │   " + shipsDestroyed + "     │");
        System.out.println("    └─────────────────────┴─────────┘");
    }
    
    public void displayBoardsSideBySide(Player opponent) {

        Board own = this.board;
        Board enemy = opponent.board;

        System.out.println();
        System.out.println("        YOUR BOARD                                      OPPONENT BOARD");
        System.out.println();

        // Headers
        System.out.println(own.getOwnBoardHeader() + "        " + enemy.getHiddenBoardHeader());

        // Top borders
        System.out.println(own.getTopBorder() + "        " + enemy.getTopBorder());

        // Rows
        for (int row = 0; row < 10; row++) {

            System.out.println(own.getOwnBoardRow(row) + "        " + enemy.getHiddenBoardRow(row));

            if (row < 9) {
                System.out.println(own.getMiddleBorder() + "        " + enemy.getMiddleBorder());
            }
        }

        // Bottom borders
        System.out.println(own.getBottomBorder() + "        " + enemy.getBottomBorder());

        System.out.println();
    }

//====================== INPUT METHODS =====================

    protected int getRowInput() throws InputMismatchException{
        System.out.print("Enter row: ");
        
        int x = sc.nextInt();
        sc.nextLine();
        
        return x;
    }

    protected char getColumnInput() throws InvalidInputFormatException {
    	while(true)
    	{
    		try
    		{
    			System.out.print("Enter column: ");
    	        String input=sc.nextLine().trim();
    	        
    	        if(input.length()!=1)
    	        	throw new InvalidInputFormatException("Column must be a single character (A-J)");
    	        
    	        char ch = Character.toUpperCase(input.charAt(0));
    	        
    	        if(ch<'A'||ch>'J')
    	        	throw new InvalidInputFormatException("Column must be between A-J, no other symbols");
    	        
    	        return ch;
    		}
    		catch(InvalidInputFormatException e)
    		{
    			System.out.println(e.getMessage());
    		}
    	}
        
        
    }

    private char getDirectionInput() throws InvalidInputFormatException{
    	while (true) {
    		try {
    			System.out.print("Enter direction (H/V): ");
    			String input = sc.nextLine().trim();

    			if (input.length() != 1)
    				throw new InvalidInputFormatException("Direction must be a single character (H or V).");

    			char ch = Character.toUpperCase(input.charAt(0));

    			if (ch != 'H' && ch != 'V')
    				throw new InvalidInputFormatException("Invalid direction. Use H or V.");
            
    			return ch;
    		} 
    		catch (InvalidInputFormatException e) {
    			System.out.println(e.getMessage());
            
    		}
    	}
    }
    
    public void printTurnBanner(String name) {
        System.out.println();
        System.out.println(       "==============================================================");
        System.out.println(       "||                "+ConsoleSymbols.FIRE+"  " + name.toUpperCase() + "'S TURN  "+ConsoleSymbols.FIRE+"                       ||");
        System.out.println(       "==============================================================");
        //System.out.println();
    }
    
 // TEMPORARY – for testing without manual placement
    public void placeShipDirectly(Ship ship, int row, int col, char direction) throws ShipPlacementException  {
        board.placeShip(ship, row, col, direction);
    }
    
 //HELPER METHOD   
    public void printMessageForException(Exception e)
    {
    	System.out.println(e.getMessage());
		System.out.println("Please enter new coordinates");
    }
    
    


}
