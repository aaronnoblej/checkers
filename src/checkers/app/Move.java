/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers.app;

/**
 *
 * @author aaron
 */
class Move {
    
    //==========================================================================
    // PROPERTIES
    //==========================================================================
    
    private Piece piece;
    public Piece getPiece() {return piece;}
    public void setPiece(Piece val) {piece = val;}
    
    private Slot slot;
    public Slot getSlot() {return slot;}
    public void setSlot(Slot val) {slot = val;}
	
	private boolean jump;
	public boolean getJump() {return jump;}
	public void setJump(boolean val) {jump = val;}
	
	private Piece jumpedPiece;
	public Piece getJumpedPiece() {return jumpedPiece;}
	public void setJumpedPiece(Piece val) {jumpedPiece = val;}
	
	private boolean kingMove;
	public boolean getKingMove() {return kingMove;}
	public void setKingMove(boolean val) {kingMove = val;}
    
    //==========================================================================
    // CONSTRUCTORS
    //==========================================================================
	
	public Move(Piece movingPiece, Slot resultingSlot) {
		setPiece(movingPiece);
        setSlot(resultingSlot);
	}
    
    //==========================================================================
    // METHODS
    //==========================================================================
    
}
