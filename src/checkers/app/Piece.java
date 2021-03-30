/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers.app;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JLabel;

/**
 *
 * @author aaron
 */
public class Piece implements Cloneable {
    
    //==========================================================================
    // PROPERTIES
    //==========================================================================
    
    private JLabel pieceDesign = new JLabel();
    public JLabel getPieceDesign() {return pieceDesign;}
    public void setPieceDesign(JLabel val) {pieceDesign = val;}
    
    private Player owner = null;
    public Player getOwner() {return owner;}
    public void setOwner(Player val) {owner = val;}
    
    private Slot slot = null;
    public Slot getSlot() {return slot;}
    public void setSlot(Slot val) {slot = val;}
        
    private ArrayList<Slot> availableMoves = new ArrayList();
    public ArrayList<Slot> getAvailableMoves() {return availableMoves;}
    public void setAvailableMoves(ArrayList<Slot> val) {availableMoves = val;}
    
    private boolean isKing = false;
    public boolean isKing() {return isKing;}
    public void setKing(boolean val) {
        isKing = val;
        if(val) System.out.println("KINGED!");
    }
		
    //==========================================================================
    // CONSTRUCTORS
    //==========================================================================
    
    public Piece() {}
    
    public Piece(Player owner) {
        setOwner(owner);
    }
    
    //==========================================================================
    // METHODS
    //==========================================================================

    /**
     * Moves the current piece to its new position and removes the piece that was jumped from memory
     * @param piece Piece to be jumped/removed
     */
    public void jump(Piece piece) {
        piece.getSlot().setOccupied(false);
        piece.getPieceDesign().getParent().remove(piece.getPieceDesign());
        piece.getOwner().getPieces().remove(piece);
        System.out.println("JUMPED!");
		piece.getOwner().setJumped(true);
    }
    
    /**
     * Finds available slots and adds them to the available moves list
     * @param board - board in which should be analyzed for moves
     */
    public void genAvailableMoves(Board board) {
        // For Player 1
        if(getOwner() == board.getP1() || isKing()) {
            ArrayList<Slot> temp = new ArrayList();
            try {
                //Checks if down-right slot is occupied
                if(!board.getSlots()[getSlot().getRow()+1][getSlot().getColumn()+1].getOccupied()) {
                    temp.add(board.getSlots()[getSlot().getRow()+1][getSlot().getColumn()+1]);
                //Checks if slot two down and two right is available
                } else {
                    if(!board.getSlots()[getSlot().getRow()+2][getSlot().getColumn()+2].getOccupied() && board.getSlots()[getSlot().getRow()+1][getSlot().getColumn()+1].getOccupyingPiece(board).getOwner() == getOwner().getOpponent()) {
                        temp.add(board.getSlots()[getSlot().getRow()+2][getSlot().getColumn()+2]);
                    }
                }
            } catch(ArrayIndexOutOfBoundsException e) {}
            try {
                //Checks if down-left slot is occupied
                if(!board.getSlots()[getSlot().getRow()+1][getSlot().getColumn()-1].getOccupied()) {
                    temp.add(board.getSlots()[getSlot().getRow()+1][getSlot().getColumn()-1]);
                //Checks if slot two down and two left is available
                } else {
                    if(!board.getSlots()[getSlot().getRow()+2][getSlot().getColumn()-2].getOccupied() && board.getSlots()[getSlot().getRow()+1][getSlot().getColumn()-1].getOccupyingPiece(board).getOwner() == getOwner().getOpponent()) {
                        temp.add(board.getSlots()[getSlot().getRow()+2][getSlot().getColumn()-2]);
                    }
                }
            } catch(ArrayIndexOutOfBoundsException e) {}
            setAvailableMoves(temp);
        } 
        if(getOwner() == board.getP2() || isKing()) {
            //For Player 2
            ArrayList<Slot> temp = new ArrayList();
            try {
                //Checks if up-left slot is occupied
                if(!board.getSlots()[getSlot().getRow()-1][getSlot().getColumn()-1].getOccupied()) {
                    temp.add(board.getSlots()[getSlot().getRow()-1][getSlot().getColumn()-1]);
                //Checks if slot two up and two left is available
                } else {
                    if(!board.getSlots()[getSlot().getRow()-2][getSlot().getColumn()-2].getOccupied() && board.getSlots()[getSlot().getRow()-1][getSlot().getColumn()-1].getOccupyingPiece(board).getOwner() == getOwner().getOpponent()) {
                        temp.add(board.getSlots()[getSlot().getRow()-2][getSlot().getColumn()-2]);
                    }
                }
            } catch(ArrayIndexOutOfBoundsException e) {}
            try {
                //Checks if up-right slot is occupied
                if(!board.getSlots()[getSlot().getRow()-1][getSlot().getColumn()+1].getOccupied()) {
                    temp.add(board.getSlots()[getSlot().getRow()-1][getSlot().getColumn()+1]);
                //Checks if slot two up and two right is available
                } else {
                    if(!board.getSlots()[getSlot().getRow()-2][getSlot().getColumn()+2].getOccupied() && board.getSlots()[getSlot().getRow()-1][getSlot().getColumn()+1].getOccupyingPiece(board).getOwner() == getOwner().getOpponent()) {
                        temp.add(board.getSlots()[getSlot().getRow()-2][getSlot().getColumn()+2]);
                    }
                }
            } catch(ArrayIndexOutOfBoundsException e) {}
            //Additional moves if move is king
            if(isKing()) {
                try {
                    //Checks if down-right slot is occupied
                    if(!board.getSlots()[getSlot().getRow()+1][getSlot().getColumn()+1].getOccupied()) {
                        temp.add(board.getSlots()[getSlot().getRow()+1][getSlot().getColumn()+1]);
                    //Checks if slot two down and two right is available
                    } else {
                        if(!board.getSlots()[getSlot().getRow()+2][getSlot().getColumn()+2].getOccupied() && board.getSlots()[getSlot().getRow()+1][getSlot().getColumn()+1].getOccupyingPiece(board).getOwner() == getOwner().getOpponent()) {
                            temp.add(board.getSlots()[getSlot().getRow()+2][getSlot().getColumn()+2]);
                        }
                    }
                } catch(ArrayIndexOutOfBoundsException e) {}
                try {
                    //Checks if down-left slot is occupied
                    if(!board.getSlots()[getSlot().getRow()+1][getSlot().getColumn()-1].getOccupied()) {
                        temp.add(board.getSlots()[getSlot().getRow()+1][getSlot().getColumn()-1]);
                    //Checks if slot two down and two left is available
                    } else {
                        if(!board.getSlots()[getSlot().getRow()+2][getSlot().getColumn()-2].getOccupied() && board.getSlots()[getSlot().getRow()+1][getSlot().getColumn()-1].getOccupyingPiece(board).getOwner() == getOwner().getOpponent()) {
                            temp.add(board.getSlots()[getSlot().getRow()+2][getSlot().getColumn()-2]);
                        }
                    }
                } catch(ArrayIndexOutOfBoundsException e) {}
            }
            setAvailableMoves(temp);
        }
    }
    
