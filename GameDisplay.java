import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * This GameDisplay class is responsible for the displays or outputs in the terminal.
 * It is used by the driver class to provide information and UI/UX for the players of 
 * the game.
 * 
 * @author Pierre Vincent Hernandez
 * @author Matthew James Villarica
 */
public class GameDisplay extends JFrame  {

	public GameDisplay() {
		
		randPieceContainer = new JPanel();
		startButton = new JLabel(new ImageIcon("images\\start.png"));

		randomPicker = null;
		boardListener = null;

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
		PIECE_SIZE = new Dimension(78, 78);
		TILE_SIZE = new Dimension(80, 80);
		BUTTON_SIZE = new Dimension(200, 100);

		BASE = new JLayeredPane();
		
		BACKGROUNDS = new JPanel[2];
		BACKGROUNDS[0] = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D bg = (Graphics2D) g;
				bg.drawImage(new ImageIcon("images\\background.png").getImage(), 0, 0, null);
			}
		};
		BACKGROUNDS[1] = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D wood = (Graphics2D) g;
				wood.drawImage(new ImageIcon("images\\boardBackground.jpg").getImage(), 0, 0, null);
			}
		};

		TEXT_BOARD = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D board = (Graphics2D) g;
				board.drawImage(new ImageIcon("images\\textBoard.png").getImage(), 0, 0, null);
			}
		};

		HEADER = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D h = (Graphics2D) g;
				h.drawImage(new ImageIcon("images\\header.png").getImage(), 0, 0, null);
			}
		};

		TEXT_PANEL = new JPanel();
		TEXT_CONTAINER = new JPanel(); 
		UPPER_CONTAINER = new JPanel();
		LOWER_CONTAINER = new JPanel();
		POPUP_PANEL = new JPanel();
		RED_PANEL = new JPanel();
		BLUE_PANEL = new JPanel();
		BOARD_PANEL = new JPanel();
		BOARD_CONTAINER = new JPanel();
		
		
		TEXT_LABELS = new JLabel[2];
		TEXT_LABELS[0] = new JLabel();
		TEXT_LABELS[1] = new JLabel();

		POPUP_PAPER = new JLabel(new ImageIcon("images\\popup.png"));
		RED_PLAYER = new JLabel();
		BLUE_PLAYER = new JLabel();

		CHOICE_BUTTONS = new JButton[3];
		CHOICE_BUTTONS[0] = new JButton(); // red
		CHOICE_BUTTONS[1] = new JButton(); // blue
		CHOICE_BUTTONS[2] = new JButton(); // ok

		TILES = new BoardTile[9][7];
		PIECE_PICS = new JLabel[2][8];
		
		TERRAIN_PICS = new ImageIcon[4];
		TERRAIN_PICS[0] = new ImageIcon("images\\land.png");
		TERRAIN_PICS[1] = new ImageIcon("images\\river.png");
		TERRAIN_PICS[2] = new ImageIcon("images\\trap.png");
		TERRAIN_PICS[3] = new ImageIcon("images\\animalDen.png");

		
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

		// set HEADER
		HEADER.setPreferredSize(new Dimension(1017, 114));

		//	set TEXT_LABELS
		setTextLabels();

		// set POPUP_PAPER
		setPopupPaper();

		//set blue and red player banners on left and right of the board
		setPlayerBanners();

		//set choice buttons on player banner
		setChoiceButtons();
		
		//set main JFrame of GUI and add base
		setFrame();
		
		// add the base to this frame
		this.add(BASE, BorderLayout.CENTER);
		// add components to the base
		BASE.add(BACKGROUNDS[0], JLayeredPane.DEFAULT_LAYER);
		BACKGROUNDS[0].add(LOWER_CONTAINER, BorderLayout.SOUTH);
		LOWER_CONTAINER.add(startButton);

		
		repaint();
		validate();
		// System.out.println(this.getSize().toString());
	}


	public void setListeners(MouseListener start, MouseListener random, ActionListener choicePicker, MouseListener board) {
		startButton.addMouseListener(start);
		// set the listener for other MouseInputListener attributes of this class
		// implementation of MouseInputListener are in Game class
		randomPicker = random;
		CHOICE_BUTTONS[0].addActionListener(choicePicker);
		CHOICE_BUTTONS[1].addActionListener(choicePicker);
		CHOICE_BUTTONS[2].addActionListener(choicePicker);
		boardListener = board;
	}

	
	public JLabel getStartButton() {
		return startButton;
	}

	public void removeStartButton() {
		LOWER_CONTAINER.remove(startButton);
		startButton = null;
	}

	public void removeRandomChoices() {
		LOWER_CONTAINER.remove(randPieceContainer);
		randPieceContainer = null;
	}

	
	public void initBoardDisplay(BoardModel board) {

		for(int row = 0; row < 9; row++) 
			for(int col = 0; col < 7; col++) {
				if(board.getTiles().getTerrains()[row][col].isLand())
					TILES[row][col] = new BoardTile("" + row + col, TERRAIN_PICS[0]);
				else if (board.getTiles().getTerrains()[row][col].isRiver())
					TILES[row][col] = new BoardTile("" + row + col, TERRAIN_PICS[1]);
				else if(board.getTiles().getTerrains()[row][col].isTrap())
					TILES[row][col] = new BoardTile("" + row + col, TERRAIN_PICS[2]);
				else if(board.getTiles().getTerrains()[row][col].isAnimalDen())
					TILES[row][col] = new BoardTile("" + row + col, TERRAIN_PICS[3]);
				
				if(board.getTiles().getTerrains()[row][col].getState())
					TILES[row][col].addPiece("" + (board.getTiles().getTerrains()[row][col].getAnimal().getPlayerSide() - 1) +
					 	board.getTiles().getTerrains()[row][col].getAnimal().getRank());
			}
	}

	public void assignPlayers(int playerInd) {
		// HEADER.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		String[] playerString = new String[2];

		playerString[playerInd] = "<HTML>P<br>L<br>A<br>Y<br>E<br>R<br><br>1</HTML>";
		playerString[(playerInd + 1) % 2] = "<HTML>P<br>L<br>A<br>Y<br>E<br>R<br><br>2</HTML>";

		RED_PLAYER.setText(playerString[0]);
		System.out.println("Assigning players... Red is " + RED_PLAYER.getText());

		BLUE_PLAYER.setText(playerString[1]);
	}

	
	public void updateTurn(int turn) {
		switch(turn) {
			case 1:
				TEXT_LABELS[1].setText("TURN: PERSON 2");
				break;

			default:
				TEXT_LABELS[1].setText("LOADING ...");
				break;
		}

		repaint();
	}

	public void updateColorPanel(int turn) {
		if(turn == 0) { // 0 - red
			RED_PANEL.setBackground(Color.RED);
			BLUE_PANEL.setBackground(Color.LIGHT_GRAY);
		} else { // 1 = blue
			RED_PANEL.setBackground(Color.LIGHT_GRAY);
			BLUE_PANEL.setBackground(Color.BLUE);
		}
	}

	public void updateTiles(Terrain[][] board, Animal movingPiece, String[] validIDs, int move) {
		for(int m = 0; m < validIDs.length; m++)
			if(!validIDs[m].equalsIgnoreCase("null")) {
				switch(move) {
					case 0: // done making a move
						TILES[movingPiece.getRow()][movingPiece.getCol()]
							.setBorder(BorderFactory.createEmptyBorder());

						TILES[Integer.parseInt("" + validIDs[m].charAt(0))]
							[Integer.parseInt("" + validIDs[m].charAt(1))]
							.setBorder(BorderFactory.createEmptyBorder());

						// if there are no pieces occupying current tile
						if(!board[Integer.parseInt("" + validIDs[m].charAt(0))]
						[Integer.parseInt("" + validIDs[m].charAt(1))].getState())
							TILES[Integer.parseInt("" + validIDs[m].charAt(0))]
								[Integer.parseInt("" + validIDs[m].charAt(1))]
								.setEnabled(false);
						break;

					case 1: // starting to make a move
						TILES[movingPiece.getRow()][movingPiece.getCol()]
							.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));

						TILES[Integer.parseInt("" + validIDs[m].charAt(0))]
							[Integer.parseInt("" + validIDs[m].charAt(1))]
							.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));

						TILES[Integer.parseInt("" + validIDs[m].charAt(0))]
							[Integer.parseInt("" + validIDs[m].charAt(1))]
							.setEnabled(true);
						break;
				}
					
			}
	}


	public void movePiece(Terrain[][] board, Animal movingPiece, String[] validIDs, boolean movesIn) {
		if(TILES[movingPiece.getRow()][movingPiece.getCol()].hasPiece())
			TILES[movingPiece.getRow()][movingPiece.getCol()].removePiece();

		if(movesIn) {
			TILES[movingPiece.getRow()][movingPiece.getCol()].addPiece("" + (movingPiece.getPlayerSide() - 1) + movingPiece.getRank());
			updateTiles(board, movingPiece, validIDs, 0);
		} else
			TILES[movingPiece.getRow()][movingPiece.getCol()]
				.setBorder(BorderFactory.createEmptyBorder());
	}

	public void displayRandomChoices(int[] randIndexes) {
		TEXT_LABELS[0].setText("~ PICK A PIECE ~");
		
		TEXT_LABELS[1].setText("TURN: PERSON 1");
		
		initRandomPieces(randIndexes);

		LOWER_CONTAINER.setPreferredSize(LOWER_CONTAINER_SIZE[1]);

		BACKGROUNDS[0].add(UPPER_CONTAINER, BorderLayout.CENTER);
		UPPER_CONTAINER.add(TEXT_PANEL, BorderLayout.SOUTH);
		TEXT_PANEL.add(TEXT_BOARD);
		TEXT_BOARD.add(TEXT_CONTAINER, BorderLayout.SOUTH);
		TEXT_CONTAINER.add(TEXT_LABELS[0]);
		TEXT_CONTAINER.add(TEXT_LABELS[1]);
		LOWER_CONTAINER.add(randPieceContainer);
		revalidate();
	}

	public void displayColorChoices(int person) {
		randPieceContainer.removeAll();
		
		TEXT_PANEL.removeAll();

		TEXT_LABELS[0].setText("~ PICK A COLOR ~");
		TEXT_LABELS[1].setText("PERSON " + person + " IS PLAYER 1");

		BACKGROUNDS[0].removeAll();

		UPPER_CONTAINER.removeAll();
		
		LOWER_CONTAINER.removeAll();
		LOWER_CONTAINER.setPreferredSize(LOWER_CONTAINER_SIZE[0]); // new Dimension(600, 400)
		// LOWER_CONTAINER.setBackground(Color.BLACK);

		BASE.add(POPUP_PANEL, JLayeredPane.POPUP_LAYER); 
		POPUP_PANEL.add(POPUP_PAPER, BorderLayout.CENTER); 
		
		POPUP_PAPER.add(LOWER_CONTAINER, BorderLayout.SOUTH);
		LOWER_CONTAINER.add(CHOICE_BUTTONS[0]);
		LOWER_CONTAINER.add(CHOICE_BUTTONS[1]);
		POPUP_PAPER.add(UPPER_CONTAINER, BorderLayout.CENTER);
		UPPER_CONTAINER.add(TEXT_PANEL, BorderLayout.SOUTH);
		TEXT_PANEL.add(TEXT_CONTAINER);

		// BASE.repaint();
		// BASE.revalidate();
		repaint();
		revalidate();
	}

	public void displayAnimalChess(int playerInd) {
		assignPlayers(playerInd);

		// BASE.removeAll();
		// BASE.add(BOARD_PANEL, JLayeredPane.DEFAULT_LAYER);
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

		repaint();
		revalidate();
	}

	public void displayResults(int winningPlayer) {
		if(winningPlayer == 0) // 0 - red
			TEXT_LABELS[0].setText("PLAYER " + RED_PLAYER.getText().charAt(40) + " WINS !!!");
		else // 1 - blue
			TEXT_LABELS[0].setText("PLAYER " + BLUE_PLAYER.getText().charAt(40) + " WINS !!!");
		
		TEXT_LABELS[1].setText("WANNA PLAY AGAIN DADDY?");

		LOWER_CONTAINER.removeAll(); // remove red & blue buttons
		LOWER_CONTAINER.add(CHOICE_BUTTONS[2]);

		BASE.add(POPUP_PANEL, JLayeredPane.POPUP_LAYER);

		repaint();
		revalidate();
	}


	private void addBoardTiles() {
		
		for(int col = 6; col >= 0; col--)
			for(int row = 0; row < 9; row++)
				BOARD_PANEL.add(TILES[row][col]);
	}
	
	private void initRandomPieces(int[] randIndexes) {
		int n;

		for(n = 0; n < randIndexes.length; n++)
			randPieceContainer.add(new RandomPiece("" + randIndexes[n], 
				new ImageIcon("images\\randPiece.png"), 
				new ImageIcon("images\\" + (randIndexes[n] + 1) + ".png")));
	}

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

	private void setBase() {
		BASE.setLayout(null);
		BASE.setBounds(0, 0, (int)DEFAULT_SIZE.getWidth(), (int)DEFAULT_SIZE.getHeight());
		BASE.setBackground(TRANSPARENT);
		// System.out.println(BASE.getSize());
	}

	private void setBackgrounds() {
		// BACKGROUNDS[0].setSize(1033, 772);
		BACKGROUNDS[0].setLayout(new BorderLayout());
		BACKGROUNDS[0].setBounds(0, 0, (int)DEFAULT_SIZE.getWidth(), (int)DEFAULT_SIZE.getHeight());
		// System.out.println(BACKGROUNDS[0].getSize());

		BACKGROUNDS[1].setLayout(new BorderLayout());
		// BACKGROUNDS[1].setBounds(0, 0, 777, 618);
	}

	private void setTransparentContainers() {
		// set transparent upper container 
		UPPER_CONTAINER.setLayout(new BorderLayout());
		UPPER_CONTAINER.setBackground(TRANSPARENT);

		// set transparent lower container
		LOWER_CONTAINER.setLayout(new FlowLayout(FlowLayout.CENTER, 56, 20));
		LOWER_CONTAINER.setPreferredSize(LOWER_CONTAINER_SIZE[0]); //new Dimension(1017, 400) 
		LOWER_CONTAINER.setBackground(TRANSPARENT);
	}
	
	private void setRandPieceContainer() {
		randPieceContainer.setLayout(new GridLayout(2, 4, 15, 15));
		randPieceContainer.setBackground(TRANSPARENT); // Color.BLACK
	}

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

	private void setPopupPanel() {
		POPUP_PANEL.setLayout(new BorderLayout());
		POPUP_PANEL.setBounds(0, 0, (int)DEFAULT_SIZE.getWidth() - 1, (int)DEFAULT_SIZE.getHeight()); // 1016, 732
		POPUP_PANEL.setBackground(new Color(0, 0, 0, 100));
		// POPUP_PANEL.setOpaque(true);
	}

	private void setColorPanels() {
		// set RED_PANEL
		RED_PANEL.setLayout(new BorderLayout());
		RED_PANEL.setBackground(Color.RED);
		RED_PANEL.setPreferredSize(COLOR_PANEL_SIZE);
		RED_PANEL.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		// System.out.println(RED_PANEL.getSize().toString());

		// set BLUE_PANEL
		BLUE_PANEL.setLayout(new BorderLayout());
		BLUE_PANEL.setBackground(Color.BLUE);
		BLUE_PANEL.setPreferredSize(COLOR_PANEL_SIZE); // new Dimension(120, 620)
		BLUE_PANEL.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		// System.out.println(BLUE_PANEL.getSize().toString());
	}

	private void setBoard() {
		BOARD_PANEL.setLayout(new GridLayout(7, 9, 1, 1)); 
		// BOARD_PANEL.setBounds(0, 0, 1017, 734);
		// BOARD_PANEL.setBounds(0, 0, 720, 560);
		BOARD_PANEL.setBackground(Color.BLACK);
		// BACKGROUNDS[1].setLayout(new FlowLayout());
		
		
		BOARD_CONTAINER.setLayout(new FlowLayout());
		BOARD_CONTAINER.setPreferredSize(new Dimension(1000, 617));
		BOARD_CONTAINER.setBackground(TRANSPARENT);
	}
	
	private void setTextLabels() {
		// set TEXT_LABELS[0]
		TEXT_LABELS[0].setPreferredSize(TEXT_LABEL_SIZE); // new Dimension(400, 50)
		TEXT_LABELS[0].setFont(new Font("Showcard Gothic", Font.PLAIN, 32));
		TEXT_LABELS[0].setForeground(Color.BLACK);
		TEXT_LABELS[0].setHorizontalAlignment(JLabel.CENTER);
		// System.out.println(TEXT_LABELS[0].getSize().toString());

		// set TEXT_LABELS[1]
		TEXT_LABELS[1].setPreferredSize(TEXT_LABEL_SIZE); // new Dimension(400, 50)
		TEXT_LABELS[1].setFont(new Font("Showcard Gothic", Font.PLAIN, 28));
		TEXT_LABELS[1].setForeground(Color.BLACK);
		TEXT_LABELS[1].setHorizontalAlignment(JLabel.CENTER);
		// System.out.println(TEXT_LABELS[1].getSize().toString());
	}

	private void setPopupPaper() {
		POPUP_PAPER.setLayout(new BorderLayout());
		POPUP_PAPER.setPreferredSize(new Dimension(600, 484));
		POPUP_PAPER.setBackground(TRANSPARENT);
		POPUP_PAPER.setVerticalAlignment(JLabel.CENTER);
		POPUP_PAPER.setHorizontalAlignment(JLabel.CENTER);
	}

	private void setPlayerBanners() {
		// set RED_PLAYER
		RED_PLAYER.setFont(new Font("Showcard Gothic", Font.PLAIN, 36));
		RED_PLAYER.setForeground(Color.BLACK);
		// RED_PLAYER.setBackground(TRANSPARENT);
		RED_PLAYER.setVerticalAlignment(JLabel.CENTER);
		RED_PLAYER.setHorizontalAlignment(JLabel.CENTER);

		// set BLUE_PLAYER
		BLUE_PLAYER.setFont(new Font("Showcard Gothic", Font.PLAIN, 36));
		BLUE_PLAYER.setForeground(Color.BLACK);
		// BLUE_PLAYER.setBackground(TRANSPARENT);
		BLUE_PLAYER.setVerticalAlignment(JLabel.CENTER);
		BLUE_PLAYER.setHorizontalAlignment(JLabel.CENTER);
	}

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

		// set CHOICE_BUTTONS[2] (red button)
		CHOICE_BUTTONS[2].setActionCommand("OK");
		CHOICE_BUTTONS[2].setFocusable(false);
		CHOICE_BUTTONS[2].setPreferredSize(BUTTON_SIZE);
		CHOICE_BUTTONS[2].setForeground(Color.BLACK);
		CHOICE_BUTTONS[2].setBackground(Color.GREEN);
	}

	private void setFrame() {
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
		
	}


	private class RandomPiece extends JLabel {

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

