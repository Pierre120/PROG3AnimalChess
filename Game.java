import java.util.Random;

public class Game {
    
    public Game () {
        gameBoard = new Board(); // instantiate the Board object
        gameGUI = new GameDisplay();
        // Board object automatically instantiates Tiles and Animal objects it contains
    }

    public void executeGame () {
        // execute the whole game play
    }


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
                num = randomizer.nextInt(2);
            
            }while(!isIndexUnique(pieceIndex, num));
            // continues to produce random number until produced
            // random number

            pieceIndex[q] = num;
        }

    }

    private Board gameBoard;
    private GameDisplay gameGUI; 
}
