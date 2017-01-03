/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.roles.implementations;

import pandemic.game.roles.Role;
import pandemic.game.roles.behavior.CuringAction;

/**
 *
 * @author Pípa
 */
public class Scientist extends Role implements CuringAction{

    @Override
    public void doJob() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onCuringAction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String getDescription() {
        return "Inventing cure costs only 4 cards";
    }
    
}
