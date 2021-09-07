import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

/**
 * The GameController class is responsible for executing the gameplay. It
 * implements listeners for the GUI and makes the corresponding changes on
 * both the model and the GUI depending on how the players interact with
 * the GUI. It follows the MVC archietecture.
 * 
 * @author Pierre Vincent Hernandez
 * @author Matthew James D. Villarica
 */
public class GameController {
    /**
     * This constructor instantiates GameController given the model (BoardModel)
     * and the gui (GameGUI). It also initializes the random pieces to be used
     * at the start of the game.
     * 
     * @param board the model class that contains the Animal objects and the terrain
     * @param gui the GUI class that contains the user interface, components and methods to update it
     */
    
    public GameController (BoardModel board, GameGUI gui) {
        randIndexes = new int[] {-1,-1,-1,-1,-1,-1,-1,-1};
        randomizePieces();
        randPick = new int[2];

        validTileIDs = new String[4];

        gameBoard = board; // instantiate the Board object
        gameGUI = gui;
        // Board object automatically instantiates Tiles and Animal objects it contains
        gameGUI.setListeners(new StartListener(), new RandomListener(), new ChoiceListener(), new BoardListener());
 
    }

    /**
     * This function determines if a BoardTile object that was left clicked by the player
     * is on the same team(red or blue) of the player.
     * 
     * @param e a mouse event, which is a BoardTile in the GUI being clicked
     * @return true if the piece in the tile is in the same team as the player, false otherwise
     */
    private boolean isOwnPiece(MouseEvent e) {
        return gameBoard.getTiles().getTerrains()[Integer.parseInt("" + e.getComponent().getName().charAt(0))]
                [Integer.parseInt("" + e.getComponent().getName().charAt(1))].getAnimal().getPlayerSide() - 1 == person;
    }

    /**
     * This function determines if the user left clicked on the same tile or terrain as the 
     * piece being moved by the player which was left clicked previously.
     * 
     * @param e a mouse event, which is the player clicking on a tile of the GUI
     * @return true if the player left clicked the same terrain, false otherwise
     */
    private boolean isSameTerrain(MouseEvent e) {
        return e.getComponent().getName().equals("" + movingPiece.getRow() + movingPiece.getCol());
        
    }
    /**
     * This function determines if the user left clicked on a valid tile (BoardTile)
     * after the user selects a valid piece to move. The valid move IDs are generated in
     * the attribute validTileIDs.
     * 
     * @param e a mouse event, which is the user clicking on a tile in the GUI
     * @return true if the user clicked on a tile a part of validTileIDs, false otherwise.
     */
    private boolean isValidMove(MouseEvent e) {
        for(int n = 0; n < validTileIDs.length; n++)
            if(validTileIDs[n].equals(e.getComponent().getName()))
                return true;

        return false;
    }


    /** This method is responsible for checking if the random number
     * produced for the player to choose is unique in the array of indexes
     * it's going to be stored to.
     * 
     * @param arrIndex the array of index values
     * @param randomIndex the random number produced
     * @return true if the index is unique, false otherwise
     */
    private boolean isIndexUnique(int[] arrIndex, int randomIndex) {
        int r;

        for(r = 0; arrIndex[r] != -1 && r < arrIndex.length; r++)
            if(arrIndex[r] == randomIndex)
                return false; // the randomIndex already exists in the arrIndex
        
        return true; // randomIndex is a unique entry in arrIndex
    }


    /** This method is responsible for the randomization of index values
     * that will be stored in an array for the players to pick from. Those indexes
     * are the indexes for the array of Animal objects that is comprised of the 8
     * unique animal objects.
     * 
     */
    private void randomizePieces() {
        Random randomizer = new Random();
        int num;
        int q;
        

        for(q = 0; q < 8; q++) {
            do{
                //num generated is >= 0 or <= 7, since nextInt() is exclusive of upper bound
                num = randomizer.nextInt(8);
                
            }while(!isIndexUnique(randIndexes, num));
            // continues to produce random number until produced
            // random number
            
            randIndexes[q] = num;
        }

        randomizer = null;
    }

