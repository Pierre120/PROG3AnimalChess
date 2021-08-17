public class Mouse extends Animal {

    /** This constructor instantiates a mouse object given it's starting position in row and column format, and player side.
     *
     *@param playerNum can be 1 or 2 depending on which side the Mouse is on
     *@param startingRow the row component of the starting position of the mouse
     *@param startingCol the column component of the starting position of the Mouse
     */
    public Mouse(int playerNum, int startingRow, int startingCol) {
        super(playerNum, startingRow, startingCol, 1); // RANK = 1
    }
    

    /** This method determines the terrain that the animal is going to move into. 
     * It's going to check if it is a land, a river, a trap or an animal den (and who's animal den).
     * 
     * @param specificArea the area that the animal is checking if it could move into it
     * @return true if terrain is not its own animal den, false otherwise
     */
    @Override
    public boolean isValidTerrain(Terrain specificArea) {
        if(specificArea.isAnimalDen() && specificArea.getOwner() == getPlayerSide())
            return false;
        return true;
    }


    /** This method determines if an enemy animal is capturable by the animal based on 
     * the animal rank and current terrain.
     * 
     * @param enemyAnimal the enemy animal 
     * @return true if the enemy animal is capturable, false otherwise
     */
    @Override
    public boolean canCapture(Animal enemyAnimal) {
        if(enemyAnimal != null && enemyAnimal.getPlayerSide() != getPlayerSide()
        && (enemyAnimal.getRank() == 8 || enemyAnimal.isCaptured()))  
            return true;
        return false;
        
    }


    /** This method determines if an enemy animal is capturable by the animal based on 
     * the animal rank and current terrain.
     * 
     * @param enemyAnimal the enemy animal 
     * @return true if the enemy animal is capturable, false otherwise
     */
    public boolean canCapture(Animal enemyAnimal, Terrain toOccupy, Terrain currentArea) {
        if(toOccupy.isRiver())
            return canCapture(enemyAnimal) && currentArea.isRiver();
        else if(toOccupy.isLand())
            return canCapture(enemyAnimal) && currentArea.isLand();
        
        return canCapture(enemyAnimal);
    }




    /** This method determines if the player's animal could occupy the terrain it will go to.
     * 
     * @param specificArea
     * @return true if the area doesn't have any animal occupying it or if it can capture the animal occupying the area, otherwise false.
     */
    public boolean canOccupy(Terrain toOccupy, Terrain currentArea) {
        if(!toOccupy.getState() || (toOccupy.getState() && canCapture(toOccupy.getAnimal(), toOccupy, currentArea)))
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
    @Override
    public boolean canMoveUp(Terrain[][] gameArea) {
        // checks if index out of bounce
        if(colPosition + 1 <= 6) {

            if(isValidTerrain(gameArea[rowPosition][colPosition + 1]) && 
                canOccupy(gameArea[rowPosition][colPosition + 1], gameArea[rowPosition][colPosition])) {
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
    @Override
    public boolean canMoveDown(Terrain[][] gameArea) {
        // checks if index out of bounce
        if(colPosition - 1 >= 0) {
            
             if(isValidTerrain(gameArea[rowPosition][colPosition - 1]) && 
                canOccupy(gameArea[rowPosition][colPosition - 1], gameArea[rowPosition][colPosition])) {
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
    @Override
    public boolean canMoveLeft(Terrain[][] gameArea) {
        // checks if index out of bounce
        if(rowPosition - 1 >= 0) {
            
            if(isValidTerrain(gameArea[rowPosition - 1][colPosition]) && 
                canOccupy(gameArea[rowPosition - 1][colPosition], gameArea[rowPosition][colPosition])) {
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
    @Override
    public boolean canMoveRight(Terrain[][] gameArea) {
        // checks if index out of bounce
        if(rowPosition + 1 <= 8) {
            
            if(isValidTerrain(gameArea[rowPosition + 1][colPosition]) && 
                canOccupy(gameArea[rowPosition + 1][colPosition], gameArea[rowPosition][colPosition])) {
                rightSpace = 1;
                return true;
            }
            
        }
        return false; // animal can't move to the right
        
    }


    /** This method returns the string representation of the Mouse object 
     * and its rank.
     *  
     * @return string representation of the Mouse object and its rank
     */
    @Override
    public String toString() {
        return "MOUSE ( rank: 1 )";
    }

}
