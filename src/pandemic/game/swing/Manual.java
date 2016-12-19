/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.swing;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Pípa
 */

//public class Manual extends javax.swing.JDialog {
public class Manual extends javax.swing.JFrame {

    /**
     * Creates new form PandemicSwing
     * initComponents() is a method used in swing Design to initialize swing components( like setting the default values,..)
     * something like the connection between GUI editor and Java.
     */
    public Manual() {
        initComponents();
    }

    public static class ShowManual implements KeyEventDispatcher {

        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_RELEASED) {
                if (e.getKeyCode() == KeyEvent.VK_F1) {
                    Manual.main(null);
                }
            }
            return false;
        }

    }
    public static final ShowManual SHOW_MANUAL  = new ShowManual();

    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);// program ends after clicking on the cross
        setLayout(new BorderLayout());//placing the components in the window
        JPanel p = new JPanel(new GridLayout(0, 1));//panel for other components, it holds everything together
        setLayout(new GridLayout(0, 1));
        for (int i = 1; i < 9; i++) {
            JLabel j = new JLabel(new ImageIcon(this.getClass().getResource("/pandemic/data/manual/0" + i + ".jpg"))); //label where I can put some text
            p.add(j);
        }
        this.add(new JScrollPane(p));
        pack();
        this.setLocationRelativeTo(null);//displays the window in the center of monitor
    }

    /**
     * @param args the command line arguments
     */
    public static void main(final String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Manual().setVisible(true);
            }
        });
    }

}
