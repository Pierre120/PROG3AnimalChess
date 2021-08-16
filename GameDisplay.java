/**
 * This GameDisplay class is responsible for the displays or outputs in the terminal.
 * It is used by the driver class to provide information and UI/UX for the players of 
 * the game.
 * 
 * @author Pierre Vincent Hernandez
 * @author Matthew James Villarica
 */
public class GameDisplay {

	/**	This method updates the contents of the board in the current row.
	 * It is responsible for assigning a character to each terrain of the board.
	 * For Land terrain it is a space character (' ').<br>For River terrain it is a
	 * tilde character ('~').<br>For Animal objects there designated character would 
	 * be their first letter in their names (or animal type).
	 * 
	 * @param rowContents row of the board display
	 * @param rowArea row of the game board
	 * @param currentRow the current row the method is updating
	 */
	public void updateRowContents(char[] rowContents, Terrain[] rowArea, int currentRow) {
		int c;
		// updates all the columns of a specific row
		for(c = 0; c < 7; c++) {
			
			// assigns a specific character of an animal object when the terrain object is occupied
			if(rowArea[c].getState()) {
				// using the rank of the occupying animal object in the terrain object for
				// assigning their designated characters
				switch(rowArea[c].getAnimal().getRank()) {
					case 4: // for Dog Objects
						rowContents[c] = DOG_ICON[rowArea[c].getAnimal().getPlayerSide() - 1];
						break;
					case 6: //for Tiger objects
						rowContents[c] = TIGER_ICON[rowArea[c].getAnimal().getPlayerSide() - 1];
						break;
					case 7: // for Lion Objects
						rowContents[c] = LION_ICON[rowArea[c].getAnimal().getPlayerSide() - 1];
						break;
				}

			} else rowContents[c] = ' '; 
			// if not occupied then it is assumed to be an unoccupied Land terrain

			// checks if that unoccupied terrain might be an Animal Den			
			if(rowArea[c].isAnimalDen() && !rowArea[c].getState())
				rowContents[c] = ANIMAL_DEN_ICON;
			
			// checks if that unoccupied terrain might be a River terrain
			if(rowArea[c].isRiver() && !rowArea[c].getState())
				rowContents[c] = RIVER_ICON;

		}
	}


	/**	This method updates the contents of the whole board.
	 * 
	 * @param gameArea the game board
	 */
	public void updateDisplayContents(Terrain[][] gameArea) {
		int r;
		for(r = 0; r < 9; r++)
			updateRowContents(boardDisplay[r], gameArea[r], r);
	}


	/**	This method updates the contents of the whole board when
	 * there is an animal who's going to move. It's going to update
	 * the character assigned to an area into a pound sign or hashtag '#'
	 * character to show to the players that the chosen animal could 
	 * move to that terrain.
	 * 
	 * @param gameArea the game board
	 * @param animalPiece the animal object that is subject to move
	 */
	public void updateDisplayContents(Terrain[][] gameArea, Animal animalPiece) {  

		// upward moves
		if(animalPiece.canMoveUp(gameArea))
			boardDisplay[animalPiece.getRow()][animalPiece.getCol() + animalPiece.getUpwardSpace()] = MOVE_ICON;

		// downward moves
		if(animalPiece.canMoveDown(gameArea))
			boardDisplay[animalPiece.getRow()][animalPiece.getCol() - animalPiece.getDownwardSpace()] = MOVE_ICON;

		// right moves
		if(animalPiece.canMoveRight(gameArea))
			boardDisplay[animalPiece.getRow() + animalPiece.getRightSpace()][animalPiece.getCol()] = MOVE_ICON;

		// left moves
		if(animalPiece.canMoveLeft(gameArea))
			boardDisplay[animalPiece.getRow() - animalPiece.getLeftSpace()][animalPiece.getCol()] = MOVE_ICON;

	}
	
	
	/**	This method displays the column of the game board.
	 * 
	 * @param boardContents contains character representation of the game board
	 * @param currentCol the current column that is being displayed by this method
	 */
	public void displayContents(char[][] boardContents, int currentCol) {
		int row;
		System.out.print("||");
		for(row = 0; row < 9; row++) {
			System.out.print(" " + boardContents[row][currentCol] + " ");
			
			if(row < 8)
				System.out.print("|");
		}
		
		System.out.println("||");
	}
	
	
	/**	This method displays the contents of the whole board
	 */
	public void displayBoard() {
		int col;
		System.out.println();
		System.out.println("  Red                            Blue");
		System.out.println("======================================="); // borders
		
		// print out the contents per column
		for(col = 6; col >= 0; col--)
			displayContents(boardDisplay, col); 
		
		System.out.println("======================================="); // borders
		System.out.println();
	}


