/** This Terrain class is the parent class of 4 types of terrain sub-classes.
 * It is being occupied by animal objects.
 * 
 * @author Matthew James Villarica
 * @author Pierre Vincent Hernandez
 */
public class Terrain {
    
    /** This constructor initializes a Terrain object that has no 
     * occupants or inhabitants yet in its area.
     */
    public Terrain(int playerSide) {
        animalInhabiting = null;
        occupied = false;
        PLAYER_SIDE = playerSide;
    }


    /** This constructor initializes a Terrain object that has an
     * occupant or inhabitant in its area. This is used at the
     * start of the game since each Animal objects has a starting
     * Terrain position.
     * 
     * @param startingAnimal the animal object that will be the first to occupy the terrain
     */
    public Terrain(Animal startingAnimal) {
        animalInhabiting = startingAnimal;
        occupied = true;
        PLAYER_SIDE = 0; // neutral ownership of terrain
    }


    /** This method assigns a new animal object to the animalInhabiting attribute
     * and updates its occupation status to true to show that the new animal 
     * object will be moving in or occupying or inhabiting the current terrain.
     * 
     * @param newAnimal the new animal object who will be occupying the terrain
     */
    public void animalMovesIn(Animal newAnimal) {
        animalInhabiting = newAnimal;
        occupied = true;
    }


    /** This method removes the animal object who is occupying or inhabiting
     * it and updates its occupation status to false to show that an animal object
     * left or moved out of the terrain.
     */
    public void animalMovesOut() {
        animalInhabiting = null;
        occupied = false;
    }
    

    /** This method determines whether the current terrain is an AnimalDen object.
     * 
     * @return true if the object is an AnimalDen, false otherwise
     */
    public boolean isAnimalDen() {
        return false;
    }


    /** This method determines whether the current terrain is a Trap object.
     * 
     * @return true if the object is a Trap, false otherwise
     */
    public boolean isTrap() {
        return false;
    }


    /** This method determines whether the current terrain is a Land object.
     * 
     * @return true if the object is a Land, false otherwise
     */
    public boolean isLand() {
        return false;
    }


    /** This method determines whether the current terrain is a River object.
     * 
     * @return true if the object is a River, false otherwise
     */
    public boolean isRiver() {
        return false;
    }


    /** This method returns the animal object who is currently inhabiting or occupying the terrain.
     * 
     * @return the address of the animal object who is currently inhabiting or occupying the terrain, otherwise null
     */
    public Animal getAnimal() {
        return animalInhabiting;
    }


    /** This method returns the occupation status of the terrain.
     * 
     * @return true if the terrain is occupied, otherwise false.
     */
    public boolean getState() {
        return occupied;
    }


    /** This method returns the player who owns the terrain.
     * 
     * @return player number who owns the terrain
     */
    public int getOwner() {
        return PLAYER_SIDE;
    }


    /** This attribute stores the player number who is owning the terrain.
     */
    private final int PLAYER_SIDE;

    /** This attribute stores a boolean value of the occupation status of the terrain object.
     * It stores true if an animal object is inhabiting it, otherwise it stores false.
     */
    private boolean occupied;

    /** This attribute stores the address of the animal object who is inhabiting the
     * terrain.
     */
    private Animal animalInhabiting;
}
