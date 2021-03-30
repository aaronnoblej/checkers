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
    
    //==========================================================================
    // CONSTRUCTORS
    //==========================================================================
    
    public AI(String name, int difficulty) {
        super(name);
        setDifficulty(difficulty);
    }
    
    public AI(int difficulty) {
        super("AI");
        setDifficulty(difficulty);
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
            //1) Run minimax method to return a value pair with the move and score value
            //2) Make the move according to the returned move from the minimax
            //3) Find a way to rotate turn to the opposing player
            minimax(CheckersApp.game.getBoard(), getDifficulty(), true);
        }
    }
    
    public int minimax(Board board, int depth, boolean isMaximizing) {
        //System.out.println("Depth = " + depth);
        if(depth == 0 || board.checkWin()) {
            //return the current board state node in the tree
            return board.getScore();
        }
        if(isMaximizing) {
            //Recalculates every available move again
            for(Piece piece : board.getP1().getPieces()) {
                piece.genAvailableMoves(board);
            }
            int value = -10000;
            for(Piece piece : board.getP1().getMoveablePieces()) {
                for(Slot slot : piece.getAvailableMoves()) {
                    value = Math.max(value, minimax(genBoardState(board, piece, slot), depth-1, false));
                }
            }
            System.out.println("Maximum val: " + value);
            return value;
        } else {
            //Recalculates every available move again
            for(Piece piece : board.getP2().getPieces()) {
                piece.genAvailableMoves(board);
            }
            int value = 10000;
            for(Piece piece : board.getP2().getMoveablePieces()) {
                for(Slot slot : piece.getAvailableMoves()) {
                    value = Math.min(value, minimax(genBoardState(board, piece, slot), depth-1, true));
                }
            }
            System.out.println("Minimum val: " + value);
            game.getGUI().update(board);
            return value;
        }
    }
    
    private Board genBoardState(Board board, Piece piece, Slot slot) {
        Board node = board.clone();
        node.movePiece(piece, slot);
        return node;
    }
}
