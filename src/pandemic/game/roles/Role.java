/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.roles;

import java.awt.Graphics2D;
import java.util.Collection;
import pandemic.game.board.parts.tokens.City;
import pandemic.game.board.parts.Deck;
import pandemic.game.cards.PlayerCards;

/**
 *
 * @author Pípa
 */
public abstract class Role {
    private String name;
    
    private Collection<PlayerCards> cardsInHands;
    
    private City city;
    
    public abstract void doJob();
    
    public void moveToTheCity(City city){
    }
    
    public void cureDisease(){
    }
    
    public void flzToTheCity(City city){
    }
    
    public void makeCure(){
    }
    
    public void changeCards(Role role){
    }
    
    public void buildStation(){
    }
    
    public void passTheAction(){
    }
    
    public void drawCards(Deck deck){
    }
    
    public void discardCard(){
    }

    void paint(Graphics2D g) {
        g.drawString(this.getClass().getName(), 100, 100);
    }
    
   

}

