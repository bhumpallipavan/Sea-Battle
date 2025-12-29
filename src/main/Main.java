/*
 * Entry point of the Sea Battle game.
 * Responsible only for:
 *  - Displaying rules
 *  - Creating players
 *  - Initializing ships
 *  - Running the game loop
 *
 * Game logic is delegated to Player and Board classes.
 */


package main;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import console.ConsoleColors;
import console.ConsoleSymbols;
import exceptions.ShipPlacementException;
import player.AIPlayer;
import player.Player;

import ships.DestroyerShip;
import ships.Ship;
import ships.Submarine;
import ships.BattleShip;
import ships.CarrierShip;

public class Main {

    private static final int BOARD_SIZE = 10;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        boolean MANUAL_PLACEMENT = true;

        displayRules();
//============================================================ASK FOR NO OF PLAYERS PLAYING=======================================================
        
        int choice = getChoice(sc);

//============================================================ PLAYER 1 SETUP ========================================================================
        
        String p1Name;
        
        //while true takes input until the inputs are valid.
        while(true)
        {
        	System.out.print("\nEnter Player 1 name: ");
        	p1Name = sc.nextLine();
        	
        	if(p1Name.isBlank())
        		System.out.println("Enter valid name, not just spaces");
        	else
        		break;
        }

        Player player1 = new Player(p1Name, BOARD_SIZE);
        addShips(player1);
        
        if (MANUAL_PLACEMENT) {
            player1.placeAllShips();
        } else {
            setupRandomBoard(player1);
        }

        System.out.println("\nFinal board for " + p1Name + ":");
        player1.displayOwnBoard();
        pause(sc);

//================================================================ PLAYER 2 SETUP ====================================================================
        
        Player player2;
        String p2Name="" ;
        
        if (choice == 1) {
            // Human vs Human
        	//while true takes input until the inputs are valid.
            while(true)
            {
            	System.out.print("\nEnter Player 2 name: ");
            	p2Name= sc.nextLine();
            	
            	if(p2Name.isBlank())
            		System.out.println("Enter valid name, not just spaces");
            	else
            		break;
            }

            player2 = new Player(p2Name, BOARD_SIZE);
            addShips(player2);
            
            if (MANUAL_PLACEMENT) {
                player2.placeAllShips();
            } else {
                setupRandomBoard(player2);
            }
            System.out.println("\nFinal board for " + p2Name + ":");
            player2.displayOwnBoard();
            
            

        } else {
            // Human vs Computer
            System.out.println("\nYou are playing against the COMPUTER!");

            player2 = new AIPlayer("COMPUTER", BOARD_SIZE);
            addShips(player2);

            // use random ship placement for AI
            setupRandomBoard(player2);
        }

        
        
        pause(sc);
        
//====================================================================================================================================================        
        
        //adding suffixes if names are same.
        if(p1Name.equals(p2Name))
        {
        	System.out.println("As the names are same adding 1 and 2 at the end to differentiate");
        	player1.setName(p1Name+"1");
        	player2.setName(p2Name+"2");
        }
        
        //displaying names of the players:
        System.out.println("Player 1: "+player1.getName());
        System.out.println("Player 2: "+player2.getName());

// =================================================================== GAME LOOP =====================================================================
        
        
        System.out.println(ConsoleSymbols.FULL_BLOCK.repeat(62));
        System.out.println(ConsoleSymbols.FULL_BLOCK +"                                                            " +ConsoleSymbols.FULL_BLOCK);
        System.out.println(ConsoleSymbols.FULL_BLOCK +"                B A T T L E   B E G I N S                   " +ConsoleSymbols.FULL_BLOCK);
        System.out.println(ConsoleSymbols.FULL_BLOCK +"                                                            " +ConsoleSymbols.FULL_BLOCK);
        System.out.println(ConsoleSymbols.FULL_BLOCK.repeat(62));


        //this runs until a winner is declared
        //alternative attacks
        while (true) {

            player1.takeAttackTurn(player2);
            if (player1.hasWon()) {
            	displayVictoryScreen(player1.getName(),player1.getHitCount(),player1.getShipsDestroyed());
                break;
            }
            pause(sc);


            player2.takeAttackTurn(player1);
            if (player2.hasWon()) {
            	displayVictoryScreen(player2.getName(),player2.getHitCount(),player2.getShipsDestroyed());
                break;
            }
            pause(sc);
        }

    }

