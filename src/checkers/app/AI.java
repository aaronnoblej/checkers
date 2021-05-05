/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers.app;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.swing.SwingUtilities;

/**
 *
 * @author aaron
 */
public class AI extends Player {
    
    //==========================================================================
    // PROPERTIES
    //==========================================================================
    
    private int difficulty = 0;
    public int getDifficulty() {return difficulty;}
    public void setDifficulty(int val) {difficulty = val;}
	
	private Game game;
	public Game getGame() {return game;}
	public void setGame(Game val) {game = val;}
	
	private int bestMoveIndex[];
	private ArrayList<Move> moves = new ArrayList<Move>();
	
	private boolean isMaximizingPlayer;
	public void setMaximizingPlayer(boolean val) {isMaximizingPlayer = val;};
    
    //==========================================================================
    // CONSTRUCTORS
    //==========================================================================
    
    public AI(String name, int difficulty, boolean isMaximizing) {
        super(name);
        setDifficulty(difficulty);
		setMaximizingPlayer(isMaximizing);
    }
    
    public AI(int difficulty, boolean isMaximizing) {
        super("AI");
        setDifficulty(difficulty);
		setMaximizingPlayer(isMaximizing);
    }
    
    //==========================================================================
    // METHODS
    //==========================================================================
    
    /**
     * Like its parent, enables player to interact with the board
     * For the AI, also triggers minimax function and makes a move
     * @param val 
     */
    @Override
    public void setEnabled(boolean val) {
        enabled = val;
        if(val) {
			//Create thread and run minimax
			Thread boardAnalysis = new Thread() {
				@Override
				public void run() {
					bestMoveIndex = new int[numberOfMoves()];
					int best = minimax(getGame().getBoard(), getDifficulty(), isMaximizingPlayer);
					makeMove(best);
					moves.clear();
					game.getGUI().update(game.getBoard());
					game.rotateTurn();
				}
			};
			boardAnalysis.start();
        }
    }
	
	public void makeMove(int best) {
		//If there is a chance for a double jump, do it
		Piece bestPiece = moves.get(best).getPiece();
		Slot bestSlot = moves.get(best).getSlot();
		Runnable highlighter = new Runnable() {
			@Override
			public void run() {
				if(bestPiece.getPieceDesign().getParent().getBackground() == GUI.boardColor2) {
					bestPiece.getPieceDesign().getParent().setBackground(GUI.highlightColor);
				} else {
					bestPiece.getPieceDesign().getParent().setBackground(GUI.highlightColor);
				}
				for(Slot move : bestPiece.getAvailableMoves()) {
					move.setHighlighted(true);
				}
			}
		}; //Runnable to highlight/unhighlight
		SwingUtilities.invokeLater(highlighter);
		//Pause for half a second
		try {Thread.sleep(500);} catch(InterruptedException e) {}
		if(game.getBoard().movePiece(bestPiece, bestSlot)) {
			game.getGUI().update(game.getBoard());
			bestPiece.setAvailableMoves(bestPiece.genJumps(game.getBoard()));
			makeMove(bestPiece); //For now, only takes the first (possibly only) of the multiple jump route(s)*/
			
			//I MUST FIND A WAY TO UPDATE THE GUI AFTER THE FIRST JUMP
		}
	}
	
	/**
	 * Method used for doing an additional jump
	 * @param nextJumpPiece piece to move forward
	 */
	public void makeMove(Piece nextJumpPiece) {
		Slot slot = nextJumpPiece.getAvailableMoves().get(0);
		Runnable highlighter = new Runnable() {
			@Override
			public void run() {
				if(nextJumpPiece.getPieceDesign().getParent().getBackground() == GUI.boardColor2) {
					nextJumpPiece.getPieceDesign().getParent().setBackground(GUI.highlightColor);
				} else {
					nextJumpPiece.getPieceDesign().getParent().setBackground(GUI.highlightColor);
				}
				for(Slot move : nextJumpPiece.getAvailableMoves()) {
					move.setHighlighted(true);
				}
			}
		}; //Runnable to highlight/unhighlight
		SwingUtilities.invokeLater(highlighter);
		//Pause for half a second
		try {Thread.sleep(500);} catch(InterruptedException e) {}
		if(game.getBoard().movePiece(nextJumpPiece, slot)) {
			game.getGUI().update(game.getBoard());
			nextJumpPiece.setAvailableMoves(nextJumpPiece.genJumps(game.getBoard()));
			makeMove(nextJumpPiece); //For now, only takes the first (possibly only) of the multiple jump route(s)*/
		}
	}
    
    public int minimax(Board board, int depth, boolean isMaximizing) {		
        if(depth == 0 || board.checkWin()) {
            //return the current board state node in the tree
            return board.getScore();
        }
        if(isMaximizing) {			
            int value = Integer.MIN_VALUE;
			int count = 0;
            for(Piece piece : board.getP1().getMoveablePieces()) {
                for(Slot slot : piece.getAvailableMoves()) {
                    value = Math.max(value, minimax(genBoardState(board, piece, slot), depth-1, false));
					if(depth == getDifficulty()) {
						this.moves.add(new Move(piece, slot));
						this.bestMoveIndex[count] = value;
					}
					count++;
                }
            }
			if(depth == getDifficulty()) return getOptimalIndex();
            return value;
        } else {
            int value = Integer.MAX_VALUE;
			int count = 0;
            for(Piece piece : board.getP2().getMoveablePieces()) {
                for(Slot slot : piece.getAvailableMoves()) {
                    value = Math.min(value, minimax(genBoardState(board, piece, slot), depth-1, true));
					if(depth == getDifficulty()) {
						this.moves.add(new Move(piece, slot));
						this.bestMoveIndex[count] = value;
					}
					count++;
                }
            }
			if(depth == getDifficulty()) return getOptimalIndex();
            return value;
        }
    }
    
    private Board genBoardState(Board board, Piece piece, Slot slot) {
        Board node = new Board(board);
		piece = node.getSlots()[piece.getSlot().getRow()][piece.getSlot().getColumn()].getOccupyingPiece(node);
		slot = node.getSlots()[slot.getRow()][slot.getColumn()];
		
        if(node.movePiece(piece, slot)) {
			piece.setAvailableMoves(piece.genJumps(node));
			node = genBoardState(node, piece, piece.getAvailableMoves().get(0));
		}
		
		//Generate available moves for the node
		for(Piece p1 : node.getP1().getPieces()) {
			p1.genAvailableMoves(node);
		}
		for(Piece p2 : node.getP2().getPieces()) {
			p2.genAvailableMoves(node);
		}
		
        return node;
    }
	
	private int getOptimalIndex() {
		if(this.isMaximizingPlayer) {
			int max = 0;
			for(int i = 0; i < bestMoveIndex.length; i++) {
				if(bestMoveIndex[i] > bestMoveIndex[max]) max = i;
			}
			return max;
		} else {
			int min = 0;
			for(int i = 0; i < bestMoveIndex.length; i++) {
				if(bestMoveIndex[i] < bestMoveIndex[min]) min = i;
			}
			return min;
		}
	}
	
}
