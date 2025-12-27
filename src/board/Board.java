/*
 * Represents the game board in the Sea Battle game.
 * Responsible for:
 *  - Maintaining the grid of cells
 *  - Enforcing ship placement rules
 *  - Enforcing attack rules
 *  - Providing controlled board views for display
 *
 * This class does not handle user input or game flow.
 */


package board;

import console.ConsoleColors;
import console.ConsoleSymbols;
import exceptions.CellAlreadyHitException;
import exceptions.InvalidCoordinateException;
import exceptions.ShipPlacementException;
import ships.Ship;

public class Board {

    private final int size;
    private Cell[][] grid;
    
    private int lastHitRow = -1;
    private int lastHitCol = -1;


    public Board(int size) {
        this.size = size;
        grid = new Cell[size][size];
        
        //initializing Board
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new Cell();
            }
        }
    }

//==================================== SHIP PLACEMENT =================================

    public void placeShip(Ship ship, int row, int col, char direction) throws ShipPlacementException  {

        int shipSize = ship.getSize();
        direction = Character.toUpperCase(direction);

        // 1️ Direction validation
        if (direction != 'H' && direction != 'V')
            throw new ShipPlacementException("Invalid direction. Use H for Horizontal or V for Vertical.");

        // 2️ Boundary validation
        if (direction == 'H') {
            if (col < 0 || col + shipSize > size || row < 0 || row >= size)
                throw new ShipPlacementException("Ship goes out of board horizontally.");
        } else {
            if (row < 0 || row + shipSize > size || col < 0 || col >= size) 
                throw new ShipPlacementException("Ship goes out of board vertically.");
        }

        // 3️ Collision validation
        for (int i = 0; i < shipSize; i++) {
            int r = row + (direction == 'V' ? i : 0);
            int c = col + (direction == 'H' ? i : 0);

            if (grid[r][c].hasShip()) {
                throw new ShipPlacementException(
                    "Ship placement overlaps with another ship."
                );
            }
        }

        // 4️ Place ship
        for (int i = 0; i < shipSize; i++) {
            int r = row + (direction == 'V' ? i : 0);
            int c = col + (direction == 'H' ? i : 0);

            grid[r][c].placeShip(ship);
        }
    }

    
//=========================================== ATTACK LOGIC =====================================

    public Ship attackCell(int row, int col) throws CellAlreadyHitException, InvalidCoordinateException{

    	//out of board coordinates
    	if (row < 0 || row >= size || col < 0 || col >= size) 
            throw new InvalidCoordinateException("Invalid coordinates. Please enter values within the board.");
    	
        Cell cell = grid[row][col];
        
        //multiple hits on same cell
        if (cell.isHit())
            throw new CellAlreadyHitException("This cell has already been attacked. Try a different coordinate.");

        cell.markHit();
        
        lastHitRow = row;
        lastHitCol = col;

        if (cell.hasShip()) {
            Ship ship = cell.getShip();
            ship.registerHit();
            return ship;
        }

        return null; // miss
    }

//====================== DISPLAY LOGIC ======================

    // What opponent sees

//    public void displayHiddenBoard() {
//
//    	System.out.println(getHiddenBoardHeader());
//    	System.out.println(getTopBorder());
//
//    	for (int i = 0; i < size; i++) {
//    		System.out.println(getHiddenBoardRow(i));
//    	    if (i < size - 1) {
//               System.out.println(getMiddleBorder());
//   	        }
//   	    }
//
//        System.out.println(getBottomBorder());
//    }





    // What owner sees
    public void displayOwnBoard() {

        System.out.println(getOwnBoardHeader());
        System.out.println(getTopBorder());

        for (int i = 0; i < size; i++) {
            System.out.println(getOwnBoardRow(i));
            if (i < size - 1) {
                System.out.println(getMiddleBorder());
            }
        }

        System.out.println(getBottomBorder());
    }

    private boolean isLastHit(int row, int col) {
        return row == lastHitRow && col == lastHitCol;
    }
    
    
    
 // =================RENDERING HELPERS =================

    public String getOwnBoardHeader() {
        StringBuilder sb = new StringBuilder("    ");
        for (int c = 0; c < size; c++) {
            sb.append((char) ('A' + c)).append("   ");
        }
        return sb.toString();
    }

    public String getHiddenBoardHeader() {
        return getOwnBoardHeader();
    }

