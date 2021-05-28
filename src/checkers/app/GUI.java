/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author aaron
 */
public class GUI extends JLayeredPane {
    
    //==========================================================================
    // PROPERTIES
    //==========================================================================
    
    //Colors
    public static Color boardColor1 = Color.RED;
    public static Color boardColor2 = Color.BLACK;
    public static Color highlightColor = Color.YELLOW;
    public static Color hoverColor = Color.GRAY;
    
    //Linked with game
	private final Game game;
    private JPanel[][] slots;
    public JPanel[][] getSlots() {return slots;}
    public void setSlots(JPanel[][] val) {slots = val;}
	
	//JPanel layers
	private JPanel background;
	private JPanel gridContainer;
	private JPanel mainContainer;
	
	private Image backgroundImage = new ImageIcon(getClass().getResource("background.jpg")).getImage();
    
    //==========================================================================
    // CONSTRUCTORS
    //==========================================================================
    
    public GUI(Game game) {
		//Basic component config
        this.game = game;
		this.setPreferredSize(new Dimension(CheckersApp.width, CheckersApp.height));
		mainContainer = new JPanel(new BorderLayout());
		mainContainer.setBounds(0, 0, CheckersApp.width, CheckersApp.height);
		mainContainer.setOpaque(false);
		
		//Set background image
		background = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
			}
		};
		background.setBounds(0, 0, CheckersApp.width, CheckersApp.height);
		this.add(background);
		
		//Set game
		gridContainer = new JPanel(new GridLayout(game.getBoard().getRows(), game.getBoard().getColumns(), 0, 0));
		gridContainer.setBorder(new LineBorder(Color.BLACK, 3));
		gridContainer.setPreferredSize(new Dimension(CheckersApp.height, CheckersApp.height));
		gridContainer.setBackground(Color.BLACK);
        //Set the slots
        setSlots(new JPanel[game.getBoard().getRows()][game.getBoard().getColumns()]);
        Color temp;
        for(int i = 0; i < game.getBoard().getRows(); i++) {
            if(i % 2 == 0) {
                temp = boardColor1;
            } else {
                temp = boardColor2;
            }
            for(int j = 0; j < game.getBoard().getColumns(); j++) {
                //Create the visual slot
                slots[i][j] = new JPanel(new BorderLayout());
                slots[i][j].setBackground(temp);
                //Switch color for next slot creation
                if(temp.equals(boardColor1)) {
                    temp = boardColor2;
                } else {
                    temp = boardColor1;
                }
				gridContainer.add(slots[i][j]);
            }
        }
		mainContainer.add(gridContainer, BorderLayout.CENTER);
		
		//Set panels
		mainContainer.add(game.getBoard().getP1().getPanel(), BorderLayout.WEST);
		mainContainer.add(game.getBoard().getP2().getPanel(), BorderLayout.EAST);
		
		this.add(mainContainer);
		this.setLayer(mainContainer, 1);
    }
    
    //==========================================================================
    // METHODS
    //==========================================================================
    
	/**
	 * Initiates each piece design and places them on the GUI board
	 */
	public void createPieces() {
		for(int i = 0; i < game.getBoard().getRows(); i++) {
			for(int j = 0; j < game.getBoard().getColumns(); j++) {
				if(!(i % 2 == j % 2) && i < 3) {
					JLabel piece = game.getBoard().getSlots()[i][j].getOccupyingPiece(game.getBoard()).getPieceDesign();
					slots[i][j].add(piece, BorderLayout.CENTER);
					piece.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("P1 Piece.png")).getImage().getScaledInstance(piece.getParent().getWidth(), piece.getParent().getHeight(), Image.SCALE_DEFAULT)));
                } else if(!(i % 2 == j % 2) && i > game.getBoard().getRows()-4) {
                    JLabel piece = game.getBoard().getSlots()[i][j].getOccupyingPiece(game.getBoard()).getPieceDesign();
					slots[i][j].add(piece, BorderLayout.CENTER);
					piece.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("P2 Piece.png")).getImage().getScaledInstance(piece.getParent().getWidth(), piece.getParent().getHeight(), Image.SCALE_DEFAULT)));
                }
			}
		}
	}
	
    /**
     * Updates the GUI to the user showing the results of last move
     * @param board Board state to be shown
     */
    public synchronized void update(Board board) {		
		Move lastMove = board.getLastMove();
		if(lastMove.getJump()) {
			lastMove.getJumpedPiece().getPieceDesign().getParent().remove(lastMove.getJumpedPiece().getPieceDesign());
			lastMove.getJumpedPiece().getOwner().setJumped();
			lastMove.setJump(false);
		}
		if(lastMove.getKingMove()) {
			Piece piece = lastMove.getPiece();
			if(game.getCurrentTurn() == game.getBoard().getP1()) {
				piece.getPieceDesign().setIcon(new ImageIcon(new ImageIcon(getClass().getResource("P1 King.png")).getImage().getScaledInstance(piece.getPieceDesign().getParent().getWidth(), piece.getPieceDesign().getParent().getHeight(), Image.SCALE_DEFAULT)));
			} else {
				piece.getPieceDesign().setIcon(new ImageIcon(new ImageIcon(getClass().getResource("P2 King.png")).getImage().getScaledInstance(piece.getPieceDesign().getParent().getWidth(), piece.getPieceDesign().getParent().getHeight(), Image.SCALE_DEFAULT)));
			}
			lastMove.setKingMove(false);
		}
		lastMove.getPiece().getPieceDesign().getParent().remove(lastMove.getPiece().getPieceDesign());
		slots[lastMove.getSlot().getRow()][lastMove.getSlot().getColumn()].add(lastMove.getPiece().getPieceDesign());
		lastMove.getOrigin().setHighlighted(false);
		game.highlightSlot(lastMove.getOrigin());
		for(Slot slot : lastMove.getPiece().getAvailableMoves()) {
            slot.setHighlighted(false);
			game.highlightSlot(slot);
        }
		revalidate();
		repaint();
    }
	
	public void winnerPopup(Player winner) {
		JPanel popup = new JPanel(new BorderLayout());
		JLabel name = new JLabel();
		JLabel text = new JLabel("Click to return to the main menu.");
		popup.setBounds(gridContainer.getX(), (CheckersApp.height-200)/2, CheckersApp.height, 200);
		
		//If tie game (nobody can move and score is equal)
		if(winner == null) {
			name.setText("Tie game!");
		} else {
			name.setText(winner.getName() + " Wins!");
		}
		
		popup.setOpaque(false);
		
		name.setFont(new Font("Arial", Font.BOLD, 48));
		name.setHorizontalAlignment(JLabel.CENTER);
		name.setForeground(Color.WHITE);
		text.setFont(new Font("Arial", Font.BOLD, 18));
		text.setHorizontalAlignment(JLabel.CENTER);
		text.setForeground(Color.WHITE);
		
		popup.add(name, BorderLayout.NORTH);
		popup.add(text, BorderLayout.CENTER);
		
		this.add(popup);
		this.setLayer(popup, 3);
		popup.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		game.getCurrentTurn().getOpponent().getPanel().highlight();
		
		//Mouse listener
		popup.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CheckersApp.game = null;
				CheckersApp.screen = new MainScreen();
				CheckersApp.checkers.getContentPane().removeAll();
				CheckersApp.checkers.setContentPane(CheckersApp.screen);
				CheckersApp.checkers.repaint();
				CheckersApp.checkers.revalidate();
			}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
			
		});
	}
	
	public void playSound(String fileName) {
		//Add sound effects and music
	}
    
}
