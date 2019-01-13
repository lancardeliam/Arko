package sample.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
//import sample.Database.DatabaseHandler;
//import sample.Database.User;
import sample.Database.*;
import sample.Main;
import static sample.controller.Controller.client;
public class Controller_Reg {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField logUsername;

    @FXML
    private TextField logEmail;

    @FXML
    private TextField logPassword;

    @FXML
    private Button singUpButton;

    public static TextField textField;

    @FXML
    void initialize() {
        singUpButton.setOnAction(event -> {
            singUpNewUser();
        });
    }

    private void singUpNewUser() {
        String userName = logUsername.getText();
        String password = logPassword.getText();
        String email = logEmail.getText();
        User user = new User(userName,password,email);
        client.send(user,1);
            }
}
