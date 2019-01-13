package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("fxml/in.fxml"));
        stage = primaryStage;
        primaryStage.setTitle("Quest");
        primaryStage.setScene(new Scene(root, 860, 642));
        primaryStage.show();
    }
    public static void create(String fxml) throws IOException {
        Platform.runLater(() -> {
            stage.setResizable(false);
            Parent root = null;
            try {
                root = FXMLLoader.load(Main.class.getResource(fxml));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setTitle("Quest");
            stage.setScene(new Scene(root, 860, 642));
            stage.show();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
