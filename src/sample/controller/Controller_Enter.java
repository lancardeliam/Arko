package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


import java.net.URL;
import java.util.ResourceBundle;

import static sample.controller.Controller.client;

public class Controller_Enter {
    @FXML
    private Label label2;
    @FXML
    private Button disconnectTogame;
    public static Label label = new Label();
    public Button connectToGame;
    public Pane paneForLabel;

    @FXML
    void initialize() {
//        client.connect();
//        client.handle();
//        label.setBackground();

        label.setTextFill(Color.WHITE);
        label.setLayoutX(480);
      label.setLayoutY(6);
        label.setStyle("-fx-background-color: black");
        paneForLabel.getChildren().add(label);

        client.send("хочу кол-во клиентов",4);
        disconnectTogame.setOnAction(event -> {
            client.send("",5);
            connectToGame.setDisable(false);
        });
        connectToGame.setOnAction(event ->{
            client.send("",3);

            connectToGame.setDisable(true);
        });
    }

    public static void setLabel(String message) {
        label.setText(message);
    }
}