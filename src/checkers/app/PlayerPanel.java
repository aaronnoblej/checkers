/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author aaron
 */
public class PlayerPanel extends JPanel {
    
    //==========================================================================
    // PROPERTIES
    //==========================================================================
    
	public static Color containerColor = Color.LIGHT_GRAY;
	public static Color backgroundColor = Color.BLUE;
	
    private Player player;
    public Player getPlayer() {return player;}
    public void setPlayer(Player val) {player = val;}
    
    private JLabel text = new JLabel("Player");
    private JLabel text2 = new JLabel("Captured:");
    private JPanel container = new JPanel();
	private JPanel container2 = new JPanel(); //Second layer of containers
	private JLabel turnIndicator = new JLabel();
    private int width = (CheckersApp.width-CheckersApp.height)/2;
    private int height = CheckersApp.height;
    
    //==========================================================================
    // CONSTRUCTORS
    //==========================================================================
    
    public PlayerPanel(Player player, boolean colored) {
        setPlayer(player);
        this.setLayout(new BorderLayout());
        this.setBackground(backgroundColor);
        this.setPreferredSize(new Dimension(width, height));
        
        text.setText(player.getName());
        text.setPreferredSize(new Dimension(width, height/8));
        text.setFont(new Font("Arial", Font.BOLD, 48));
        text.setHorizontalAlignment(JLabel.CENTER);
        text2.setPreferredSize(new Dimension(width, 20));
        text2.setFont(new Font("Arial", Font.PLAIN, 18));
        text2.setHorizontalAlignment(JLabel.CENTER);
                
        container.setLayout(new FlowLayout(FlowLayout.CENTER));
        container.setPreferredSize(new Dimension(width, height/2));
        container.setBackground(containerColor);
		container.setBorder(new LineBorder(backgroundColor, 8));
        container.add(text);
        container.add(text2);
		
		turnIndicator.setPreferredSize(new Dimension(width, height/20));
		turnIndicator.setOpaque(true);
		turnIndicator.setBackground(backgroundColor);
		
		container2.setLayout(new BorderLayout());
		container2.setPreferredSize(new Dimension(width, height/2 + height/20));
		container2.add(container, BorderLayout.CENTER);
		        
        if(colored) {
            text.setForeground(Color.RED);
            text2.setForeground(Color.RED);
			container2.add(turnIndicator, BorderLayout.SOUTH);
            this.add(container2, BorderLayout.NORTH);
        } else {
            text.setForeground(Color.BLACK);
            text2.setForeground(Color.BLACK);
			container2.add(turnIndicator, BorderLayout.NORTH);
            this.add(container2, BorderLayout.SOUTH);
        }
		turnIndicator.setHorizontalAlignment(JLabel.CENTER);
    }
    
    //==========================================================================
    // METHODS
    //==========================================================================
    
    public void highlight() {
        if(turnIndicator.getBackground() == Color.YELLOW) {
			turnIndicator.setText("");
			turnIndicator.setBackground(backgroundColor);
        } else {
			turnIndicator.setText("It is your turn.");
			turnIndicator.setBackground(Color.YELLOW);
        }
    }
    
    public void addPiece() {
        JLabel piece = new JLabel();
        if(player.getName().equals("Black")) {
            piece.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("P1 Piece.png")).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
        } else {
            piece.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("P2 Piece.png")).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
        }
        piece.setPreferredSize(new Dimension(50,50));
        container.add(piece);
    }
    
}
