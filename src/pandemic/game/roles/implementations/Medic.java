/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.roles.implementations;

import pandemic.game.roles.Role;
import pandemic.game.roles.behavior.CityStaying;
import pandemic.game.roles.behavior.DiseaseTreater;

/**
 *
 * @author Pípa
 */
public class Medic extends Role implements DiseaseTreater, CityStaying {

    @Override
    public void doJob() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onTreatDisease() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void inCityStaying() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
