package com.trabrobotartaruga.robo_tartaruga.classes;


public class MovimentoInvalidoException extends Exception{
    public String toString(){
        return "O movimento é inválido pois está em uma posição fora da matriz!";
    }
    
    public void mensagemDeErro(){
        System.out.println("O movimento é inválido pois está em uma posição fora da matriz!");
    }
}
