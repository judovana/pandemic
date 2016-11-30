/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import pandemic.game.roles.Roles;

/**
 *
 * @author Pípa
 */
public class PandemicSwing extends javax.swing.JFrame {

    /**
     * Creates new form PandemicSwing
     */
    public PandemicSwing() {
        initComponents();
    }

    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 1));
        final List<String> q = Roles.knownRolesList;
        final List<JCheckBox> boxes = new ArrayList<>(q.size());
        for (String q1 : q) {
            JCheckBox j = new JCheckBox(q1);
            boxes.add(j);
            this.add(j);
        }
        JButton s = new JButton("Start game");
        s.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int l = 0;
                for (JCheckBox box : boxes) {
                    if (box.isSelected()) {
                        l++;
                    }
                }
                String[] args = new String[l];
                l = 0;
                for (JCheckBox box : boxes) {
                    if (box.isSelected()) {
                        args[l] = box.getText();
                        l++;
                    }
                }
                try {
                    Pandemic.main(args);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(PandemicSwing.this, ex.getMessage());
                } finally {
                    PandemicSwing.this.dispose();
                }
            }
        });
        this.add(s);
        pack();
        this.setLocationRelativeTo(null);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PandemicSwing().setVisible(true);
            }
        });
    }

}
