/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.roles.implementations.dummy;

import pandemic.game.roles.Role;

/**
 *
 * @author jvanek
 */
public abstract class DummyPlayer extends Role {

    @Override
    public String getDescription() {
        return "No special ability! What an chalange!";
    }
}
