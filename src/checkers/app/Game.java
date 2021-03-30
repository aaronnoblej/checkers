/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers.app;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 *
 * @author aaron
 */
public class Game {
    
    //==========================================================================
    // PROPERTIES
    //==========================================================================
    
    private Board board;
    public Board getBoard() {return board;}
    public void setBoard(Board val) {board = val;}
    
    private Piece selectedPiece;
    public Piece getSelectedPiece() {return selectedPiece;}
    public void setSelectedPiece(Piece val) {selectedPiece = val;}
    
    private Player currentTurn;
    public Player getCurrentTurn() {return currentTurn;}
    public void setCurrentTurn(Player val) {
        currentTurn = val;
        val.setEnabled(true);
    }
    
    private GUI gui;
    public GUI getGUI() {return gui;}
    public void setGUI(GUI val) {gui = val;}
    
    private boolean jumpInProgress = false;
        
    //==========================================================================
    // CONSTRUCTORS
    //==========================================================================
    
    /**
     * Default constructor for a game between two human players
     */
    public Game() {
        setBoard(new Board());
        enableHighlights(getBoard().getP1());
        enableHighlights(getBoard().getP2());
        setGUI(new GUI(this));
    }
    
    /**
     * Constructor used for AI matches
     * @param difficulty Depth for the AI algorithm - higher number means harder AI and longer "thinking" time
     * @param opponentIsHuman If true, only makes one player AI, else makes both players AIs
     */
    public Game(int difficulty, boolean opponentIsHuman) {
        setBoard(new Board());
        AI p1 = new AI(difficulty);
        p1.setGame(this);
        ArrayList<Piece> piecesP1 = new ArrayList();
        for(Piece piece : getBoard().getP1().getPieces()) {
            piecesP1.add(piece);
        }
        p1.setPieces(piecesP1);
        getBoard().setP1(p1);
        if(!opponentIsHuman) {
            AI p2 = new AI(difficulty);
            ArrayList<Piece> piecesP2 = new ArrayList();
            for(Piece piece : getBoard().getP2().getPieces()) {
                piecesP2.add(piece);
            }
            p2.setPieces(piecesP2);
            getBoard().setP2(p2);
        } else {
            enableHighlights(getBoard().getP2());
        }
        setGUI(new GUI(this));
    }
    
    //==========================================================================
    // METHODS
    //==========================================================================
    
    /**
     * Sets the first turn to enable the game to be played
     */
    public void start() {
        for(Piece piece : getBoard().getP1().getPieces()) {
            piece.genAvailableMoves(getBoard());
        }
        for(Piece piece : getBoard().getP2().getPieces()) {
            piece.genAvailableMoves(getBoard());
        }
		
        setCurrentTurn(getBoard().getP1());
		getCurrentTurn().getPanel().highlight();
        System.out.println("It is " + getBoard().getP1().getName() + "'s turn.");
        System.out.println(getCurrentTurn().getName() + " has " + getBoard().getP1().numberOfMoves() + " available moves");
        System.out.println("Score: " + getBoard().getScore());
    }
    
    /**
     * Switches turn to opposite player
     */
    public void rotateTurn() {
        jumpInProgress = false;
        getCurrentTurn().setEnabled(false);
        setCurrentTurn(getCurrentTurn().getOpponent());
        for(Piece piece : getCurrentTurn().getPieces()) {
            piece.genAvailableMoves(getBoard());
        }
        System.out.println("It is " + getCurrentTurn().getName() + "'s turn");
        System.out.print(getCurrentTurn().getName() + " has " + getCurrentTurn().numberOfMoves() + " available moves");
        System.out.println(" | " + getCurrentTurn().getPieces().size() + " pieces left");
        System.out.println("Score: " + getBoard().getScore());
		
		getCurrentTurn().getPanel().highlight();
		getCurrentTurn().getOpponent().getPanel().highlight();
		
        if(getBoard().checkWin()) System.exit(0);
    }
    
