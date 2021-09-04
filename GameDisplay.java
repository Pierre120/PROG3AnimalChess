import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
// import javax.swing.LayoutStyle.ComponentPlacement;
// import javax.swing.text.ComponentView;

/**
 * This GameDisplay class is responsible for the displays or outputs in the terminal.
 * It is used by the driver class to provide information and UI/UX for the players of 
 * the game.
 * 
 * @author Pierre Vincent Hernandez
 * @author Matthew James Villarica
 */
public class GameDisplay extends JFrame  {
	
	private JLayeredPane base;

	private JPanel[] backgrounds; 
	private JPanel upperContainer; 
	private JPanel lowerContainer;
	private JPanel randPieceContainer;
	private JPanel textPanel;
	private JPanel textContainer;
	private JPanel textBoard;
	private JPanel popupPanel;
	private JPanel header;
	private JPanel redPanel;
	private JPanel bluePanel;
	
	private JLabel startButton;
	private JLabel[] textLabels;
	private JLabel popupPaper;
	private JLabel redPlayer;
	private JLabel bluePlayer;

	private JButton[] choiceButtons;

	private ImageIcon[][] animalPiecePics; // final
	
	private MouseListener randomPicker; 
	
	private final Color TRANSPARENT;

	private final Dimension DEF_FRAME_SIZE;
	private final Dimension DEFAULT_SIZE;
	private final Dimension[] LOWER_CONTAINER_SIZE;
	private final Dimension TEXT_LABEL_SIZE;
	private final Dimension COLOR_PANEL_SIZE;
	private final Dimension TILE_SIZE;
	private final Dimension BUTTON_SIZE;
	
