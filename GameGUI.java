import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * This GameGUI class is responsible for the displays or outputs in the terminal.
 * It is used by the driver class to provide information and UI/UX for the players of 
 * the game.
 * animal piece pictures source: https://www.pousseurdebois.fr/jeux-de-strategie/doushouqi/
 * @author Pierre Vincent Hernandez
 * @author Matthew James Villarica
 */
public class GameGUI extends JFrame  {

	/**
	 * This constructor is responsible for instantiating and setting up this class' attributes.
	 */
	public GameGUI() {
		
		randPieceContainer = new JPanel(); // container for the random animal pieces
		startButton = new JLabel(new ImageIcon("images\\start.png")); // start button for the start of the game

		// temporarily assign null to listeners
		randomPicker = null; // listener for picking random animal pieces
		boardListener = null; // listener during the main gameplay

		TRANSPARENT = new Color(0, 0, 0, 0); // transparent background

		DEF_FRAME_SIZE = new Dimension(1033, 772); // allowance of dimension for the frame
		DEFAULT_SIZE = new Dimension((int)DEF_FRAME_SIZE.getWidth() - 16, (int)DEF_FRAME_SIZE.getHeight() - 40); // 1017 x 732
		LOWER_CONTAINER_SIZE = new Dimension[2];
		LOWER_CONTAINER_SIZE[0] = new Dimension((int)DEFAULT_SIZE.getWidth(), 
									(int)DEFAULT_SIZE.getHeight() - 332); // default: 1017 x 400
		LOWER_CONTAINER_SIZE[1] = new Dimension((int)DEFAULT_SIZE.getWidth(), 
									(int)LOWER_CONTAINER_SIZE[0].getHeight() - 50); // during random picking size
		TEXT_LABEL_SIZE = new Dimension(400, 50); // text labels
		COLOR_PANEL_SIZE = new Dimension(120, 618); // color panels during main gameplay
		PIECE_SIZE = new Dimension(78, 78); // piece size
		TILE_SIZE = new Dimension(80, 80); // tiles size
		BUTTON_SIZE = new Dimension(200, 100); // size of buttons

		// base for all components
		BASE = new JLayeredPane();
		
		// backgrounds that will be used throughout the game
		BACKGROUNDS = new JPanel[2];
		// main background
		BACKGROUNDS[0] = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D bg = (Graphics2D) g;
				bg.drawImage(new ImageIcon("images\\background.png").getImage(), 0, 0, null);
			}
		};
		// wooden background of the game board
		BACKGROUNDS[1] = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D wood = (Graphics2D) g;
				wood.drawImage(new ImageIcon("images\\boardBackground.jpg").getImage(), 0, 0, null);
			}
		};

		// wooden board background for the text labels
		TEXT_BOARD = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D board = (Graphics2D) g;
				board.drawImage(new ImageIcon("images\\textBoard.png").getImage(), 0, 0, null);
			}
		};

		// header during the main game play
		HEADER = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D h = (Graphics2D) g;
				h.drawImage(new ImageIcon("images\\header.png").getImage(), 0, 0, null);
			}
		};

		TEXT_PANEL = new JPanel(); // transparent ontainer for text-related components (TEXT_CONTAINER & TEXT_LABELS)
		TEXT_CONTAINER = new JPanel(); // will hold the TEXT_LABELS
		UPPER_CONTAINER = new JPanel(); // transparent upper container in the frame to hold components
		LOWER_CONTAINER = new JPanel(); // transparent lower container in the frame to hold components
		POPUP_PANEL = new JPanel(); // container for the POPUP_PAPER
		RED_PANEL = new JPanel(); // color panel for the red player 
		BLUE_PANEL = new JPanel(); // color panel for the blue player
		BOARD_PANEL = new JPanel(); // transparent container for the board-related components (BOARD_CONTAINER & TILES)
		BOARD_CONTAINER = new JPanel(); // will hold the TILES
		
		TEXT_LABELS = new JLabel[2];
		TEXT_LABELS[0] = new JLabel(); // serves as heading 1
		TEXT_LABELS[1] = new JLabel(); // serves as heading 2

		// popup notif
		POPUP_PAPER = new JLabel(new ImageIcon("images\\popup.png"));
		RED_PLAYER = new JLabel(); // text for the player assigned to red
		BLUE_PLAYER = new JLabel(); // text for the player assigned to blue

		CHOICE_BUTTONS = new JButton[3];
		CHOICE_BUTTONS[0] = new JButton(); // red
		CHOICE_BUTTONS[1] = new JButton(); // blue
		CHOICE_BUTTONS[2] = new JButton(); // ok

		TILES = new BoardTile[9][7]; // tiles of the board
		PIECE_PICS = new JLabel[2][8]; // animal pieces
		
		TERRAIN_PICS = new ImageIcon[4];
		TERRAIN_PICS[0] = new ImageIcon("images\\land.png"); // land
		TERRAIN_PICS[1] = new ImageIcon("images\\river.png"); // river
		TERRAIN_PICS[2] = new ImageIcon("images\\trap.png"); // trap
		TERRAIN_PICS[3] = new ImageIcon("images\\animalDen.png"); // animal den

		
		// initialize pictures of pieces
		initPiecePics();

		// set base for all components
		setBase();

		// set background
		setBackgrounds();

		//set transparent upper and lower containers
		setTransparentContainers();

		// set randPieceContainer 
		setRandPieceContainer();

		// set TEXT_BOARD, TEXT_PANEL, & TEXT_CONTAINER
		setTextPanelComps();

		// set POPUP_PANEL
		setPopupPanel();

		// set RED_PANEL & BLUE_PANEL for each player
		setColorPanels();

		// set game board
		setBoard();

		// set HEADER for main gameplay
		HEADER.setPreferredSize(new Dimension(1017, 114));

		//	set TEXT_LABELS
		setTextLabels();

		// set POPUP_PAPER
		setPopupPaper();

		//set blue and red player banners on left and right of the board
		setPlayerBanners();

		//set choice buttons on player banner
		setChoiceButtons();
		
		//set main JFrame of GUI 
		setFrame();
		
		// add the base to this frame
		this.add(BASE, BorderLayout.CENTER);

		// add components to the base
		BASE.add(BACKGROUNDS[0], JLayeredPane.DEFAULT_LAYER);
		BACKGROUNDS[0].add(LOWER_CONTAINER, BorderLayout.SOUTH);
		LOWER_CONTAINER.add(startButton);

		// update changes to reflect
		repaint();
		validate();
	}


	/**
	 * This method is responsible for setting the listeners for this class.
	 * 
	 * @param start MouseListener for the start button
	 * @param random MouseListener for the random pieces
	 * @param choicePicker ActionListener for the buttons in the game
	 * @param board MouseListener for the game board tiles
	 */
	public void setListeners(MouseListener start, MouseListener random, ActionListener choicePicker, MouseListener board) {
		startButton.addMouseListener(start); // listener for start button
		CHOICE_BUTTONS[0].addActionListener(choicePicker); // listener for the red button
		CHOICE_BUTTONS[1].addActionListener(choicePicker); // listener for the blue button
		CHOICE_BUTTONS[2].addActionListener(choicePicker); // listener for the ok button
		randomPicker = random; // listener for the random pices
		boardListener = board; // listener for the board tiles
	}

	
	/**
	 * This method gets the start button component of this GUI class.
	 * 
	 * @return returns the start button which is a JLabel type component
	 */
	public JLabel getStartButton() {
		return startButton;
	}


	/**
	 * This method removes the start button (startButton) from the game after it is used by removing it from
	 * its container and assigning a null value to it.
	 */
	public void removeStartButton() {
		LOWER_CONTAINER.remove(startButton); // remove start button from container
		startButton = null;
	}


	/**
	 * This method removes the random choices (randPieceContainer) from the game after it is used by removing it 
	 * from its container and assigning a null value to it.
	 */
	public void removeRandomChoices() {
		LOWER_CONTAINER.remove(randPieceContainer); // remove the random pieces from the container
		randPieceContainer = null;
	}

	
	/**
	 * This method initializes the tiles for the game board.
	 * 
	 * @param board game board model which holds the tiles for terrains and the animal pieces
	 */
	public void initBoardDisplay(BoardModel board) {

		for(int row = 0; row < 9; row++) 
			for(int col = 0; col < 7; col++) {
				if(board.getTiles().getTerrains()[row][col].isLand()) // land terrain
					TILES[row][col] = new BoardTile("" + row + col, TERRAIN_PICS[0]);

				else if (board.getTiles().getTerrains()[row][col].isRiver()) // river terrain
					TILES[row][col] = new BoardTile("" + row + col, TERRAIN_PICS[1]);

				else if(board.getTiles().getTerrains()[row][col].isTrap()) // trap terrain
					TILES[row][col] = new BoardTile("" + row + col, TERRAIN_PICS[2]);

				else if(board.getTiles().getTerrains()[row][col].isAnimalDen()) // animal den
					TILES[row][col] = new BoardTile("" + row + col, TERRAIN_PICS[3]);
				
				// assigns the piece that is currently occupying the terrain
				if(board.getTiles().getTerrains()[row][col].getState())
					TILES[row][col].addPiece("" + (board.getTiles().getTerrains()[row][col].getAnimal().getPlayerSide() - 1) +
					 	board.getTiles().getTerrains()[row][col].getAnimal().getRank());
			}
	}


	/**
	 * This method assigns the players to their designated color side.
	 * 
	 * @param player1 chosen side of Player 1 (0 - Red & 1 - Blue)
	 */
	public void assignPlayers(int player1) {
		// HEADER.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		String[] playerString = new String[2];

		playerString[player1] = "<HTML>P<br>L<br>A<br>Y<br>E<br>R<br><br>1</HTML>";
		playerString[(player1 + 1) % 2] = "<HTML>P<br>L<br>A<br>Y<br>E<br>R<br><br>2</HTML>";

		RED_PLAYER.setText(playerString[0]);

		// DEBUGGING PURPOSES
		System.out.println("Assigning players... Red is " + RED_PLAYER.getText());

		BLUE_PLAYER.setText(playerString[1]);
	}

	
	/**
	 * This method updates the turn of the person who's picking
	 * for random pieces by setting a text to the text labels and displaying it
	 * for the user to be notified on whose turn it is.
	 * 
	 * @param turn index turn of the current person picking (1 - for persson 2)
	 */
	public void updateTurn(int turn) {
		switch(turn) {
			case 1: // person 2's turn
				TEXT_LABELS[1].setText("TURN: PERSON 2");
				break;

			default: // both person 1 & 2 are done picking
				TEXT_LABELS[1].setText("LOADING ...");
				break;
		}

		repaint(); // update changes
	}


	/**
	 * This method updates the color panels to show to the user whose turn it is,
	 * whether if it's the turn of the player in the red side or blue side.
	 * 
	 * @param turn current player's turn (0 - Red & 1 - Blue)
	 */
	public void updateColorPanel(int turn) {
		if(turn == 0) { // 0 - red
			RED_PANEL.setBackground(Color.RED);
			BLUE_PANEL.setBackground(Color.LIGHT_GRAY);
		} else { // 1 = blue
			RED_PANEL.setBackground(Color.LIGHT_GRAY);
			BLUE_PANEL.setBackground(Color.BLUE);
		}
	}


	/**
	 * This method updates the tiles when the player picks an animal piece to show its valid
	 * moves in the board by setting border highlights to the valid tiles. It removes the 
	 * border highlights when the player cancels his/her move or made a valid move.
	 * 
	 * @param board contains the terrain tiles of the board model
	 * @param movingPiece the chosen piece of the player
	 * @param validIDs valid tile ID array of the valid terrains that the chosen animal can move into
	 * @param move holds 1 if player has chosen a piece to move, and holds 2 if player cancels his move or a move has been made
	 */
	public void updateTiles(Terrain[][] board, Animal movingPiece, String[] validIDs, int move) {
		for(int m = 0; m < validIDs.length; m++)
			if(!validIDs[m].equalsIgnoreCase("null")) {
				switch(move) {
					case 0: // done making a move
						// remove highlight border on the tile of the chosen piece
						TILES[movingPiece.getRow()][movingPiece.getCol()]
							.setBorder(BorderFactory.createEmptyBorder());

						// remove highlight border on the valid tiles
						TILES[Integer.parseInt("" + validIDs[m].charAt(0))]
							[Integer.parseInt("" + validIDs[m].charAt(1))]
							.setBorder(BorderFactory.createEmptyBorder());

						// disable tile if there are no pieces occupying current tile
						if(!board[Integer.parseInt("" + validIDs[m].charAt(0))]
						[Integer.parseInt("" + validIDs[m].charAt(1))].getState())
							TILES[Integer.parseInt("" + validIDs[m].charAt(0))]
								[Integer.parseInt("" + validIDs[m].charAt(1))]
								.setEnabled(false);
						break;

					case 1: // starting to make a move
						// add highlight border on the current tile of the chosen piece
						TILES[movingPiece.getRow()][movingPiece.getCol()]
							.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));

						// add highlight border to the valid tiles
						TILES[Integer.parseInt("" + validIDs[m].charAt(0))]
							[Integer.parseInt("" + validIDs[m].charAt(1))]
							.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 5));

						// enable the valid tiles to perform the actions of its listeners
						TILES[Integer.parseInt("" + validIDs[m].charAt(0))]
							[Integer.parseInt("" + validIDs[m].charAt(1))]
							.setEnabled(true);
						break;
				}
					
			}
	}


	/**
	 * This method moves the chosen animal piece to its tile destination. First it removes the piece on its
	 * current tile and adds it on its tile destination. If the tile destination has an opposing side's piece
	 * it will first remove the opposing side's piece, then adds the chosen piece to that tile.
	 * 
	 * @param board contains the terrain tiles of the board model
	 * @param movingPiece the chosen piece of the player
	 * @param validIDs valid tile ID array of the valid terrains that the chosen animal can move into
	 * @param movesIn true if animal is moving in, otherwise false
	 */
	public void movePiece(Terrain[][] board, Animal movingPiece, String[] validIDs, boolean movesIn) {
		// removes piece currently occupying the current tile
		if(TILES[movingPiece.getRow()][movingPiece.getCol()].hasPiece())
			TILES[movingPiece.getRow()][movingPiece.getCol()].removePiece();

		if(movesIn) { // adds piece to the current tile
			TILES[movingPiece.getRow()][movingPiece.getCol()].addPiece("" + (movingPiece.getPlayerSide() - 1) + movingPiece.getRank());
			updateTiles(board, movingPiece, validIDs, 0);
		} else // removes the highlight border
			TILES[movingPiece.getRow()][movingPiece.getCol()]
				.setBorder(BorderFactory.createEmptyBorder());
	}


	/**
	 * This method displays the random animal piece from which both starting players,
	 * labeled as person 1 and 2, will pick from.
	 * 
	 * @param randIndexes contains the generated random indexes for the animal pieces
	 */
	public void displayRandomChoices(int[] randIndexes) {
		TEXT_LABELS[0].setText("~ PICK A PIECE ~");
		
		TEXT_LABELS[1].setText("TURN: PERSON 1");
		
		// intialize the random pieces base from the data in randIndexes
		initRandomPieces(randIndexes); 

		// lower the height of the LOWER_CONTAINER
		LOWER_CONTAINER.setPreferredSize(LOWER_CONTAINER_SIZE[1]);

		// add the necessary components for random choices display
		BACKGROUNDS[0].add(UPPER_CONTAINER, BorderLayout.CENTER);
		UPPER_CONTAINER.add(TEXT_PANEL, BorderLayout.SOUTH);
		TEXT_PANEL.add(TEXT_BOARD);
		TEXT_BOARD.add(TEXT_CONTAINER, BorderLayout.SOUTH);
		TEXT_CONTAINER.add(TEXT_LABELS[0]);
		TEXT_CONTAINER.add(TEXT_LABELS[1]);
		LOWER_CONTAINER.add(randPieceContainer);

		revalidate(); // update changes
	}


	/**
	 * This method displays the two color sides that the player can choose from.
	 * 
	 * @param person the current person who will be player 1
	 */
	public void displayColorChoices(int person) {
		TEXT_PANEL.removeAll();

		TEXT_LABELS[0].setText("~ PICK A COLOR ~");
		TEXT_LABELS[1].setText("PERSON " + person + " IS PLAYER 1"); // display whether person 1 or 2 is player 1

		BACKGROUNDS[0].removeAll();

		UPPER_CONTAINER.removeAll();
		
		LOWER_CONTAINER.removeAll();
		LOWER_CONTAINER.setPreferredSize(LOWER_CONTAINER_SIZE[0]); 
		
		// add necessary components for the color choices display
		BASE.add(POPUP_PANEL, JLayeredPane.POPUP_LAYER); 
		POPUP_PANEL.add(POPUP_PAPER, BorderLayout.CENTER); 
		POPUP_PAPER.add(LOWER_CONTAINER, BorderLayout.SOUTH);
		POPUP_PAPER.add(UPPER_CONTAINER, BorderLayout.CENTER);
		LOWER_CONTAINER.add(CHOICE_BUTTONS[0]);
		LOWER_CONTAINER.add(CHOICE_BUTTONS[1]);
		UPPER_CONTAINER.add(TEXT_PANEL, BorderLayout.SOUTH);
		TEXT_PANEL.add(TEXT_CONTAINER);
		
		randPieceContainer = null; // assign null since it will not be used anymore

		// update changes
		repaint();
		revalidate();
	}


	/**
	 * This method displays the main game play which consists of the header title, 
	 * designated color panel for each player side and the board itself.
	 * 
	 * @param player1 chosen side of Player 1 (0 - Red & 1 - Blue)
	 */
	public void displayAnimalChess(int player1) {
		assignPlayers(player1);

		BASE.remove(POPUP_PANEL);
		
		addBoardTiles();

		BACKGROUNDS[0].add(HEADER, BorderLayout.NORTH);
		BACKGROUNDS[0].add(RED_PANEL, BorderLayout.WEST);
		BACKGROUNDS[0].add(BLUE_PANEL, BorderLayout.EAST);
		BACKGROUNDS[0].add(BACKGROUNDS[1], BorderLayout.CENTER);
		BACKGROUNDS[1].add(BOARD_CONTAINER, BorderLayout.SOUTH);
		BOARD_CONTAINER.add(BOARD_PANEL);
		RED_PANEL.add(RED_PLAYER, BorderLayout.CENTER);
		BLUE_PANEL.add(BLUE_PLAYER, BorderLayout.CENTER);

		// update changes
		repaint();
		revalidate();
	}


	/**
	 * This method displays the results of the game after the game ends. It 
	 * displays who won the game (Player 1 or 2) and prompts the user that
	 * the game will exit itself.
	 * 
	 * @param winningPlayer the winning player (0 - Red & 1- Blue)
	 */
	public void displayResults(int winningPlayer) {
		
		// label for the ok button
		JLabel ok = new JLabel("OK");
		ok.setFont(new Font("Showcard Gothic", Font.PLAIN, 52));
		ok.setForeground(Color.BLACK);
		ok.setHorizontalAlignment(JLabel.CENTER);
		ok.setVerticalAlignment(JLabel.CENTER);

		
		CHOICE_BUTTONS[2].add(ok, BorderLayout.SOUTH);
		
		if(winningPlayer == 0) // 0 - red
			TEXT_LABELS[0].setText("PLAYER " + RED_PLAYER.getText().charAt(40) + " WINS !!!");
		else // 1 - blue
			TEXT_LABELS[0].setText("PLAYER " + BLUE_PLAYER.getText().charAt(40) + " WINS !!!");
		
		TEXT_LABELS[1].setText("GAME WILL NOW EXIT");

		LOWER_CONTAINER.removeAll(); // remove red & blue buttons
		LOWER_CONTAINER.add(CHOICE_BUTTONS[2]); // add ok button

		// display popup prompt
		BASE.add(POPUP_PANEL, JLayeredPane.POPUP_LAYER);

		// update changes
		repaint();
		revalidate();
	}


	/**
	 * This method adds the tiles to the board container
	 */
	private void addBoardTiles() {
		
		for(int col = 6; col >= 0; col--)
			for(int row = 0; row < 9; row++)
				BOARD_PANEL.add(TILES[row][col]);
	}
	
	/**
	 * This method initializes and adds the random pieces to its container.
	 * 
	 * @param randIndexes contains the generated random indexes for the animal pieces
	 */
	private void initRandomPieces(int[] randIndexes) {
		int n;

		for(n = 0; n < randIndexes.length; n++)
			randPieceContainer.add(new RandomPiece("" + randIndexes[n], 
				new ImageIcon("images\\randPiece.png"), 
				new ImageIcon("images\\" + (randIndexes[n] + 1) + ".png")));
	}


	/**
	 * This method intializes the animal pieces that will be displayed
	 * and added to the tiles of the board display.
	 */
	private void initPiecePics() {
		
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 8; j++) {
				PIECE_PICS[i][j] = new JLabel(new ImageIcon("images\\" + i + (j + 1) + ".png"));
				PIECE_PICS[i][j].setPreferredSize(PIECE_SIZE);
				PIECE_PICS[i][j].setHorizontalAlignment(JLabel.CENTER);
				PIECE_PICS[i][j].setVerticalAlignment(JLabel.CENTER);
				// PIECE_PICS[i][j].setBounds(0, 0, 78, 78);
			}
		}
	}


	/**
	 * This method sets the layout, bounds and background of the BASE attribute
	 */
	private void setBase() {
		BASE.setLayout(null);
		BASE.setBounds(0, 0, (int)DEFAULT_SIZE.getWidth(), (int)DEFAULT_SIZE.getHeight());
		BASE.setBackground(TRANSPARENT);
		// System.out.println(BASE.getSize());
	}


	/**
	 * This method sets the layout and bounds of the BACKGROUNDS attribute.
	 */
	private void setBackgrounds() {
		// BACKGROUNDS[0].setSize(1033, 772);
		BACKGROUNDS[0].setLayout(new BorderLayout());
		BACKGROUNDS[0].setBounds(0, 0, (int)DEFAULT_SIZE.getWidth(), (int)DEFAULT_SIZE.getHeight());
		// System.out.println(BACKGROUNDS[0].getSize());

		BACKGROUNDS[1].setLayout(new BorderLayout());
		// BACKGROUNDS[1].setBounds(0, 0, 777, 618);
	}


	/**
	 * This method sets the layout, background and size of the UPPER_CONTAINER
	 * and LOWER_CONTAINER attributes.
	 */
	private void setTransparentContainers() {
		// set transparent upper container 
		UPPER_CONTAINER.setLayout(new BorderLayout());
		UPPER_CONTAINER.setBackground(TRANSPARENT);

		// set transparent lower container
		LOWER_CONTAINER.setLayout(new FlowLayout(FlowLayout.CENTER, 56, 20));
		LOWER_CONTAINER.setPreferredSize(LOWER_CONTAINER_SIZE[0]); //new Dimension(1017, 400) 
		LOWER_CONTAINER.setBackground(TRANSPARENT);
	}
	
	/**
	 * This method sets the layout and background of the randPieceContainer attribute.
	 */
	private void setRandPieceContainer() {
		randPieceContainer.setLayout(new GridLayout(2, 4, 15, 15));
		randPieceContainer.setBackground(TRANSPARENT); // Color.BLACK
	}


	/**
	 * This method sets the layout, background and size of the TEXT_BOARD, 
	 * TEXT_PANEL and TEXT_CONTAINER attributes.
	 */
	private void setTextPanelComps() {
		// set TEXT_BOARD 
		TEXT_BOARD.setLayout(new BorderLayout());
		TEXT_BOARD.setPreferredSize(new Dimension(400, 120));
		TEXT_BOARD.setBackground(TRANSPARENT);
		
		// set TEXT_PANEL 
		TEXT_PANEL.setLayout(new FlowLayout());
		TEXT_PANEL.setBackground(TRANSPARENT);

		// set TEXT_CONTAINER 
		TEXT_CONTAINER.setLayout(new GridLayout(2, 1));
		TEXT_CONTAINER.setBackground(TRANSPARENT); 
	}


	/**
	 * This method sets the layout, bounds and background of the POPUP_PANEL attribute.
	 */
	private void setPopupPanel() {
		POPUP_PANEL.setLayout(new BorderLayout());
		POPUP_PANEL.setBounds(0, 0, (int)DEFAULT_SIZE.getWidth() - 1, (int)DEFAULT_SIZE.getHeight()); // 1016, 732
		POPUP_PANEL.setBackground(new Color(0, 0, 0, 100));
		// POPUP_PANEL.setOpaque(true);
	}


	/**
	 * This method sets the layout, background, size and border of the
	 * RED_PANEL and BLUE_PANEL attributes.
	 */
	private void setColorPanels() {
		// set RED_PANEL
		RED_PANEL.setLayout(new BorderLayout());
		RED_PANEL.setBackground(Color.RED);
		RED_PANEL.setPreferredSize(COLOR_PANEL_SIZE);
		RED_PANEL.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

		// set BLUE_PANEL
		BLUE_PANEL.setLayout(new BorderLayout());
		BLUE_PANEL.setBackground(Color.BLUE);
		BLUE_PANEL.setPreferredSize(COLOR_PANEL_SIZE); 
		BLUE_PANEL.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
	}


	/**
	 * This method sets the layout, background and size of the 
	 * BOARD_PANEL and BOARD_CONTAINER attributes.
	 */
	private void setBoard() {
		BOARD_PANEL.setLayout(new GridLayout(7, 9, 1, 1)); 
		BOARD_PANEL.setBackground(Color.BLACK);
		
		BOARD_CONTAINER.setLayout(new FlowLayout());
		BOARD_CONTAINER.setPreferredSize(new Dimension(1000, 600));
		BOARD_CONTAINER.setBackground(TRANSPARENT);
	}
	

	/**
	 * This method sets the size, font, foreground and horizontal alignment
	 * of the TEXT_LABELS attribute.
	 */
	private void setTextLabels() {
		// set TEXT_LABELS[0]
		TEXT_LABELS[0].setPreferredSize(TEXT_LABEL_SIZE); // new Dimension(400, 50)
		TEXT_LABELS[0].setFont(new Font("Showcard Gothic", Font.PLAIN, 32));
		TEXT_LABELS[0].setForeground(Color.BLACK);
		TEXT_LABELS[0].setHorizontalAlignment(JLabel.CENTER);

		// set TEXT_LABELS[1]
		TEXT_LABELS[1].setPreferredSize(TEXT_LABEL_SIZE); // new Dimension(400, 50)
		TEXT_LABELS[1].setFont(new Font("Showcard Gothic", Font.PLAIN, 28));
		TEXT_LABELS[1].setForeground(Color.BLACK);
		TEXT_LABELS[1].setHorizontalAlignment(JLabel.CENTER);
	}


	/**
	 * This method sets the layout, size, background, vertical and horizontal alignment
	 * of the POPUP_PAPER attribute.
	 */
	private void setPopupPaper() {
		POPUP_PAPER.setLayout(new BorderLayout());
		POPUP_PAPER.setPreferredSize(new Dimension(600, 484));
		POPUP_PAPER.setBackground(TRANSPARENT);
		POPUP_PAPER.setVerticalAlignment(JLabel.CENTER);
		POPUP_PAPER.setHorizontalAlignment(JLabel.CENTER);
	}


	/**
	 * This method sets the font, foreground, vertical and horizontal alignment
	 * of the RED_PLAYER and BLUE_PLAYER attributes.
	 */
	private void setPlayerBanners() {
		// set RED_PLAYER
		RED_PLAYER.setFont(new Font("Showcard Gothic", Font.PLAIN, 36));
		RED_PLAYER.setForeground(Color.BLACK);
		RED_PLAYER.setVerticalAlignment(JLabel.CENTER);
		RED_PLAYER.setHorizontalAlignment(JLabel.CENTER);

		// set BLUE_PLAYER
		BLUE_PLAYER.setFont(new Font("Showcard Gothic", Font.PLAIN, 36));
		BLUE_PLAYER.setForeground(Color.BLACK);
		BLUE_PLAYER.setVerticalAlignment(JLabel.CENTER);
		BLUE_PLAYER.setHorizontalAlignment(JLabel.CENTER);
	}


	/**
	 * This method sets the focus, size, foreground, background, layout and
	 * action commands of the CHOICE_BUTTONS attribute.
	 */
	private void setChoiceButtons() {
		// set CHOICE_BUTTONS[0] (red button)
		CHOICE_BUTTONS[0].setActionCommand("RED");
		CHOICE_BUTTONS[0].setFocusable(false);
		CHOICE_BUTTONS[0].setPreferredSize(BUTTON_SIZE);
		CHOICE_BUTTONS[0].setForeground(TRANSPARENT);
		CHOICE_BUTTONS[0].setBackground(Color.RED);

		// set CHOICE_BUTTONS[1] (blue button)
		CHOICE_BUTTONS[1].setActionCommand("BLUE");
		CHOICE_BUTTONS[1].setFocusable(false);
		CHOICE_BUTTONS[1].setPreferredSize(BUTTON_SIZE);
		CHOICE_BUTTONS[1].setForeground(TRANSPARENT);
		CHOICE_BUTTONS[1].setBackground(Color.BLUE);

		// set CHOICE_BUTTONS[2] (ok button)
		CHOICE_BUTTONS[2].setActionCommand("OK");
		CHOICE_BUTTONS[2].setFocusable(false);
		CHOICE_BUTTONS[2].setPreferredSize(BUTTON_SIZE);
		CHOICE_BUTTONS[2].setForeground(Color.BLACK);
		CHOICE_BUTTONS[2].setBackground(Color.GREEN);
		CHOICE_BUTTONS[2].setLayout(new BorderLayout()); // set layout for "OK" text
	}


	/**
	 * This method sets the title, icon, size, resizability, location, layout,
	 * visibility and default close operation of this (GameGUI) extended JFrame class
	 */
	private void setFrame() {
		// setup this frame
		this.setTitle("Animal Chess"); // title for the window
		this.setIconImage(new ImageIcon("images\\AC_icon.png").getImage()); // icon for the frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(DEF_FRAME_SIZE);
		this.setResizable(false); // not resizable
    	this.setLocationRelativeTo(null); // center of screen
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		
	}


	/**
	 * This inner class, RandomPiece, extends the JLabel class and it is responsible for the
	 * behavior of the random piece objects that will be used in the GUI.
	 */
	private class RandomPiece extends JLabel {

		/**
		 * This contructor is responsible for setting the instantiated object. It sets the
		 * name, icon (for enabled and disabled), enability, mouse listener and background
		 * of this object.
		 * 
		 * @param name name given this component object
		 * @param back backside of the random piece
		 * @param animalPiece assigned animal piece for this random piece object
		 */
		public RandomPiece(String name, ImageIcon back, ImageIcon animalPiece) {
			setName(name);
			setIcon(back);
			setDisabledIcon(animalPiece);
			setEnabled(true);
			addMouseListener(randomPicker);
			setBackground(TRANSPARENT);
		}
	}


	private class BoardTile extends JPanel	{
		
		private JLabel animalPiece;
		private ImageIcon tile;

		public BoardTile(String tileID, ImageIcon tilePic) {
			animalPiece = null;
			tile = tilePic;
			setName(tileID);
			setLayout(new BorderLayout());
			setPreferredSize(TILE_SIZE);
			setBackground(TRANSPARENT);
			setEnabled(false);
			addMouseListener(boardListener);
		}	

		public boolean hasPiece() {
			if(animalPiece != null)
				return true;

			return false;
		}

		//when animal moves in
		public void addPiece(String pieceId) {
			animalPiece = PIECE_PICS[Integer.parseInt("" + pieceId.charAt(0))][Integer.parseInt("" + pieceId.charAt(1)) - 1];
			
			setEnabled(true);
			add(animalPiece);
		}

		//when animal moves out
		public void removePiece() {
			remove(animalPiece);
			animalPiece = null;
			setEnabled(false);
			// repaint();
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D t = (Graphics2D) g;
			t.drawImage(tile.getImage(), 0, 0, null);
		}
	}

	private JPanel randPieceContainer;
	private JLabel startButton;

	private MouseListener randomPicker; 
	private MouseListener boardListener;

	private final Color TRANSPARENT;
	
	private final Dimension DEF_FRAME_SIZE;
	private final Dimension DEFAULT_SIZE;
	private final Dimension[] LOWER_CONTAINER_SIZE;
	private final Dimension TEXT_LABEL_SIZE;
	private final Dimension COLOR_PANEL_SIZE;
	private final Dimension PIECE_SIZE;
	private final Dimension TILE_SIZE;
	private final Dimension BUTTON_SIZE; 

	private final JLayeredPane BASE;

	private final JPanel[] BACKGROUNDS; 
	private final JPanel UPPER_CONTAINER; 
	private final JPanel LOWER_CONTAINER;
	private final JPanel TEXT_PANEL;
	private final JPanel TEXT_CONTAINER;
	private final JPanel TEXT_BOARD;
	private final JPanel POPUP_PANEL;
	private final JPanel HEADER;
	private final JPanel RED_PANEL;
	private final JPanel BLUE_PANEL;
	private final JPanel BOARD_PANEL;
	private final JPanel BOARD_CONTAINER;
	
	private final JLabel[] TEXT_LABELS;
	private final JLabel POPUP_PAPER;
	private final JLabel RED_PLAYER;
	private final JLabel BLUE_PLAYER;
	private final JLabel[][] PIECE_PICS;

	private final JButton[] CHOICE_BUTTONS;

	private final BoardTile[][] TILES;
	private final ImageIcon[] TERRAIN_PICS;

}

