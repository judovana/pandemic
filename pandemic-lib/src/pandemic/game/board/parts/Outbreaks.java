/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.board.parts;

import j2a.Color;
import j2a.GraphicsCanvas;
import java.util.List;
import j2a.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 *
 * @author Pípa
 */
public class Outbreaks {

    private int count = 0;
    private final List<Point> centers = new ArrayList<>(8);
    private static final int radius = 20;

    //FIXME fasle singleton to finish cheat game quickly
    public static Outbreaks self;

    public void addOutbreak() {
        if (count >= 8) {
            //lost
            return;
        }
        count++;
    }
    /**
     * Loading configuration file 
     */
    public Outbreaks() {
        URL cfgFile = this.getClass().getResource("/pandemic/data/board/outbreaks");
        try {
            load(cfgFile);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        self = this;
    }
    /**
     * Loading the coordinates of outbreaks
     * @param u url of resource to load
     * @throws IOException 
     */
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
                String[] parts = s.split(",");
                centers.add(j2a.Factory.Point.newPoint(Integer.valueOf(parts[0]), Integer.valueOf(parts[1])));

            }
        }
    }

    public void draw(GraphicsCanvas g) {
        g.setColor(j2a.Factory.Color.newColor(count * 30, 125, 125, 125));
        g.fillOval(centers.get(count).getX() - radius, centers.get(count).getY() - radius, radius * 2, radius * 2);
    }
}
