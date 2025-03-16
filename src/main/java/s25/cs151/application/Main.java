package s25.cs151.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Office Hours Manager");

        // Load the Home Page on startup
        setRoot("HomePage.fxml");

        primaryStage.show();
    }

    /**
     * Switching the scene to a new FXML file.
     * @param fxml the FXML file name
     */
    public static void setRoot(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/" + fxml));
            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