    /**
     * Generates an ArrayList of available jumps for that piece
     * @param board - board to generate jumps for
     * @return the ArrayList of slots that are jump moves
     */
    public ArrayList<Slot> genJumps(Board board) {
        ArrayList<Slot> jumps = new ArrayList();
        //Player 1
        if(getOwner() == board.getP1() || isKing()) {
            //Down-Right Jump
            try {
                if(board.getSlots()[getSlot().getRow()+1][getSlot().getColumn()+1].getOccupied()) {
                    if(!board.getSlots()[getSlot().getRow()+2][getSlot().getColumn()+2].getOccupied() && board.getSlots()[getSlot().getRow()+1][getSlot().getColumn()+1].getOccupyingPiece(board).getOwner() == getOwner().getOpponent()) {
                        jumps.add(board.getSlots()[getSlot().getRow()+2][getSlot().getColumn()+2]);
                    }
                }
            } catch(ArrayIndexOutOfBoundsException e) {}
            //Down-Left Jump
            try {
                if(board.getSlots()[getSlot().getRow()+1][getSlot().getColumn()-1].getOccupied()) {
                    if(!board.getSlots()[getSlot().getRow()+2][getSlot().getColumn()-2].getOccupied() && board.getSlots()[getSlot().getRow()+1][getSlot().getColumn()-1].getOccupyingPiece(board).getOwner() == getOwner().getOpponent()) {
                        jumps.add(board.getSlots()[getSlot().getRow()+2][getSlot().getColumn()-2]);
                    }
                }
            } catch(ArrayIndexOutOfBoundsException e) {}
        }
        //Player 2
        if(getOwner() == board.getP2() || isKing()) {
            //Up-Left Jump
            try {
                if(board.getSlots()[getSlot().getRow()-1][getSlot().getColumn()-1].getOccupied()) {
                    if(!board.getSlots()[getSlot().getRow()-2][getSlot().getColumn()-2].getOccupied() && board.getSlots()[getSlot().getRow()-1][getSlot().getColumn()-1].getOccupyingPiece(board).getOwner() == getOwner().getOpponent()) {
                        jumps.add(board.getSlots()[getSlot().getRow()-2][getSlot().getColumn()-2]);
                    }
                }
            } catch(ArrayIndexOutOfBoundsException e) {}
            //Up-Right Jump
            try {
                if(board.getSlots()[getSlot().getRow()-1][getSlot().getColumn()+1].getOccupied()) {
                    if(!board.getSlots()[getSlot().getRow()-2][getSlot().getColumn()+2].getOccupied() && board.getSlots()[getSlot().getRow()-1][getSlot().getColumn()+1].getOccupyingPiece(board).getOwner() == getOwner().getOpponent()) {
                        jumps.add(board.getSlots()[getSlot().getRow()-2][getSlot().getColumn()+2]);
                    }
                }
            } catch(ArrayIndexOutOfBoundsException e) {}
        }
        return jumps;
    }
    
    @Override
    public Piece clone() {
        try {
            return (Piece) super.clone();
        } catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
