package com.trabrobotartaruga.robo_tartaruga;

import java.io.IOException;
import java.util.concurrent.Semaphore;

import com.trabrobotartaruga.robo_tartaruga.classes.Map;
import com.trabrobotartaruga.robo_tartaruga.classes.bot.Bot;
import com.trabrobotartaruga.robo_tartaruga.exceptions.InvalidMoveException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class TabletopController {

    private Map map;
    private final Semaphore semaphore = new Semaphore(0);

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
            while (!map.isFoodFound()) {
                Platform.runLater(() -> map.updateBots());
                for (Bot bot : map.getBots()) {
                    pause();
                    try {
                        bot.move(1);
                        bot.move(4);
                    } catch (InvalidMoveException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(() -> {
                        map.updateBots();
                        showBots();
                        if (map.isFoodFound()) {
                            goToFinalScreen();
                        }
                    });
                }
            }

        }).start();
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

    private void goToFinalScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/trabrobotartaruga/robo_tartaruga/tela_final.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) tabletopAnchorPane).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showBots() {
        for (int i = 0; i < map.getX(); i++) {
            for (int j = 0; j < map.getY(); j++) {
                FlowPane gridCell = (FlowPane) gameGrid.lookup("#gridCell" + i + "" + j);
                gridCell.getChildren().clear();
            }
        }
        for (int i = 0; i < map.getX(); i++) {
            for (int j = 0; j < map.getY(); j++) {
                if (!map.getPositions().get(i).get(j).getObjects().isEmpty()) {
                    for (Object object : map.getPositions().get(i).get(j).getObjects()) {
                        if (object instanceof Bot actualBot) {
                            FlowPane gridCell = (FlowPane) gameGrid.lookup("#gridCell" + i + "" + j);
                            gridCell.getChildren().add(new Circle(20, Paint.valueOf(actualBot.getColor())));
                        }
                    }

                }
            }
        }
    }
}
