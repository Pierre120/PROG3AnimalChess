/** This Cat class is a subclass of the Animal class that represents the CatPieces animal piece in the game.
 *
 *@author Pierre Vincent C. Hernandez
 *@author Matthew James D. Villarica
 */
public class Cat extends Animal {
    
    /** This constructor instantiates a Cat object given it's starting position in row and column format, and player side.
     *
     *@param playerNum can be 1 or 2 depending on which side the Cat is on
     *@param startingRow the row component of the starting position of the Cat
     *@param startingCol the column component of the starting position of the Cat
     */
    public Cat(int playerNum, int startingRow, int startingCol) {
        super(playerNum, startingRow, startingCol, 2); // RANK = 2
    }
   
    
    
    /** This method returns the string representation of the Cat object 
     * and its rank.
     *  
     * @return string representation of the Cat object and its rank
     */
    @Override
    public String toString() {
        return "CAT ( rank: 2 )";
    }
}
