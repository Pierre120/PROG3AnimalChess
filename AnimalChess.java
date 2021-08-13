import java.util.Random; // for randomizing pieces
import java.util.Scanner; // for getting inputs from players

/**
 * This class is the driver class for the program Animal Chess.
 * This executes the game itself and handles the game mechanics.
 * 
 * @author Matthew James Villarica
 * @author Pierre Vincent Hernandez
 */
public class AnimalChess {
    public static void main (String[] args) {
        Game mechanic = new Game();
        mechanic.executeGame();
        
    }
        
    //     Game animalChess = new Game();
    //     Random rand = new Random();
    //     Scanner sc = new Scanner(System.in);
    //     AnimalChess mechanic = new AnimalChess(); // object responsible for the game mechanics
    //     GameDisplay screen = new GameDisplay(); // object responsible for displaying
    //     final int[] playerSide = new int[2]; // contains player number
    //     final String[] teamColor = new String [] {"Red", "Blue"}; // index[0] for red team and index[1] for blue team
    //     int personNum = 0; // temporarily assigns Player 1 to person 1
    //     int numChoice; // will be used throughout the game for number inputs of players
    //     int k;
        
        

    //     // temporary variables
    //     int[] playerPiece = new int[2]; // to store the index values that the players got. 
    //                                     // Will be used as index in the animalChoices
    //     int[] randomIndexes = new int[] {-1, -1}; // contains the random index values for the players to choose
    //     int[] validChoices = new int[] {1, 2}; // valid number inputs that the player could choose during the start of the game
    //     Animal[] animalChoices = new Animal[2]; // contains the animal objects that the players will choose at the start of the game
    //     animalChoices[0] = new Dog(1, 0, 0);
    //     animalChoices[1] = new Lion(1, 0, 0);


    //     // ===================== START OF GAME ==============================================

    //     // randomizes the index for the animalChoices for the players to choose
    //     mechanic.randomizePieces(randomIndexes, rand, 2);

    //     // asks the players for a random animal piece to be used 
    //     // in determining who will be player 1
    //     for(k = 0; k < 2; k++) {
    //         do {
    //             screen.displayRandomAnimalChoice(2, validChoices);
                
    //             do {
    //                 System.out.print("Person " + (k+1) + " pick a random animal piece: ");
    //                 numChoice = sc.nextInt();

    //                 if(numChoice < 1 || numChoice > 2)
    //                     System.out.println("Invalid Input!");
    //             } while(numChoice < 1 || numChoice > 2); // to avoid index out of bounds
                
    //             if(validChoices[numChoice - 1] == 0)
    //                 System.out.print("Piece is already taken!\n\n");
                
    //         }while(validChoices[numChoice - 1] == 0); 
    //         // asks user for a valid input for a random animal piece

    //         validChoices[numChoice - 1] = 0;
    //         playerPiece[k] = randomIndexes[numChoice - 1];
    //         System.out.println();
    //     }
        
    //     // assign null to the values that will not be used anymore
    //     randomIndexes = null;
    //     validChoices = null;

    //     // displays the animal pieces that each player got
    //     System.out.println("Animal Pieces:");
    //     for(k = 0; k < 2; k++)
    //         System.out.println("Person " + (k + 1) + " got " + animalChoices[playerPiece[k]]);
        
    //     // changes the Player 1 to person 2 if he/she got a higher ranked animal, otherwise stay to person 1
    //     if(animalChoices[playerPiece[0]].getRank() < animalChoices[playerPiece[1]].getRank())
    //         personNum = 1;

    //     // display who will be player 1
    //     System.out.print("\n\nPerson " + (personNum + 1) + " will be Player 1\n");
    //     System.out.println();

    //     // assign null to the values that will not be used anymore
    //     animalChoices = null;
    //     playerPiece = null;
    //     Animal.resetAnimalCount(); // resets the animal count to zero
        
    //     // asks Player 1 which color side he/she will use
    //     do {
    //         screen.displayTeamColorChoice();
    //         System.out.print("Pick a color Player 1: ");
    //         numChoice = sc.nextInt();
            
