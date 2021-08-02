/**
 * This AnimalDen is a sub class of Terrain. When an enemy animal gets in, the game is over.
 * 
 * @author Pierre Vincent Hernandez
 * @author Matthew James Villarica
 */
public class AnimalDen extends Terrain {
    
    /**This constructor instantiates an AnimalDen given the player number.
     * 
     * @param playerSide this can be 1 or 2 depending on which side of the board the AnimalDen
     * is positioned in.
     */
    public AnimalDen(int playerSide) {
        super(playerSide);
    }


    /** This method determines whether the current terrain is an AnimalDen object.
     * 
     * @return true if the object is an AnimalDen, false otherwise
     */
    @Override
    public boolean isAnimalDen() {
        return true;
    }

}
