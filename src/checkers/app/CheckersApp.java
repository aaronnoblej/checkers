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
    static int height = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.9);
    static int width = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.75);
	static MainScreen screen = new MainScreen();
    static Game game;
    
    public static void main(String[] args) {
        //Set Main Frame
        checkers.setLayout(new BorderLayout());
        checkers.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        checkers.setSize(width, height);
        checkers.setLocationRelativeTo(null);
        checkers.setResizable(false);
		
		//Main Screen
		checkers.add(screen);
		
		checkers.pack();
        checkers.setVisible(true);
    }
	
	/**
	 * This method affects the static screen variable by adding MouseListeners
	 * This could be moved to the MainScreen class but I thought this would be easier
	 */
	
}
