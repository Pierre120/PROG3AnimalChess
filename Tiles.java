public class Tiles {
    
    public Tiles () {
        terrainTiles = new Terrain[9][7];
        //instantiate the elements of terrainTiles with designated Terrain objects
        instantiateTiles();
    }

    public Terrain[][] getTerrains() {
        return terrainTiles;
    }

    private void instantiateTiles() {
        int r;
        int c;
        int p = 0;

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
                // instantiate Land terrains to the rest of the tiles
                else
                    terrainTiles[r][c] = new Land(0);
            }
        }
    }
    
    private Terrain[][] terrainTiles;
}
