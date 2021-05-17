/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers.app;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author aaron
 */
public class Board {
    
    //==========================================================================
    // PROPERTIES
    //==========================================================================
    
    private int rows = 8;
    public int getRows() {return rows;}
    public void setRows(int val) {rows = val;}
    
    private int columns = 8;
    public int getColumns() {return columns;}
    public void setColumns(int val) {columns = val;}
    
    private Slot[][] slots = null;
    public Slot[][] getSlots() {return slots;}
    public void setSlots(Slot[][] val) {slots = val;}
    
    private Player p1 = new Player("Red");
    public Player getP1() {return p1;}
    public void setP1(Player val) {p1 = val;}
    
    private Player p2 = new Player("Black");
    public Player getP2() {return p2;}
    public void setP2(Player val) {p2 = val;}
    
    private Move lastMove = null;
    public Move getLastMove() {return lastMove;}
    public void setLastMove(Move val) {lastMove = val;}
    
    //==========================================================================
    // CONSTRUCTORS
    //==========================================================================
    
    /**
     * Creates a default board with 8 rows and 8 columns
	 * @param isNew indicates if we need to initialize new pieces
     */
    public Board() {
		p1.setOpponent(p2);
        p2.setOpponent(p1);
		setSlots(new Slot[rows][columns]);
		createBoard();
		initPieces();
    }
	
	/**
	 * Cloning constructor, creates a deep copy of an already-existing board
	 * @param board: the board to be copied
	 */
	public Board(Board board) {
		//Clone player and pieces
		this.setP1(new Player(board.getP1()));
		this.setP2(new Player(board.getP2()));
		p1.setOpponent(p2);
        p2.setOpponent(p1);
		this.setRows(board.getRows());
		this.setColumns(board.getColumns());
		
		//Clone slots
		Slot[][] clonedSlots = new Slot[rows][columns];
		for(int r = 0; r < board.getRows(); r++) {
			for(int c = 0; c < board.getColumns(); c++) {
				clonedSlots[r][c] = new Slot(board.getSlots()[r][c]);
			}
		}
		this.setSlots(clonedSlots);

		//Set new pieces to new slots
		for(int i = 0; i < getP1().getPieces().size(); i++) {
			this.getP1().getPieces().get(i).setSlot(this.getSlots()[board.getP1().getPieces().get(i).getSlot().getRow()][board.getP1().getPieces().get(i).getSlot().getColumn()]);
		}
		for(int i = 0; i < getP2().getPieces().size(); i++) {
			this.getP2().getPieces().get(i).setSlot(this.getSlots()[board.getP2().getPieces().get(i).getSlot().getRow()][board.getP2().getPieces().get(i).getSlot().getColumn()]);
		}
		
	}
    
    /**
     * Creates a board with custom rows and columns
     * @param rows Number of rows
     * @param cols Number of columns
     */
    public Board(int rows, int cols) {
        p1.setOpponent(p2);
        p2.setOpponent(p1);
        setRows(rows);
        setColumns(cols);
        setSlots(new Slot[rows][cols]);
        //this.setLayout(new GridLayout(getRows(), getColumns()));
        createBoard();
        initPieces();
    }
    
    //==========================================================================
    // METHODS
    //==========================================================================
    
    /**
     * Sets slots, positions and colors
     */
    private void createBoard() {
        //Color temp;
        for(int i = 0; i < getRows(); i++) {
            /*if(i % 2 == 0) {
                temp = col1;
            } else {
                temp = col2;
            }*/
            for(int j = 0; j < getColumns(); j++) {
                getSlots()[i][j] = new Slot();
                getSlots()[i][j].setRow(i);
                getSlots()[i][j].setColumn(j);
                //getSlots()[i][j].setBackground(temp);
                /*if(temp.equals(col1)) {
                    temp = col2;
                } else {
                    temp = col1;
                }*/
                //this.add(getSlots()[i][j]);
            }
        }
    }
    
