import javax.swing.*;
import java.awt.event.*;
import java.util.Random;


public class Game {
    
    public Game (Board board, GameDisplay gui) {
        randIndexes = new int[] {-1,-1,-1,-1,-1,-1,-1,-1};
        randomizePieces();
        randPick = new int[2];

        validTileIDs = new String[4];

        gameBoard = board; // instantiate the Board object
        gameGUI = gui;
        // Board object automatically instantiates Tiles and Animal objects it contains
        gameGUI.setListeners(new StartListener(), new RandomListener(), new ChoiceListener(), new BoardListener());
 
    }

    
    private boolean isOwnPiece(MouseEvent e) {
        return gameBoard.getTiles().getTerrains()[Integer.parseInt("" + e.getComponent().getName().charAt(0))]
                [Integer.parseInt("" + e.getComponent().getName().charAt(1))].getAnimal().getPlayerSide() - 1 == person;
    }


    private boolean isSameTerrain(MouseEvent e) {
        return e.getComponent().getName().equals("" + movingPiece.getRow() + movingPiece.getCol());
        
    }

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
     * @return
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
                num = randomizer.nextInt(8);
                
            }while(!isIndexUnique(randIndexes, num));
            // continues to produce random number until produced
            // random number
            
            randIndexes[q] = num;
        }

        randomizer = null;
    }


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

    private void executeMovements(MouseEvent e) {
        int i;
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


    


    private class StartListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            
        }


        @Override
        public void mousePressed(MouseEvent e) {
            gameGUI.getStartButton().setIcon(new ImageIcon("images\\startPressed.png"));
           
            gameGUI.repaint();

            
        }


        @Override
        public void mouseReleased(MouseEvent e) {
            // do nothing
            System.out.println("You just clicked the start button.");
            System.out.println("Game will start now.");

            person = 0;
            
            gameGUI.removeStartButton();
            
            gameGUI.displayRandomChoices(randIndexes);
            gameGUI.repaint();
        }


        @Override
        public void mouseEntered(MouseEvent e) {
            gameGUI.getStartButton().setIcon(new ImageIcon("images\\startHighlight.png"));
            // gameGUI.setTransparentBackground(gameGUI.getStartButton());
            gameGUI.repaint();
        }


        @Override
        public void mouseExited(MouseEvent e) {
            gameGUI.getStartButton().setIcon(new ImageIcon("images\\start.png"));
            // gameGUI.setTransparentBackground(gameGUI.getStartButton());
            gameGUI.repaint();
        }
        
    }


    private class RandomListener implements MouseListener {
        
        @Override
        public void mouseClicked(MouseEvent e) {
           // do nothing
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if(person < 2 && e.getComponent().isEnabled()) {
                randPick[person] = Integer.parseInt(e.getComponent().getName());
                e.getComponent().setEnabled(false);
                
                System.out.println("Index = " + randPick[person]);
                person++;

                gameGUI.updateTurn(person);
                gameGUI.repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
           
            if(person == 2) {
                System.out.println("Congrats! Picking of random animal successful");
                person %= 2; // assume person 1 has a higher rank piece

                // check if person 2 has a higher rank piece
                if(randPick[1] > randPick[0])
                    person += 1;

                // delay
                try {
                    Thread.sleep(1000);
                }
                catch(InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
                
                gameGUI.displayColorChoices(person + 1);
            }
            
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if(person < 2 && e.getComponent().isEnabled()) {
                ((JLabel)e.getComponent()).setIcon(new ImageIcon("images\\randPieceHighlight.png"));
               
                gameGUI.repaint();
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if(person < 2 && e.getComponent().isEnabled()) {
                ((JLabel)e.getComponent()).setIcon(new ImageIcon("images\\randPiece.png"));
                // gameGUI.setTransparentBackground(e.getComponent());
                gameGUI.repaint();
            }
        }
        
    }


    private class BoardListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
           // do nothing
           
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if(SwingUtilities.isRightMouseButton(e) && e.getComponent().isEnabled() && 
                movingPiece != null && isSameTerrain(e)) { // && isSameChosenPiece(e)
                
                System.out.println("RIGHT");
                System.out.println("Current chosen animal: " + movingPiece);
                gameGUI.updateTiles(gameBoard.getTiles().getTerrains(), movingPiece, validTileIDs, 0);
                movingPiece = null;
                
            }
            else if(SwingUtilities.isLeftMouseButton(e) && e.getComponent().isEnabled()) {
                System.out.println("LEFT");
                
                if(movingPiece == null && isOwnPiece(e)) {
                    movingPiece = gameBoard.getTiles().getTerrains()[Integer.parseInt("" + e.getComponent().getName().charAt(0))]
                        [Integer.parseInt("" + e.getComponent().getName().charAt(1))].getAnimal();

                    generateValidMoves();
                    gameGUI.updateTiles(gameBoard.getTiles().getTerrains(), movingPiece, validTileIDs, 1);
                }
                    
                
                else if(movingPiece != null && isValidMove(e)) {
                    gameGUI.movePiece(gameBoard.getTiles().getTerrains(), movingPiece, validTileIDs, false); // out of current tile of piece
                    // move animal in Model
                    executeMovements(e);
                    gameGUI.movePiece(gameBoard.getTiles().getTerrains(), movingPiece, validTileIDs, true); // into the chosen tile of movement
                    //chosenTileID = e.getComponent().getName();
                    person = (person + 1) % 2;
                    System.out.println(person);
                    // gameGUI.repaint();
                    movingPiece = null;

                }

                System.out.println("Chosen piece: " + movingPiece);
            }
                
        }

        @Override
        public void mouseReleased(MouseEvent e) {
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

        @Override
        public void mouseEntered(MouseEvent e) {
            
        }

        @Override
        public void mouseExited(MouseEvent e) {
           
        }
        
    }

    
    private class ChoiceListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("OK")) {
                
                //terminate program at the end of the game
                System.gc();
                System.exit(0);
            }
            else if(e.getActionCommand().equals("RED"))
                person = 0;
            else if(e.getActionCommand().equals("BLUE"))
                person = 1;

            
            System.out.println(e.getActionCommand());

            gameGUI.initBoardDisplay(gameBoard);
            gameGUI.updateColorPanel(person);
            gameGUI.displayAnimalChess(person);
        }
        
    }


    private Board gameBoard;
    private GameDisplay gameGUI; 

    private int[] randIndexes;
    private int[] randPick;
    private int person;

    private Animal movingPiece;
   
    private String[] validTileIDs; //[0]/1-Up; [1]/2-Down; [2]/3-Left; [3]/4-Right
    
}
