/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.board.parts.tokens;

import java.awt.Color;
import java.awt.Point;
import pandemic.game.board.DiseaseType;

/**
 *
 * @author Pípa
 */
public class DrugTokens extends Token {

    private DiseaseType diseaseType;

    public DrugTokens(Point point, Color c) {
        super(point, c);
    }
}
