public class Board {
    
    public Board () {
        boardTiles = new Tiles(); // instantiate Tiles object
        animalPieces = new Animal[8][2]; // allot size/number of animal pieces
        // instantiate the animal pieces
        // red team
        animalPieces[0][0] = new Mouse(1, 2, 0);
        animalPieces[1][0] = new Cat(1, 1, 5);
        animalPieces[2][0] = new Wolf(1, 2, 4);
        animalPieces[3][0] = new Dog(1, 1, 1);
        animalPieces[4][0] = new Leopard(1, 2, 2);
        animalPieces[5][0] = new Tiger(1, 0, 6);
        animalPieces[6][0] = new Lion(1, 0, 0);
        animalPieces[7][0] = new Elephant(1, 2, 6);
        // blue team
        animalPieces[0][1] = new Mouse(2, 6, 6);
        animalPieces[1][1] = new Cat(2, 7, 1);
        animalPieces[2][1] = new Wolf(2, 6, 2);
        animalPieces[3][1] = new Dog(2, 7, 5);
        animalPieces[4][1] = new Leopard(2, 6, 4);
        animalPieces[5][1] = new Tiger(2, 8, 0);
        animalPieces[6][1] = new Lion(2, 8, 6);
        animalPieces[7][1] = new Elephant(2, 6, 0);
        // assign animal pieces to designated starting terrain tile
        boardTiles.getTerrains()[1][1].animalMovesIn(animalPieces[0][0]);
        boardTiles.getTerrains()[0][0].animalMovesIn(animalPieces[1][0]);
        boardTiles.getTerrains()[7][5].animalMovesIn(animalPieces[0][1]);
        boardTiles.getTerrains()[8][6].animalMovesIn(animalPieces[1][1]);
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
