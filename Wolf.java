/** This Wolf class is a subclass of the Animal class that represents the WolfPieces animal piece in the game.
 *
 *@author Pierre Vincent C. Hernandez
 *@author Matthew James D. Villarica
 */
public class Wolf extends Animal {
    
    /** This constructor instantiates a Wolf object given it's starting position in row and column format, and player side.
     *
     *@param playerNum can be 1 or 2 depending on which side the Wolf is on
     *@param startingRow the row component of the starting position of the Wolf
     *@param startingCol the column component of the starting position of the Wolf
     */
    public Wolf(int playerNum, int startingRow, int startingCol) {
        super(playerNum, startingRow, startingCol, 3); // RANK = 3
    }
   
    
    
    /** This method returns the string representation of the Wolf object 
     * and its rank.
     *  
     * @return string representation of the Wolf object and its rank
     */
    @Override
    public String toString() {
        return "WOLF ( rank: 3 )";
    }
}
