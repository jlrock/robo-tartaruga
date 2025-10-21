package com.trabrobotartaruga.robo_tartaruga.classes.bot;

import com.trabrobotartaruga.robo_tartaruga.exceptions.InvalidMoveException;

public class Bot {

    protected final String color;
    protected int posX;
    protected int posY;
    protected int lastMove;

    public Bot(String color) {
        this.color = color;
        this.posX = 0;
        this.posY = 0;
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

        switch (move.toLowerCase()) {
            case "up" -> {
                posY++;
                lastMove = 1;
            }
            case "down" -> {
                posY--;
                lastMove = 2;
            }
            case "right" -> {
                posX++;
                lastMove = 3;
            }
            case "left" -> {
                posX--;
                lastMove = 4;
            }
            default -> {
                System.out.println(move + " é um movimento inválido!");
                return;
            }
        }

        System.out.println("Robô " + color + " está agora em (" + posX + ", " + posY + ")");
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

    public int getLastMove() {
        return lastMove;
    }
}