	public GameDisplay() {

		TRANSPARENT = new Color(0, 0, 0, 0);

		DEF_FRAME_SIZE = new Dimension(1033, 772); // allowance of dimension for the frame
		DEFAULT_SIZE = new Dimension((int)DEF_FRAME_SIZE.getWidth() - 16, (int)DEF_FRAME_SIZE.getHeight() - 40); // 1017 x 732
		LOWER_CONTAINER_SIZE = new Dimension[2];
		LOWER_CONTAINER_SIZE[0] = new Dimension((int)DEFAULT_SIZE.getWidth(), 
									(int)DEFAULT_SIZE.getHeight() - 332); // 1017 x 400
		LOWER_CONTAINER_SIZE[1] = new Dimension((int)DEFAULT_SIZE.getWidth(), 
									(int)LOWER_CONTAINER_SIZE[0].getHeight() - 50); // during random picking size
		TEXT_LABEL_SIZE = new Dimension(400, 50);
		COLOR_PANEL_SIZE = new Dimension(120, 618);
		TILE_SIZE = new Dimension(80, 80);
		BUTTON_SIZE = new Dimension(200, 100);

		base = new JLayeredPane();
		
		backgrounds = new JPanel[2];
		backgrounds[0] = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D bg = (Graphics2D) g;
				bg.drawImage(new ImageIcon("images\\background.png").getImage(), 0, 0, null);
			}
		};
		backgrounds[1] = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D wood = (Graphics2D) g;
				wood.drawImage(new ImageIcon("images\\boardBackground.jpg").getImage(), 0, 0, null);
			}
		};
		textBoard = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D board = (Graphics2D) g;
				board.drawImage(new ImageIcon("images\\textBoard.png").getImage(), 0, 0, null);
			}
		};
		header = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D h = (Graphics2D) g;
				h.drawImage(new ImageIcon("images\\header.png").getImage(), 0, 0, null);
			}
		};
		randPieceContainer = new JPanel();
		textPanel = new JPanel();
		textContainer = new JPanel(); 
		upperContainer = new JPanel();
		lowerContainer = new JPanel();
		popupPanel = new JPanel();
		redPanel = new JPanel();
		bluePanel = new JPanel();
		
		startButton = new JLabel(new ImageIcon("images\\start.png"));
		textLabels = new JLabel[2];
		textLabels[0] = new JLabel();
		textLabels[1] = new JLabel();
		popupPaper = new JLabel(new ImageIcon("images\\popup.png"));
		redPlayer = new JLabel();
		bluePlayer = new JLabel();

		choiceButtons = new JButton[4];
		choiceButtons[0] = new JButton(); // red
		choiceButtons[1] = new JButton(); // blue
		choiceButtons[2] = new JButton(); // yes
		choiceButtons[3] = new JButton(); // no

		animalPiecePics = new ImageIcon[2][8];

		randomPicker = null;


		// set base for all components
		setBase();

		// set background
		// backgrounds[0].setSize(1033, 772);
		backgrounds[0].setLayout(new BorderLayout());
		backgrounds[0].setBounds(0, 0, (int)DEFAULT_SIZE.getWidth(), (int)DEFAULT_SIZE.getHeight());
		// System.out.println(backgrounds[0].getSize());

		backgrounds[1].setLayout(new BorderLayout());
		// backgrounds[1].setBounds(0, 0, 777, 618);

		// set transparent upper container 
		upperContainer.setLayout(new BorderLayout());
		upperContainer.setBackground(TRANSPARENT);

		// set transparent lower container
		lowerContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 56, 20));
		lowerContainer.setPreferredSize(LOWER_CONTAINER_SIZE[0]); //new Dimension(1017, 400) 
		lowerContainer.setBackground(TRANSPARENT);

		// set randPieceContainer 
		randPieceContainer.setLayout(new GridLayout(2, 4, 15, 15));
		randPieceContainer.setBackground(TRANSPARENT); // Color.BLACK

		// set textBoard 
		textBoard.setLayout(new BorderLayout());
		textBoard.setPreferredSize(new Dimension(400, 120));
		textBoard.setBackground(TRANSPARENT);
		
		// set textPanel 
		textPanel.setLayout(new FlowLayout());
		textPanel.setBackground(TRANSPARENT);

		// set textContainer 
		textContainer.setLayout(new GridLayout(2, 1));
		textContainer.setBackground(TRANSPARENT); 

		// set popupPanel
		popupPanel.setLayout(new BorderLayout());
		popupPanel.setBounds(0, 0, (int)DEFAULT_SIZE.getWidth() - 1, (int)DEFAULT_SIZE.getHeight()); // 1016, 732
		popupPanel.setBackground(new Color(0, 0, 0, 100));
		// popupPanel.setOpaque(true);

		// set redPanel
		redPanel.setLayout(new BorderLayout());
		redPanel.setBackground(Color.RED);
		redPanel.setPreferredSize(COLOR_PANEL_SIZE);
		redPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		// System.out.println(redPanel.getSize().toString());

		// set bluePanel
		bluePanel.setLayout(new BorderLayout());
		bluePanel.setBackground(Color.BLUE);
		bluePanel.setPreferredSize(COLOR_PANEL_SIZE); // new Dimension(120, 620)
		bluePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		// System.out.println(bluePanel.getSize().toString());

		// set header
		header.setPreferredSize(new Dimension(1017, 114));


		// start button
		// startButton.setBounds(416, 350, 250, 150);
		// startButton.setSize(250, 150);

		// set textLabels[0]
		textLabels[0].setPreferredSize(TEXT_LABEL_SIZE); // new Dimension(400, 50)
		textLabels[0].setFont(new Font("Showcard Gothic", Font.PLAIN, 32));
		textLabels[0].setForeground(Color.BLACK);
		textLabels[0].setHorizontalAlignment(JLabel.CENTER);
		// System.out.println(textLabels[0].getSize().toString());

		// set textLabels[1]
		textLabels[1].setPreferredSize(TEXT_LABEL_SIZE); // new Dimension(400, 50)
		textLabels[1].setFont(new Font("Showcard Gothic", Font.PLAIN, 28));
		textLabels[1].setForeground(Color.BLACK);
		textLabels[1].setHorizontalAlignment(JLabel.CENTER);
		// System.out.println(textLabels[1].getSize().toString());

		// set popupPaper
		popupPaper.setLayout(new BorderLayout());
		popupPaper.setPreferredSize(new Dimension(600, 484));
		popupPaper.setBackground(TRANSPARENT);
		popupPaper.setVerticalAlignment(JLabel.CENTER);
		popupPaper.setHorizontalAlignment(JLabel.CENTER);

		// set redPlayer
		redPlayer.setFont(new Font("Showcard Gothic", Font.PLAIN, 36));
		redPlayer.setForeground(Color.BLACK);
		// redPlayer.setBackground(TRANSPARENT);
		redPlayer.setVerticalAlignment(JLabel.CENTER);
		redPlayer.setHorizontalAlignment(JLabel.CENTER);

		// set bluePlayer
		bluePlayer.setFont(new Font("Showcard Gothic", Font.PLAIN, 36));
		bluePlayer.setForeground(Color.BLACK);
		// bluePlayer.setBackground(TRANSPARENT);
		bluePlayer.setVerticalAlignment(JLabel.CENTER);
		bluePlayer.setHorizontalAlignment(JLabel.CENTER);


		// set choiceButtons[0] (red button)
		choiceButtons[0].setActionCommand("RED");
		choiceButtons[0].setFocusable(false);
		choiceButtons[0].setPreferredSize(BUTTON_SIZE);
		choiceButtons[0].setForeground(TRANSPARENT);
		choiceButtons[0].setBackground(Color.RED);

		// set choiceButtons[1] (blue button)
		choiceButtons[1].setActionCommand("BLUE");
		choiceButtons[1].setFocusable(false);
		choiceButtons[1].setPreferredSize(BUTTON_SIZE);
		choiceButtons[1].setForeground(TRANSPARENT);
		choiceButtons[1].setBackground(Color.BLUE);
		

		// setup this frame
		this.setTitle("Animal Chess"); // title for the window
		this.setIconImage(new ImageIcon("images\\AC_icon.png").getImage()); // icon for the frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// this.setSize((int)DEFAULT_SIZE.getWidth() + 16, (int)DEFAULT_SIZE.getHeight() + 40); // original frame size: 1033, 772
		this.setSize(DEF_FRAME_SIZE);
		this.setResizable(false); // not resizable
    	this.setLocationRelativeTo(null); // center of screen
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		
		// add the base to this frame
		this.add(base, BorderLayout.CENTER);
		// add components to the base
		base.add(backgrounds[0], JLayeredPane.DEFAULT_LAYER);
		backgrounds[0].add(lowerContainer, BorderLayout.SOUTH);
		lowerContainer.add(startButton);


		// Refresh contents
		// refresh();
		repaint();
		validate();
		// System.out.println(this.getSize().toString());
	}


	private void setBase() {
		base.setLayout(null);
		base.setBounds(0, 0, (int)DEFAULT_SIZE.getWidth(), (int)DEFAULT_SIZE.getHeight());
		base.setBackground(TRANSPARENT);
		// System.out.println(base.getSize());
	}

	private void setBackgrounds() {
		
	}
	
	
	public void refresh() {
		this.setSize(1032, 771);
		this.setSize(1033, 772);
	}

	public void setListener(MouseListener start, MouseListener random, ActionListener colorPicker) {
		startButton.addMouseListener(start);
		// set the listener for other MouseInputListener attributes of this class
		// implementation of MouseInputListener are in Game class
		randomPicker = random;
		choiceButtons[0].addActionListener(colorPicker);
		choiceButtons[1].addActionListener(colorPicker);
	}

	public void setTransparentBackground(Component comp) {
		comp.setBackground(TRANSPARENT);
		// refresh();
		repaint();
	}

	public JLabel getStartButton() {
		return startButton;
	}

	public void removeStartButton() {
		lowerContainer.remove(startButton);
		startButton = null;
	}

	
	private void initRandomPieces(int[] randIndexes) {
		int n;

		for(n = 0; n < randIndexes.length; n++)
			randPieceContainer.add(new RandomPiece("" + randIndexes[n], 
				new ImageIcon("images\\randPiece.png"), 
				new ImageIcon("images\\" + (randIndexes[n] + 1) + ".png")));
	}
	
	public void displayRandomChoices(int[] randIndexes) {
		textLabels[0].setText("~ PICK A PIECE ~");
		
		textLabels[1].setText("TURN: PERSON 1");
		
		initRandomPieces(randIndexes);

		lowerContainer.setPreferredSize(LOWER_CONTAINER_SIZE[1]);

		backgrounds[0].add(upperContainer, BorderLayout.CENTER);
		upperContainer.add(textPanel, BorderLayout.SOUTH);
		textPanel.add(textBoard);
		textBoard.add(textContainer, BorderLayout.SOUTH);
		textContainer.add(textLabels[0]);
		textContainer.add(textLabels[1]);
		lowerContainer.add(randPieceContainer);
		revalidate();
		// lowerContainer.revalidate();
		//refresh();
	}

	public void displayColorChoices(int person) {
		randPieceContainer.removeAll();
		
		textPanel.removeAll();

		textLabels[0].setText("~ PICK A COLOR ~");
		textLabels[1].setText("PERSON " + person + " IS PLAYER 1");

		backgrounds[0].removeAll();

		upperContainer.removeAll();
		
		lowerContainer.removeAll();
		lowerContainer.setPreferredSize(LOWER_CONTAINER_SIZE[0]); // new Dimension(600, 400)
		// lowerContainer.setBackground(TRANSPARENT);

		base.add(popupPanel, JLayeredPane.POPUP_LAYER); 
		popupPanel.add(popupPaper, BorderLayout.CENTER); 
		
		popupPaper.add(lowerContainer, BorderLayout.SOUTH);
		lowerContainer.add(choiceButtons[0]);
		lowerContainer.add(choiceButtons[1]);
		popupPaper.add(upperContainer, BorderLayout.CENTER);
		upperContainer.add(textPanel, BorderLayout.SOUTH);
		textPanel.add(textContainer);

		// base.repaint();
		// base.revalidate();
		repaint();
		revalidate();
	}

	public void updateTurn(int turn) {
		switch(turn) {
			case 1:
				textLabels[1].setText("TURN: PERSON 2");
				break;

			default:
				textLabels[1].setText("LOADING ...");
				break;
		}
	}

	public void removeRandomChoices() {
		lowerContainer.remove(randPieceContainer);
		randPieceContainer = null;
	}

	public void initPiecePics() {
		
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 8; j++) {
				animalPiecePics[i][j] = new ImageIcon("images\\" + i + (j + 1) +".png");
			}
		}
	}
	 
	/*public void initBoardTiles() {

	} */

	public void assignPlayers() {
		// header.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		
		redPlayer.setText("<HTML>P<br>L<br>A<br>Y<br>E<br>R<br><br>X</HTML>");

		bluePlayer.setText("<HTML>P<br>L<br>A<br>Y<br>E<br>R<br><br>X</HTML>");
	}

	public void displayAnimalChess() {
		assignPlayers();
		
		// temporary
		JPanel background2 = new JPanel();
		background2.setLayout(new BorderLayout()); 
		background2.setBounds(0, 0, 1017, 734);
		background2.setBackground(Color.CYAN);
		
		// base.removeAll();
		// base.add(background2, JLayeredPane.DEFAULT_LAYER);
		base.remove(popupPanel);

		backgrounds[0].add(header, BorderLayout.NORTH);
		backgrounds[0].add(redPanel, BorderLayout.WEST);
		backgrounds[0].add(bluePanel, BorderLayout.EAST);
		backgrounds[0].add(backgrounds[1], BorderLayout.CENTER);
		redPanel.add(redPlayer, BorderLayout.CENTER);
		bluePanel.add(bluePlayer, BorderLayout.CENTER);

		repaint();
		revalidate();
	}

	public class RandomPiece extends JLabel {

		public RandomPiece(String name, ImageIcon back, ImageIcon animalPiece) {
			setName(name);
			setIcon(back);
			setDisabledIcon(animalPiece);
			// setPreferredSize(new Dimension(80, 80));
			setEnabled(true);
			addMouseListener(randomPicker);
			setBackground(TRANSPARENT);
		}
	}


	public class BoardTile extends JLabel	{
		
		private String animalID;
		private ImageIcon tile;

		public BoardTile(String tileID, String pieceId, ImageIcon animalPiece, ImageIcon tilePic, boolean enabled) {
			animalID = pieceId;
			tile = tilePic;
			setName(tileID);
			setIcon(animalPiece); 
			setPreferredSize(TILE_SIZE);
			setBackground(TRANSPARENT);
			setEnabled(enabled);	
		}	

		//when animal moves in
		public void addAnimal(String pieceId) {
			animalID = pieceId;
			
			setEnabled(true);
			setIcon(animalPiecePics[Integer.parseInt("" + animalID.charAt(0))][Integer.parseInt("" + animalID.charAt(1)) - 1]); // new ImageIcon("images\\" + animalID + ".png")
		}

		//when animal moves out
		public void removeAnimal() {
			setIcon(null);
			animalID = null;
			setEnabled(false);
			repaint();
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D t = (Graphics2D) g;
			t.drawImage(tile.getImage(), 0, 0, null);
		}
	}
}

