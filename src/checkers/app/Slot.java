/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers.app;

import java.awt.Cursor;
import javax.swing.JPanel;

/**
 *
 * @author aaron
 */
public class Slot {
    
    //==========================================================================
    // PROPERTIES
    //==========================================================================
    
    private boolean occupied = false;
    public boolean getOccupied() {return occupied;}
    public void setOccupied(boolean val) {occupied = val;}
    
    private boolean highlighted = false;
    public boolean getHighlighted() {return highlighted;}
    public void setHighlighted(boolean val) {highlighted = val;}
    
    private int row;
    public int getRow() {return row;}
    public void setRow(int val) {row = val;}
    
    private int column;
    public int getColumn() {return column;}
    public void setColumn(int val) {column = val;}
    
    //==========================================================================
    // CONSTRUCTORS
    //==========================================================================
    
    public Slot() {}
	
	/**
	 * Cloning constructor
	 * @param slot: slot to be cloned
	 */
	public Slot(Slot slot) {
		setOccupied(slot.getOccupied());
		setRow(slot.getRow());
		setColumn(slot.getColumn());
	}
        
    //==========================================================================
    // METHODS
    //==========================================================================
    
    public void addPiece(Piece piece) {
        setOccupied(true);
        piece.setSlot(this);
    }
    
    public Piece getOccupyingPiece(Board board) {
        if(getOccupied()) {
            //Check if P1 piece is occupying the slot
            for(Piece piece : board.getP1().getPieces()) {
                if(piece.getSlot() == this) {
                    return piece;
                }
            }
            //Check if P2 piece is occupying the slot
            for(Piece piece : board.getP2().getPieces()) {
                if(piece.getSlot() == this) {
                    return piece;
                }
            }
        }
        return null;
    }
    
}