//========================================================================== HELPER METHODS ==========================================================

    //============RULES===============
    public static void displayRules() {
    	
    	System.out.println("                 |\\");
    	System.out.println("                 | \\");
    	System.out.println("                 |  \\");
    	System.out.println("            _____|___\\_____                           ==============================================================");
    	System.out.println("___________/________________\\_____________            ||                                                          ||");
    	System.out.println("\\_________________________________________/           ||                  S E A   B A T T L E                     ||");
    	System.out.println(" \\                                       /            ||                                                          ||");
    	System.out.println("~~\\_____________________________________/~~~~~~       ==============================================================");
    	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    	System.out.println();


    	System.out.println("---------------------------------------------------------------");
    	System.out.println("|                          RULES                             |            --------------------------------------------------------------");
    	System.out.println("---------------------------------------------------------------           |                     SHIP DETAILS                            |");
    	System.out.println("| Board Size        | 10 x 10                                |            --------------------------------------------------------------");
    	System.out.println("| Players           | 2                                      |            | Ship Type     | Size | Count per Player | Symbol            |");
    	System.out.println("| Turns             | Alternate                              |            --------------------------------------------------------------");
    	System.out.println("| Hit Symbol        | o                                      |            | Destroyer     |  2   |        1         |   "
    	        + ConsoleColors.BRIGHT_CYAN + ConsoleSymbols.SOLID_SQUARE + ConsoleColors.RESET + "               |");
    	System.out.println("| Miss Symbol       | x                                      |            | Submarine     |  3   |        2         |   "
    	        + ConsoleColors.BRIGHT_GREEN + ConsoleSymbols.SOLID_SQUARE + ConsoleColors.RESET + "               |");
    	System.out.println("| Sea Symbol        | ~                                      |            | Battleship    |  4   |        1         |   "
    	        + ConsoleColors.BRIGHT_BLUE + ConsoleSymbols.SOLID_SQUARE + ConsoleColors.RESET + "               |");
    	System.out.println("| Destroyed Ship    | Revealed with ship symbol              |            | Carrier       |  5   |        1         |   "
    	        + ConsoleColors.BRIGHT_PURPLE + ConsoleSymbols.SOLID_SQUARE + ConsoleColors.RESET + "               |");
    	System.out.println("| Win Condition     | Destroy all opponent ships             |            --------------------------------------------------------------");
    	System.out.println("| Recent Attack     | Red color                              |");
    	System.out.println("--------------------------------------------------------------");
    	System.out.println();


    }

    //==========ADDING SHIPS TO PLAYER===========
    public static void addShips(Player player) {
        player.addShip(new DestroyerShip());
        player.addShip(new Submarine());
        player.addShip(new Submarine());
        player.addShip(new BattleShip());
        player.addShip(new CarrierShip());
    }
    
    
    //=============DEFAULT BOARDS MAKING===============
    public static void setupRandomBoard(Player player) {

        Random random = new Random();

        for (Ship ship : List.of(new DestroyerShip(),new Submarine(),new Submarine(),new BattleShip(),new CarrierShip())) 
        {

            boolean placed = false;

            while (!placed) {
                try {
                    int row = random.nextInt(10);
                    int col = random.nextInt(10);
                    char direction = random.nextBoolean() ? 'H' : 'V';

                    player.placeShipDirectly(ship, row, col, direction);
                    placed = true;

                } catch (ShipPlacementException ignored) {
                    // Retry silently until a valid position is found
                }
            }
        }
    }

    
    public static void pause(Scanner sc) {
        System.out.println("\nPress ENTER to continue...");
        sc.nextLine();
    }
    
    public static void displayVictoryScreen(String winnerName,
            int hits,
            int shipsDestroyed) {

    	System.out.println();
    	System.out.println("==============================================================");
    	System.out.println("||                                                          ||");
    	System.out.println("||                  "+ConsoleSymbols.PARTY_POPPER+"  V I C T O R Y  "+ConsoleSymbols.PARTY_POPPER+"                   ||");
    	System.out.println("||                                                          ||");
    	System.out.println("==============================================================");
    	System.out.println();

    	System.out.println("                "+ConsoleSymbols.TROPHY+" WINNER : " + winnerName.toUpperCase());
    	System.out.println();

    	System.out.println("--------------------------------------------------------------");
    	System.out.println("|                    FINAL STATISTICS                        |");
    	System.out.println("--------------------------------------------------------------");
    	System.out.println("| Total Hits        | " + hits);
    	System.out.println("| Ships Destroyed   | " + shipsDestroyed);
    	System.out.println("--------------------------------------------------------------");
    	System.out.println();

    	System.out.println("          Thank you for playing SEA BATTLE!");
    	System.out.println("          May the seas favor you next time as well");
    	System.out.println();
    }
    
    public static int getChoice(Scanner sc)
    {
    	int gameMode = 0;

    	while (true) {
    	    System.out.println("Choose Game Mode:");
    	    System.out.println("1. Player vs Player");
    	    System.out.println("2. Player vs Computer");
    	    System.out.print("Enter choice (1 or 2): ");

    	    try {
    	    	gameMode = sc.nextInt();
    	    	sc.nextLine();
    	        if (gameMode == 1 || gameMode == 2) {
    	            break;
    	        }
    	        System.out.println("Invalid choice. Please enter 1 or 2.");
    	    } catch (InputMismatchException e) {
    	        System.out.println("Please enter a valid number.");
    	        sc.nextLine();
    	    }
    	}
    	return gameMode;

    }

    
}
