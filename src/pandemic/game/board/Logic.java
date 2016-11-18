/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.board;

import pandemic.game.board.parts.Deck;
import pandemic.game.roles.Roles;
import pandemic.game.roles.Role;

/**
 *
 * @author Pípa
 */
 public class Logic {

    private Role currentPlayer;
    private final Roles roles;

    public Logic(Roles roles) {
        this.roles = roles;
    }

    public Roles getRoles() {
        return roles;
    }
    
    

    public void nextPlayer() {
    }
    
    public void InfectCities(Deck deck,int numberOfCard){
    }
    
    public boolean forceCardToSeven(){
        return true;
    }
    
    public boolean checkVictory(){
        return true;
    }
    
    public boolean checkDefeat(){
        return true;
    }

     public boolean playedAction(){
        return true;
    }


}



