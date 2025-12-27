package player;

import java.util.Random;

public class AIPlayer extends Player{

	private Random random;

    public AIPlayer(String name, int boardSize) {
        super(name, boardSize);
        this.random = new Random();
    }

    // ================= AI INPUT OVERRIDES =================

    protected int getRowInput() {
        int row = random.nextInt(10);
        return row;
    }

    protected char getColumnInput() {
        char col = (char) ('A' + random.nextInt(10));
        return col;
    }
    
    public void displayBoardsSideBySide(Player opponent)
    {
    	//do not display anything
    }
    
    public void displayPoints() {
    	//do not display anything
    }
}
