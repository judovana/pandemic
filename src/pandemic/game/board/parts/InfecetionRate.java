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
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Pípa
 */
public class InfecetionRate {

    private int count;
    private final List<Value> values = new ArrayList<>(7);
    private final int radius = 20;
    
    //FIXME fasle singleton to make cheat game possible
    public static InfecetionRate self;

    private class Value {

        private final Point center;
        private final int value;

        public Value(Point center, int value) {
            this.center = center;
            this.value = value;
        }

    }

    public InfecetionRate() {
        URL cfgFile = this.getClass().getResource("/pandemic/data/board/infectionRates");
        try {
            load(cfgFile);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        self=this;
    }

    private void load(URL u) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(u.openStream(), StandardCharsets.UTF_8))) {
            while (true) {
                String s = br.readLine();
                if (s == null) {
                    break;
                }
                s = s.trim();
                if (s.startsWith("#")) {
                    continue;
                }
                String[] parts = s.split(";");
                int value = Integer.valueOf(parts[0]);
                int x = Integer.valueOf(parts[1]);
                int y = Integer.valueOf(parts[2]);
                values.add(new Value(new Point(x, y), value));
            }
        }
    }

    public void addInfectionRate() {
        count++;
    }

    ///fixme method for fake gameplay only
    public void chaos() {
        count = new Random().nextInt(values.size() - 1);
    }

    public int getInfectionRate() {
        return values.get(count).value;
    }

    public void draw(Graphics2D g) {
        g.setColor(new Color(120 + (count * 15), 125, 125, 125));
        g.fillOval(values.get(count).center.x - radius, values.get(count).center.y - radius, radius * 2, radius * 2);
    }

}
