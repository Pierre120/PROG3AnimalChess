/**
 * This class represents tiles on the gameboard, which contains the specific terrain
 * (trap, animal den, river, land) that animals can occupy.
 * 
 * @author Pierre Vincent Hernandez
 * @author Matthew James D. Villarica
 */
public class Tiles {
    
    /**
     * This constructor instantiates the Tiles with a 9 x 7 array of Terrain.
     */
    public Tiles () {
        terrainTiles = new Terrain[9][7];
        //instantiate the elements of terrainTiles with designated Terrain objects
        instantiateTiles();
    }
    /**
     * This method returns the 9x7 array of Terrain.
     * @return 9 x 7 array of Terrain.
     */
    public Terrain[][] getTerrains() {
        return terrainTiles;
    }

    /**
     * This method is a supporting method of the constructor, it instantiates the
     * 9 x 7 array of terrain with the specific sub class (type of Terrain) of
     * each space in the tiles of the board (animal den, river, land, or trap).
     */
    private void instantiateTiles() {
        int r;
        int c;
        int p = 1;

        for(r = 0; r < 9; r++) {
            for(c = 0; c < 7; c++) {
                // instantiate Den for both players
                if((r == 0 || r == 8) && c == 3) {
                    terrainTiles[r][c] = new AnimalDen(p);
                    p++;
                }
                // instantiate River terrains
                else if(r >= 3 && r <= 5 && c != 0 && c != 3 && c != 6) 
                    terrainTiles[r][c] = new River(0);
                //TRAP POSITIONS: 0 2, 0 4, 1 3, 8 2, 8 4, 7 3
                else if(r == 0 && (c == 2 || c == 4) || r == 1 && c == 3)
                    terrainTiles[r][c] = new Trap(1);
                else if(r == 8 && (c == 2 || c == 4) || r == 7 && c == 3 )
                    terrainTiles[r][c] = new Trap(2);
                
                // instantiate Land terrains to the rest of the TILES
                else
                    terrainTiles[r][c] = new Land(0);
            }
        }
    }
    
    /**
     * This attribute is the 9 x 7 array of Terrain.
     */
    private Terrain[][] terrainTiles;
}
