/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.roles.implementations;

import pandemic.game.roles.Role;
import pandemic.game.roles.behavior.CityStaying;

/**
 *
 * @author Pípa
 */
public class QuarantineSpecialist extends Role implements CityStaying {

    @Override
    public void doJob() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void inCityStaying() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String getDescription() {
        return "In city where it is, or in adjectent cities, no disease can spread";
    }
    
}
