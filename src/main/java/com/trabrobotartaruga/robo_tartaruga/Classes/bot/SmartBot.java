package com.trabrobotartaruga.robo_tartaruga.classes.bot;

import com.trabrobotartaruga.robo_tartaruga.exceptions.InvalidMoveException;
import java.util.Random;

public class SmartBot extends Bot {

    public SmartBot(String color, int mapX, int mapY) {
        super(color, mapX, mapY);
    }

    private int newMove() {
        int newMove;

        do {
            newMove = new Random().nextInt(4) + 1;
        } while (newMove == lastMove);

        return newMove;
    }

    @Override
    public void move(int motion) throws InvalidMoveException {
        boolean moved = false;
        motion = newMove();

        while (!moved) {
            try {
                super.move(motion);
                moved = true;
            } catch (InvalidMoveException e) {
                motion = newMove();
            }
        }
    }
}
