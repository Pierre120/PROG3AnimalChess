/** This River class is a sub class of Terrain. Lions and tigers can jump over them, and mice can move
 * on rivers like normal.
 *@author Pierre Vincent Hernandez
 *@author Matthew James Villarica
 */
public class River extends Terrain {

    /** This constructor instantiates a River object that has no animal objects occupying or inhabiting it.
     * 
     * @param playerSide the value of this is always zero since this River terrain is neutral
     */
    public River(int playerSide) {
        super(playerSide);
    }


    /** This method determines whether the current terrain is a River object.
     * 
     * @return true if the object is a River, false otherwise
     */
    @Override
    public boolean isRiver() {
        return true;
    }
}