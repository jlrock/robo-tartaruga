package com.trabrobotartaruga.robo_tartaruga.classes;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.trabrobotartaruga.robo_tartaruga.classes.bot.Bot;

public class Map {
    private final int x; 
    private final int y; 
    List<List<Position>> positions = new CopyOnWriteArrayList<>();
    List<Bot> bots;

    public Map(int x, int y, List<Bot> bots) {
        this.x = x;
        this.y = y;
        for (int i = y-1; i <= 0; i--) {
            List<Position> linha = new CopyOnWriteArrayList<>();
            for (int j = 0; j < x; j++) {
                linha.add(j, new Position(x, y));
            }
            positions.add(y, linha);
        }
        this.bots = bots;
        
        for (Bot bot : bots) {
            this.positions.get(0).get(0).getObjects().add(bot);
        }
    }

    public List<List<Position>> getPositions() {
        return positions;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<Bot> getBots() {
        return bots;
    }
    
}
