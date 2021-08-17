/**
 * This Animal class is the parent class of the 8 types of animal sub-classes. It
 * contains the methods to check surroundings, move from terrain to terrain, and 
 * capture the opposing side's animal peices.
 * 
 * @author Pierre Vincent Hernandez
 * @author Matthew James Villarica
 */
public class Animal {
    
    /** This constructor instantiates an animal object given the playerNum, which can be 1 or 2 depending 
     * on which team that animal is on, the animalRank, and the starting position of the animal 
     * on the gameboard in row and column format.
     * 
     * @param playerNum the side the animal is on, which can be 1 or 2 (Player 1 or Player 2)
     * @param startingRow the starting row position of the animal in the 9x7 gamebaord
     * @param startingCol the starting col position of the animal in the 9x7 gameboard
     * @param animalRank the rank of the animal
     */
    public Animal(int playerNum, int startingRow, int startingCol, int animalRank) {
        PLAYER_SIDE = playerNum;
        rowPosition = startingRow;
        colPosition = startingCol;
        RANK = animalRank;
        captured = false;
        upwardSpace = 0;
        downwardSpace = 0;
        rightSpace = 0;
        leftSpace = 0;
        animalCount[playerNum - 1]++;
    }


    /** This method determines if the animal is already captured by the opponent.
     * 
     * @return true if the animal is captured by the opponent, otherwise false
     */
    public boolean isCaptured() {
        return captured;
    }


    /** This method determines the terrain that the animal is going to move into. 
     * It's going to check if it is a land, a river, a trap or an animal den (and who's animal den).
     * 
     * @param specificArea the area that the animal is checking if it could move into it
     * @return true if terrain is not a river and not its own animal den, false otherwise
     */
    public boolean isValidTerrain(Terrain specificArea) {
        if(specificArea.isRiver() || (specificArea.isAnimalDen() && specificArea.getOwner() == this.PLAYER_SIDE))
            return false;
        return true;
    }


    /** This method determines if an enemy animal is capturable by the animal based on rank.
     * 
     * @param enemyAnimal the enemy animal 
     * @return true if the enemy animal is capturable, false otherwise
     */
    public boolean canCapture(Animal enemyAnimal) {
        if(enemyAnimal != null && enemyAnimal.getRank() <= RANK && enemyAnimal.getPlayerSide() != PLAYER_SIDE) 
            return true;
        return false;
    }


    /** This method determines if the player's animal could occupy the terrain it will go to.
     * 
     * @param specificArea
     * @return true if the area doesn't have any animal occupying it or if it can capture the animal occupying the area, otherwise false.
     */
    public boolean canOccupy(Terrain specificArea) {
        if((specificArea.getState() && (canCapture(specificArea.getAnimal()) || specificArea.isTrap()))
            || !specificArea.getState())
            return true;

        return false;
    }
    

    /** This method determines if the animal can move up one space on the board.
     * 
     * @param gameArea the 9x7 2d array that represents the gameboard 
     * @return false if the position does not exist in the game board or if there is an animal that can't be captured in t
     * the way, true otherwise
     * 
     */
    public boolean canMoveUp(Terrain[][] gameArea) {
        // checks if index out of bounce
        if(colPosition + 1 <= 6) {

            if(isValidTerrain(gameArea[rowPosition][colPosition + 1]) && canOccupy(gameArea[rowPosition][colPosition + 1])) {
                upwardSpace = 1;
                return true;
            }
        }
        
        return false; // animal can't move up
    }


     /** This method determines if the animal can move down one space on the board.
     * 
     * @param gameArea the 9x7 2d array that represents the gameboard 
     * @return false if the position does not exist in the game board or if there is an animal that can't be captured in t
     * the way, true otherwise
     * 
     */
    public boolean canMoveDown(Terrain[][] gameArea) {
        // checks if index out of bounce
        if(colPosition - 1 >= 0) {
            
             if(isValidTerrain(gameArea[rowPosition][colPosition - 1]) && canOccupy(gameArea[rowPosition][colPosition - 1])) {
                downwardSpace = 1;
                return true;
             }

        } 
         
        return false; // animal can't move down

    }


