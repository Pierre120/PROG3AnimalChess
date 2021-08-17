/**
 * This Trap is a sub class of Terrain. When an enemy animal gets in, it makes that animal vulnerable.
 * Thus when that opposing animal is trapped it can be captured by any animal.
 * 
 * @author Pierre Vincent Hernandez
 * @author Matthew James Villarica
 */
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


    /** This method assigns a new animal object to the animalInhabiting attribute
     * and updates its occupation status to true to show that the new animal 
     * object will be moving in or occupying or inhabiting the current terrain.
     * 
     * @param newAnimal the new animal object who will be occupying the terrain
     */
    @Override
    public void animalMovesIn(Animal newAnimal) {
        animalInhabiting = newAnimal;
        /* Can also be:
                if(newAnimal.getPlayerSide() != PLAYER_SIDE)

                but PLAYER_SIDE needs to be protected in Terrain class
                for it to be inherited
            */
        if(newAnimal.getPlayerSide() != this.getOwner())
            animalInhabiting.setTrapState(true);
            
        occupied = true;
    }


    /** This method removes the animal object who is occupying or inhabiting
     * it and updates its occupation status to false to show that an animal object
     * left or moved out of the terrain.
     */
    @Override
    public void animalMovesOut() {

        if(animalInhabiting.getPlayerSide() != this.getOwner())
            animalInhabiting.setTrapState(false);
        animalInhabiting = null;
        occupied = false;
    }
}
