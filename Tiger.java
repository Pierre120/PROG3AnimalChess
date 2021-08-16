/**
 * This Tiger class is a subclass of Animal class that represents the Tiger piece on the gameboard.
 * 
 * @author Pierre Vincent Hernandez
 * @author Matthew James Villarica
 */
public class Tiger extends Animal { 
    
    /** This contructor instantiates which side the Tiger object is, and where its starting
     * position in row and column format..
     * 
     * @param playerNum player side the animal is on, which can be 1 (player 1) or 2 (player 2)
     * @param startingRow the starting row position of the animal in the 9x7 gamebaord
     * @param startingCol the starting col position of the animal in the 9x7 gameboard
     */
    public Tiger(int playerNum, int startingRow, int startingCol) {
        super(playerNum, startingRow, startingCol, 6); // RANK = 6
    }

    /** This method determines if the animal can move up one space on the board.
     * 
     * @param gameArea the 9x7 2d array of Terrain that represents the gameboard 
     * @return false if the position does not exist in the game board or if there is an animal that can't be captured in
     * the way, true otherwise
     * 
     */
    @Override
    public boolean canMoveUp(Terrain[][] gameArea) {
        // checks if index out of bounce
        if(colPosition + 1 <= 6) {
            upwardSpace = 1; 

         
            // checks if terrain to be occupied is a River terrain. If true it
            // increments the spaces it could move upwards while the terrain
            // checked is still a river and there is no mouse. Otherwise, skip this.
            while(gameArea[rowPosition][colPosition + upwardSpace].isRiver()) {
                // checks if there is a mouse
                if(gameArea[rowPosition][colPosition + upwardSpace].getState()) {
                    upwardSpace = 0; // no valid upward movement
                    return false; // can't move up
                }

                upwardSpace++;
            }

            // checks if terrain can be captured or occupied
            if(isValidTerrain(gameArea[rowPosition][colPosition + upwardSpace]) 
                && canOccupy(gameArea[rowPosition][colPosition + upwardSpace]))
                return true;

            // if it reached this part then animal can't move up
            upwardSpace = 0;
        }
        
        return false; // index out of bounds or animal can't move up
    }


     /** This method determines if the animal can move down one space on the board.
     * 
     * @param gameArea the 9x7 2d array that represents the gameboard 
     * @return false if the position does not exist in the game board or if there is an animal that can't be captured in
     * the way, true otherwise
     * 
     */
    @Override
    public boolean canMoveDown(Terrain[][] gameArea) {
        // checks if index out of bounce
        if(colPosition - 1 >= 0) {
            downwardSpace = 1; 
            
            // checks if terrain to be occupied is a River terrain. If true it
            // increments the spaces it could move upwards while the terrain
            // checked is still a river and there is no mouse. Otherwise, skip this.
            while(gameArea[rowPosition][colPosition - downwardSpace].isRiver()) {
                // checks if there is a mouse
                if(gameArea[rowPosition][colPosition - downwardSpace].getState()) {
                    downwardSpace = 0; // no valid upward movement
                    return false; // can't move up
                }

                downwardSpace++;
            }

            // checks if terrain can be captured or occupied
            if(isValidTerrain(gameArea[rowPosition][colPosition - downwardSpace]) 
                && canOccupy(gameArea[rowPosition][colPosition - downwardSpace]))
                return true;

            // if it reached this part then animal can't move 
            downwardSpace = 0;
        } 
        // animal can't move down
       
        return false;

    }


    /** This method determines if the animal can move left once on the gameboard.
     * 
     * @param gameArea the 9x7 2d array that represents the gameboard 
     * @return false if the position does not exist in the game board (index out of bounds) or if there is an animal that can't be captured in
     * the way, true otherwise
     * 
     */
    @Override
    public boolean canMoveLeft(Terrain[][] gameArea) {
        // checks if index out of bounce
        if(rowPosition - 1 >= 0) {
            leftSpace = 1; 
            
            // checks if terrain to be occupied is a River terrain. If true it
            // increments the spaces it could move upwards while the terrain
            // checked is still a river and there is no mouse. Otherwise, skip this.
            while(gameArea[rowPosition - leftSpace][colPosition].isRiver()) {
                // checks if there is a mouse
                if(gameArea[rowPosition - leftSpace][colPosition].getState()) {
                    leftSpace = 0; // no valid upward movement
                    return false; // can't move up
                }

                leftSpace++;
            }

            // checks if terrain can be captured or occupied
            if(isValidTerrain(gameArea[rowPosition - leftSpace][colPosition]) 
                && canOccupy(gameArea[rowPosition - leftSpace][colPosition]))
                return true;

            // if it reached this part then animal can't move up
             leftSpace = 0;
        }
        // animal can't move to the left
     
        return false;
        
    }


    /** This method determines if the animal can move right once on the gameboard.
     * 
     * @param gameArea the 9x7 2d array that represents the gameboard 
     * @return false if the position does not exist in the game board (index out of bounds) or if there is an animal that can't be captured in
     * the way, true otherwise
     * 
     */
    @Override
     public boolean canMoveRight(Terrain[][] gameArea) {
        // checks if index out of bounce
        if(rowPosition + 1 <= 8) {
            rightSpace = 1; 
            
            // checks if terrain to be occupied is a River terrain. If true it
            // increments the spaces it could move upwards while the terrain
            // checked is still a river and there is no mouse. Otherwise, skip this.
            while(gameArea[rowPosition + rightSpace][colPosition].isRiver()) {
                // checks if there is a mouse
                if(gameArea[rowPosition + rightSpace][colPosition].getState()) {
                    rightSpace = 0; // no valid upward movement
                    return false; // can't move up
                }

                rightSpace++;
            }

            // checks if terrain can be captured or occupied
            if(isValidTerrain(gameArea[rowPosition + rightSpace][colPosition]) 
                && canOccupy(gameArea[rowPosition + rightSpace][colPosition]))
                return true;

            // if it reached this part then animal can't move up
            rightSpace = 0;
          
            
        }
        // animal can't move to the right
        return false;
        
    }


    /** This method returns the string representation of the Tiger object 
     * and its rank.
     * 
     * @return string representation of the Tiger object (as a name) and its rank
     */
    @Override
    public String toString() {
        return "TIGER ( rank: 6 )";
    }

}