// -------------------------- TERMINAL/CONSOLE VERSION --------------------------


	// /**	This method updates the contents of the board in the current row.
	//  * It is responsible for assigning a character to each terrain of the board.
	//  * For Land terrain it is a space character (' ').<br>For River terrain it is a
	//  * tilde character ('~').<br>For Animal objects there designated character would 
	//  * be their first letter in their names (or animal type).
	//  * 
	//  * @param rowContents row of the board display
	//  * @param rowArea row of the game board
	//  * @param currentRow the current row the method is updating
	//  */
// 	public void updateRowContents(char[] rowContents, Terrain[] rowArea, int currentRow) {
// 		int c;
// 		// updates all the columns of a specific row
// 		for(c = 0; c < 7; c++) {
			
// 			// assigns a specific character of an animal object when the terrain object is occupied
// 			if(rowArea[c].getState()) {
// 				// using the rank of the occupying animal object in the terrain object for
// 				// assigning their designated characters
// 				switch(rowArea[c].getAnimal().getRank()) {
// 					case 1: // for Mouse Objects
// 						rowContents[c] = MOUSE_ICON[rowArea[c].getAnimal().getPlayerSide() - 1];
// 						break;
// 					case 2: // for Cat Objects
// 						rowContents[c] = CAT_ICON[rowArea[c].getAnimal().getPlayerSide() - 1];
// 						break;
// 					case 3: // for Wolf Objects
// 						rowContents[c] = WOLF_ICON[rowArea[c].getAnimal().getPlayerSide() - 1];
// 						break;
// 					case 4: // for Dog Objects
// 						rowContents[c] = DOG_ICON[rowArea[c].getAnimal().getPlayerSide() - 1];
// 						break;
// 					case 5: // for Leopard Objects
// 						rowContents[c] = LEOPARD_ICON[rowArea[c].getAnimal().getPlayerSide() - 1];
// 						break;
// 					case 6: //for Tiger objects
// 						rowContents[c] = TIGER_ICON[rowArea[c].getAnimal().getPlayerSide() - 1];
// 						break;
// 					case 7: // for Lion Objects
// 						rowContents[c] = LION_ICON[rowArea[c].getAnimal().getPlayerSide() - 1];
// 						break;
// 					case 8: // for Elephant Objects
// 						rowContents[c] = ELEPHANT_ICON[rowArea[c].getAnimal().getPlayerSide() - 1];
// 						break;
// 				}

