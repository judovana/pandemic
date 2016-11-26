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
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import pandemic.game.board.parts.tokens.City;
import pandemic.game.cards.Card;
import pandemic.game.roles.Role;
import pandemic.game.roles.Roles;

/**
 *
 * @author jvanek
 */
public class OtherActions extends JDialog {

    private static class CardsList extends JList<Card> {

        public CardsList(ListModel<Card> dataModel) {
            super(dataModel);
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

    public OtherActions(Roles roles) {
        super((Dialog) null, true);
        List<Role> allInCity = roles.getPlayersInCity(roles.getCurrentPlayer().getCity());
        this.setLayout(new GridLayout(0, 6));
        this.add(new JLabel(roles.getCurrentPlayer().getName()));
        this.add(new JButton("Build station"));
        this.add(new JButton("Cure disease"));
        this.add(new JButton("invent cure"));
        this.add(new CardsList(new CardsModel(roles.getCurrentPlayer())));
        this.add(new JButton("drop card"));

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
        finish.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                OtherActions.this.setVisible(false);
                roles.setNextPlayer();
                OtherActions.this.dispose();

            }
        });
        this.add(finish);
        this.pack();
        this.setVisible(true);
    }

}