    /**
     * This method generates the valid moves a player can make based on the 
     * current position of the chosen piece through the String[] attribute validTileIDs.
     * Each element in the array contains a String of the row and column position of the valid
     * tile the player can move the piece to. (i.e. if the player can move his animal piece
     * into row 2 and column 4 in array index notation of the board, "24" is in the array).
     * 
     * validTileIDs[0] - contains tileID of upward movement if it is a valid move
     * validTileIDs[1] - contains tileID of downward movement if its a valid move
     * validTileIDs[3] - contains tileID of left movement if its a valid move
     * validTileIDs[4] - contains tileID of right movement if its a valid move
     * 
     * If its not a valid move, the element in the array will contain the String "null" .
     */
    private void generateValidMoves() {
        for(int k = 0; k < validTileIDs.length; k++)
            validTileIDs[k] = "null";

        //add validTileIDs if the animal can move to tile
        if(movingPiece.canMoveUp(gameBoard.getTiles().getTerrains())) 
            validTileIDs[0] = "" + movingPiece.getRow() + (movingPiece.getCol() + movingPiece.getUpwardSpace());
        if(movingPiece.canMoveDown(gameBoard.getTiles().getTerrains())) 
            validTileIDs[1] = "" + movingPiece.getRow() + (movingPiece.getCol() - movingPiece.getDownwardSpace());
        if(movingPiece.canMoveLeft(gameBoard.getTiles().getTerrains())) 
            validTileIDs[2] = "" + (movingPiece.getRow() - movingPiece.getLeftSpace()) + movingPiece.getCol();
        if(movingPiece.canMoveRight(gameBoard.getTiles().getTerrains())) 
            validTileIDs[3] = "" + (movingPiece.getRow() + movingPiece.getRightSpace()) + movingPiece.getCol(); 
    }
   
    /**
     * This method executes the movements (up, down, left, right) of the animal in the BoardModel class.
     * @param e a mouse event, which is the tile (BoardTile) the player chose for his chosen piece
     *          to move into
     */
    private void executeMovements(MouseEvent e) {
        int i;

        /*If the tile clicked by the player is in validTileIDs, then it is the 
           the tile the player wants to move the piece to. */
        for(i = 0; i < validTileIDs.length; i++) {
            if(validTileIDs[i].equals(e.getComponent().getName()))
                break;
        }
        //execute move depending on chosen tile player wants to move on
        switch(i) {
            case 0:
                movingPiece.moveUp(gameBoard.getTiles().getTerrains());
                break;
            case 1:
                movingPiece.moveDown(gameBoard.getTiles().getTerrains());
                break;
            case 2:
                movingPiece.moveLeft(gameBoard.getTiles().getTerrains());
                break;
            case 3:
                movingPiece.moveRight(gameBoard.getTiles().getTerrains());
                break;

        }
    }


    

    /**
     * This inner class is responsible for the implementation of the
     * MouseListener for the start button of the GUI. The MouseListener
     * responds to the start button being interacted with using the mouse.
     */
    private class StartListener implements MouseListener {

        /**
         * This overriden method is responsible for what will happen
         * when the start button is clicked. In this case, it does nothing.
         * 
         * @param e the mouse event
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            
        }

        /**
         * This overriden method is responsible for responding to mouse presses by the user
         * on the start button. The picture changes and the GUI is repainted.
         * 
         * @param e the mouse event
         */
        @Override
        public void mousePressed(MouseEvent e) {
            gameGUI.getStartButton().setIcon(new ImageIcon("images\\startPressed.png"));
           
            gameGUI.repaint();

            
        }

        /**
         * This overriden method is responsible for responding to the mouse being released
         * after the start button is pressed by the user with the mouse pointer.
         * 
         * @param e the mouse event
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            // do nothing
            System.out.println("You just clicked the start button.");
            System.out.println("Game will start now.");

            person = 0;
            
            gameGUI.removeStartButton();
            //after mouse is released on start button, display the 8 mystery pieces for player to choose
            gameGUI.displayRandomChoices(randIndexes);
            gameGUI.repaint();
        }

        /**
         * This overiden method responds to the mouse hovering over the start button.
         * 
         * @param e the mouse event
         */
        @Override
        public void mouseEntered(MouseEvent e) {
            gameGUI.getStartButton().setIcon(new ImageIcon("images\\startHighlight.png"));
            
            gameGUI.repaint();
        }

