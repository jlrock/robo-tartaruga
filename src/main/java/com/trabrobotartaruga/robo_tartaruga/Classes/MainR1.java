package com.trabrobotartaruga.robo_tartaruga.Classes;

import java.util.Scanner;

public class MainR1 {
    public static void main(String argrs[]){
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite uma cor para o robô:");
        String cor = sc.nextLine();
        Robo robo = new Robo(cor);
        System.out.println("Digite as coordenadas do alimento:");
        System.out.print("X: ");
        int x = sc.nextInt();
        System.out.print("\nY: ");
        int y = sc.nextInt();
        sc.nextLine();
        
        while (robo.verificarAlimentoEncontrado(x, y) == false) {
            try{
                System.out.println("Digite o movimento: ");
                String movimento = sc.nextLine();
                robo.mover(movimento);
            }catch(MovimentoInvalidoException e){
                e.mensagemDeErro();
            }
            
        }
        System.out.println("Alimento encontrado!");
        sc.close();
    }
}