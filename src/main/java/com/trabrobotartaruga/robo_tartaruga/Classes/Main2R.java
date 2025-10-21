package com.trabrobotartaruga.robo_tartaruga.classes;
import java.util.Scanner;

import com.trabrobotartaruga.robo_tartaruga.classes.bot.Bot;
import com.trabrobotartaruga.robo_tartaruga.exceptions.InvalidMoveException;

public class Main2R {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite uma cor para o 1º robô:");
        String cor1 = sc.nextLine();
        Bot robo1 = new Bot(cor1);
        
        System.out.println("Digite outra cor para o 2º robô:");
        String cor2 = sc.nextLine();
        Bot robo2 = new Bot(cor2);

        System.out.println("Digite as coordenadas do alimento:");
        System.out.println("X (De 0 a 3): ");
        int x = sc.nextInt();

        if(x > 3 || x<0){
            while (x > 3 || x < 0) {
                System.out.println("Digite o X novamente: ");
                x = sc.nextInt();
            }
        }

        System.out.println("Y (De 0 a 3): ");
        int y = sc.nextInt();

        if(y > 3 || y<0){
            while (y > 3 || y < 0) {
                System.out.println("Digite o Y novamente: ");
                y = sc.nextInt();
            }
        }
        sc.nextLine();
        
        int quant_val1 =0;
        int quant_val2 =0;
        int quant_inval1 =0;
        int quant_inval2 =0;
        
        while(true){
            try{
                System.out.println("Digite o movimento do robô " + robo1.getCor() +": ");
                String movimento = sc.nextLine();
                robo1.mover(movimento);

                if(robo1.getPosX() >= 0 && robo1.getPosX() <= 4 && robo1.getPosY() >= 0 && robo1.getPosY() <= 4){
                    quant_val1++;
                }

                if(robo1.verificarAlimentoEncontrado(x, y) == true){
                    System.out.println("O robô "+ robo1.getCor()+" encontrou o alimento!");
                    System.out.println(quant_val1+" movimentos válidos e "+ quant_inval1+" movimentos inválidos!");
                    System.out.println();
                    break;
                }
                
                System.out.println();
            }catch(InvalidMoveException e1){
                e1.mensagemDeErro();
                quant_inval1++;
            }
            
            try{
                System.out.println("Digite o movimento do robô " + robo2.getCor() +": ");
                String movimento2 = sc.nextLine();
                robo2.mover(movimento2);
                
                if(robo2.getPosX() >= 0 && robo2.getPosX() <= 4 && robo2.getPosY() >= 0 && robo2.getPosY() <= 4){
                    quant_val2++;
                }
                
                if(robo2.verificarAlimentoEncontrado(x, y) == true){
                    System.out.println("O robô "+ robo2.getCor()+" encontrou o alimento!");
                    System.out.println(quant_val2+" movimentos válidos e "+ quant_inval2+" movimentos inválidos!");
                    System.out.println();
                    break;
                }

                System.out.println();
            }catch(InvalidMoveException e2){
                e2.mensagemDeErro();
                quant_inval2++;
            }

        }

        if(robo1.verificarAlimentoEncontrado(x, y) == true){
            System.out.println("E o robô "+robo2.getCor()+" teve "+quant_val2+" movimentos válidos e "+ quant_inval2+" movimentos inválidos!");
        }

        if(robo2.verificarAlimentoEncontrado(x, y) == true){
            System.out.println("E o robô "+robo1.getCor()+" teve "+quant_val1+" movimentos válidos e "+ quant_inval1+" movimentos inválidos!");
        }

        sc.close();
    }
}