    //         if(numChoice < 1 || numChoice > 2)
    //             System.out.print("Invalid Input!\n\n");
            
    //     }while(numChoice < 1 || numChoice > 2);

    //     /*
    //     changes the index for Player 1 (assuming his playerNum is 1) 
    //     to 0 because all the index 0 for Animals and teamColor 
    //     would be for team RED and index 1 for team BLUE, but
    //     if Player 1 chooses BLUE team and his current
    //     playerNum is 1 then there is no need to change */
    //     if(numChoice == 1)
    //         personNum = 0;
    //     else personNum = 1;

    //     playerSide[personNum] = 1; // personNum index for Player 1
    //     playerSide[(personNum + 1) % 2] = 2; // personNum index for PLayer 2



    //     // ===================== INSTATIATION OF THE GAMEBOARD HERE =========================
       
    //     Terrain[][] gameArea = new Terrain[9][7];
    //     // row 1-8 = type of animal (for now, 2 since theres only dog and lion)
    //     // col 1 = Red; col 2 = Blue
    //     Animal[][] animalPieces = new Animal[2][2];

    //     //instantiate board with specific animal and terrain subclasses
    //     mechanic.instantiateBoard(animalPieces, gameArea);

        

    //     // ===================== GAMEPLAY ===================================================
        
    //     boolean movePiece;
    //     int animalPieceIndex;
    //     char charChoice;

    //     // updates the contents of the display board
    //     screen.updateDisplayContents(gameArea);

    //     // loops until one of the animal den is conquered
    //     // as long as neither of the animal dens are captured and 
    //     // neither of the player has no uncaptured animals left, the game goes on 
    //     while(!gameArea[0][3].getState() && !gameArea[8][3].getState() && 
    //         Animal.getAnimalCount(0) > 0 && Animal.getAnimalCount(1) > 0 )
    //     {   
    //         movePiece = false;

    //         while(!movePiece) {
    //             // displays whose turn is it
    //             System.out.print("\nPlayer " + playerSide[personNum] + "'s turn! (" + teamColor[personNum] + ")\n");
    //             screen.displayBoard(); // displays the board
                
    //             screen.displayAvailableAnimals(animalPieces, personNum);
    //             do {
    //                 do {
    //                     System.out.print("Enter animal: ");
    //                     numChoice = sc.nextInt();

    //                     if(numChoice < 1 || numChoice > 2)
    //                         System.out.println("Invalid Input!");
    //                 } while(numChoice < 1 || numChoice > 2); // to avoid index out of bounds
                    
    //                 animalPieceIndex = numChoice - 1;
    //             } while(animalPieces[animalPieceIndex][personNum].isCaptured());
    //             // ^^ asks user for a valid animal piece (it means, not captured)

    //             sc.nextLine(); // gets newline char left in buffer

    //             // updates the contents of board to display the
    //             // available moves of the chosen animal
    //             screen.updateDisplayContents(gameArea, animalPieces[animalPieceIndex][personNum]);
    //             // displays whose turn is it
    //             System.out.print("\nPlayer " + playerSide[personNum] + "'s turn! (" + teamColor[personNum] + ")\n");
    //             screen.displayBoard(); // displays the board

    //             // asks player which direction to move the chosen animal piece
    //             System.out.print("Chosen animal piece: " + animalPieces[animalPieceIndex][personNum] + "\n");
    //             screen.displayMovementKeys(); // display the 'L', 'R', 'U', 'D' and 'X' options for movement controls
    //             do {
    //                 System.out.print("Enter key: ");
    //                 charChoice = sc.nextLine().toUpperCase().charAt(0);

    //                 // cancel move of animal with 'X' input
    //             } while(!mechanic.isValidMove(charChoice, animalPieces[animalPieceIndex][personNum], gameArea));

    //             // checks whether player moved a piece or cancels to move animal piece
    //             movePiece = mechanic.executeMovements(charChoice, gameArea, animalPieces[animalPieceIndex][personNum]);
    //             // updates the contents of gameboard display
    //             screen.updateDisplayContents(gameArea);
    //         }
            
