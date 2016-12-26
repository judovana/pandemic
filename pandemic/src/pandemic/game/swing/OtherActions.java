/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import pandemic.game.board.parts.Deck;
import pandemic.game.board.parts.Drugs;
import pandemic.game.board.parts.InfecetionRate;
import pandemic.game.board.parts.tokens.Cubes;
import pandemic.game.cards.Card;
import pandemic.game.cards.PlayerCard;
import pandemic.game.roles.Role;
import pandemic.game.roles.Roles;

/**
 * Dialog is representing the interactions between players
 *
 * @author pipa
 */
public class OtherActions extends JDialog {

    public static final int CARDS_TO_CURE = 5;
    private final String CD = "Cure disease";
    private final List<OtherPlayerGuiWrapper> others = new ArrayList<>(); //
    private final Deck deck;

    private static class CardsList extends JList<Card> {

        public CardsList(ListModel<Card> dataModel) {
            super(dataModel);
            this.setSelectionMode(DefaultListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        }

        @Override
        public ListCellRenderer<? super Card> getCellRenderer() {
            return new ListCellRenderer<Card>() {

                @Override
                public Component getListCellRendererComponent(JList<? extends Card> list, Card value, int index, boolean isSelected, boolean cellHasFocus) {
                    JLabel l = new JLabel(value.getCity().getName());
                    if (isSelected) {
                        l.setForeground(Color.magenta);
                        l.setBackground((Color) value.getCity().getColor().getOriginal());
                        l.setOpaque(true);
                    } else {
                        l.setForeground(Color.green);
                        l.setBackground((Color) value.getCity().getColor().getOriginal());
                        l.setOpaque(true);
                    }
                    return l;
                }
            };
        }

    }

    private static class CardsModel extends AbstractListModel<Card> {

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

        public void update() {
            fireContentsChanged(this, 0, getSize());
        }

    }

    final CardsList mainList;

