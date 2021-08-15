/** This Leopard class is a subclass of the Animal class that represents the LeopardPieces animal piece in the game.
 *
 *@author Pierre Vincent C. Hernandez
 *@author Matthew James D. Villarica
 */
public class Leopard extends Animal {
    
    /** This constructor instantiates a Leopard object given it's starting position in row and column format, and player side.
     *
     *@param playerNum can be 1 or 2 depending on which side the Leopard is on
     *@param startingRow the row component of the starting position of the Leopard
     *@param startingCol the column component of the starting position of the Leopard
     */
    public Leopard(int playerNum, int startingRow, int startingCol) {
        super(playerNum, startingRow, startingCol, 5); // RANK = 5
    }
   
    
    
    /** This method returns the string representation of the Leopard object 
     * and its rank.
     *  
     * @return string representation of the Leopard object and its rank
     */
    @Override
    public String toString() {
        return "LEOPARD ( rank: 5 )";
    }
}
