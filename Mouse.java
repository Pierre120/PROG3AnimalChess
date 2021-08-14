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
    
}
