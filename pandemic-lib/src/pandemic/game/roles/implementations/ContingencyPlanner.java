/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.roles.implementations;

import pandemic.game.roles.Role;
import pandemic.game.roles.behavior.CardPlay;
import pandemic.game.roles.behavior.SpecialAction;

/**
 *
 * @author Pípa
 */
public class ContingencyPlanner extends Role implements SpecialAction, CardPlay{

    @Override
    public void doJob() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void instedAction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onCardRemove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDescription() {
        return "Can have 8 cards instead of 7";
    }
    
}