    //         // switch player turn if one of the animal den is not yet captured 
    //         // or either of the players still has animal pieces left
    //         if(!gameArea[0][3].getState() && !gameArea[8][3].getState() && 
    //             Animal.getAnimalCount(0) > 0 && Animal.getAnimalCount(1) > 0)
    //             personNum = (personNum + 1) % 2;
    //     }
        
    //     sc.close(); // closes the scanner

    //     // last display of the current state of the board
    //     screen.displayBoard();
    //     // display winner
    //     screen.displayWinner(playerSide[personNum], teamColor[personNum]);
        
    //     // null the variables used
    //     screen = null;
    //     mechanic = null;
    //     personNum = 0;
    //     numChoice = 0;
    //     animalPieces = null;
    //     gameArea = null;
    //     movePiece = false;
    //     animalPieceIndex = 0;
    //     charChoice = '\0';
       
        
    // }


    // /** This method is responsible for transcribing the input of the players into
    //  * the movement of the chosen animal object.
    //  * 
    //  * @param moveKey character input of player for movement
    //  * @param gameBoard the game board comprised of terrain objects and where the animal object moves
    //  * @param movingAnimal the animal object that is subject to move
    //  * @return
    //  */
    // private boolean executeMovements(char moveKey, Terrain[][] gameBoard, Animal movingAnimal) {
    //     switch(moveKey) {
    //         case 'L':
    //             movingAnimal.moveLeft(gameBoard); // moves animal piece to the left
    //             return true; // successfully moved

    //         case 'R':
    //             movingAnimal.moveRight(gameBoard); // moves animal piece to the right
    //             return true; // successfully moved

    //         case 'U':
    //             movingAnimal.moveUp(gameBoard); // moves animal piece upwards
    //             return true; // successfully moved

    //         case 'D':
    //             movingAnimal.moveDown(gameBoard); // moves animal piece downwards
    //             return true; // successfully moved

    //         default:
    //             return false; // cancels the move of the animal piece or just do nothing
    //     }
    // }


    // /** This method is responsible for checking whether the character movement key
    //  * input of the player is correct and if that move is can be done by the animal
    //  * object.
    //  * 
    //  * @param moveKey character input of player for movement
    //  * @param movingAnimal the animal object that is subject to move
    //  * @param gameBoard the game board comprised of terrain objects and where the animal object moves
    //  * @return
    //  */
    // private boolean isValidMove(char moveKey, Animal movingAnimal, Terrain[][] gameBoard) {
    //     /*
    //     // frequency count quadruples but less conditions to check
    //     // in the if statements
    //     if(moveKey == 'L' && movingAnimal.canMoveLeft(gameBoard))
    //         return false;
        
    //     if(moveKey == 'R' && movingAnimal.canMoveRight(gameBoard))
    //         return false;

    //     if(moveKey == 'U' && movingAnimal.canMoveUp(gameBoard))
    //         return false;

    //     if(moveKey != 'D' && movingAnimal.canMoveDown(gameBoard))
    //         return false;
    //     */
    //     /*
    //     // less frequency count but contains many conditions to check
    //     // character input is correct and chosen movement can be done by animal
    //     if(moveKey == 'L' && movingAnimal.canMoveLeft(gameBoard) ||
    //         moveKey == 'R' && movingAnimal.canMoveRight(gameBoard) ||
    //         moveKey == 'U' && movingAnimal.canMoveUp(gameBoard) ||
    //         moveKey == 'D' && movingAnimal.canMoveDown(gameBoard) ||
    //         moveKey == 'X')
    //         return false;

    //     // neither the character input is correct or the animal 
    //     // can't move in the desired direction
    //     return true;  
    //     */

    //     // true - character input is correct and chosen movement can be done by animal
    //     // false - neither the character input is correct or the animal can't move in the desired direction
    //     return moveKey == 'L' && movingAnimal.canMoveLeft(gameBoard) ||
    //             moveKey == 'R' && movingAnimal.canMoveRight(gameBoard) ||
    //             moveKey == 'U' && movingAnimal.canMoveUp(gameBoard) ||
    //             moveKey == 'D' && movingAnimal.canMoveDown(gameBoard) ||
    //             moveKey == 'X';
    // }