    /** This method determines if the animal can move left once on the gameboard.
     * 
     * @param gameArea the 9x7 2d array that represents the gameboard 
     * @return false if the position does not exist in the game board (index out of bounds) or if there is an animal that can't be captured in t
     * the way, true otherwise
     * 
     */
    public boolean canMoveLeft(Terrain[][] gameArea) {
        // checks if index out of bounce
        if(rowPosition - 1 >= 0) {
            
            if(isValidTerrain(gameArea[rowPosition - 1][colPosition]) && canOccupy(gameArea[rowPosition - 1][colPosition])) {
                leftSpace = 1;
                return true;
            }
        }
        return false; // animal can't move to the left
        
    }


    /** This method determines if the animal can move right once on the gameboard.
     * 
     * @param gameArea the 9x7 2d array that represents the gameboard 
     * @return false if the position does not exist in the game board (index out of bounds) or if there is an animal that can't be captured in t
     * the way, true otherwise
     * 
     */
     public boolean canMoveRight(Terrain[][] gameArea) {
        // checks if index out of bounce
        if(rowPosition + 1 <= 8) {
            
            if(isValidTerrain(gameArea[rowPosition + 1][colPosition]) && canOccupy(gameArea[rowPosition + 1][colPosition])) {
                rightSpace = 1;
                return true;
            }
            
        }
        return false; // animal can't move to the right
        
    }

    
    /** This method captures the opponent's animal by setting the opponent's animal captured attribute to true
     *  and moving it out of its current terrain.
     * 
     * @param enemyAnimal the opponent's animal
     * @param capturedArea the area where the captured animal was
     */
    public void captureAnimal(Animal enemyAnimal, Terrain capturedArea) {
        enemyAnimal.setToCaptured(); // captures the opposing animal object
        // decrements the number of animal objects the opposing player has
        animalCount[enemyAnimal.getPlayerSide() - 1]--; 
        // then moves that captured opposing animal object out of the terrain it is currently occupying
        capturedArea.animalMovesOut();
    }


    /** This method moves the animal 1 space up in the gameboard.
     * 
     * @param gameArea the 9x7 array of Terrain that represents the gameboard
     */
    public void moveUp(Terrain[][] gameArea) {
        colPosition += upwardSpace;

        // captures the animal object in the terrain this animal object is moving in to
        if(gameArea[rowPosition][colPosition].getState())
            captureAnimal(gameArea[rowPosition][colPosition].getAnimal(), gameArea[rowPosition][colPosition]);

        // this animal object occupies the new terrain it is moving in to
        gameArea[rowPosition][colPosition].animalMovesIn(this);
        // this animal object then moves out of the recent terrain it's on
        gameArea[rowPosition][colPosition - upwardSpace].animalMovesOut();
        upwardSpace = 0;
    }


    /** This method moves the animal 1 space down in the gameboard.
     * 
     * @param gameArea the 9x7 array of Terrain that represents the gameboard
     */
    public void moveDown(Terrain[][] gameArea) {
        colPosition -= downwardSpace;

        // captures the animal object in the terrain this animal object is moving in to
        if(gameArea[rowPosition][colPosition].getState())
            captureAnimal(gameArea[rowPosition][colPosition].getAnimal(), gameArea[rowPosition][colPosition]);

        // this animal object occupies the new terrain it is moving in to
        gameArea[rowPosition][colPosition].animalMovesIn(this);
        // this animal object then moves out of the recent terrain it's on
        gameArea[rowPosition][colPosition + downwardSpace].animalMovesOut();
        downwardSpace = 0;
    }


    /** This method moves the animal 1 space to the right in the gameboard.
     * 
     * @param gameArea the 9x7 array of Terrain that represents the gameboard
     */
    public void moveRight(Terrain[][] gameArea) {
        rowPosition += rightSpace;

        // captures the animal object in the terrain this animal object is moving in to
        if(gameArea[rowPosition][colPosition].getState())
            captureAnimal(gameArea[rowPosition][colPosition].getAnimal(), gameArea[rowPosition][colPosition]);

        // this animal object occupies the new terrain it is moving in to
        gameArea[rowPosition][colPosition].animalMovesIn(this);
        // this animal object then moves out of the recent terrain it's on
        gameArea[rowPosition - rightSpace][colPosition].animalMovesOut();
        rightSpace = 0;
    }
    