        /**
         * This overriden method respnds to the mouse exiting after hovering over the start button.
         * 
         * @param e the mouse event
         */
        @Override
        public void mouseExited(MouseEvent e) {
            gameGUI.getStartButton().setIcon(new ImageIcon("images\\start.png"));
            
            gameGUI.repaint();
        }
        
    }

    /**
     * This inner class implements the MouseListener for the random piece selection before 
     * the start of the game.
     */
    private class RandomListener implements MouseListener {
        /**
         * This overriden method responds to the user clicking on the GUI during random piece
         * selection.
         * 
         * @param e the mouse event
         */
        @Override
        public void mouseClicked(MouseEvent e) {
           // do nothing
        }

        /**
         * This overriden method responds to mouse presses on the random
         * pieces the players choose.
         * 
         * @param e the mouse event
         */
        @Override
        public void mousePressed(MouseEvent e) {
            if(person < 2 && e.getComponent().isEnabled()) {
                /*Records the name of the component clicked on, which is the rank
                of the animals (1-8).*/
                randPick[person] = Integer.parseInt(e.getComponent().getName());

                /*Disable piece after it is picked to prevent duplicates */
                e.getComponent().setEnabled(false);
                
                System.out.println("Index = " + randPick[person]);
                person++;

                //update text prompts
                gameGUI.updateTurn(person);
                gameGUI.repaint();
            }
        }
        /**
         * This overriden method is responsible for responses to the mouse being released
         * on enabled components, which are the 8 random pieces in the gui.
         * 
         * @param e the mouse event
         */
        @Override
        public void mouseReleased(MouseEvent e) {
           
            if(person == 2) {
                System.out.println("Congrats! Picking of random animal successful");
                person %= 2; // assume person 1 has a higher rank piece

                // check if person 2 has a higher rank piece
                if(randPick[1] > randPick[0])
                    person += 1;

                // delay to simulate loading, which gives time for players to process results of random picking
                try {
                    Thread.sleep(1000);
                }
                catch(InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
                //display GUI for picking of red and blue
                gameGUI.displayColorChoices(person + 1);
            }
            
        }
        /**
         * This overriden method is responsible for responding to the mouse pointer hovering over
         * the enabled components of the GUI, which are the 8 random mystery pieces for the players
         * to pick.
         * 
         * @param e the mouse event
         */
        @Override
        public void mouseEntered(MouseEvent e) {
            if(person < 2 && e.getComponent().isEnabled()) {
                ((JLabel)e.getComponent()).setIcon(new ImageIcon("images\\randPieceHighlight.png"));
               
                gameGUI.repaint();
            }
        }
        /**
         * This overriden method is responsible for the mouse exiting an enabled piece after
         * hovering over it with the mouse pointer.
         * 
         * @param e the mouse event
         */
        @Override
        public void mouseExited(MouseEvent e) {
            if(person < 2 && e.getComponent().isEnabled()) {
                ((JLabel)e.getComponent()).setIcon(new ImageIcon("images\\randPiece.png"));
                
                gameGUI.repaint();
            }
        }
        
    }

    /**
     * This inner class is responsible for implementing the MouseListener of the BoardTiles
     * in the GUI, which are JPanels that visually represent the GUI of the game board.
     */
    private class BoardListener implements MouseListener {
        /**
         * This overriden method responds to the user clicking on the GUI during random piece
         * selection.
         * 
         * @param e the mouse event
         */
        @Override
        public void mouseClicked(MouseEvent e) {
           // do nothing
           
        }

