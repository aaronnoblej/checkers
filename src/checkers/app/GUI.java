/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author aaron
 */
public class GUI extends JPanel {
    
    //==========================================================================
    // PROPERTIES
    //==========================================================================
    
    //Colors
    public static Color boardColor1 = Color.RED;
    public static Color boardColor2 = Color.BLACK;
    public static Color highlightColor = Color.YELLOW;
    public static Color hoverColor = Color.GRAY;
    
    //Linked with game
	private Game game;
    public static JPanel[][] slots = null;
    public JPanel[][] getSlots() {return slots;}
    public void setSlots(JPanel[][] val) {slots = val;}
    
    //==========================================================================
    // CONSTRUCTORS
    //==========================================================================
    
    public GUI(Game game) {
        this.game = game;
		this.setBorder(new LineBorder(Color.BLACK, 3));
        this.setPreferredSize(new Dimension(CheckersApp.height, CheckersApp.height));
        //Set the pieces
        setSlots(new JPanel[game.getBoard().getRows()][game.getBoard().getColumns()]);
        this.setLayout(new GridLayout(game.getBoard().getRows(), game.getBoard().getColumns(), 0, 0));
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
                this.add(slots[i][j]);
            }
        }
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
    public void update(Board board) {
        board.getLastMove().getPiece().getPieceDesign().getParent().remove(board.getLastMove().getPiece().getPieceDesign());
        slots[board.getLastMove().getSlot().getRow()][board.getLastMove().getSlot().getColumn()].add(board.getLastMove().getPiece().getPieceDesign());
        revalidate();
        repaint();
    }
    
}
