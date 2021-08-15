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
    

    /** This method determines if an enemy animal is capturable by the animal based on 
     * the animal rank and current terrain.
     * 
     * @param enemyAnimal the enemy animal 
     * @return true if the enemy animal is capturable, false otherwise
     */
    @Override
    public boolean canCapture(Animal enemyAnimal) {
        if(enemyAnimal != null && enemyAnimal.getPlayerSide() != getPlayerSide() &&
            (enemyAnimal.getRank() == RANK || enemyAnimal.getRank() == 8 )) 
            return true;
        return false;
    }
}