    /**
     * Enables selected slot AND available move slots to turn yellow upon clicking
     * Also reverts them to black when deselected
     * Handles changing of default and hand cursors on slots
     * @param player to enable highlighting for
     */
    public void enableHighlights(Player player) {
        for(Piece piece : player.getPieces()) {
            piece.getPieceDesign().addMouseListener(new MouseListener() {
                @Override
                public void mouseEntered(MouseEvent me) {
                    if(piece.getOwner().getEnabled() && !jumpInProgress) {
                        piece.getPieceDesign().setCursor(new Cursor(Cursor.HAND_CURSOR));
                        //Turns slot to the hover color if a piece occupies it
                        if(getSelectedPiece() != piece) {
                            getGUI().getSlots()[piece.getSlot().getRow()][piece.getSlot().getColumn()].setBackground(GUI.hoverColor);
                            //piece.getSlot().setBackground(GUI.hoverColor);
                        }
                    //Enables hand cursor for double jumping
                    } else if(jumpInProgress && piece == getSelectedPiece()) {
                        piece.getPieceDesign().setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }
                    //Sets to default cursor for all other slots
                    else {
                        piece.getPieceDesign().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }
                }

                @Override
                public void mouseClicked(MouseEvent me) {
                    if(piece.getOwner().getEnabled() && !jumpInProgress) {
                        //Used when a piece is selected and another piece is clicked on
                        if(getSelectedPiece() != null) {
                            getSelectedPiece().getPieceDesign().getParent().setBackground(GUI.boardColor2);
                            if(!getSelectedPiece().getAvailableMoves().isEmpty()) {
                                for(Slot slot : getSelectedPiece().getAvailableMoves()) {
                                    slot.setHighlighted(false);
                                }
                            }
                        }
                        //Used when a piece is selected and clicked on again
                        if(piece == getSelectedPiece()) {
                            piece.getPieceDesign().getParent().setBackground(GUI.boardColor2);
                            setSelectedPiece(null);
                            if(!piece.getAvailableMoves().isEmpty()) {
                                for(Slot slot : piece.getAvailableMoves()) {
                                    slot.setHighlighted(false);
                                }
                            }
                        //Used when no piece is selected and a piece is clicked on    
                        } else {
                            piece.getPieceDesign().getParent().setBackground(GUI.highlightColor);
                            setSelectedPiece(piece);
                            for(Slot move : piece.getAvailableMoves()) {
                                move.setHighlighted(true);
                                getGUI().getSlots()[move.getRow()][move.getColumn()].addMouseListener(new MouseListener() {
                                    @Override
                                    public void mouseClicked(MouseEvent me) {
                                        //Used when an available move is selected
                                        if(move.getHighlighted()) {
                                            //Checks if there are additional jumps
                                            if(getBoard().movePiece(getSelectedPiece(), move)) {
                                                highlightAdditionalJumps(getSelectedPiece());
                                            } else {
                                                setSelectedPiece(null);
                                                rotateTurn();
                                            }
                                            getGUI().update(getBoard());
                                        }
                                    }

                                    @Override
                                    public void mousePressed(MouseEvent me) {}

                                    @Override
                                    public void mouseReleased(MouseEvent me) {}

                                    @Override
                                    public void mouseEntered(MouseEvent me) {}

                                    @Override
                                    public void mouseExited(MouseEvent me) {}

                                });
                            }
                        }
                    //Used when a piece is in jump mode and player clicks piece to cancel additional jumping
                    } else if(jumpInProgress && piece == getSelectedPiece()) {
                        piece.getSlot().setHighlighted(false);
                        //Unhighlights all moves
                        for(Slot slot : piece.getAvailableMoves()) {
                            slot.setHighlighted(false);
                        }
                        setSelectedPiece(null);
                        getGUI().update(getBoard());
                        rotateTurn();
                    }

                }

                @Override
                public void mousePressed(MouseEvent me) {}

                @Override
                public void mouseReleased(MouseEvent me) {}

                @Override
                public void mouseExited(MouseEvent me) {
                    if(player.getEnabled() && getSelectedPiece() != piece) {
                        getGUI().getSlots()[piece.getSlot().getRow()][piece.getSlot().getColumn()].setBackground(GUI.boardColor2);
                        //piece.getSlot().setBackground(GUI.boardColor2);
                    }
                }
            });
        }
    }
    
    /**
     * Checks if player can do an additional jump after already jumping
     * @param piece - Piece to be analyzed
     */
    public void highlightAdditionalJumps(Piece piece) {
        jumpInProgress = true;
        piece.setAvailableMoves(piece.genJumps(getBoard()));
        piece.getPieceDesign().getParent().setBackground(GUI.boardColor2);
        for(Slot move : piece.getAvailableMoves()) {
            move.setHighlighted(true);
            getGUI().getSlots()[move.getRow()][move.getColumn()].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    if(move.getHighlighted()) {
                        if(getBoard().movePiece(getSelectedPiece(), move)) {
                            highlightAdditionalJumps(getSelectedPiece());
                        } else {
                            setSelectedPiece(null);
                            rotateTurn();
                        }
                        getGUI().update(getBoard());
                    }
                }

                @Override
                public void mousePressed(MouseEvent me) {}

                @Override
                public void mouseReleased(MouseEvent me) {}

                @Override
                public void mouseEntered(MouseEvent me) {}

                @Override
                public void mouseExited(MouseEvent me) {}

            });
        }
    }
    
}