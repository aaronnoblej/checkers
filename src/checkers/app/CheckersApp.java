/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
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
    static Game game = new Game(3, true);
    
    public static void main(String[] args) {
        //Set Main Frame
        checkers.setLayout(new BorderLayout());
        checkers.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        checkers.setSize(width, height);
        checkers.setLocationRelativeTo(null);
        checkers.setResizable(false);
        checkers.add(game.getGUI(), BorderLayout.CENTER);
        checkers.getContentPane().setBackground(Color.BLUE);
        
        checkers.add(game.getBoard().getP1().getPanel(), BorderLayout.WEST);
        checkers.add(game.getBoard().getP2().getPanel(), BorderLayout.EAST);
		
		//Pack the frame and add the pieces
		checkers.pack();
		game.getGUI().createPieces();
		
        checkers.setVisible(true);
		
        game.start();
    }
}
