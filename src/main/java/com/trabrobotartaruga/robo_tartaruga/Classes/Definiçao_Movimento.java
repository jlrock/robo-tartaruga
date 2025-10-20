package com.trabrobotartaruga.robo_tartaruga.Classes;

public class Definiçao_Movimento {
    private int posicaoX;
    private int posicaoY;
    String cor;

    public Definiçao_Movimento(String cor) {
        this.cor = cor;
        this.posicaoX = 0;
        this.posicaoY = 0;
    }

    public int getPosicaoX() {
        return posicaoX;
    }

    public void setPosicaoX(int posicaoX) {
        this.posicaoX = posicaoX;
    }

    public int getPosicaoY() {
        return posicaoY;
    }

    public void setPosicaoY(int posicaoY) {
        this.posicaoY = posicaoY;
    }

    public void mover(String movimento) throws MovimentoInvalidoException {

        switch (movimento.toLowerCase()) {
            case "up":
                posicaoY++;
                break;
            case "down":
                posicaoY--;
                break;
            case "right":
               posicaoX++;
                break;
            case "left":
               posicaoX--;
                break;
            default:
                System.out.println(movimento+" é um movimento inválido!");
                return;
        }

        if(posicaoX<0){
            throw new MovimentoInvalidoException(posicaoX);
        }
        else if(posicaoY<0){
             throw new MovimentoInvalidoException(posicaoY);
        }

        System.out.println("Robô " + cor + " está agora em (" + posicaoX + ", " + posicaoY + ")");
    }

}