// 			} else rowContents[c] = ' '; 
// 			// if not occupied then it is assumed to be an unoccupied Land terrain

// 			// checks if that unoccupied terrain might be an Animal Den			
// 			if(rowArea[c].isAnimalDen() && !rowArea[c].getState())
// 				rowContents[c] = ANIMAL_DEN_ICON;
			
// 			// checks if that unoccupied terrain might be a River terrain
// 			if(rowArea[c].isRiver() && !rowArea[c].getState())
// 				rowContents[c] = RIVER_ICON;

// 			if(rowArea[c].isTrap() && !rowArea[c].getState())
// 				rowContents[c] = TRAP_ICON;

// 		}
// 	}


// 	/**	This method updates the contents of the whole board.
// 	 * 
// 	 * @param gameArea the game board
// 	 */
// 	public void updateDisplayContents(Terrain[][] gameArea) {
// 		int r;
// 		for(r = 0; r < 9; r++)
// 			updateRowContents(boardDisplay[r], gameArea[r], r);
// 	}


// 	/**	This method updates the contents of the whole board when
// 	 * there is an animal who's going to move. It's going to update
// 	 * the character assigned to an area into a pound sign or hashtag '#'
// 	 * character to show to the players that the chosen animal could 
// 	 * move to that terrain.
// 	 * 
// 	 * @param gameArea the game board
// 	 * @param animalPiece the animal object that is subject to move
// 	 */
// 	public void updateDisplayContents(Terrain[][] gameArea, Animal animalPiece) {  

