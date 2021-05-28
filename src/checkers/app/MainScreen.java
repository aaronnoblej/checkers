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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author aaron
 */
public class MainScreen extends JPanel {
    
    //==========================================================================
    // PROPERTIES
    //==========================================================================

    private Color background = Color.RED;
    public JLabel aiMatchButton = new JLabel();
    public JLabel twoPlayerGameButton = new JLabel();
    public JLabel watchAiMatchButton = new JLabel();
	public JLabel quitButton = new JLabel();
    private JLabel title = new JLabel();
	private MainScreen prev;
    
    //==========================================================================
    // CONSTRUCTORS
    //==========================================================================
    
    public MainScreen() {
		this.setLayout(new BorderLayout());
        this.setBackground(background);
		this.setPreferredSize(new Dimension(CheckersApp.width, CheckersApp.height));
		
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
		this.addListeners();
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
		
		quitButton.setIcon(new ImageIcon(getClass().getResource("button_quit.png")));
		quitButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		center.add(Box.createRigidArea(new Dimension(100, 50)));
		center.add(twoPlayerGameButton);
		center.add(Box.createRigidArea(new Dimension(100, 25)));
		center.add(aiMatchButton);
		center.add(Box.createRigidArea(new Dimension(100, 25)));
		center.add(watchAiMatchButton);
		center.add(Box.createRigidArea(new Dimension(100, 25)));
		center.add(quitButton);
		
		return center;
	}
	
	/**
	 * Adds MouseListeners to buttons
	 */
	private void addListeners() {
		
		//Cursors
		twoPlayerGameButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		aiMatchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		watchAiMatchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		quitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		//Mouse listeners
		twoPlayerGameButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CheckersApp.game = new Game();
				CheckersApp.checkers.getContentPane().removeAll();
				CheckersApp.checkers.add(CheckersApp.game.getGUI());

				//Pack the frame and add the pieces
				CheckersApp.checkers.pack();
				CheckersApp.game.getGUI().createPieces();
				
				CheckersApp.game.start();
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
		aiMatchButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CheckersApp.checkers.setContentPane(new DifficultyScreen());
				CheckersApp.checkers.repaint();
				CheckersApp.checkers.revalidate();
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
		watchAiMatchButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CheckersApp.game = new Game(4, false);
				CheckersApp.checkers.getContentPane().removeAll();
				CheckersApp.checkers.add(CheckersApp.game.getGUI(), BorderLayout.CENTER);

				//Pack the frame and add the pieces
				CheckersApp.checkers.pack();
				CheckersApp.game.getGUI().createPieces();
				
				CheckersApp.game.start();
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
		quitButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
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
	
	private class DifficultyScreen extends JPanel {
		
		//==========================================================================
		// PROPERTIES
		//==========================================================================

		private JPanel buttonsContainer;
		private JLabel text;
		private JLabel easy;
		private JLabel medium;
		private JLabel hard;
		private JLabel back;
		
		//==========================================================================
		// CONSTRUCTORS
		//==========================================================================
		
		private DifficultyScreen() {
			
			//Initialize JPanel
			//this.setLayout(new BorderLayout());
			this.setBackground(background);
			this.setPreferredSize(new Dimension(CheckersApp.width, CheckersApp.height));
			
			//Initialize properties
			buttonsContainer = new JPanel();
			text = new JLabel("Select a Difficulty");
			easy = new JLabel();
			medium = new JLabel();
			hard = new JLabel();
			back = new JLabel();
			
			buttonsContainer.setLayout(new BoxLayout(buttonsContainer, BoxLayout.Y_AXIS));
			buttonsContainer.setOpaque(false);
			text.setAlignmentX(JLabel.CENTER_ALIGNMENT);
			text.setFont(new Font("Arial", Font.BOLD, 36));
			text.setForeground(Color.WHITE);

			easy.setIcon(new ImageIcon(getClass().getResource("button_easy.png")));
			easy.setAlignmentX(JLabel.CENTER_ALIGNMENT);
			medium.setIcon(new ImageIcon(getClass().getResource("button_med.png")));
			medium.setAlignmentX(JLabel.CENTER_ALIGNMENT);
			hard.setIcon(new ImageIcon(getClass().getResource("button_hard.png")));
			hard.setAlignmentX(JLabel.CENTER_ALIGNMENT);
			back.setIcon(new ImageIcon(getClass().getResource("button_back.png")));
			back.setAlignmentX(JLabel.CENTER_ALIGNMENT);

			buttonsContainer.add(text);
			buttonsContainer.add(Box.createRigidArea(new Dimension(100, 25)));
			buttonsContainer.add(easy);
			buttonsContainer.add(Box.createRigidArea(new Dimension(100, 25)));
			buttonsContainer.add(medium);
			buttonsContainer.add(Box.createRigidArea(new Dimension(100, 25)));
			buttonsContainer.add(hard);
			buttonsContainer.add(Box.createRigidArea(new Dimension(100, 100)));
			buttonsContainer.add(back);
			
			this.add(buttonsContainer);

			addListeners();
		}
		
		//==========================================================================
		// METHODS
		//==========================================================================
		
		public void addListeners() {
			//Cursors
			easy.setCursor(new Cursor(Cursor.HAND_CURSOR));
			medium.setCursor(new Cursor(Cursor.HAND_CURSOR));
			hard.setCursor(new Cursor(Cursor.HAND_CURSOR));
			back.setCursor(new Cursor(Cursor.HAND_CURSOR));
			//Mouse Listeners
			easy.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					CheckersApp.game = new Game(1, true);
					CheckersApp.checkers.setContentPane(CheckersApp.game.getGUI());

					//Pack the frame and add the pieces
					CheckersApp.checkers.pack();
					CheckersApp.game.getGUI().createPieces();

					CheckersApp.game.start();
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
			medium.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					CheckersApp.game = new Game(3, true);
					CheckersApp.checkers.setContentPane(CheckersApp.game.getGUI());

					//Pack the frame and add the pieces
					CheckersApp.checkers.pack();
					CheckersApp.game.getGUI().createPieces();

					CheckersApp.game.start();
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
			hard.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					CheckersApp.game = new Game(5,  true);
					CheckersApp.checkers.setContentPane(CheckersApp.game.getGUI());

					//Pack the frame and add the pieces
					CheckersApp.checkers.pack();
					CheckersApp.game.getGUI().createPieces();

					CheckersApp.game.start();
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
			back.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					CheckersApp.checkers.setContentPane(CheckersApp.screen);
					CheckersApp.checkers.repaint();
					CheckersApp.checkers.revalidate();
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
}