    /** This method moves the animal 1 space to the left in the gameboard
     * 
     * @param gameArea the 9x7 array of Terrain that represents the gameboard
     */
    public void moveLeft(Terrain[][] gameArea) {
        rowPosition -= leftSpace;

        // captures the animal object in the terrain this animal object is moving in to
        if(gameArea[rowPosition][colPosition].getState())
            captureAnimal(gameArea[rowPosition][colPosition].getAnimal(), gameArea[rowPosition][colPosition]);

        // this animal object occupies the new terrain it is moving in to
        gameArea[rowPosition][colPosition].animalMovesIn(this);
        // this animal object then moves out of the recent terrain it's on
        gameArea[rowPosition + leftSpace][colPosition].animalMovesOut();
        leftSpace = 0;
    }


    /** This method sets the captured state of the animal to true.
     */
    public void setToCaptured() {
        captured = true;
    }


    /** This method gets the rank of the animal.
     * 
     * @return rank of the animal
     */
    public int getRank() {
        return RANK;
    }


    /** This method gets which player the animal belongs to.
     * 
     * @return player number(side) the animal belongs to
     */
    public int getPlayerSide() {
        return PLAYER_SIDE;
    }
    

    /** This method gets the current row position of the animal in the gameboard.
     * 
     * @return current row position of the animal in the gameboard
     */
    public int getRow() {
        return rowPosition;
    }


    /** This method gets the current column position of the animal in the gameboard.
     * 
     * @return current column position of the animal in the gameboard
     */
    public int getCol() {
        return colPosition;
    }


    /** This method gets the number of spaces that the animal can move upward.
     * 
     * @return number of spaces that the animal can move upward
     */
    public int getUpwardSpace() {
        return upwardSpace;
    }
    

    /** This method gets the number of spaces that the animal can move downward.
     * 
     * @return number of spaces that the animal can move downward
     */
    public int getDownwardSpace() {
        return downwardSpace;
    }


    /** This method gets the number of right spaces that the animal can move.
     * 
     * @return number of right spaces that the animal can move
     */
    public int getRightSpace() {
        return rightSpace;
    }


    /** This attribute stores the number of left spaces that the animal can move.
     * 
     * @return number of left spaces that the animal can move
     */
    public int getLeftSpace() {
        return leftSpace;
    }


    /** This method returns the number of animal pieces that a player still has.<br>
     * animalCount[0] - animal count for Player in team red<br>
     * animalCount[1] - animal count for Player in team blue
     * 
     * @param playerNumIndex
     * @return number of animal pieces that a player still has
     */
    public static int getAnimalCount(int playerNumIndex) {
        return animalCount[playerNumIndex];
    }

    /** Resets animal count of player 1 and player 2 to 0.
     * 
     */
    public static void resetAnimalCount() {
        animalCount[0] = 0;
        animalCount[1] = 0;
    }
    

     /** This attribute holds the animal counts for both player 1 and player 2. 
         Index 0 contains player 1's animal count and index 1 contains player 2's animal count.
         When a player's animal count reaches 0, that player loses.
     */
    private static int[] animalCount = new int[]{0, 0};

    /**
     * This attribute holds the rank of the animal.
     */
    protected final int RANK; 

    /**
     * This attribute holds the player number(side) it belongs to.
     */
    private final int PLAYER_SIDE;

    /**
     * This attribute stores the row position of the animal in the gameboard.
     */
    protected int rowPosition;

    /**
     * This attribute stores the column position of the animal in the gameboard.
     */
    protected int colPosition;

    /**
     * This attribute stores the number of spaces that the animal can move upward.
     */
    protected int upwardSpace;

    /**
     * This attribute stores the number of spaces that the animal can move downward.
     */
    protected int downwardSpace;

    /**
     * This attribute stores the number of right spaces that the animal can move.
     */
    protected int rightSpace;

    /**
     * This attribute stores the number of left spaces that the animal can move.
     */
    protected int leftSpace;

    /**
     * This attribute stores true value if the animal is captured, false otherwise.
     */
    protected boolean captured;
    
}