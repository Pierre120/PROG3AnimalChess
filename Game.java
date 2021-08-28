import java.util.Random;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;

public class Game {
    
    private int[] randIndexes;
    private int[] randPick;
    private int person;

    public Game (Board board, GameDisplay gui) {
        randIndexes = new int[] {-1,-1,-1,-1,-1,-1,-1,-1};
        randomizePieces(randIndexes);
        randPick = new int[2];

        gameBoard = board; // instantiate the Board object
        gameGUI = gui;
        // Board object automatically instantiates Tiles and Animal objects it contains
        gameGUI.setListener(new StartListener());

    }


    private class StartListener implements MouseInputListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("You just clicked the start button.");
            System.out.println("Game will start now.");
            gameGUI.refresh();

            gameGUI.removeStartButton();
            
            gameGUI.displayRandomChoices(randIndexes);
        }


        @Override
        public void mousePressed(MouseEvent e) {
            gameGUI.getStartButton().setIcon(new ImageIcon("images\\startPressed.png"));
            gameGUI.setTransparentBackground(gameGUI.getStartButton());
        }


        @Override
        public void mouseReleased(MouseEvent e) {
            // do nothing
        }


        @Override
        public void mouseEntered(MouseEvent e) {
            gameGUI.getStartButton().setIcon(new ImageIcon("images\\startHighlight.png"));
            gameGUI.setTransparentBackground(gameGUI.getStartButton());
        }


        @Override
        public void mouseExited(MouseEvent e) {
            gameGUI.getStartButton().setIcon(new ImageIcon("images\\start.png"));
            gameGUI.setTransparentBackground(gameGUI.getStartButton());
        }


        @Override
        public void mouseDragged(MouseEvent e) {
            // do nothing
        }


        @Override
        public void mouseMoved(MouseEvent e) {
            // do nothing
        }
        
    }


    private class RandomListener implements MouseInputListener {
        
        public RandomListener() {
            person = 0;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub
            if(person < 2 && e.getComponent().isEnabled()) {
                randPick[person] = Integer.parseInt(e.getComponent().getName());
                e.getComponent().setEnabled(false);
                gameGUI.refresh();
                System.out.println("Index = " + randPick[person]);
                person++;

                if(person == 2)
                    System.out.println("Congrats! Picking of random animal successful");
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }
        
    }
    

    /*
    public void executeGame () {
        // execute the whole game play
        Scanner sc = new Scanner(System.in);
        final String[] TEAM_COLOR = new String [] {"Red", "Blue"}; // index[0] for red team and index[1] for blue team
        final int[] PLYR_SIDE = new int[2]; // contains player number
        int personNum = 0; // temporarily assigns Player 1 to person 1
        int numChoice; // will be used throughout the game for number inputs of players

        // temporary variables
        int[] playerPiece = new int[2]; // to store the index values that the players got. 
                                        // Will be used as index in the animalChoices

        // ===================== START OF GAME ==============================================

        // both players will pick a random animal piece to determine who will be Player 1
        pickRandomAnimalPieces(playerPiece, sc);

        // changes the Player 1 to person 2 if he/she got a higher ranked animal, otherwise stay to person 1
        if(gameBoard.getAnimals()[playerPiece[0]][0].getRank() < gameBoard.getAnimals()[playerPiece[1]][0].getRank())
            personNum = 1;

        // display who will be player 1
        System.out.print("\n\nPerson " + (personNum + 1) + " will be Player 1\n");
        System.out.println();

        // assign null to the values that will not be used anymore
        playerPiece = null;

        personNum = pickTeamColor(sc);

        PLYR_SIDE[personNum] = 1; // personNum index for Player 1
        PLYR_SIDE[(personNum + 1) % 2] = 2; // personNum index for PLayer 2



        // ===================== GAMEPLAY ===================================================
        
        boolean movePiece;
        int animalPieceIndex;
        char charChoice;

        // updates the contents of the display board
        gameGUI.updateDisplayContents(gameBoard.getTiles().getTerrains());

        // loops until one of the animal den is conquered
        // as long as neither of the animal dens are captured and 
        // neither of the player has no uncaptured animals left, the game goes on 
        while(!gameBoard.getTiles().getTerrains()[0][3].getState() && 
            !gameBoard.getTiles().getTerrains()[8][3].getState() && 
            Animal.getAnimalCount(0) > 0 && Animal.getAnimalCount(1) > 0 )
        {   
            movePiece = false;
    
            while(!movePiece) {
                // displays whose turn is it
                System.out.print("\nPlayer " + PLYR_SIDE[personNum] + "'s turn! (" + TEAM_COLOR[personNum] + ")\n");
                gameGUI.displayBoard(); // displays the board
                    
                gameGUI.displayAvailableAnimals(gameBoard.getAnimals(), personNum);
                do {
                    do {
                        System.out.print("Enter animal: ");
                        numChoice = sc.nextInt();
    
                        if(numChoice < 1 || numChoice > 8)
                            System.out.println("Invalid Input!");
                    } while(numChoice < 1 || numChoice > 8); // to avoid index out of bounds
                        
                    animalPieceIndex = numChoice - 1;
                } while(gameBoard.getAnimals()[animalPieceIndex][personNum].isCaptured());
                // ^^ asks user for a valid animal piece (it means not captured)
    
                sc.nextLine(); // gets newline char left in buffer
    
                // updates the contents of board to display the
                // available moves of the chosen animal
                gameGUI.updateDisplayContents(gameBoard.getTiles().getTerrains(), 
                        gameBoard.getAnimals()[animalPieceIndex][personNum]);
                // displays whose turn is it
                System.out.print("\nPlayer " + PLYR_SIDE[personNum] + "'s turn! (" + TEAM_COLOR[personNum] + ")\n");
                gameGUI.displayBoard(); // displays the board
    
                // asks player which direction to move the chosen animal piece
                System.out.print("Chosen animal piece: " + gameBoard.getAnimals()[animalPieceIndex][personNum] + "\n");
                gameGUI.displayMovementKeys(); // display the 'L', 'R', 'U', 'D' and 'X' options for movement controls
                do {
                    System.out.print("Enter key: ");
                    charChoice = sc.nextLine().toUpperCase().charAt(0);
    
                    // cancel move of animal with 'X' input
                } while(!isValidMove(charChoice, gameBoard.getAnimals()[animalPieceIndex][personNum]));
    
                // checks whether player moved a piece or cancels to move animal piece
                movePiece = executeMovements(charChoice, gameBoard.getAnimals()[animalPieceIndex][personNum]);
                // updates the contents of gameboard display
                gameGUI.updateDisplayContents(gameBoard.getTiles().getTerrains());
            }
                
            // switch player turn if one of the animal den is not yet captured 
            // or either of the players still has animal pieces left
            if(!gameBoard.getTiles().getTerrains()[0][3].getState() && 
                !gameBoard.getTiles().getTerrains()[8][3].getState() && 
                Animal.getAnimalCount(0) > 0 && Animal.getAnimalCount(1) > 0)
                personNum = (personNum + 1) % 2;
        }

        sc.close(); // closes the scanner

        // last display of the current state of the board
        gameGUI.displayBoard();
        // display winner
        gameGUI.displayWinner(PLYR_SIDE[personNum], TEAM_COLOR[personNum]);

        // null the variables used
        gameBoard = null;
        gameGUI = null;
        personNum = 0;
        numChoice = 0;
        movePiece = false;
        animalPieceIndex = 0;
        charChoice = '\0';
        System.gc();
    }
    */

    /*
    private int pickTeamColor(Scanner sc) {
        int numChoice;

        // asks Player 1 which color side he/she will use
        do {
            gameGUI.displayTeamColorChoice();
            System.out.print("Pick a color Player 1: ");
            numChoice = sc.nextInt();
            
            if(numChoice < 1 || numChoice > 2)
                System.out.print("Invalid Input!\n\n");
            
        }while(numChoice < 1 || numChoice > 2);

        /*
        changes the index for Player 1 (assuming his playerNum is 1) 
        to 0 because all the index 0 for Animals and teamColor 
        would be for team RED and index 1 for team BLUE, but
        if Player 1 chooses BLUE team and his current
        playerNum is 1 then there is no need to change 
        if(numChoice == 1)
            return 0;
        else return 1;
    }
    */

    /* 
    private void pickRandomAnimalPieces(int[] playerPiece, Scanner sc) {
        int k;
        int numChoice;
        int[] randomIndexes = new int[] {-1, -1, -1, -1, -1, -1, -1, -1}; // contains the random index values for the players to choose
        int[] validChoices = new int[] {1, 2, 3, 4, 5, 6, 7, 8}; // valid number inputs that the player could choose during the start of the game

        // randomizes the index for the animalChoices for the players to choose
        randomizePieces(randomIndexes);

        // asks the players for a random animal piece to be used 
        // in determining who will be player 1
        for(k = 0; k < 2; k++) {
            do {
                gameGUI.displayRandomAnimalChoice(8, validChoices);
                
                do {
                    System.out.print("Person " + (k + 1) + " pick a random animal piece: ");
                    numChoice = sc.nextInt();

                    if(numChoice < 1 || numChoice > 8)
                        System.out.println("Invalid Input!");
                } while(numChoice < 1 || numChoice > 8); // to avoid index out of bounds
                
                if(validChoices[numChoice - 1] == 0)
                    System.out.print("Piece is already taken!\n\n");
                
            }while(validChoices[numChoice - 1] == 0); 
            // asks user for a valid input for a random animal piece

            validChoices[numChoice - 1] = 0;
            playerPiece[k] = randomIndexes[numChoice - 1];
            System.out.println();
        }

        // assign null to the values that will not be used anymore
        randomIndexes = null;
        validChoices = null;

        // displays the animal pieces that each player got
        System.out.println("Animal Pieces:");
        for(k = 0; k < 2; k++)
            System.out.println("Person " + (k + 1) + " got " + gameBoard.getAnimals()[playerPiece[k]][0]);
    }
    */


    /** This method is responsible for transcribing the input of the players into
     * the movement of the chosen animal object.
     * 
     * @param moveKey character input of player for movement
     * @param gameBoard the game board comprised of terrain objects and where the animal object moves
     * @param movingAnimal the animal object that is subject to move
     * @return
     */
    private boolean executeMovements(char moveKey, Animal movingAnimal) {
        switch(moveKey) {
            case 'L':
                movingAnimal.moveLeft(gameBoard.getTiles().getTerrains()); // moves animal piece to the left
                return true; // successfully moved

            case 'R':
                movingAnimal.moveRight(gameBoard.getTiles().getTerrains()); // moves animal piece to the right
                return true; // successfully moved

            case 'U':
                movingAnimal.moveUp(gameBoard.getTiles().getTerrains()); // moves animal piece upwards
                return true; // successfully moved

            case 'D':
                movingAnimal.moveDown(gameBoard.getTiles().getTerrains()); // moves animal piece downwards
                return true; // successfully moved

            default:
                return false; // cancels the move of the animal piece or just do nothing
        }
    }


    /** This method is responsible for checking whether the character movement key
     * input of the player is correct and if that move is can be done by the animal
     * object.
     * 
     * @param moveKey character input of player for movement
     * @param movingAnimal the animal object that is subject to move
     * @param gameBoard the game board comprised of terrain objects and where the animal object moves
     * @return
     */
    private boolean isValidMove(char moveKey, Animal movingAnimal) {
        // true - character input is correct and chosen movement can be done by animal
        // false - neither the character input is correct or the animal can't move in the desired direction
        return moveKey == 'L' && movingAnimal.canMoveLeft(gameBoard.getTiles().getTerrains()) ||
                moveKey == 'R' && movingAnimal.canMoveRight(gameBoard.getTiles().getTerrains()) ||
                moveKey == 'U' && movingAnimal.canMoveUp(gameBoard.getTiles().getTerrains()) ||
                moveKey == 'D' && movingAnimal.canMoveDown(gameBoard.getTiles().getTerrains()) ||
                moveKey == 'X';
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
     * @param pieceIndex
     * @param randomizer
     * @param numToRandomize
     */
    private void randomizePieces(int[] pieceIndex) {
        Random randomizer = new Random();
        int num;
        int q;
        
        for(q = 0; q < 8; q++) {
            do{
                num = randomizer.nextInt(8);
                // System.out.print("1");
            
            }while(!isIndexUnique(pieceIndex, num));
            // continues to produce random number until produced
            // random number

            pieceIndex[q] = num;
        }

        randomizer = null;
    }

    private Board gameBoard;
    private GameDisplay gameGUI; 
}