    // /** This method is responsible for checking if the random number
    //  * produced for the player to choose is unique in the array of indexes
    //  * it's going to be stored to.
    //  * 
    //  * @param arrIndex the array of index values
    //  * @param randomIndex the random number produced
    //  * @return
    //  */
    // private boolean isIndexUnique(int[] arrIndex, int randomIndex) {
    //     int r;

    //     for(r = 0; arrIndex[r] != -1 && r < arrIndex.length; r++)
    //         if(arrIndex[r] == randomIndex)
    //             return false; // the randomIndex already exists in the arrIndex
        
    //     return true; // randomIndex is a unique entry in arrIndex
    // }


    // /** This method is responsible for the randomization of index values
    //  * that will be stored in an array for the players to pick from. Those indexes
    //  * are the indexes for the array of Animal objects that is comprised of the 8
    //  * unique animal objects.
    //  * 
    //  * @param pieceIndex
    //  * @param randomizer
    //  * @param numToRandomize
    //  */
    // private void randomizePieces(int[] pieceIndex, Random randomizer, int numToRandomize) {
    //     int num;
    //     int q;
        
    //     for(q = 0; q < numToRandomize; q++) {
    //         do{
    //             num = randomizer.nextInt(numToRandomize);
            
    //         }while(!isIndexUnique(pieceIndex, num));
    //         // continues to produce random number until produced
    //         // random number

    //         pieceIndex[q] = num;
    //     }

    // }


    // /** This method is responsible for instantiating the Animal Chess game board. Instantiates the
    //  * 2D array of Terrain objects that will serve as the board itself and the 2D array of Animal
    //  * objects for the animal pieces of the players. For the animalPieces, row index[0] is for the 
    //  * in red side and row index[1] is for the player in blue side.
    //  * 
    //  * @param animalPieces 2D array of Animal objects of each players
    //  * @param gameArea 2D of Terrain objects that will serve as the board itself
    //  */
    // private void instantiateBoard(Animal[][] animalPieces, Terrain[][] gameArea) {

    //       /*  INDEX GUIDE
    //         RIVERS 31 32 34 35 41 42 44 45 51 52 54 55
    //         DEN 03 83
    //         LAND with lionPieces 00 86
    //         LAND with dogPieces 11 75
    //         LAND with no starting animal - everything else
    //     */

    //     // row 1-8 = type of animal (for now, 1-2 since theres only dog and lion)
    //     // index guide: 0 - DOG, 1 - LION (will reflect rank of animal in MCO2)
    //     // col 0 = Red; col 1 = Blue

    //     int lionPiecesIndex = 0;
    //     int dogPiecesIndex = 0;
	// 	int p = 1;
        
    //     //assigning specific terrain with respective starting animals to each space in gameboard
    //     for(int j = 0; j < 9; j++) {
    //         for(int b = 0; b < 7; b++) {
    //             // instantiate River terrains
    //             if(j >= 3 && j <= 5 && b != 0 && b != 3 && b != 6) 
    //                 gameArea[j][b] = new River(0);
    //             // instantiate Den for both players
    //             else if((j == 0 || j == 8) && (b == 3)) {
    //                 gameArea[j][b] = new AnimalDen(p);
    //                 p++;
    //             } else {

    //                 if((j == 0 && b == 0) || (j == 8 && b == 6)) {
                        
    //                     //instantiate Lion objects on land with startig Lions
    //                     gameArea[j][b] = new Land(animalPieces[1][lionPiecesIndex] = new Lion(lionPiecesIndex + 1, j, b));
                        
    //                     lionPiecesIndex++;
    //                 } else if ((j == 1 && b == 1) || (j == 7 && b == 5)) {
                        
    //                     //isntantiate Dog objects on land with starting Dog
    //                     gameArea[j][b] = new Land(animalPieces[0][dogPiecesIndex] = new Dog(dogPiecesIndex + 1, j, b));
    //                     dogPiecesIndex++;

    //                 }
    //                 else {

    //                     //every other terrain on the board is land without a starting animal
    //                     gameArea[j][b] = new Land(0);
    //                 }
    //             }
    //         }
    //     }

    // }


}
