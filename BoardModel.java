/**
 * This class is the model component of the game in the MVC architecture. It contains the data,
 * states, and behaviors of each object in the game that will be visually represented in the GUI.
 * 
 * @author Pierre Vincent Hernandez
 * @author Matthew James D. Villarica
 */
public class BoardModel {
    
    /**
     * This constructor instantiates the board, terrain and animals in their respective starting
     * positions.
     */
    public BoardModel () {
        boardTiles = new Tiles(); // instantiate Tiles object
        animalPieces = new Animal[8][2]; // allot size/number of animal pieces
        // instantiate the animal pieces
        initRedPieces(); // red pieces
        initBluePieces(); // blues pieces

        // assign animal pieces to designated starting terrain tile
        for(int row = 0; row < 8; row++)
            for(int col = 0; col < 2; col++)
                boardTiles.getTerrains()[animalPieces[row][col].getRow()][animalPieces[row][col].getCol()].
                    animalMovesIn(animalPieces[row][col]);
    }

    /**
     * This method returns the 8 x 2 array of Animals.
     * @return the 8 x 2 arrray of Animals.
     */
    public Animal[][] getAnimals() {
        return animalPieces;
    }

    /**
     * This method returns the Tiles of the BoardModel, which contains the 9 x 7 array of Terrain.
     * @return the Tiles attribute of the BoardModel, boardTiles
     */
    public Tiles getTiles() {
        return boardTiles;
    }

    /**
     * This method properly disposes of the animalPieces and boardTiles attributes, used at the end of the game.
     */
    public void setToNull() {
        animalPieces = null;
        boardTiles = null;
    }

    /**
     * This method instantiates the 8 x 2 Animal array with the proper sub classes for red team with
     * their respective starting row and column position on the board.
     */
    private void initRedPieces() {
        // red team
        animalPieces[0][0] = new Mouse(1, 2, 0);
        animalPieces[1][0] = new Cat(1, 1, 5);
        animalPieces[2][0] = new Wolf(1, 2, 4);
        animalPieces[3][0] = new Dog(1, 1, 1);
        animalPieces[4][0] = new Leopard(1, 2, 2);
        animalPieces[5][0] = new Tiger(1, 0, 6);
        animalPieces[6][0] = new Lion(1, 0, 0);
        animalPieces[7][0] = new Elephant(1, 2, 6);
    }
    /**
     * This method instantiates the 8 x 2 Animal array with the proper sub classes for blue team
     * with their proper row and column position on the board.
     */
    private void initBluePieces() {
        // blue team
        animalPieces[0][1] = new Mouse(2, 6, 6);
        animalPieces[1][1] = new Cat(2, 7, 1);
        animalPieces[2][1] = new Wolf(2, 6, 2);
        animalPieces[3][1] = new Dog(2, 7, 5);
        animalPieces[4][1] = new Leopard(2, 6, 4);
        animalPieces[5][1] = new Tiger(2, 8, 0);
        animalPieces[6][1] = new Lion(2, 8, 6);
        animalPieces[7][1] = new Elephant(2, 6, 0);
    }
    
    /**
     * This attribute contains the 8 x 2 array of Animals for both players
     * ,which will represent the behavior of the animal pieces in the GUI.
     */
    private Animal[][] animalPieces;

    /**
     * This attribute contains the Tiles of the BoardModel which contains the array of Terrain.
     */
    private Tiles boardTiles;
}
