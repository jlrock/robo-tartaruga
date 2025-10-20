package com.trabrobotartaruga.robo_tartaruga.Classes;

public class Encontrar_Alimento {
    private int posicaoX;
    private int posicaoY;
    
     public boolean verificarAlimentoEncontrado(int x, int y){
        if(posicaoX == x && posicaoY == y){
            return true;
        }
        else{
            return false;
        }
    }
}
