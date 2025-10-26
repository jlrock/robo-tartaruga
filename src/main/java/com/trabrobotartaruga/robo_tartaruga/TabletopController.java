package com.trabrobotartaruga.robo_tartaruga;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;

import com.trabrobotartaruga.robo_tartaruga.classes.Map;
import com.trabrobotartaruga.robo_tartaruga.classes.bot.Bot;
import com.trabrobotartaruga.robo_tartaruga.classes.bot.RandomBot;
import com.trabrobotartaruga.robo_tartaruga.classes.bot.SmartBot;
import com.trabrobotartaruga.robo_tartaruga.classes.obstacle.Obstacle;
import com.trabrobotartaruga.robo_tartaruga.classes.obstacle.Stone;
import com.trabrobotartaruga.robo_tartaruga.exceptions.InvalidInputException;
import com.trabrobotartaruga.robo_tartaruga.exceptions.InvalidMoveException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TabletopController {

    private Map map;
    private final Semaphore semaphore = new Semaphore(0);
    private Bot lastPlayedBot;

    @FXML
    GridPane gameGrid;
    @FXML
    AnchorPane tabletopAnchorPane;

    public void load(@SuppressWarnings("exports") Map map) {
        this.map = map;

        showBots();
        play();
    }

    private void play() {
        new Thread(() -> {
            while (!map.isGameOver()) {
                Platform.runLater(() -> map.updateBots());
                for (Bot bot : map.getBots()) {
                    if ((bot.equals(lastPlayedBot) && map.getBots().size() > 1) || !bot.isActive()) {
                        continue;
                    }

                    boolean goodMove = true;

                    try {
                        Thread.sleep(1000);
                        switch (bot) {
                            case RandomBot randomBot ->
                                randomBot.move("");
                            case SmartBot smartBot ->
                                smartBot.move(0);
                            default -> {
                                move(bot);
                                pause();
                            }
                        }
                    } catch (InvalidMoveException e) {
                        System.out.println(e);
                        bot.setInvalidMoves(bot.getInvalidMoves() + 1);
                        goodMove = false;
                    } catch (InvalidInputException | InterruptedException e) {
                    }

                    lastPlayedBot = bot;
                    bot.setRounds(bot.getRounds() + 1);
                    if (goodMove) {
                        bot.setValidMoves(bot.getValidMoves() + 1);
                    }
                    Platform.runLater(() -> {
                        map.updateBots();
                        showBots();
                    });

                    if (!map.getObstacles().isEmpty()) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException ex) {
                        }

                        Platform.runLater(() -> {
                            try {
                                map.obstacleAction();
                            } catch (InvalidMoveException | InvalidInputException e) {
                                e.printStackTrace();
                            }
                        });

                        Platform.runLater(() -> {
                            map.updateBots();
                            showBots();
                        });
                    }
                }
            }
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
            }

            Platform.runLater(() -> goToFinalScreen(map.getBots(), map.getWinnerBots()));
        }).start();
    }


    private void move(Bot bot) {
        String[] possibleInputs = {"up", "down", "left", "right", "1", "2", "3", "4"};
        int goodInput = -1;
        boolean moved = false;
        HBox playerHBox = (HBox) tabletopAnchorPane.lookup("#playerHBox");
        TextField moveTextField = (TextField) tabletopAnchorPane.lookup("#moveTextField");
        while (!moved) {
            playerHBox.setDisable(false);
            pause();

            for (int i = 0; i < possibleInputs.length; i++) {
                if (moveTextField.getText().equals(possibleInputs[i])) {
                    goodInput = i;
                }
            }

            if (goodInput != -1) {
                try {
                    bot.move(moveTextField.getText());
                    moved = true;
                } catch (InvalidMoveException e) {
                    playerHBox.setDisable(true);
                    Platform.runLater(() -> showErrorPane(e.toString()));
                    moved = false;
                } catch (InvalidInputException ex) {
                    try {
                        bot.move(goodInput - 3);
                        moved = true;
                    } catch (InvalidMoveException | InvalidInputException e) {
                        moved = false;
                        Platform.runLater(() -> showErrorPane(e.toString()));
                    }
                    playerHBox.setDisable(true);
                }
            } else {
                Platform.runLater(() -> showErrorPane(new InvalidInputException().toString()));
                moved = false;
            }
        }

        playerHBox.setDisable(true);
        resume();
    }

    public void resume() {
        semaphore.release();
    }

    private void pause() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void goToFinalScreen(List<Bot> bots, List<Bot> winnerBot) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/trabrobotartaruga/robo_tartaruga/tela_final.fxml"));
                Parent root = loader.load();
                Stage newStage = new Stage();
                FinalScreenController finalScreenController = loader.getController();
                finalScreenController.build(bots, winnerBot);
                newStage.setTitle("Resultado Final");
                newStage.setScene(new Scene(root));
                Stage currentstage = (Stage) ((Node) tabletopAnchorPane).getScene().getWindow();
                currentstage.close();
                Platform.runLater(() -> newStage.show());
            } catch (IOException e) {
            }
        });
    }

    private void showErrorPane(String message) {
        try {
            Stage stage = new Stage();
            Scene scene;
            scene = new Scene(new FXMLLoader(App.class.getResource("error.fxml")).load());
            stage.initModality(Modality.APPLICATION_MODAL);
            Label warningLabel = (Label) scene.lookup("#warningLabel");
            warningLabel.setText(message);
            Button okButton = (Button) scene.lookup("#okButton");
            okButton.setOnAction(eh -> {
                stage.close();
            });
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
        }
    }

    private void showBots() {
        for (int i = 0; i < map.getX(); i++) {
            for (int j = 0; j < map.getY(); j++) {
                FlowPane gridCell = (FlowPane) gameGrid.lookup("#gridCell" + i + "" + j);
                gridCell.getChildren().clear();
                if (map.getFood().getPosX() == j && map.getFood().getPosY() == i) {
                    ImageView foodImage = new ImageView(new Image(getClass().getResourceAsStream("/com/trabrobotartaruga/robo_tartaruga/assets/food.png"), 100, 100, false, false));
                    gridCell.getChildren().add(foodImage);
                }
                for (Obstacle obstacle : map.getObstacles()) {
                    if (obstacle.getPosX() == j && obstacle.getPosY() == i) {
                        if (obstacle instanceof Stone) {
                            ImageView stoneImage = new ImageView(new Image(getClass().getResourceAsStream("/com/trabrobotartaruga/robo_tartaruga/assets/stone.png"), 100, 100, false, false));
                            gridCell.getChildren().add(stoneImage);
                        } else {
                            ImageView bombImage = new ImageView(new Image(getClass().getResourceAsStream("/com/trabrobotartaruga/robo_tartaruga/assets/bomb.png"), 100, 100, false, false));
                            gridCell.getChildren().add(bombImage);
                        }
                    }
                }
            }
        }
        for (int i = 0; i < map.getX(); i++) {
            for (int j = 0; j < map.getY(); j++) {
                if (!map.getPositions().get(i).get(j).getObjects().isEmpty()) {
                    for (Object object : map.getPositions().get(i).get(j).getObjects()) {
                        FlowPane gridCell = (FlowPane) gameGrid.lookup("#gridCell" + i + "" + j);
                        DropShadow dropShadow = new DropShadow();
                        dropShadow.setRadius(1.0);
                        dropShadow.setOffsetX(10.0);
                        dropShadow.setOffsetY(10.0);
                        switch (object) {
                            case RandomBot randomBot -> {
                                ImageView image = new ImageView(new Image(
                                        getClass().getResourceAsStream(
                                                "/com/trabrobotartaruga/robo_tartaruga/assets/random_bot.png"),
                                        70, 70, false, false));
                                dropShadow.setColor(Color.valueOf(randomBot.getColor()));
                                image.setEffect(dropShadow);
                                gridCell.getChildren().add(image);
                            }
                            case SmartBot smartBot -> {
                                ImageView image = new ImageView(new Image(
                                        getClass().getResourceAsStream(
                                                "/com/trabrobotartaruga/robo_tartaruga/assets/smart_bot.png"),
                                        55, 55, false, false));
                                dropShadow.setColor(Color.valueOf(smartBot.getColor()));
                                image.setEffect(dropShadow);
                                gridCell.getChildren().add(image);
                            }
                            case Bot bot -> {
                                ImageView image = new ImageView(new Image(
                                        getClass().getResourceAsStream(
                                                "/com/trabrobotartaruga/robo_tartaruga/assets/bot.png"),
                                        55, 55, false, false));
                                dropShadow.setColor(Color.valueOf(bot.getColor()));
                                image.setEffect(dropShadow);
                                gridCell.getChildren().add(image);
                            }
                            default -> {
                            }
                        }
                    }

                }
            }
        }
    }
}