// 		// upward moves
// 		if(animalPiece.canMoveUp(gameArea))
// 			boardDisplay[animalPiece.getRow()][animalPiece.getCol() + animalPiece.getUpwardSpace()] = MOVE_ICON;

// 		// downward moves
// 		if(animalPiece.canMoveDown(gameArea))
// 			boardDisplay[animalPiece.getRow()][animalPiece.getCol() - animalPiece.getDownwardSpace()] = MOVE_ICON;

// 		// right moves
// 		if(animalPiece.canMoveRight(gameArea))
// 			boardDisplay[animalPiece.getRow() + animalPiece.getRightSpace()][animalPiece.getCol()] = MOVE_ICON;

// 		// left moves
// 		if(animalPiece.canMoveLeft(gameArea))
// 			boardDisplay[animalPiece.getRow() - animalPiece.getLeftSpace()][animalPiece.getCol()] = MOVE_ICON;

// 	}
	
	
// 	/**	This method displays the column of the game board.
// 	 * 
// 	 * @param boardContents contains character representation of the game board
// 	 * @param currentCol the current column that is being displayed by this method
// 	 */
// 	public void displayContents(char[][] boardContents, int currentCol) {
// 		int row;
// 		System.out.print("||");
// 		for(row = 0; row < 9; row++) {
// 			System.out.print(" " + boardContents[row][currentCol] + " ");
			
