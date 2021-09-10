/**This Land class is a subClass of Terrain class which represents the land terrain/area of the gameboard.
 * @author Pierre Vincent Hernandez
 * @author Matthew James Villarica
 */
public class Land extends Terrain {

    /**This constructor instantiates a Land object that has no animal objects occupying or inhabiting it.
     * 
     * @param playerSide the value of this is always 0 since this Land terrain is neutral
     */
    public Land(int playerSide) {
        super(playerSide);
    }
    /**This constructor instantiates a Land object that happens to contain an Animal already at the start of the game.
    *
    *@param startingAnimal the animal that is currently on this land 
    */
    public Land(Animal startingAnimal) {
        super(startingAnimal);
    }


    /** This method determines whether the current terrain is a Land object.
     * 
     * @return true if the object is a Land, false otherwise
     */
    @Override
    public boolean isLand() {
        return true;
    } 
}