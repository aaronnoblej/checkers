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
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Aaron
 */
public class CheckersApp {

    /**
     * @param args the command line arguments
     */
    static JFrame checkers = new JFrame("Java Checkers");
    static Container gameBoard = new Container();
    static int height = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.9);
    static int width = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.75);
    static Game game = new Game(5, true);
	static MainScreen screen = new MainScreen();
    
    public static void main(String[] args) {
        //Set Main Frame
        checkers.setLayout(new BorderLayout());
        checkers.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        checkers.setSize(width, height);
        checkers.setLocationRelativeTo(null);
        checkers.setResizable(false);
		
		//Main Screen
		
		checkers.add(screen);
		addListeners();
		/*
        checkers.add(game.getGUI(), BorderLayout.CENTER);
        checkers.getContentPane().setBackground(Color.BLUE);
        
        checkers.add(game.getBoard().getP1().getPanel(), BorderLayout.WEST);
        checkers.add(game.getBoard().getP2().getPanel(), BorderLayout.EAST);
		
		//Pack the frame and add the pieces
		checkers.pack();
		game.getGUI().createPieces();*/
		
        checkers.setVisible(true);
		
        //game.start();
    }
	
	private static void addListeners() {
		screen.twoPlayerGameButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		screen.aiMatchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		screen.watchAiMatchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				
		screen.twoPlayerGameButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				game = new Game();
				checkers.getContentPane().removeAll();
				
				checkers.add(game.getGUI(), BorderLayout.CENTER);
				checkers.getContentPane().setBackground(Color.BLUE);

				checkers.add(game.getBoard().getP1().getPanel(), BorderLayout.WEST);
				checkers.add(game.getBoard().getP2().getPanel(), BorderLayout.EAST);

				//Pack the frame and add the pieces
				checkers.pack();
				game.getGUI().createPieces();
				
				game.start();
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
		
		screen.aiMatchButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				game = new Game(5, true);
				checkers.getContentPane().removeAll();
				
				checkers.add(game.getGUI(), BorderLayout.CENTER);
				checkers.getContentPane().setBackground(Color.BLUE);

				checkers.add(game.getBoard().getP1().getPanel(), BorderLayout.WEST);
				checkers.add(game.getBoard().getP2().getPanel(), BorderLayout.EAST);

				//Pack the frame and add the pieces
				checkers.pack();
				game.getGUI().createPieces();
				
				game.start();
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
		
		screen.watchAiMatchButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				game = new Game(4, false);
				checkers.getContentPane().removeAll();
				
				checkers.add(game.getGUI(), BorderLayout.CENTER);
				checkers.getContentPane().setBackground(Color.BLUE);

				checkers.add(game.getBoard().getP1().getPanel(), BorderLayout.WEST);
				checkers.add(game.getBoard().getP2().getPanel(), BorderLayout.EAST);

				//Pack the frame and add the pieces
				checkers.pack();
				game.getGUI().createPieces();
				
				game.start();
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
