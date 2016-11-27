/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.board.parts.tokens;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Pípa
 */
public class Cities {

    private final Collection<City> cities = new ArrayList<>(100);
    public static final Set<Color> colors = new HashSet<>(4);
    //parody to singleton
    public static Cities self;

    public Cities() {
        URL cfgFile = this.getClass().getResource("/pandemic/data/board/cities");
        try {
            loadBaseCities(cfgFile);
            for (City city : cities) {
                colors.add(city.getColor());
            }
            setNeighbors(cfgFile);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        this.self = this;
    }

    public City getCityByName(String name) {
        for (City city : cities) {
            if (city.getName().equalsIgnoreCase(name)) {
                return city;
            }
        }
        throw new CityNotFoundException("City not found: " + name);
    }

    public City getCityByCoord(Point point) {
        for (City city : cities) {
            if (city.isOn(point)) {
                return city;
            }
        }
        return null;
    }

    public Collection<City> getCities() {
        return Collections.unmodifiableCollection(cities);
    }

    public static class CityNotFoundException extends RuntimeException {

        private CityNotFoundException(String string) {
            super(string);
        }

    }

    private static void processCities(URL u, CitiesProcessor p) throws IOException {
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
                p.processLine(parts);

            }
        }
    }

    private void loadBaseCities(URL u) throws IOException {
        processCities(u, new CitiesProcessor() {

            @Override
            public void processLine(String... parts) {
                cities.add(City.create(parts[0], parts[2], parts[1]));
            }
        });

    }

    private void setNeighbors(URL u) throws IOException {
        processCities(u, new CitiesProcessor() {

            @Override
            public void processLine(String... parts) {

                String[] nbrs = parts[3].split(",");
                City current = getCityByName(parts[2]);
                for (String nbr : nbrs) {
                    try {
                        City neighbor = getCityByName(nbr);
                        current.addNeighbor(neighbor);
                        System.out.println(current.getName() + " siding: " + neighbor.getName());
                    } catch (CityNotFoundException ex) {
                        System.out.println(ex.getMessage());

                    }

                }
            }
        });
    }

    private static interface CitiesProcessor {

        public void processLine(String... s);
    }

    public void drawStations(Graphics2D g2d) {
        for (City city : cities) {
            city.drawStation(g2d);
        }
    }

    public int countColor(Color c) {
        int i = 0;
        for (City city : cities) {
            i += city.getCubesCount(c);
        }
        return i;
    }
}
