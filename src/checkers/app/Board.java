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
public class Board /*extends Container*/ implements Cloneable {
    
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
     */
    public Board() {
        p1.setOpponent(p2);
        p2.setOpponent(p1);
        setSlots(new Slot[rows][columns]);
        //this.setLayout(new GridLayout(getRows(), getColumns()));
        createBoard();
        initPieces();
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
    
    /**
     * Create a new board with existing pieces of two players
     * @param piecesP1 Pieces of Player 1
     * @param piecesP2 Pieces of Player 2
     */
    /*public Board(ArrayList<Piece> piecesP1, ArrayList<Piece> piecesP2) {
        p1.setOpponent(p2);
        p2.setOpponent(p1);
        setSlots(new Slot[rows][columns]);
        //this.setLayout(new GridLayout(getRows(), getColumns()));
        createBoard();
        for(Piece piece : getP1().getPieces()) {
            piece.getSlot().add(piece.getPieceDesign());
        }
        for(Piece piece : getP2().getPieces()) {
            piece.getSlot().add(piece.getPieceDesign());
        }
    }*/
    
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
                    //piece.getPieceDesign().setIcon(new ImageIcon(new ImageIcon(getClass().getResource("P1 Piece.png")).getImage().getScaledInstance(piece.getPieceDesign().getWidth(), piece.getPieceDesign().getHeight(), Image.SCALE_DEFAULT)));
                    //getSlots()[i][j].add(piece.getPieceDesign());
                    getP1().getPieces().add(piece);
                } else if(!(i % 2 == j % 2) && i > getRows()-4) {
                    Piece piece = new Piece(p2);
                    getSlots()[i][j].addPiece(piece);
                    piece.getPieceDesign().setIcon(new ImageIcon(getClass().getResource("P2 Piece.png")));
                    //GUI.slots[i][j].add(piece.getPieceDesign());
                    getP2().getPieces().add(piece);
                }
            }
        }
    }
    
    /**
     * Gives each piece a value on the board and computes a total score value for the board
     * @return score of the board
     */
    public int getScore() {
        int score = 0;
        for(Piece piece : getP1().getPieces()) {
            if(piece.isKing()) score += 2;
            else score += 1;
        }
        for(Piece piece : getP2().getPieces()) {
            if(piece.isKing()) score -= 2;
            else score -= 1;
        }
        return score;
    }
    
    /**
     * Checks if a player has won
     * A player wins if the opposing player has no pieces remaining or has no more available moves
     * @return true if a player has won and the game is over
     */
    public boolean checkWin() {
        if(getP2().getPieces().isEmpty() || getP2().numberOfMoves() == 0) {
            System.out.println("Game Over! Player 1 Wins!");
            return true;
        } else if(getP1().getPieces().isEmpty() || getP1().numberOfMoves() == 0) {
            System.out.println("Game Over! Player 2 Wins!");
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
        //If Player 1's turn...
        if(piece.getOwner() == getP1()) {
            //Down-Right Jump
            try {
                if(getSlots()[piece.getSlot().getRow()+2][piece.getSlot().getColumn()+2] == move) {
                    piece.jump(getSlots()[move.getRow()-1][move.getColumn()-1].getOccupyingPiece(this));
                    jump = true;
                }
            } catch(ArrayIndexOutOfBoundsException e) {}
            //Down-Left Jump
            try {
                if(getSlots()[piece.getSlot().getRow()+2][piece.getSlot().getColumn()-2] == move) {
                    piece.jump(getSlots()[move.getRow()-1][move.getColumn()+1].getOccupyingPiece(this));
                    jump = true;
                }
            } catch(ArrayIndexOutOfBoundsException e) {}
            //Additional jump configs for kings
            if(piece.isKing()) {
                //Up-Left Jump
                try {
                    if(getSlots()[piece.getSlot().getRow()-2][piece.getSlot().getColumn()-2] == move) {
                        piece.jump(getSlots()[move.getRow()+1][move.getColumn()+1].getOccupyingPiece(this));
                        jump = true;
                    }
                } catch(ArrayIndexOutOfBoundsException e) {}
                //Up-Right Jump
                try {
                    if(getSlots()[piece.getSlot().getRow()-2][piece.getSlot().getColumn()+2] == move) {
                        piece.jump(getSlots()[move.getRow()+1][move.getColumn()-1].getOccupyingPiece(this));
                        jump = true;
                    }
                } catch(ArrayIndexOutOfBoundsException e) {}                
            }
            //Checks if move lands in a king spot (for the first time)
            if(move.getRow() == getSlots().length-1 && !piece.isKing()) {
                king = true;
                piece.setKing(true);
                piece.getPieceDesign().setIcon(new ImageIcon(getClass().getResource("P1 King.png")));
            }
        //If Player 2's turn...
        } else {
            //Up-Left Jump
            try {
                if(getSlots()[piece.getSlot().getRow()-2][piece.getSlot().getColumn()-2] == move) {
                    piece.jump(getSlots()[move.getRow()+1][move.getColumn()+1].getOccupyingPiece(this));
                    jump = true;
                }
            } catch(ArrayIndexOutOfBoundsException e) {}
            //Up-Right Jump
            try {
                if(getSlots()[piece.getSlot().getRow()-2][piece.getSlot().getColumn()+2] == move) {
                    piece.jump(getSlots()[move.getRow()+1][move.getColumn()-1].getOccupyingPiece(this));
                    jump = true;
                }
            } catch(ArrayIndexOutOfBoundsException e) {}
            //Additional jump configs for kings
            if(piece.isKing()) {
                //Down-Right Jump
                try {
                    if(getSlots()[piece.getSlot().getRow()+2][piece.getSlot().getColumn()+2] == move) {
                        piece.jump(getSlots()[move.getRow()-1][move.getColumn()-1].getOccupyingPiece(this));
                        jump = true;
                    }
                } catch(ArrayIndexOutOfBoundsException e) {}
                //Down-Left Jump
                try {
                    if(getSlots()[piece.getSlot().getRow()+2][piece.getSlot().getColumn()-2] == move) {
                        piece.jump(getSlots()[move.getRow()-1][move.getColumn()+1].getOccupyingPiece(this));
                        jump = true;
                    }
                } catch(ArrayIndexOutOfBoundsException e) {}
            }
            //Checks if move lands in a king spot (for the first time)
            if(move.getRow() == 0 && !piece.isKing()) {
                king = true;
                piece.setKing(true);
                piece.getPieceDesign().setIcon(new ImageIcon(getClass().getResource("P2 King.png")));
            }
        }
        piece.getSlot().setOccupied(false);
        piece.getSlot().setHighlighted(false);
        //Unhighlights all moves
        for(Slot slot : piece.getAvailableMoves()) {
            slot.setHighlighted(false);
        }
        setLastMove(new Move(piece, move));
        //piece.getSlot().remove(piece.getPieceDesign());
        //move.add(piece.getPieceDesign());
        move.addPiece(piece);
        return jump && !king && !piece.genJumps(this).isEmpty();
    }
    
    /**
     * Default clone method
     * @return cloned board
     */
    @Override
    public Board clone()  {
        try {
            Board board = (Board) super.clone();
            ArrayList<Piece> pieces = new ArrayList();
            for(Piece piece : board.getP1().getPieces()) {
                pieces.add(piece.clone());
            }
            for(Piece piece : board.getP2().getPieces()) {
                pieces.add(piece.clone());
            }
            return board;
        } catch(CloneNotSupportedException e) {
            System.out.println("Error in cloning minimax node.");
            e.printStackTrace();
        }
        return null;
    }
    
}
