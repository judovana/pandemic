/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.board.parts;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import pandemic.game.board.parts.tokens.Cities;
import pandemic.game.board.parts.tokens.City;
import pandemic.game.board.parts.tokens.DrugTokens;

/**
 *
 * @author Pípa
 */
public class Drugs {

    private final Map<Color, DrugTokens> centers = new HashMap<>(4);
    private final Set<DrugTokens> cured = new HashSet<>(4);
    //very bad parody to singleton
    public static Drugs self;

    public Drugs() throws IOException {
        
        try (BufferedReader br
                //comfortable wrapper around reader allowing work with whole lines(see readline bellow)
                = new BufferedReader(
                        //reader is converting the bytes to characters (see encoding UTF8 bellow)
                        new InputStreamReader(
                                //openstream allows to read resources as bytes
                                this.getClass().getResource("/pandemic/data/board/cures").openStream(), StandardCharsets.UTF_8))) {
                            while (true) {
                                String s = br.readLine();
                                if (s == null) {
                                    break;
                                }
                                //all lines that strats with "#" are ignored
                                s = s.trim();
                                if (s.startsWith("#")) {
                                    continue;
                                }
                                //first we split the line by usin ";".
                                String[] parts = s.split(";");
                                //splitting second part of above split.
                                String[] coords = parts[1].split(",");
                                //converting firt and second part of second split to x and y to numerical values 
                                int x = Integer.valueOf(coords[0]);
                                int y = Integer.valueOf(coords[1]);
                                //converting the color to the real color object
                                Color c = City.stringToColor(parts[0]);
                                centers.put(c, new DrugTokens(new Point(x, y), c, false));
                            }
                        }
                        this.self = this;
    }

    public void cure(Color c) {
        cured.add(centers.get(c));
        checkFixed(c);
    }
    /**
     * checking the disease is cured - checking all cities
     * @param c the color of the disease in the city
     */
    public void checkFixed(Color c) {
        if (Cities.self.countColor(c) == 0) {
            centers.get(c).setFixed(true);
        }
    }

    public void draw(Graphics2D g) {
        for (DrugTokens c : cured) {
            c.draw(g);;
        }
    }

    public boolean isCured(Color c) {
        for (DrugTokens cured1 : cured) {
            if (cured1.getColor().equals(c)) {
                return true;
            }
        }
        return false;
    }
    /**
     * checkong if the disease was cured in the past
     * @param c color of the disease
     * @return 
     */
    public boolean isFixed(Color c) {
        for (DrugTokens cured1 : cured) {
            if (cured1.getColor().equals(c) && cured1.isFixed()) {
                return true;
            }
        }
        return false;
    }

}