    /**
     * Creates, stores, and places the pieces on the board
     */
    private void initPieces() {
        for(int i = 0; i < getRows(); i++){
            for(int j = 0; j < getColumns(); j++) {
                if(!(i % 2 == j % 2) && i < 3) {
                    Piece piece = new Piece(p1);
                    getSlots()[i][j].addPiece(piece);
                    getP1().getPieces().add(piece);
                } else if(!(i % 2 == j % 2) && i > getRows()-4) {
                    Piece piece = new Piece(p2);
                    getSlots()[i][j].addPiece(piece);
                    piece.getPieceDesign().setIcon(new ImageIcon(getClass().getResource("P2 Piece.png")));
                    getP2().getPieces().add(piece);
                }
            }
        }
    }
    
    /**
     * Gives each piece a value on the board and computes a total score value for the board
	 * Looks at number of pieces, kings, number of jumps, and number of moves
     * @return score of the board
     */
    public int getScore() {
        int score = 0;
        for(Piece piece : getP1().getPieces()) {
            if(piece.isKing()) score += 3;
            else score += 1;
			score += piece.genJumps(this).size();
        }
        for(Piece piece : getP2().getPieces()) {
            if(piece.isKing()) score -= 3;
            else score -= 1;
			score -= piece.genJumps(this).size();
        }
        return score;
    }
	
	public Player getWinningPlayer() {
		if(getScore() < 0) return getP2();
		else if(getScore() > 0) return getP1();
		else return null;
	}
    
    /**
     * Checks if a player has won
     * A player wins if the opposing player has no pieces remaining or has no more available moves
	 * @param currentTurn the current player's turn; it will check if the player's opponent has anymore moves
     * @return true if a player has won and the game is over
     */
    public boolean checkWin(Player currentTurn) {
		//Check if anyone is out of pieces
		if(getP2().getPieces().isEmpty()) {
            System.out.println("Game Over! Player 1 Wins!");
            return true;
        } else if(getP1().getPieces().isEmpty()) {
            System.out.println("Game Over! Player 2 Wins!");
            return true;
        }
		//Check if the current player can make moves
		if(currentTurn.numberOfMoves() == 0) {
			if(currentTurn == getP1()) {
				System.out.println("Game Over! Player 1 Cannot Move! Player 2 Wins!");
			} else {
				System.out.println("Game Over! Player 2 Cannot Move! Player 1 Wins!");
			}
			return true;
		}
        return false;
    }
    
