public class Elephant extends Animal {

   /** This contructor instantiates which side the Elephant object is, and where its starting
     * position in row and column format..
     * 
     * @param playerNum player side the animal is on, which can be 1 (player 1) or 2 (player 2)
     * @param startingRow the starting row position of the animal in the 9x7 gamebaord
     * @param startingCol the starting col position of the animal in the 9x7 gameboard
     */
    public Elephant(int playerNum, int startingRow, int startingCol) {
        super(playerNum, startingRow, startingCol, 8); // RANK = 8
    }


    /** This method determines if an enemy animal is capturable by the animal based on rank.
     * 
     * @param enemyAnimal the enemy animal 
     * @return true if the enemy animal is capturable, false otherwise
     */
    @Override
    public boolean canCapture(Animal enemyAnimal) {
        if(enemyAnimal != null && enemyAnimal.getRank() > 1 && 
            enemyAnimal.getRank() <= RANK && enemyAnimal.getPlayerSide() != getPlayerSide()) 
            return true;
        return false;
    }
}