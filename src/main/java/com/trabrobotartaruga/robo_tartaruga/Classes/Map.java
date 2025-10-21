package com.trabrobotartaruga.robo_tartaruga.classes;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.trabrobotartaruga.robo_tartaruga.classes.bot.Bot;

public class Map {

    List<List<Position>> positions = new CopyOnWriteArrayList<>();
    List<Bot> bots;

    public Map(int x, int y, List<Bot> bots) {
        for (int i = 1; i <= y; i++) {
            List<Position> linha = new CopyOnWriteArrayList<>();
            for (int j = 1; j <= x; j++) {
                linha.add(new Position(x, y));
            }
            positions.add(linha);
        }
        this.bots = bots;
        
        for (Bot bot : bots) {
            this.positions.get(0).get(0).getObjects().add(bot);
        }
    }

    public List<List<Position>> getPositions() {
        return positions;
    }
    
}
