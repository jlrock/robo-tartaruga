package com.trabrobotartaruga.robo_tartaruga.classes.bot;

import com.trabrobotartaruga.robo_tartaruga.classes.Map;
import com.trabrobotartaruga.robo_tartaruga.exceptions.InvalidMoveException;

public class Bot {

    protected final String color;
    protected int posX;
    protected int posY;
    protected int lastMove;
    protected final int mapX;
    protected final int mapY;

    public Bot(String color, Map map) {
        this.color = color;
        this.posX = 0;
        this.posY = 0;
        this.mapX = map.getX();
        this.mapY = map.getY();
    }

    public String getColor() {
        return color;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void move(String move) throws InvalidMoveException {
        if(posX >= 0 && posX <= mapX && posY >= 0 && posY <= mapY) {
            switch (move.toLowerCase()) {
                case "up" -> {if(posY < mapY) {moveUp();}}
                case "down" -> {if(posX > 0) { moveDown();}}
                case "right" -> {if(posX < mapX) {moveRight();}}
                case "left" -> {if(posX > 0) {moveLeft();}}
                default -> {
                    System.out.println(move + " é um movimento inválido!");
                    return;
                }
            }
            System.out.println("Robô " + color + " está agora em (" + posX + ", " + posY + ")");
        } else {
            throw new InvalidMoveException();
        }
    }

    public void move(int i) throws InvalidMoveException {
        switch (i) {
            case 1 ->
                posY++;
            case 2 ->
                posY--;
            case 3 ->
                posX++;
            case 4 ->
                posX--;
            default -> {
                System.out.println("Apenas 1, 2, 3 e 4 são permitidos!");
                return;
            }
        }

        lastMove = i;
        System.out.println("Robô " + color + " está agora em (" + posX + ", " + posY + ")");
    }
    
    private void moveUp() {
        posY++;
        lastMove = 1;
    }
    private void moveDown() {
        posY--;
        lastMove = 2;
    }
    private void moveLeft() {
        posX--;
        lastMove = 4;
    }
    private void moveRight() {
        posX++;
        lastMove = 3;
    }

    public int getLastMove() {
        return lastMove;
    }
}