    public OtherActions(final Roles roles, final Deck playerCards) {
        super((Dialog) null, true);
        this.deck = playerCards;
        List<Role> allInCity = roles.getPlayersInCity(roles.getCurrentPlayer().getCity());
        this.setLayout(new GridLayout(0, 6));

        this.add(new JLabel(roles.getCurrentPlayer().getName()));

        mainList = new CardsList(new CardsModel(roles.getCurrentPlayer()));
        final JButton drop = new JButton("drop card(s)");
        final JButton station = new JButton("Build station");
        final JButton cure = new JButton("invent cure");
        final JButton cureDisease = new JButton(CD);

        drop.setEnabled(false);
        station.setEnabled(false);
        station.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainList.getSelectedValuesList().size() != 1) {
                    throw new RuntimeException("only one can be selected");
                }
                Card c = mainList.getSelectedValue();
                if (c.getCity().equals(roles.getCurrentPlayer().getCity())) {
                    roles.getCurrentPlayer().getCity().setStation();
                    station.setEnabled(false);
                    drop.setEnabled(false);
                    roles.getCurrentPlayer().discardCard(c);
                    playerCards.returnCard(c);
                    for (OtherPlayerGuiWrapper other : others) {
                        other.takeFrom.setEnabled(false);
                        other.dropCard.setEnabled(false);
                    }
                    OtherActions.this.repaint();
                } else {
                    throw new RuntimeException("only current city may be selected");
                }
            }
        });

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
                mainList.setSelectedIndices(new int[0]);
            }
        });

        mainList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                for (OtherPlayerGuiWrapper other : others) {
                    other.cantTake();
                }
                if (mainList.getSelectedIndices().length > 0) {
                    drop.setEnabled(true);
                } else {
                    drop.setEnabled(false);
                }
                if (mainList.getSelectedIndices().length == CARDS_TO_CURE && roles.getCurrentPlayer().getCity().haveStation()) {
                    List<Card> l = mainList.getSelectedValuesList();
                    j2a.Color c = l.get(0).getCity().getColor();
                    for (Card l1 : l) {
                        if (!l1.getCity().getColor().equals(c)) {
                            cure.setEnabled(false);
                            return;
                        }
                    }
                    cure.setEnabled(true);
                } else {
                    cure.setEnabled(false);
                }
                station.setEnabled(false);
                if (mainList.getSelectedIndices().length == 1) {
                    if (mainList.getSelectedValue().getCity().equals(roles.getCurrentPlayer().getCity())){
                        station.setEnabled(true);
                    }
                    if (mainList.getSelectedValue().getCity().equals(roles.getCurrentPlayer().getCity())) {
                        for (OtherPlayerGuiWrapper other : others) {
                            other.canTake();
                        }
                    }
                }
            }
        }
        );

        tuneCureButton(roles, cureDisease);

        cureDisease.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        final List<Cubes> cubes = roles.getCurrentPlayer().getCity().getCubes();
                        Set<j2a.Color> colors = new HashSet<>();
                        for (Cubes cube : cubes) {
                            colors.add(cube.getColor());
                        }
                        if (colors.size() == 1) {
                            j2a.Color cc = cubes.get(0).getColor();
                            if (Drugs.self.isCured(cc)) {
                                int inLength = cubes.size();
                                for (int i = 0; i < inLength; i++) {
                                    cubes.remove(0);
                                }
                                Drugs.self.checkFixed(cc);
                                tuneCureButton(roles, cureDisease);
                            } else {
                                cubes.remove(0);
                                Drugs.self.checkFixed(cc);
                                tuneCureButton(roles, cureDisease);
                            }
                        } else {
                            JPopupMenu jpp = new JPopupMenu();
                            for (j2a.Color color : colors) {

                                int localCount = 0;
                                for (Cubes cube : cubes) {
                                    if (cube.getColor().equals(color)) {
                                        localCount++;

                                    }
                                }

                                JMenuItem jpps = new JMenuItem("_-_-_-_ ( " + localCount + " ) _-_-_-_");
                                jpps.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        Color targetColor = ((JMenuItem) e.getSource()).getBackground();
                                        for (int i = 0; i < cubes.size(); i++) {
                                            Cubes cube = cubes.get(i);
                                            if (cube.getColor().equals(new j2a.java.Color(targetColor))) {
                                                cubes.remove(i);
                                                i--;
                                                if (Drugs.self.isCured(new j2a.java.Color(targetColor))) {
                                                    continue;
                                                } else {
                                                    break;
                                                }
                                            }
                                        }
                                        Drugs.self.checkFixed(new j2a.java.Color(targetColor));
                                        tuneCureButton(roles, cureDisease);
                                    }
                                });
                                jpps.setBackground((Color) color.getOriginal());
                                jpp.add(jpps);
                            }
                            JButton jb = (JButton) e.getSource();
                            jpp.show(jb, 0, 0);
                        }
                    }
                }
        );

        cure.setEnabled(false);

        cure.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e
                    ) {
                        if (!roles.getCurrentPlayer().getCity().haveStation()) {
                            return;
                        }
                        List<Card> l = mainList.getSelectedValuesList();
                        j2a.Color c = l.get(0).getCity().getColor();
                        for (Card l1 : l) {
                            if (!l1.getCity().getColor().equals(c)) {
                                return;
                            }
                        }
                        for (Card l1 : l) {
                            roles.getCurrentPlayer().getCardsInHand().remove(l1);
                            playerCards.returnCard(l1);
                        }
                        mainList.setSelectedIndices(new int[0]);
                        Drugs.self.cure(c);
                        OtherActions.this.repaint();
                    }
                }
        );

        this.add(station);
        this.add(cureDisease);
        this.add(cure);
        this.add(new JScrollPane(mainList));
        this.add(drop);

        for (Role role : allInCity) {
            if (role != roles.getCurrentPlayer()) {
                this.add(new JLabel());
                this.add(new JLabel(role.getName()));
                OtherPlayerGuiWrapper other = new OtherPlayerGuiWrapper(role, roles.getCurrentPlayer());
                others.add(other);
                this.add(other.giveTo);
                this.add(other.takeFrom);
                this.add(new JScrollPane(other.cardList));
                this.add(other.dropCard);
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
        this.setVisible(true);
    }

    /**
     *
     */
    private class OtherPlayerGuiWrapper {

        private final JButton giveTo;
        private final JButton takeFrom;
        private final CardsList cardList;
        private final JButton dropCard;

        public OtherPlayerGuiWrapper(final Role role, final Role main) {
            giveTo = new JButton("give to " + main.getName());
            takeFrom = new JButton("take from " + main.getName());
            takeFrom.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (mainList.getSelectedValuesList().size() != 1) {
                        throw new RuntimeException("only one can be selected");
                    }
                    Card c = mainList.getSelectedValue();
                    main.getCardsInHand().remove((PlayerCard) c);
                    role.getCardsInHand().add((PlayerCard) c);
                    c.setCoords(role.getHome());
                    ((CardsModel) mainList.getModel()).update();
                    ((CardsModel) cardList.getModel()).update();
                    cardList.setSelectedIndices(new int[0]);
                    mainList.setSelectedIndices(new int[0]);
                    for (OtherPlayerGuiWrapper other : others) {
                        other.takeFrom.setEnabled(false);
                        other.dropCard.setEnabled(false);
                    }
                }
            });
            cardList = new CardsList(new CardsModel(role));
            dropCard = new JButton("drop card(s)");
            dropCard.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    List<Card> l = cardList.getSelectedValuesList();
                    for (Card c : l) {
                        role.discardCard(c);
                        deck.returnCard(c);
                        dropCard.setEnabled(false);
                        OtherActions.this.repaint();
                    }
                    mainList.setSelectedIndices(new int[0]);
                }
            });
            giveTo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    Card c = cardList.getSelectedValue();
                    role.getCardsInHand().remove((PlayerCard) c);
                    main.getCardsInHand().add((PlayerCard) c);
                    c.setCoords(main.getHome());
                    ((CardsModel) mainList.getModel()).update();
                    ((CardsModel) cardList.getModel()).update();
                    cardList.setSelectedIndices(new int[0]);
                    mainList.setSelectedIndices(new int[0]);
                    for (OtherPlayerGuiWrapper other : others) {
                        other.giveTo.setEnabled(false);
                        other.dropCard.setEnabled(false);
                    }
                }
            });
            cardList.addListSelectionListener(new ListSelectionListener() {

                @Override
                public void valueChanged(ListSelectionEvent e) {
                    giveTo.setEnabled(false);
                    dropCard.setEnabled(false);
                    if (cardList.getSelectedIndices().length == 1) {
                        if (cardList.getSelectedValue().getCity().equals(main.getCity())) {
                            giveTo.setEnabled(true);
                        }
                    }
                    if (cardList.getSelectedIndices().length > 0) {
                        dropCard.setEnabled(true);
                    }
                }
            });
            giveTo.setEnabled(false);
            takeFrom.setEnabled(false);
            dropCard.setEnabled(false);
        }

        private void cantTake() {
            takeFrom.setEnabled(false);
        }

        private void canTake() {
            takeFrom.setEnabled(true);
        }

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