// 			if(row < 8)
// 				System.out.print("|");
// 		}
		
// 		System.out.println("||");
// 	}
	
	
// 	/**	This method displays the contents of the whole board
// 	 */
// 	public void displayBoard() {
// 		int col;
// 		System.out.println();
// 		System.out.println("  Red                            Blue");
// 		System.out.println("======================================="); // borders
		
// 		// print out the contents per column
// 		for(col = 6; col >= 0; col--)
// 			displayContents(boardDisplay, col); 
		
// 		System.out.println("======================================="); // borders
// 		System.out.println();
// 	}


// 	/**	Displays the random animal choices for the players to pick at
// 	 * the start of the game. It also displays whether that random animal piece
// 	 * is already picked by another player.
// 	 * 
// 	 * @param numChoices number of Animal objects that is present or implemented in the game
// 	 * @param validPiece cointains the valid choices of random animal objects. This also helps in determining whether the piece is already taken.
// 	 */
// 	public void displayRandomAnimalChoice(int numChoices, int[] validPiece) {
// 		int p;

// 		System.out.print("Animal pieces:\n\n");
// 		for(p = 0; p < numChoices; p++) {
// 			System.out.print(p + 1 + ".) Random animal piece ");

// 			// checks if random animal piece is already taken
// 			if(validPiece[p] == 0)
// 				System.out.print("[X]");

// 			System.out.println();
// 		}
// 		System.out.println();
// 	}


// 	/**	This method displays the available team colors that the players could choose to side on.
// 	 */
// 	public void displayTeamColorChoice() {
// 		System.out.println("Team Colors:");
// 		System.out.print("1.) Red\n2.) Blue\n\n");
// 	}