//    public String getTopBorder() {
//        StringBuilder sb = new StringBuilder("  ┌");
//        for (int c = 0; c < size; c++) {
//            sb.append("───");
//            if (c < size - 1) sb.append("┬");
//        }
//        sb.append("┐");
//        return sb.toString();
//    }
//
//    public String getMiddleBorder() {
//        StringBuilder sb = new StringBuilder("  ├");
//        for (int c = 0; c < size; c++) {
//            sb.append("───");
//            if (c < size - 1) sb.append("┼");
//        }
//        sb.append("┤");
//        return sb.toString();
//    }
//
//    public String getBottomBorder() {
//        StringBuilder sb = new StringBuilder("  └");
//        for (int c = 0; c < size; c++) {
//            sb.append("───");
//            if (c < size - 1) sb.append("┴");
//        }
//        sb.append("┘");
//        return sb.toString();
//    }
//
//    public String getOwnBoardRow(int row) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(row).append(" │");
//
//        for (int col = 0; col < size; col++) {
//            Cell cell = grid[row][col];
//
//            if (cell.hasShip()) 
//            {
//                if (cell.isHit()) 
//                {
//                	if (isLastHit(row, col)) 
//                		sb.append(" ").append("\u001B[91m■\u001B[0m").append(" │"); // LAST HIT
//                	else 
//                    sb.append(" ").append("■").append(" │"); // normal hit
//                } 
//                else
//                {
//                    sb.append(" ").append(cell.getShip().getSymbol()).append(" │");
//                }
//            } 
//            else 
//            {
//            	if(cell.isHit())
//            	{
//            		if (isLastHit(row, col)) 
//                		sb.append(" ").append("\u001B[91mX\u001B[0m").append(" │"); // LAST HIT
//                	else
//                		sb.append(" X │");
//            	}
//            	else sb.append(" ~ │");
//            }
//        }
//        return sb.toString();
//    }
//
//    public String getHiddenBoardRow(int row) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(row).append(" │");
//
//        for (int col = 0; col < size; col++) {
//            Cell cell = grid[row][col];
//            String symbol;
//
//            if (!cell.isHit()) 
//            {
//                symbol = "~";
//            } 
//            else if (cell.hasShip()) 
//            {
//            	
//            	if(isLastHit(row,col))
//            		symbol = cell.getShip().isDestroyed() ? "\u001B[91m■\u001B[0m" : "\u001B[91m●\u001B[0m";
//            	else
//            		symbol = cell.getShip().isDestroyed() ? cell.getShip().getSymbol() : "●";
//                
//            } 
//            else 
//            {
//            	if(isLastHit(row,col)) symbol="\u001B[91mX\u001B[0m";
//            	else symbol = "X";
//            }
//
//            sb.append(" ").append(symbol).append(" │");
//        }
//        return sb.toString();
//    }
    
    public String getTopBorder() {
        StringBuilder sb = new StringBuilder("  ").append(ConsoleSymbols.TOP_LEFT);

        for (int c = 0; c < size; c++) {
            sb.append(ConsoleSymbols.HORIZONTAL).append(ConsoleSymbols.HORIZONTAL).append(ConsoleSymbols.HORIZONTAL);

            if (c < size - 1) sb.append(ConsoleSymbols.T_TOP);
        }

        sb.append(ConsoleSymbols.TOP_RIGHT);
        return sb.toString();
    }
    
    
    public String getMiddleBorder() {
        StringBuilder sb = new StringBuilder("  ").append(ConsoleSymbols.T_LEFT);

        for (int c = 0; c < size; c++) {
            sb.append(ConsoleSymbols.HORIZONTAL).append(ConsoleSymbols.HORIZONTAL).append(ConsoleSymbols.HORIZONTAL);

            if (c < size - 1) sb.append(ConsoleSymbols.CROSS);
        }

        sb.append(ConsoleSymbols.T_RIGHT);
        return sb.toString();
    }
    
    
    public String getBottomBorder() {
        StringBuilder sb = new StringBuilder("  ").append(ConsoleSymbols.BOTTOM_LEFT);

        for (int c = 0; c < size; c++) {
            sb.append(ConsoleSymbols.HORIZONTAL).append(ConsoleSymbols.HORIZONTAL).append(ConsoleSymbols.HORIZONTAL);

            if (c < size - 1) sb.append(ConsoleSymbols.T_BOTTOM);
        }

        sb.append(ConsoleSymbols.BOTTOM_RIGHT);
        return sb.toString();
    }
    
    
    public String getOwnBoardRow(int row) {
        StringBuilder sb = new StringBuilder();
        sb.append(row).append(" ").append(ConsoleSymbols.VERTICAL);

        for (int col = 0; col < size; col++) {
            Cell cell = grid[row][col];
            String symbol;

            if (cell.hasShip()) {
                if (cell.isHit()) {
                    symbol = isLastHit(row, col)
                            ? ConsoleColors.BRIGHT_RED + ConsoleSymbols.SOLID_SQUARE + ConsoleColors.RESET
                            : ConsoleSymbols.SOLID_SQUARE;
                } else {
                    symbol = cell.getShip().getSymbol();
                }
            } else {
                if (cell.isHit()) {
                    symbol = isLastHit(row, col)
                            ? ConsoleColors.BRIGHT_RED + ConsoleSymbols.MISS_X + ConsoleColors.RESET
                            : ConsoleSymbols.MISS_X;
                } else {
                    symbol = ConsoleSymbols.SEA_WAVE;
                }
            }

            sb.append(" ").append(symbol).append(" ").append(ConsoleSymbols.VERTICAL);
        }

        return sb.toString();
    }
    public String getHiddenBoardRow(int row) {
        StringBuilder sb = new StringBuilder();
        sb.append(row).append(" ").append(ConsoleSymbols.VERTICAL);

        for (int col = 0; col < size; col++) {
            Cell cell = grid[row][col];
            String symbol;

            if (!cell.isHit()) {
                symbol = ConsoleSymbols.SEA_WAVE;
            }
            else if (cell.hasShip()) {
                if (isLastHit(row, col)) {
                    symbol = cell.getShip().isDestroyed()
                            ? ConsoleColors.BRIGHT_RED + ConsoleSymbols.SOLID_SQUARE + ConsoleColors.RESET
                            : ConsoleColors.BRIGHT_RED + ConsoleSymbols.HIT_CIRCLE + ConsoleColors.RESET;
                } else {
                    symbol = cell.getShip().isDestroyed()
                            ? cell.getShip().getSymbol()
                            : ConsoleSymbols.HIT_CIRCLE;
                }
            }
            else {
                symbol = isLastHit(row, col)
                        ? ConsoleColors.BRIGHT_RED + ConsoleSymbols.MISS_X + ConsoleColors.RESET
                        : ConsoleSymbols.MISS_X;
            }

            sb.append(" ").append(symbol).append(" ").append(ConsoleSymbols.VERTICAL);
        }

        return sb.toString();
    }




}
