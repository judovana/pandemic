/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.board.parts.tokens;

import java.util.Collection;
import pandemic.game.board.DiseaseType;

/**
 *
 * @author Pípa
 */
public class Drugs {
    private Collection<DrugTokens> drugTokens;
    
    public void addCure(DiseaseType diseaseType){
    }
 
    public boolean isAllCured(){
        return true;
    }
}