// 	/**	This method displays the available Animal objects that the player could use or move
// 	 * during the gameplay.
// 	 * 
// 	 * @param animalPieces animal objects(pieces) of both players
// 	 * @param playerTurn index of the player currently playing (contains either 0 (for player in red team), and 1(for player in blue team))
// 	 */
// 	public void displayAvailableAnimals(Animal[][] animalPieces, int playerTurn) {
// 		int a;
// 		System.out.println("Available animal pieces:");
// 		for(a = 0; a < animalPieces.length; a++)
// 			if(animalPieces[a][playerTurn] != null && !animalPieces[a][playerTurn].isCaptured())
// 				System.out.println("\t" + (a+1) + ".) " + animalPieces[a][playerTurn]);
		
// 		System.out.println();
// 	}


// 	/**	This method displays the assigned character inputs for the movement of an animal object.
// 	 */
// 	public void displayMovementKeys() {
// 		System.out.println("Keys for movement:");
// 		System.out.println("\tL - Left");
// 		System.out.println("\tR - Right");
// 		System.out.println("\tU - Up");
// 		System.out.println("\tD - Down");
// 		System.out.println("\tX - Cancel");
// 		System.out.println();
// 	}


// 	/**	This method displays the winner of the Animal Chess round.
// 	 * 
// 	 * @param playerSide the player who won (could be player 1 or 2)
// 	 * @param teamColor the color that the winning player chose or sided on
// 	 */
// 	public void displayWinner(int playerSide, String teamColor) {
// 		System.out.println("CONGRATULATIONS! Player " + playerSide + " ( " + teamColor + " )");
// 		System.out.print("You have successfully conquered thy land.\n\n");
// 	}
	
	
// 	/** This constant attribute holds the characters icons for the whole game board.
// 	 * This is used for displaying the game board.
// 	 */
// 	private char[][] boardDisplay = new char[9][7];

// 	/**	This constant attribute contains the character icon for siginifying a terrain is an Animal Den
// 	 */
// 	private final char ANIMAL_DEN_ICON = '@';

// 	/** This constant attribute contains the character icon for signifying a terrain is a River object
// 	 */
// 	private final char RIVER_ICON = '~';

// 	/**	This constant attribute holds the character icon for the Mouse objects for both players.
// 	 * Index [0] for player in red team, and index [1] for player in blue team.
// 	 */
// 	private final char[] MOUSE_ICON = {'M', 'm'};

// 	/**	This constant attribute holds the character icon for the Cat objects for both players.
// 	 * Index [0] for player in red team, and index [1] for player in blue team.
// 	 */
// 	private final char[] CAT_ICON = {'C', 'c'};
	
// 	/**	This constant attribute holds the character icon for the Wolf objects for both players.
// 	 * Index [0] for player in red team, and index [1] for player in blue team.
// 	 */
// 	private final char[] WOLF_ICON = {'W', 'w'};

// 	/** This constant attribute holds the character icon for the Dog objects for both players. 
// 	 * Index [0] for player in red team, and index [1] for player in blue team.
// 	 */
// 	private final char[] DOG_ICON = {'D','d'};
	 
// 	/**	This constant attribute holds the character icon for the Leopard objects for both players.
// 	 * Index [0] for player in red team, and index [1] for player in blue team.
// 	 */
// 	private final char[] LEOPARD_ICON = {'O', 'o'};

// 	/**	This constant attribute holds the character icon for the Tiger objects for both players.
// 	 * Index [0] for player in red team, and index [1] for player in blue team.
// 	 */
// 	private final char[] TIGER_ICON = {'T', 't'};

// 	/**	This constant attribute holds the character icon for the Lion objects for both players.
// 	 * Index [0] for player in red team, and index [1] for player in blue team.
// 	 */
// 	private final char[] LION_ICON = {'L','l'};

// 	/**	This constant attribute holds the character icon for the Elephant objects for both players.
// 	 * Index [0] for player in red team, and index [1] for player in blue team.
// 	 */
// 	private final char[] ELEPHANT_ICON = {'E', 'e'};

// 	/** This constant attribute contains the character icon for signifying where the animal object could move
// 	 */
// 	private final char MOVE_ICON = '#';
	

// 	private final char TRAP_ICON = '8';
// 
// 
