/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers.app;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
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
	
	private int moveVals[];
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
    
    
    
    //==========================================================================
    // METHODS
    //==========================================================================
    
    /**
     * Like its parent, enables player to interact with the board
     * For the AI, also triggers minimax function and makes a move
     * @param val 
     */
    @Override
    public synchronized void setEnabled(boolean val) {
        enabled = val;
        if(val) {
			//Create thread and run minimax
			Thread boardAnalysis = new Thread() {
				@Override
				public void run() {
					moveVals = new int[numberOfMoves()];
					int best = minimax(getGame().getBoard(), getDifficulty(), isMaximizingPlayer);
					
					//Remove the following three lines
					System.out.print("Best Moves: ");
					for(Integer i : moveVals) System.out.print(i + " ");
					System.out.println("");
					
					makeMove(best);
					moves.clear();
					game.getGUI().update(game.getBoard());
					game.rotateTurn();
				}
			};
			boardAnalysis.start();
        }
    }
	
	
	/**
	 * 
	 * @param best is the index returned from getOptimalIndex()
	 */ 
	private void makeMove(int best) {
		Piece bestPiece = moves.get(best).getPiece();
		Slot bestSlot = moves.get(best).getSlot();
		//Runnable to highlight/unhighlight
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
					game.highlightSlot(move);
				}
			}
		};
		SwingUtilities.invokeLater(highlighter);
		//Pause for one second
		try {Thread.sleep(1000);} catch(InterruptedException e) {}
		
		if(game.getBoard().movePiece(bestPiece, bestSlot)) {
			game.getGUI().update(game.getBoard());
			bestPiece.setAvailableMoves(bestPiece.genJumps(game.getBoard()));
			
			makeAdditionalMove(bestPiece); //For now, only takes the first (possibly only) of the multiple jump route(s)*/
		}
	}
	
	/**
	 * Method used for doing an additional jump
	 * @param nextJumpPiece piece to move forward
	 */
	private void makeAdditionalMove(Piece nextJumpPiece) {
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
					game.highlightSlot(move);
				}
			}
		}; //Runnable to highlight/unhighlight
		SwingUtilities.invokeLater(highlighter);
		//Pause for half a second
		try {Thread.sleep(1000);} catch(InterruptedException e) {}
		if(game.getBoard().movePiece(nextJumpPiece, slot)) {
			game.getGUI().update(game.getBoard());
			nextJumpPiece.setAvailableMoves(nextJumpPiece.genJumps(game.getBoard()));
			makeAdditionalMove(nextJumpPiece); //For now, only takes the first (possibly only) of the multiple jump route(s)
		}
	}
    
	/**
	 * Minimax procedure for evaluating the state of the board and choosing best move
	 * @param board state of the board to be evaluated, represents one node of the generated tree
	 * @param depth how deep the algorithm should look in the tree
	 * @param isMaximizing true for player one, false for player two
	 * @return the score of the board at the bottom level; if returning back to caller, return optimal index
	 */
    public int minimax(Board board, int depth, boolean isMaximizing) {
	
		//First checks if the board is in a winning state, returns +/- 500 if it is
		switch(board.checkWin(isMaximizing ? board.getP1() : board.getP2())) {
			case 0: break;
			case 1: return 500;
			case 2: return -500;
			case 3: return 500;
			case 4: return -500;
			case 5: return 0;
		}
		
		if(depth == 0) {
            return board.getScore();
        }
		
        if(isMaximizing) {			
            int value = Integer.MIN_VALUE;
			int count = 0;
			lvl: for(Piece piece : board.getP1().getMoveablePieces()) {
                for(Slot slot : piece.getAvailableMoves()) {
					int output = minimax(genBoardState(board, piece, slot), depth-1, false);
					if(depth == getDifficulty()) {
						this.moves.add(new Move(piece, slot));
						this.moveVals[count] = output;
					}
					value = Math.max(value, output);
					count++;
                }
            }
			if(depth == getDifficulty()) return getOptimalIndex(value);
            return value;
        } else {
            int value = Integer.MAX_VALUE;
			int count = 0;
			lvl: for(Piece piece : board.getP2().getMoveablePieces()) {
                for(Slot slot : piece.getAvailableMoves()) {
					int output = minimax(genBoardState(board, piece, slot), depth-1, true);
					if(depth == getDifficulty()) {
						this.moves.add(new Move(piece, slot));
						this.moveVals[count] = output;
					}
					value = Math.min(value, output);
					count++;
                }
            }
			if(depth == getDifficulty()) return getOptimalIndex(value);
            return value;
        }
    }
	
	/*public int minimax(Board board, int depth, boolean isMaximizing) {	
		
		//First checks to see if the board is in a winning state
		if(isMaximizing) {
			if(board.checkWin(board.getP1())) {
				if(board.getWinningPlayer() == board.getP1()) {
					return board.getScore() + 10;
				} else {
					return board.getScore() - 10;
				}
			}
		} else {
			if(board.checkWin(board.getP2())) {
				if(board.getWinningPlayer() == board.getP2()) {
					return board.getScore() - 10;
				} else {
					return board.getScore() + 10;
				}
			}
		}
		
		if(depth == 0) {
            return board.getScore();
        }
		
        if(isMaximizing) {			
            int value = Integer.MIN_VALUE;
			int count = 0;
            for(Piece piece : board.getP1().getMoveablePieces()) {
                for(Slot slot : piece.getAvailableMoves()) {
					int output = minimax(genBoardState(board, piece, slot), depth-1, false);
					if(depth == getDifficulty()) {
						this.moves.add(new Move(piece, slot));
						this.moveVals[count] = output;
					}
					value = Math.max(value, output);
					count++;
                }
            }
			if(depth == getDifficulty()) return getOptimalIndex(value);
            return value;
        } else {
            int value = Integer.MAX_VALUE;
			int count = 0;
            for(Piece piece : board.getP2().getMoveablePieces()) {
                for(Slot slot : piece.getAvailableMoves()) {
					int output = minimax(genBoardState(board, piece, slot), depth-1, true);
					if(depth == getDifficulty()) {
						this.moves.add(new Move(piece, slot));
						this.moveVals[count] = output;
					}
					value = Math.min(value, output);
					count++;
                }
            }
			if(depth == getDifficulty()) return getOptimalIndex(value);
            return value;
        }
    }*/
    
	/**
	 * Generates a node for the minimax procedure by making a move on a given board
	 * @param board the board state used to proceed with the next board state
	 * @param piece the piece to move
	 * @param slot the slot to move the piece to
	 * @return the generated board state
	 */
    private Board genBoardState(Board board, Piece piece, Slot slot) {
        Board node = new Board(board);
		piece = node.getSlots()[piece.getSlot().getRow()][piece.getSlot().getColumn()].getOccupyingPiece(node);
		slot = node.getSlots()[slot.getRow()][slot.getColumn()];
				
		while(node.movePiece(piece, slot)) {
			piece.setAvailableMoves(piece.genJumps(node));
			slot = piece.getAvailableMoves().get(0); //For now only does first possible double jump
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
	
	/**
	 * Finds the best index of moveVals that AI should take
	 * @param val the minimum/maximum val from the minimax function
	 * @return the best index
	 */
	private int getOptimalIndex(int val) {
		ArrayList<Integer> bests = new ArrayList(); //List of identical max/mins
		for(int i = 0; i < moveVals.length; i++) {
			if(moveVals[i] == val) {
				bests.add(i);
			}
		}
		//If there are multiple moves of same worth, choose a random one
		if(bests.size() > 1) {
			System.out.println("Multiple best moves, choosing random from " + bests.size() + " indexes!");
			return bests.get(ThreadLocalRandom.current().nextInt(0, bests.size()));
		} else {
			return bests.get(0);
		}
	}
	
}
