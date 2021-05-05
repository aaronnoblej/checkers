/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers.app;

import java.util.ArrayList;

/**
 *
 * @author aaron
 */
public class Player {
    
    //==========================================================================
    // PROPERTIES
    //==========================================================================
    
    protected String name = "";
    public String getName() {return name;}
    public void setName(String val) {name = val;}
    
    protected boolean enabled = false;
    public boolean getEnabled() {return enabled;}
    public void setEnabled(boolean val) {enabled = val;}
    
    protected ArrayList<Piece> pieces = new ArrayList();
    public ArrayList<Piece> getPieces() {return pieces;}
    public void setPieces(ArrayList<Piece> val) {
        pieces = val;
        for(Piece piece : val) {
            piece.setOwner(this);
        }
    }
    
    protected ArrayList<Piece> moveablePieces = new ArrayList();
    public ArrayList<Piece> getMoveablePieces() {
        ArrayList<Piece> temp = new ArrayList();
        for(Piece piece : getPieces()) {
            if(!piece.getAvailableMoves().isEmpty()) {
                temp.add(piece);
            }
        }
        setMoveablePieces(temp);
        return moveablePieces;
    }
    protected void setMoveablePieces(ArrayList<Piece> val) {moveablePieces = val;}
    
    protected Player opponent;
    public Player getOpponent() {return opponent;}
    public void setOpponent(Player val) {opponent = val;}
	
	protected PlayerPanel panel;
	public PlayerPanel getPanel() {return panel;}
	public void setPanel(PlayerPanel val) {panel = val;}
	
	//protected boolean jumped = false;
	public void setJumped() {
		getOpponent().getPanel().addPiece();
	}
	
    //==========================================================================
    // CONSTRUCTORS
    //==========================================================================
    
    public Player(String name) {
        setName(name);
		if(name.equals("Red")) {
			setPanel(new PlayerPanel(this, true));
		} else {
			setPanel(new PlayerPanel(this, false));
		}
    }
	
	/**
	 * Cloning constructor
	 * @param player: player to be cloned
	 */
	public Player(Player player) {
		//Clone all pieces
		ArrayList<Piece> clonedPieces = new ArrayList();
		for(Piece piece : player.getPieces()) {
			Piece temp = new Piece(piece);
			temp.setOwner(this);
			clonedPieces.add(temp);
		}
		this.setPieces(clonedPieces);
	}
    
    //==========================================================================
    // METHODS
    //==========================================================================
    
    /**
     * Gets the sum of all pieces' available moves
     * @return Number of moves
     */
    public int numberOfMoves() {
        int count = 0;
        for(Piece piece : getPieces()) {
            count += piece.getAvailableMoves().size();
        }
        return count;
    }
    
}
