/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import pandemic.game.board.parts.Deck;
import pandemic.game.board.parts.InfecetionRate;
import pandemic.game.board.parts.Outbreaks;
import pandemic.game.board.parts.tokens.Cubes;
import pandemic.game.cards.Card;
import pandemic.game.roles.Role;
import pandemic.game.roles.Roles;

/**
 *
 * @author pipa
 */
public class OtherActions extends JDialog {

    private final String CD = "Cure disease";

    private static class CardsList extends JList<Card> {

        public CardsList(ListModel<Card> dataModel) {
            super(dataModel);
            this.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        }

        @Override
        public ListCellRenderer<? super Card> getCellRenderer() {
            return new ListCellRenderer<Card>() {

                @Override
                public Component getListCellRendererComponent(JList<? extends Card> list, Card value, int index, boolean isSelected, boolean cellHasFocus) {
                    JLabel l = new JLabel(value.getCity().getName());
                    if (isSelected) {
                        l.setForeground(Color.magenta);
                        l.setBackground(value.getCity().getColor());
                        l.setOpaque(true);
                    } else {
                        l.setForeground(Color.green);
                        l.setBackground(value.getCity().getColor());
                        l.setOpaque(true);
                    }
                    return l;
                }
            };
        }

    }

    private static class CardsModel implements ListModel<Card> {

        private final Role owner;

        public CardsModel(Role owner) {
            this.owner = owner;
        }

        @Override
        public int getSize() {
            return owner.getCardsInHand().size();
        }

        @Override
        public Card getElementAt(int index) {
            return owner.getCardsInHand().get(index);
        }

        @Override
        public void addListDataListener(ListDataListener l) {

        }

        @Override
        public void removeListDataListener(ListDataListener l) {

        }

    }

    public OtherActions(final Roles roles, final Deck playerCards) {
        super((Dialog) null, true);
        List<Role> allInCity = roles.getPlayersInCity(roles.getCurrentPlayer().getCity());
        this.setLayout(new GridLayout(0, 6));

        this.add(new JLabel(roles.getCurrentPlayer().getName()));

        final CardsList mainList = new CardsList(new CardsModel(roles.getCurrentPlayer()));
        final JButton drop = new JButton("drop card");
        final JButton station = new JButton("Build station");
        final JButton cureDisease = new JButton(CD);

        drop.setEnabled(false);
        if (roles.getCurrentPlayer().getCity().haveStation()) {
            station.setEnabled(false);
        } else {
            station.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    roles.getCurrentPlayer().getCity().setStation();
                    station.setEnabled(false);
                }
            });
        }
        drop.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                List<Card> l = mainList.getSelectedValuesList();
                for (Card c : l) {
                    roles.getCurrentPlayer().discardCard(c);
                    playerCards.returnCard(c);
                    drop.setEnabled(false);
                    OtherActions.this.repaint();
                }
            }
        });

        mainList.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (mainList.getSelectedIndices().length > 0) {
                    drop.setEnabled(true);
                } else {
                    drop.setEnabled(false);
                }
            }
        }
        );

        tuneCureButton(roles, cureDisease);

        cureDisease.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        List<Cubes> cubes = roles.getCurrentPlayer().getCity().getCubes();
                        Set<Color> colors = new HashSet<>();
                        for (Cubes cube : cubes) {
                            colors.add(cube.getColor());
                        }
                        if (colors.size() == 1) {
                            cubes.remove(0);
                            tuneCureButton(roles, cureDisease);
                        }
                        //fixme 
                        //if more then one color is there offer popup menu to select exact cube
                    }
                }
        );

        this.add(station);

        this.add(cureDisease);

        this.add(
                new JButton("invent cure"));

        this.add(mainList);

        this.add(drop);

        for (Role role : allInCity) {
            if (role != roles.getCurrentPlayer()) {
                this.add(new JLabel());
                this.add(new JLabel(role.getName()));
                this.add(new JButton("give to " + roles.getCurrentPlayer().getName()));
                this.add(new JButton("take from " + roles.getCurrentPlayer().getName()));
                this.add(new CardsList(new CardsModel(role)));
                this.add(new JButton("drop card"));
            }
        }
        JButton finish = new JButton("finish turn");

        finish.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e
                    ) {
                        OtherActions.this.setVisible(false);
                        roles.setNextPlayer();
                        //TODO remove this chaos methodsonce proper gamepaly is in place
                        InfecetionRate.self.chaos();
                        OtherActions.this.dispose();

                    }
                }
        );

        this.add(finish);

        this.pack();

        this.setVisible(
                true);
    }

    public final void tuneCureButton(final Roles roles, final JButton cureDisease) {
        if (roles.getCurrentPlayer().getCity().getCubes().size() <= 0) {
            cureDisease.setEnabled(false);
            cureDisease.setText(CD + " (" + roles.getCurrentPlayer().getCity().getCubes().size() + ")");
        } else {
            cureDisease.setEnabled(true);
            cureDisease.setText(CD + " (" + roles.getCurrentPlayer().getCity().getCubes().size() + ")");
        }
    }

}
