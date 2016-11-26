/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game;

import java.awt.Dialog;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JLabel;
import pandemic.game.roles.Role;
import pandemic.game.roles.Roles;

/**
 *
 * @author jvanek
 */
public class OtherActions extends JDialog {

    public OtherActions(Roles roles) {
        super((Dialog) null, true);
        List<Role> allInCity = roles.getPlayersInCity(roles.getCurrentPlayer().getCity());
        this.setLayout(new GridLayout());
        for (Role role : allInCity) {
            this.add(new JLabel(role.getName()));
        }
        this.pack();
        this.setVisible(true);
    }

}
