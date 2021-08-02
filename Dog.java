/** This Dog class is a subclass of the Animal class that represents the dogPieces animal piece in the game.
 *
 *@author Pierre Vincent C. Hernandez
 *@author Matthew James D. Villarica
 */
public class Dog extends Animal {
    
    /** This constructor instantiates a Dog object given it's starting position in row and column format, and player side.
     *
     *@param playerNum can be 1 or 2 depending on which side the Dog is on
     *@param startingRow the row component of the starting position of the Dog
     *@param startingCol the column component of the starting position of the Dog
     */
    public Dog(int playerNum, int startingRow, int startingCol) {
        super(playerNum, startingRow, startingCol, 4); // RANK = 4
    }
   
    
    
    /** This method returns the string representation of the Dog object 
     * and its rank.
     *  
     * @return string representation of the Dog object and its rank
     */
    @Override
    public String toString() {
        return "DOG ( rank: 4 )";
    }
}
