/**
 * This class is the driver class for the program Animal Chess.
 * This executes the game itself and handles the game mechanics.
 * 
 * @author Matthew James Villarica
 * @author Pierre Vincent Hernandez
 */
public class AnimalChess {
    public static void main (String[] args) {
        BoardModel gameBoard = new BoardModel();
        GameGUI GUI = new GameGUI();
        GameController mechanic = new GameController(gameBoard, GUI);
        //mechanic.executeGame();
        
    }

}
