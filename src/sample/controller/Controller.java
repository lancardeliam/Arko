package sample.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.Database.User;
import sample.Main;
import sample.Server_Client.Client;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField connectPassword;

    @FXML
    private TextField connectUsername;

    @FXML
    private Button connectButton;

    @FXML
    private Button regButton;

    public static Client client = new Client();

    @FXML
    void initialize() {

        client.connect();
        client.handle();
        connectButton.setOnAction(event -> {
            enterUser();

        });
        regButton.setOnAction(event -> {
            try {
                Main.create("fxml/sample.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

    private void enterUser() {
        String login = connectUsername.getText();
        String password = connectPassword.getText();
        User user = new User(login, password);
        client.send(user, 2);
    }
}
