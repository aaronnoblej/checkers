/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import javax.swing.*;

/**
 *
 * @author aaron
 */
public class MainScreen extends JPanel {
    
    //==========================================================================
    // PROPERTIES
    //==========================================================================

	private Game game;
    private Color background = Color.RED;
    public JLabel aiMatchButton = new JLabel();
    public JLabel twoPlayerGameButton = new JLabel();
    public JLabel watchAiMatchButton = new JLabel();
    private JLabel title = new JLabel();
    
    //==========================================================================
    // CONSTRUCTORS
    //==========================================================================
    
    public MainScreen() {
		this.setLayout(new BorderLayout());
        this.setBackground(background);
		
		//Sides
		JPanel redPieces = new JPanel();
		redPieces.setLayout(new BoxLayout(redPieces, BoxLayout.Y_AXIS));
		JPanel blackPieces = new JPanel();
		blackPieces.setLayout(new BoxLayout(blackPieces, BoxLayout.Y_AXIS));
		redPieces.setOpaque(false);
		blackPieces.setOpaque(false);
		
		int c = 0;
		for(int i = 0; i < 6; i++) {
			JLabel img = new JLabel(new ImageIcon(getClass().getResource("P2 Piece.png")));
			redPieces.add(img);
			img = new JLabel(new ImageIcon(getClass().getResource("P2 Piece.png")));
			blackPieces.add(img);
		}
		this.add(redPieces, BorderLayout.WEST);
		this.add(blackPieces, BorderLayout.EAST);
		
		//Set the title
		title.setIcon(new ImageIcon(getClass().getResource("title.png")));
		title.setHorizontalAlignment(JLabel.CENTER);
		this.add(title, BorderLayout.NORTH);
		
		//Add buttons
		this.add(initCenter(), BorderLayout.CENTER);
		
		
        this.setVisible(true);
    }
	
    //==========================================================================
    // METHODS
    //==========================================================================
	
	private JPanel initCenter() {
		JPanel center = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
		center.setOpaque(false);
		
		twoPlayerGameButton.setIcon(new ImageIcon(getClass().getResource("button_2p.png")));
		twoPlayerGameButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		aiMatchButton.setIcon(new ImageIcon(getClass().getResource("button_ai.png")));
		aiMatchButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		watchAiMatchButton.setIcon(new ImageIcon(getClass().getResource("button_watch.png")));
		watchAiMatchButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		center.add(Box.createRigidArea(new Dimension(100, 50)));
		center.add(twoPlayerGameButton);
		center.add(Box.createRigidArea(new Dimension(100, 25)));
		center.add(aiMatchButton);
		center.add(Box.createRigidArea(new Dimension(100, 25)));
		center.add(watchAiMatchButton);
		
		return center;
	}
	
	private void addListeners() {
		twoPlayerGameButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		aiMatchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		watchAiMatchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				
		twoPlayerGameButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				game = new Game();
			}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
			
		});
	}
    
}
