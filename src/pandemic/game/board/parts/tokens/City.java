/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.board.parts.tokens;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import pandemic.game.board.parts.Outbreaks;

/**
 *
 * @author Pípa
 */
public class City {

    static City create(String point, String name, String color) {
        Color c = stringToColor(color);
        String[] pointParts = point.split(",");
        Point p = new Point(Integer.valueOf(pointParts[0]), Integer.valueOf(pointParts[1]));
        System.out.println("Creating: " + name + ", " + p.toString() + ", " + c);
        return new City(p, name, c);

    }

    public static Color stringToColor(String color1) throws IllegalArgumentException {
        switch (color1) {
            case "yellow":
                return Color.YELLOW;
            case "blue":
                return Color.BLUE;
            case "red":
                return Color.red;
            case "black":
                return Color.black;
            default:
                throw new IllegalArgumentException("Invalid type of color: " + color1);
        }
    }

    private Station station;
    private final List<Cubes> cubes = new ArrayList<>(3);

    private final Point center;
    private final String name;
    private final Color color;
    private final Collection<City> neighbors = new ArrayList<>();
    private final int radius = 10;

    private City(Point center, String name, Color color) {
        this.center = center;
        this.name = name;
        this.color = color;

        //FIXME adapt to real play
        Random r = new Random();
        if (new Random().nextBoolean()) {
            int count = r.nextInt(3) + 1;
            for (int i = 0; i < count; i++) {
                cubes.add(new Cubes(randomize(i, center), color));
            }
        }
        // end of testing impl of  diseases
    }

    public String getName() {
        return name;
    }

    void addNeighbor(City neighbor) {
        neighbors.add(neighbor);
    }

    public Collection<City> getNeighbors() {
        return Collections.unmodifiableCollection(neighbors);
    }

    boolean isOn(Point point) {
        return (center.distance(point)) < 2 * radius;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.fillOval(center.x - radius, center.y - radius, radius * 2, radius * 2);
        drawOutlined(Color.white, color, g2d, center.x, center.y, name);
        for (City city : neighbors) {
            g2d.setColor(Color.white);
            g2d.drawLine(center.x, center.y, city.center.x, city.center.y);
            drawOutlined(Color.white, city.color, g2d, city.center.x, city.center.y, city.name);
        }

    }

    private void drawOutlined(Color c1, Color c2, Graphics2D g2d, int x, int y, String s) {
        g2d.setColor(new Color(c1.getRed(), c1.getGreen(), c1.getBlue(), 100));
        g2d.fillRect(x, y, g2d.getFontMetrics().stringWidth(s), g2d.getFontMetrics().getHeight());
        g2d.setColor(c2);
        g2d.drawString(s, x, y + g2d.getFontMetrics().getHeight());
    }

    public Point getCenter() {
        return center;
    }

    void drawStation(Graphics2D g2d) {
        if (station != null) {
            station.draw(g2d);
        }
        for (Cubes cube : cubes) {
            cube.draw(g2d);
        }
    }

    private Point randomize(int i, Point center) {
        int x = center.x + (radius * i) / 2;
        int y = center.y + (radius * i) / 2;
        return new Point(x, y);
    }

    public Color getColor() {
        return color;
    }

    public boolean isNigbouring(City found) {
        return neighbors.contains(found);
    }

    public boolean haveStation() {
        return station != null;
    }

    public void setStation() {
        station = new Station(center);
    }

    public List<Cubes> getCubes() {
        return cubes;
    }

    public boolean infect(Color c, List<City> processed) {
        int sameColors = 0;
        for (Cubes cube : cubes) {
            if (cube.getColor().equals(c)) {
                sameColors++;
            }
        }
        if (sameColors < 3) {
            cubes.add(new Cubes(randomize(cubes.size(), center), c));
            return false;
        }
        //this is hart of algotihm
        //disease canspread to cities where alredy was spread
        //but it can not spread back to cities,where already was panedmy
        //this algorithm is hyper fun when yo see it on real board!
        processed.add(this);
        Outbreaks.self.addOutbreak();
        for (City n : neighbors) {
            if (!processed.contains(n)) {
                n.infect(c, processed);
            }
        }
        return true;
    }
    /**
     * return the number of cubes of given color in this city
     * @param c desired color
     * @return number of cubes
     */
    public int getCubesCount(Color c) {
        int i = 0;
        for (Cubes cube : cubes) {
            if (cube.color.equals(c)) {
                i++;
            }
        }
        return i;
    }

}
