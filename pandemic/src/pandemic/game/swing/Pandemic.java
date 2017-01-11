/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.swing;

import j2a.java.BitmapImage;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.KeyboardFocusManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import pandemic.game.board.Board;
import pandemic.game.board.OtherActionsProvider;
import pandemic.game.board.parts.Deck;
import pandemic.game.roles.Roles;

/**
 * this is commandline launcher for application which, based on
 * arguments(players' roles) launches gameboard for given players;
 *
 * @author Pípa
 */
public class Pandemic implements Observer {

    private JFrame frame;
    private Board board;

    /**
     * This method throws RuntimeException if no player goes to play.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        new Pandemic().start(args);
    }
    private DrawingPanel drawPane;

    private void start(String[] args) throws IOException {
        if (args.length == 0) {
            throw new RuntimeException("At least one player is expected!");
        }
        int ns = countNumbers(args);
        int bs = countBooleans(args);
        int as = countAdditionalCards(args);
        boolean randomize = false;
        boolean symetric = true;
        int moreCards = 0;
        int epidemy = 4;
        String[] args2 = new String[args.length - ns - bs - as];
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (isBool(arg)) {
                randomize = Boolean.valueOf(arg);
            } else if (isInt(arg)) {
                epidemy = Integer.valueOf(arg);
            } else if (isAdditionalCards(arg)) {
                symetric = additionalCardsBool(arg);
                moreCards = additionalCardsInt(arg);
            } else {
                args2[i] = arg;
            }

        }
        board = new Board(new Roles(args2), new OtherActionsProvider() {

            @Override
            public void provide(Roles r, Deck d) {
                new OtherActions(r, d);
            }
        }, randomize, epidemy, symetric, moreCards);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
                board.addObserver(Pandemic.this);
                board.notifyObservers();
            }
        });

    }

    private void createAndShowGUI() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(Manual.SHOW_MANUAL);
        frame = new JFrame("Pandemic");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        drawPane = new DrawingPanel();
        frame.add(drawPane);
        frame.pack();
        frame.setSize(300, 200);
        frame.setVisible(true);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }

    @Override
    public void update(Observable o, Object o1) {
        if (drawPane != null) {
            drawPane.setCurrentImage((BufferedImage) (((BitmapImage) o1).getOrigianl()));
            frame.repaint();
        }
    }

    private int countNumbers(String[] args) {
        int r = 0;
        for (String arg : args) {
            if (isInt(arg)) {
                r++;
            }
        }
        return r;
    }

    private int countBooleans(String[] args) {
        int r = 0;
        for (String arg : args) {
            if (isBool(arg)) {
                r++;
            }
        }
        return r;
    }

    public boolean isBool(String arg) {
        return (arg.toLowerCase().equals("true") || arg.toLowerCase().equals("false"));
    }

    public boolean isInt(String arg) {
        try {
            Integer.valueOf(arg);
            return true;
        } catch (Exception ex) {

        }
        return false;
    }

    private static boolean isAdditionalCards(String arg) {
        return arg.matches("-+sync\\d+") || arg.matches("-+async\\d+");
    }

    private static boolean additionalCardsBool(String arg) {
        return arg.replace("-", "").startsWith("sync");
    }

    private static int additionalCardsInt(String arg) {
        return Integer.valueOf(arg.replace("-", "").split(".*sync")[1]);
    }

    private int countAdditionalCards(String[] args) {
        int r = 0;
        for (String arg : args) {
            if (isAdditionalCards(arg)) {
                r++;
            }
        }
        return r;
    }

    private class DrawingPanel extends JPanel {

        BufferedImage currentImage;

        public DrawingPanel() {
            this.addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    try {
                        board.move(
                                real(e.getX(), DrawingPanel.this.getWidth(), board.getOrigWidth()),
                                real(e.getY(), DrawingPanel.this.getHeight(), board.getOrigHeight()));
                    } catch (NullPointerException ex) {
                        //here is happening NPE in init,and I dont know why
                        //silencing it to make etacher ahppy
                    }
                }

            });

            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        board.mainClick(
                                real(e.getX(), DrawingPanel.this.getWidth(), board.getOrigWidth()),
                                real(e.getY(), DrawingPanel.this.getHeight(), board.getOrigHeight()));
                    }
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        board.second(
                                real(e.getX(), DrawingPanel.this.getWidth(), board.getOrigWidth()),
                                real(e.getY(), DrawingPanel.this.getHeight(), board.getOrigHeight()));
                    }
                }

            });

        }
        //functions for recalculate coordinations 

        private int real(int coord, int current, int orig) {
            return (int) real((double) coord, (double) current, (double) orig);
        }

        private double real(double coord, double current, double orig) {
            return (orig / current) * coord;
        }

        public void setCurrentImage(BufferedImage currentImage) {
            this.currentImage = currentImage;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(currentImage, 0, 0, this.getWidth(), this.getHeight(), null);
        }

    }
}
