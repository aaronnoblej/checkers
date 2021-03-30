/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers.app;

import java.awt.Color;
import java.awt.Container;
import javax.swing.*;

/**
 *
 * @author aaron
 */
public class MainScreen extends Container {
    
    //==========================================================================
    // PROPERTIES
    //==========================================================================
    
    private Color background = Color.RED;
    private JLabel aiMatchButton = new JLabel();
    private JLabel twoPlayerGameButton = new JLabel();
    private JLabel watchAiMatchButton = new JLabel();
    private JLabel title;
    
    //==========================================================================
    // CONSTRUCTORS
    //==========================================================================
    
    public MainScreen() {
        this.setBackground(background);
        this.setVisible(true);
    }
    
    //==========================================================================
    // METHODS
    //==========================================================================
    
}