	/**	Displays the random animal choices for the players to pick at
	 * the start of the game. It also displays whether that random animal piece
	 * is already picked by another player.
	 * 
	 * @param numChoices number of Animal objects that is present or implemented in the game
	 * @param validPiece cointains the valid choices of random animal objects. This also helps in determining whether the piece is already taken.
	 */
	public void displayRandomAnimalChoice(int numChoices, int[] validPiece) {
		int p;

		System.out.print("Animal pieces:\n\n");
		for(p = 0; p < numChoices; p++) {
			System.out.print(p+1 + ".) Random animal piece ");

			// checks if random animal piece is already taken
			if(validPiece[p] == 0)
				System.out.print("[X]");

			System.out.println();
		}
		System.out.println();
	}


	/**	This method displays the available team colors that the players could choose to side on.
	 */
	public void displayTeamColorChoice() {
		System.out.println("Team Colors:");
		System.out.print("1.) Red\n2.) Blue\n\n");
	}


	/**	This method displays the available Animal objects that the player could use or move
	 * during the gameplay.
	 * 
	 * @param animalPieces animal objects(pieces) of both players
	 * @param playerTurn index of the player currently playing (contains either 0 (for player in red team), and 1(for player in blue team))
	 */
	public void displayAvailableAnimals(Animal[][] animalPieces, int playerTurn) {
		int a;
		System.out.println("Available animal pieces:");
		for(a = 0; a < animalPieces.length; a++)
			if(animalPieces[a][playerTurn] != null && !animalPieces[a][playerTurn].isCaptured())
				System.out.println("\t" + (a+1) + ".) " + animalPieces[a][playerTurn]);
		
		System.out.println();
	}


	/**	This method displays the assigned character inputs for the movement of an animal object.
	 */
	public void displayMovementKeys() {
		System.out.println("Keys for movement:");
		System.out.println("\tL - Left");
		System.out.println("\tR - Right");
		System.out.println("\tU - Up");
		System.out.println("\tD - Down");
		System.out.println("\tX - Cancel");
		System.out.println();
	}


	/**	This method displays the winner of the Animal Chess round.
	 * 
	 * @param playerSide the player who won (could be player 1 or 2)
	 * @param teamColor the color that the winning player chose or sided on
	 */
	public void displayWinner(int playerSide, String teamColor) {
		System.out.println("CONGRATULATIONS! Player " + playerSide + " ( " + teamColor + " )");
		System.out.print("You have successfully conquered thy land.\n\n");
	}
	
	
	/** This constant attribute holds the characters icons for the whole game board.
	 * This is used for displaying the game board.
	 */
	private char[][] boardDisplay = new char[9][7];

	/**	This constant attribute contains the character icon for siginifying a terrain is an Animal Den
	 */
	private final char ANIMAL_DEN_ICON = '@';

	/** This constant attribute contains the character icon for signifying a terrain is a River object
	 */
	private final char RIVER_ICON = '~';

	/**	This constant attribute holds the character icon for the Mouse objects for both players.
	 * Index [0] for player in red team, and index [1] for player in blue team.
	 */
	private final char[] MOUSE_ICON = {'M', 'm'};

	/**	This constant attribute holds the character icon for the Cat objects for both players.
	 * Index [0] for player in red team, and index [1] for player in blue team.
	 */
	private final char[] CAT_ICON = {'C', 'c'};
	
	/**	This constant attribute holds the character icon for the Wolf objects for both players.
	 * Index [0] for player in red team, and index [1] for player in blue team.
	 */
	private final char[] WOLF_ICON = {'W', 'w'};

	/** This constant attribute holds the character icon for the Dog objects for both players. 
	 * Index [0] for player in red team, and index [1] for player in blue team.
	 */
	private final char[] DOG_ICON = {'D','d'};
	 
	/**	This constant attribute holds the character icon for the Leopard objects for both players.
	 * Index [0] for player in red team, and index [1] for player in blue team.
	 */
	private final char[] LEOPARD_ICON = {'T', 't'};

	/**	This constant attribute holds the character icon for the Tiger objects for both players.
	 * Index [0] for player in red team, and index [1] for player in blue team.
	 */
	private final char[] TIGER_ICON = {'T', 't'};

	/**	This constant attribute holds the character icon for the Lion objects for both players.
	 * Index [0] for player in red team, and index [1] for player in blue team.
	 */
	private final char[] LION_ICON = {'L','l'};

	/**	This constant attribute holds the character icon for the Elephant objects for both players.
	 * Index [0] for player in red team, and index [1] for player in blue team.
	 */
	private final char[] ELEPHANT_ICON = {'E', 'e'};

	/** This constant attribute contains the character icon for signifying where the animal object could move
	 */
	private final char MOVE_ICON = '#';
	
}