        /**
         * This overriden method responds to mouse presses (both left and right) 
         * on the BoardTiles.
         * 
         * @param e the mouse event
         */
        @Override
        public void mousePressed(MouseEvent e) {

            
            if(SwingUtilities.isRightMouseButton(e) && e.getComponent().isEnabled() && 
                movingPiece != null && isSameTerrain(e)) {
                
                /*If the player right pressed on the same piece after selecting a piece to move by left clicking
                 a piece, cancel the move and update the GUI. 
                */
                
                System.out.println("RIGHT");
                System.out.println("Current chosen animal: " + movingPiece);
                gameGUI.updateTiles(gameBoard.getTiles().getTerrains(), movingPiece, validTileIDs, 0);
                movingPiece = null;
                
            }
            else if(SwingUtilities.isLeftMouseButton(e) && e.getComponent().isEnabled()) {
                System.out.println("LEFT");
                /*If the player left clicks on a valid piece without a previously selected piece
                being enabled, assign that piece to movingPiece and generate valid moves.  */
                if(movingPiece == null && isOwnPiece(e)) {
                    movingPiece = gameBoard.getTiles().getTerrains()[Integer.parseInt("" + e.getComponent().getName().charAt(0))]
                        [Integer.parseInt("" + e.getComponent().getName().charAt(1))].getAnimal();

                    generateValidMoves();
                    gameGUI.updateTiles(gameBoard.getTiles().getTerrains(), movingPiece, validTileIDs, 1);

                } else if(movingPiece != null && isValidMove(e)) {

                    /*If player has previously chosen a piece and the BoardTile picked after is a valid destination,
                    execute the movement in both the GUI and the model, and change turns accordingly.*/
                    gameGUI.movePiece(gameBoard.getTiles().getTerrains(), movingPiece, validTileIDs, false); // out of current tile of piece

                    // move animal in Model
                    executeMovements(e);

                    gameGUI.movePiece(gameBoard.getTiles().getTerrains(), movingPiece, validTileIDs, true); // into the chosen tile of movement

                  
                    person = (person + 1) % 2;
                    System.out.println(person);
                   
                    movingPiece = null;

                }

                System.out.println("Chosen piece: " + movingPiece);
            }
                
        }
        /**
         * This overriden method is responsible for responding to the mouse being released on
         * the BoardTiles.
         * @param e the mouse event
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            /*If either animal den is captured or if either team has no animals left, the game is over
            and the results are displayed. */
           if(gameBoard.getTiles().getTerrains()[0][3].getState() || 
              gameBoard.getTiles().getTerrains()[8][3].getState() || 
              Animal.getAnimalCount(0) == 0 || Animal.getAnimalCount(1) == 0) {
                   // display results and ask player if want to play again
                   person = (person + 1) % 2; // go back to winning player
                   gameGUI.displayResults(person);
            } else {
                // updateColorPanel
                gameGUI.updateColorPanel(person);
            }
               
        }
        /*This overriden method responds to the mouse hovering over BoardTiles */
        @Override
        public void mouseEntered(MouseEvent e) {
            
        }
        /**
         * This overriden method responds to the mouse exiting after hovering over BoardTiles
         */
        @Override
        public void mouseExited(MouseEvent e) {
           
        }
        
    }

    /**
     * This inner class is responsible for implementing the ActionListener for the 
     * picking of player colors, and the "OK" button at the end of a game.
     */
    private class ChoiceListener implements ActionListener {

        /**
         * This overriden method responds to action events, which are mouse clicks on
         * the buttons.
         * @param e the ActionEvent, which is the JButtons being clicked
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getActionCommand().equals("OK")) {
                gameBoard.setToNull();
                //terminate program at the end of the game
                System.gc();
                System.exit(0);
            }
            else if(e.getActionCommand().equals("RED"))
                person = 0;
            else if(e.getActionCommand().equals("BLUE"))
                person = 1;

            
            System.out.println(e.getActionCommand());
            //after color selection, start the game 
            gameGUI.initBoardDisplay(gameBoard);
            gameGUI.updateColorPanel(person);
            gameGUI.displayAnimalChess(person);
        }
        
    }

    /**
     * This attribute contains the BoardModel, which contains the data and behaviors the GUI will reflect.
     */
    private BoardModel gameBoard;
    /**
     * This attribute contains the GameGUI, which is responsible for the visualization of the user interface.
     */
    private GameGUI gameGUI; 
    /**
     * This attribute is the array of numbers that contains the randomly generated numbers for
     * random piece picking (contains numbers 0-7 in a random order).
     */
    private int[] randIndexes;
    /**
     * This attribute is the array of numbers containing the rank of the animal chosen randomly 
     * by the player.
     */
    private int[] randPick;
    /**
     * This attribute contains either contains 0 or 1, overseas the switching of player turns.
     */
    private int person;
    /**
     * This attribute contains a reference to the Animal being moved by the players.
     */
    private Animal movingPiece;
   /**
    * This attribute holds the validTileIDs generated. Contains the String "null" if the tile
    is an invalid destination.
    */
    private String[] validTileIDs; //[0]/1-Up; [1]/2-Down; [2]/3-Left; [3]/4-Right
    
}
