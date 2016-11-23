/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.roles;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;
import pandemic.game.roles.implementations.Medic;
import pandemic.game.roles.implementations.Scientist;

/**
 *
 * @author Pípa
 */
public class Roles {

    private Collection<Role> roles = new ArrayList<>();

    public Roles(String[] args) {
        for (String arg : args) {
            switch (arg) {
                case "medic":
                    roles.add(new Medic());
                    break;
                case "scientist":
                    roles.add(new Scientist());
                    break;
                default:
                    throw new IllegalArgumentException("Invalid type of player: " + arg);
            }
        }
    }

    public Role getNextPlayer() {
        return null;
    }

    public void drawPlayers(Graphics2D g) {
        for (Role role : roles) {
            role.paint(g);
        }
    }

}
