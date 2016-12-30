/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.board.parts;

import j2a.Color;
import j2a.Point;
import j2a.GraphicsCanvas;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import pandemic.game.board.parts.tokens.Cities;
import pandemic.game.board.parts.tokens.City;
import pandemic.game.board.parts.tokens.DrugTokens;
import pandemic.game.roles.Roles;
import pandemic.game.roles.implementations.Medic;

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
                                centers.put(c, new DrugTokens(j2a.Factory.Point.newPoint(x, y), c, false));
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
     *
     * @param c the color of the disease in the city
     */
    public void checkFixed(Color c) {
        if (Cities.self.countColor(c) == 0) {
            centers.get(c).setFixed(true);
        }
    }

    public void draw(GraphicsCanvas g) {
        for (DrugTokens c : cured) {
            c.draw(g);
        }
        for (Color c : new Color[]{
            j2a.Factory.Color.getYELLOW(),
            j2a.Factory.Color.getRED(),
            j2a.Factory.Color.getBLUE(),
            j2a.Factory.Color.getBLACK()}) {
            DrugTokens dt = centers.get(c);
            int t = 24 - Cities.self.countColor(c);
            if (t != 24) {
                g.setColor(j2a.Factory.Color.getCYAN());
                if (t<0){
                    g.setColor(j2a.Factory.Color.getRED());
                }
                g.drawString("" + (t), dt.getPoint().getX(), dt.getPoint().getY());
            }

        }
    }

    public boolean isCuredForCubesRemoval(Color c) {
        if (Roles.self.getCurrentPlayer() instanceof Medic){
            return true;
        }
        return isCured(c);
        
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
     *
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
