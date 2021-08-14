public class Board {
    
    public Board () {
        boardTiles = new Tiles(); // instantiate Tiles object
        animalPieces = new Animal[3][2]; // allot size/number of animal pieces
        // instantiate the animal pieces
        // player 1
        animalPieces[0][0] = new Dog(1, 1, 1);
        animalPieces[1][0] = new Lion(1, 0, 0);
        animalPieces[2][0] = new Mouse(1, 3, 1);
        // player 2
        animalPieces[0][1] = new Dog(2, 7, 5);
        animalPieces[1][1] = new Lion(2, 8, 6);
        animalPieces[2][1] = new Mouse(2, 4, 1);
        // assign animal pieces to designated starting terrain tile
        boardTiles.getTerrains()[1][1].animalMovesIn(animalPieces[0][0]);
        boardTiles.getTerrains()[0][0].animalMovesIn(animalPieces[1][0]);
        boardTiles.getTerrains()[7][5].animalMovesIn(animalPieces[0][1]);
        boardTiles.getTerrains()[8][6].animalMovesIn(animalPieces[1][1]);

        boardTiles.getTerrains()[3][1].animalMovesIn(animalPieces[2][0]);
        boardTiles.getTerrains()[4][1].animalMovesIn(animalPieces[2][1]);
    }

    public Animal[][] getAnimals() {
        return animalPieces;
    }

    public Tiles getTiles() {
        return boardTiles;
    }
    
    private Animal[][] animalPieces;
    private Tiles boardTiles;
}
