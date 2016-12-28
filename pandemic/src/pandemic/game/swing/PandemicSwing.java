/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.swing;

import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import pandemic.game.roles.Roles;

/**
 *This is the main entrance point for application without need to specify the players on a commandline.
 * This show swing window where players are selected in checkboxes
 * @author Pípa
 */
public class PandemicSwing extends javax.swing.JFrame {

    /**
     * Creates new form PandemicSwing
     */
    public PandemicSwing(final String[] args) {
        initComponents(args);
    }

    private void initComponents(final String[] mainArgs) {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(Manual.SHOW_MANUAL);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        setLayout(
                new GridLayout(0, 1));
        final List<String> q = Roles.knownRolesList;
        final List<JCheckBox> boxes = new ArrayList<>(q.size());
        for (String q1 : q) {
            JCheckBox j = new JCheckBox(q1);
            boxes.add(j);
            this.add(j);
        }
        JButton s = new JButton("Start game");

        s.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e
                    ) {
                        int l = 0;
                        for (JCheckBox box : boxes) {
                            if (box.isSelected()) {
                                l++;
                            }
                        }
                        String[] lArgs = new String[l];
                        l = 0;
                        for (JCheckBox box : boxes) {
                            if (box.isSelected()) {
                                lArgs[l] = box.getText();
                                l++;
                            }
                        }
                        try {
                            if (mainArgs.length == 0) {
                                Pandemic.main(lArgs);
                            } else {
                                String[] call = new String[]{
                                    "java",
                                    "-cp",
                                    mainArgs[0],
                                    Pandemic.class.getName()
                                };
                                String[] c = new String[call.length + lArgs.length];
                                System.arraycopy(call, 0, c, 0, call.length);
                                System.arraycopy(lArgs, 0, c, call.length, lArgs.length);
                                Runtime.getRuntime().exec(c);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(PandemicSwing.this, ex.getMessage());
                        } finally {
                            KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(Manual.SHOW_MANUAL);
                            PandemicSwing.this.dispose();
                        }
                    }
                }
        );

        this.add(s);
        this.add(new JCheckBox("Random beggining"));
        this.add(new JLabel("Instead of placing 3,2,and 1 cube to 3,3,3 cities, random number of cubes is placed randomly"));
        this.add(new JLabel("Random beggining is ok for quick game wihtout or with low number of epidemy cards"));
        this.add(new JLabel("Number of epidemy cards:"));
        this.add(new JSpinner(new SpinnerNumberModel(4, 0, 100, 1)));
        this.add(new JLabel("2-4 are easy game, 4-5 is hard game, 6+is very hard.  Less then 2, you can easily run of time!"));

        pack();

        this.setLocationRelativeTo(null);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(final String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PandemicSwing(args).setVisible(true);
            }
        });
    }

}
