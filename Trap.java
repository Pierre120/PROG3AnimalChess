public class Trap extends Terrain {
    
    /**This constructor instantiates a Trap given the player number.
     * 
     * @param playerSide this can be 1 or 2 depending on which side of the board the Trap(s)
     * is positioned in.
     */
    public Trap(int playerSide) {
        super(playerSide);
    }


    /** This method determines whether the current terrain is a Trap object.
     * 
     * @return true if the object is a Trap, false otherwise
     */
    @Override
    public boolean isTrap() {
        return true;
    }
}
