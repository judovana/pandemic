/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.swing;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import pandemic.game.board.Board;

/**
 *
 * @author Pípa
 */
//public class Manual extends javax.swing.JDialog {
public class Manual extends javax.swing.JFrame {

    /**
     * Creates new form PandemicSwing initComponents() is a method used in swing
     * Design to initialize swing components( like setting the default
     * values,..) something like the connection between GUI editor and Java.
     */
    public Manual(String s) {
        initComponents(s);
    }

    public static boolean dispatch(int e) {
        if (e == KeyEvent.VK_F1) {
            Manual.main(new String[]{Board.MANUAL});
            return true;
        }
        if (e == KeyEvent.VK_F2) {
            if (Desktop.isDesktopSupported()) {
                try {
                    File myFile = new File(System.getProperty("java.io.tmpdir") + "/" + Board.MANUALPDF);
                    Board.exportManualEn(myFile);
                    Desktop.getDesktop().open(myFile);
                    return true;
                } catch (IOException ex) {
                    // no application registered for PDFs
                }
            }
        }
        if (e == KeyEvent.VK_F3) {
            Manual.main(new String[]{Board.MANUALCZ});
            return true;
        }
        if (e == KeyEvent.VK_F4) {
            if (Desktop.isDesktopSupported()) {
                try {
                    File myFile = new File(System.getProperty("java.io.tmpdir") + "/" + Board.MANUALPDF);
                    Board.exportManualCz(myFile);
                    Desktop.getDesktop().open(myFile);
                    return true;
                } catch (IOException ex) {
                    // no application registered for PDFs
                }
            }
        }
        return false;
    }

    public static class ShowManual implements KeyEventDispatcher {

        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_RELEASED && e.getModifiers() == 0) {
                return dispatch(e.getKeyCode());
            }
            return false;
        }

    }
    public static final ShowManual SHOW_MANUAL = new ShowManual();

    private void initComponents(String s) {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);// program ends after clicking on the cross
        setLayout(new BorderLayout());//placing the components in the window
        JPanel p = new JPanel(new GridLayout(0, 1));//panel for other components, it holds everything together
        setLayout(new GridLayout(0, 1));
        for (int i = 1; i < 9; i++) {
            JLabel j = new JLabel(new ImageIcon(this.getClass().getResource("/pandemic/data/" + s + "/0" + i + ".jpg"))); //label where I can put some text
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
                new Manual(args[0]).setVisible(true);
            }
        });
    }

}