    /**
     * Removes a piece from its current slot and moves it to another
     * @param piece - Piece to be moved
     * @param move - Slot to be moved to
     * @return true if there are additional jumps after moving piece
     */
    public boolean movePiece(Piece piece, Slot move) {
        boolean jump = false;
        boolean king = false;
		setLastMove(new Move(piece, move));
		getLastMove().setOrigin(piece.getSlot());
        //If Player 1's turn...
        if(piece.getOwner() == getP1()) {
            //Down-Right Jump
            try {
                if(getSlots()[piece.getSlot().getRow()+2][piece.getSlot().getColumn()+2] == move) {
                    getLastMove().setJumpedPiece(getSlots()[move.getRow()-1][move.getColumn()-1].getOccupyingPiece(this));
					piece.jump(getSlots()[move.getRow()-1][move.getColumn()-1].getOccupyingPiece(this));
                    jump = true;
                }
            } catch(ArrayIndexOutOfBoundsException e) {}
            //Down-Left Jump
            try {
                if(getSlots()[piece.getSlot().getRow()+2][piece.getSlot().getColumn()-2] == move) {
					getLastMove().setJumpedPiece(getSlots()[move.getRow()-1][move.getColumn()+1].getOccupyingPiece(this));
                    piece.jump(getSlots()[move.getRow()-1][move.getColumn()+1].getOccupyingPiece(this));
                    jump = true;
                }
            } catch(ArrayIndexOutOfBoundsException e) {}
            //Additional jump configs for kings
            if(piece.isKing()) {
                //Up-Left Jump
                try {
                    if(getSlots()[piece.getSlot().getRow()-2][piece.getSlot().getColumn()-2] == move) {
						getLastMove().setJumpedPiece(getSlots()[move.getRow()+1][move.getColumn()+1].getOccupyingPiece(this));
                        piece.jump(getSlots()[move.getRow()+1][move.getColumn()+1].getOccupyingPiece(this));
                        jump = true;
                    }
                } catch(ArrayIndexOutOfBoundsException e) {}
                //Up-Right Jump
                try {
                    if(getSlots()[piece.getSlot().getRow()-2][piece.getSlot().getColumn()+2] == move) {
						getLastMove().setJumpedPiece(getSlots()[move.getRow()+1][move.getColumn()-1].getOccupyingPiece(this));
                        piece.jump(getSlots()[move.getRow()+1][move.getColumn()-1].getOccupyingPiece(this));
                        jump = true;
                    }
                } catch(ArrayIndexOutOfBoundsException e) {}                
            }
            //Checks if move lands in a king spot (for the first time)
            if(move.getRow() == getSlots().length-1 && !piece.isKing()) {
				getLastMove().setKingMove(true);
                king = true;
                piece.setKing(true);
            }
        //If Player 2's turn...
        } else {
            //Up-Left Jump
            try {
                if(getSlots()[piece.getSlot().getRow()-2][piece.getSlot().getColumn()-2] == move) {
					getLastMove().setJumpedPiece(getSlots()[move.getRow()+1][move.getColumn()+1].getOccupyingPiece(this));
                    piece.jump(getSlots()[move.getRow()+1][move.getColumn()+1].getOccupyingPiece(this));
                    jump = true;
                }
            } catch(ArrayIndexOutOfBoundsException e) {}
            //Up-Right Jump
            try {
                if(getSlots()[piece.getSlot().getRow()-2][piece.getSlot().getColumn()+2] == move) {
					getLastMove().setJumpedPiece(getSlots()[move.getRow()+1][move.getColumn()-1].getOccupyingPiece(this));
                    piece.jump(getSlots()[move.getRow()+1][move.getColumn()-1].getOccupyingPiece(this));
                    jump = true;
                }
            } catch(ArrayIndexOutOfBoundsException e) {}
            //Additional jump configs for kings
            if(piece.isKing()) {
                //Down-Right Jump
                try {
                    if(getSlots()[piece.getSlot().getRow()+2][piece.getSlot().getColumn()+2] == move) {
						getLastMove().setJumpedPiece(getSlots()[move.getRow()-1][move.getColumn()-1].getOccupyingPiece(this));
                        piece.jump(getSlots()[move.getRow()-1][move.getColumn()-1].getOccupyingPiece(this));
                        jump = true;
                    }
                } catch(ArrayIndexOutOfBoundsException e) {}
                //Down-Left Jump
                try {
                    if(getSlots()[piece.getSlot().getRow()+2][piece.getSlot().getColumn()-2] == move) {
						getLastMove().setJumpedPiece(getSlots()[move.getRow()-1][move.getColumn()+1].getOccupyingPiece(this));
                        piece.jump(getSlots()[move.getRow()-1][move.getColumn()+1].getOccupyingPiece(this));
                        jump = true;
                    }
                } catch(ArrayIndexOutOfBoundsException e) {}
            }
            //Checks if move lands in a king spot (for the first time)
            if(move.getRow() == 0 && !piece.isKing()) {
				getLastMove().setKingMove(true);
                king = true;
                piece.setKing(true);
            }
        }
        piece.getSlot().setOccupied(false);
		getLastMove().setJump(jump);
        move.addPiece(piece);
        return jump && !king && !piece.genJumps(this).isEmpty();
    }
    
}
