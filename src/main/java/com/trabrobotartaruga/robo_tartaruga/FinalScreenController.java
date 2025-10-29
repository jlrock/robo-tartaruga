package com.trabrobotartaruga.robo_tartaruga;

import java.util.List;

import com.trabrobotartaruga.robo_tartaruga.classes.bot.Bot;
import com.trabrobotartaruga.robo_tartaruga.classes.bot.RandomBot;
import com.trabrobotartaruga.robo_tartaruga.classes.bot.SmartBot;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

public class FinalScreenController {

    @FXML
    Button menuButton;
    @FXML
    Label h1Label;
    @FXML
    Label h2Label;
    @FXML
    GridPane rankGridPane;

    public void build(List<Bot> bots, List<Bot> winnerBots) {
        int slot = 1;
        if (winnerBots.isEmpty()) {
            h1Label.setText("OOPS!");
            h1Label.setTextFill(Paint.valueOf("red"));
            h2Label.setText("Todos os robôs explodiram");
        } else {
            h1Label.setText("PARABÉNS!");
            h1Label.setTextFill(Paint.valueOf("green"));
            if (winnerBots.size() == 1) {
                h2Label.setText("O robô " + winnerBots.get(0).getColor() + " achou o alimento");
            } else {
                h2Label.setText("Todos os robôs acharam o alimento");
            }

            for (Bot winnerBot : winnerBots) {
                Label botType = (Label) rankGridPane.lookup("#botTypeLabel" + (slot));
                VBox botColorVBox = (VBox) rankGridPane.lookup("#botColorVBox" + (slot));
                Label botColor = (Label) rankGridPane.lookup("#botColorLabel" + (slot));
                Label validMoves = (Label) rankGridPane.lookup("#validMovesLabel" + (slot));
                Label invalidMoves = (Label) rankGridPane.lookup("#invalidMovesLabel" + (slot));
                Label rounds = (Label) rankGridPane.lookup("#roundsLabel" + (slot));
                Label foundFood = (Label) rankGridPane.lookup("#foundFoodLabel" + (slot));
                switch (winnerBot) {
                    case RandomBot randomBot ->
                        botType.setText("Aleatório");
                    case SmartBot smartBot ->
                        botType.setText("Inteligente");
                    default ->
                        botType.setText("Normal");
                }
                botColor.setText(winnerBot.getColor());
                botColorVBox.setBackground(Background.fill(Paint.valueOf(winnerBot.getColor())));
                validMoves.setText(String.valueOf(winnerBot.getValidMoves()));
                invalidMoves.setText(String.valueOf(winnerBot.getInvalidMoves()));
                rounds.setText(String.valueOf(winnerBot.getRounds()));
                foundFood.setText("Sim");
                slot++;
            }
        }

        if (slot <= 2) {
            for (Bot bot : bots) {
                boolean isWinner = false;
                for (Bot winnerBot : winnerBots) {
                    if (bot.equals(winnerBot)) {
                        isWinner = true;
                    }
                }
                if (!isWinner) {
                    Label botType = (Label) rankGridPane.lookup("#botTypeLabel" + (slot));
                    VBox botColorVBox = (VBox) rankGridPane.lookup("#botColorVBox" +(slot));
                    Label botColor = (Label) rankGridPane.lookup("#botColorLabel" + (slot));
                    Label validMoves = (Label) rankGridPane.lookup("#validMovesLabel" + (slot));
                    Label invalidMoves = (Label) rankGridPane.lookup("#invalidMovesLabel" + (slot));
                    Label rounds = (Label) rankGridPane.lookup("#roundsLabel" + (slot));
                    Label foundFood = (Label) rankGridPane.lookup("#foundFoodLabel" + (slot));
                    switch (bot) {
                        case RandomBot randomBot ->
                            botType.setText("Aleatório");
                        case SmartBot smartBot ->
                            botType.setText("Inteligente");
                        default ->
                            botType.setText("Normal");
                    }
                    botColor.setText(bot.getColor());
                    botColorVBox.setBackground(Background.fill(Paint.valueOf(bot.getColor())));
                    validMoves.setText(String.valueOf(bot.getValidMoves()));
                    invalidMoves.setText(String.valueOf(bot.getInvalidMoves()));
                    rounds.setText(String.valueOf(bot.getRounds()));
                    foundFood.setText("Não");
                }
            }
        }
    }
}
