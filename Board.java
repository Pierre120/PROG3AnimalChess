public class Board {
    
    public Board () {
        boardTiles = new Tiles(); // instantiate Tiles object
        animalPieces = new Animal[2][8]; // allot size/number of animal pieces
        // instantiate the animal pieces
        // assign animal pieces to designated starting terrain tile
    }

    public Animal getAnimal(int playerSide, int animalRank) {
        return animalPieces[playerSide][animalRank];
    }

    public Tiles getTiles() {
        return boardTiles;
    }
    
    private Animal[][] animalPieces;
    private Tiles boardTiles;
}
