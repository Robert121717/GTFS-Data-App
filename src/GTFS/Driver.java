package GTFS;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.Objects;

/**
 * @author nairac, schmidtrj
 * @version 1.0
 * @created 05-Oct-2022 8:14:31 PM
 */
public class Driver extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(
                getClass().getResource("GTFSController.fxml")));

        Parent root = loader.load();
        Scene scene = new Scene(root);

        scene.getStylesheets().add("Styles.css"); // TODO catch warning shown when not found
        try {
            stage.getIcons().add(new Image("stage icon.png"));

        } catch (IllegalArgumentException e) {
            System.out.println("Please download the stage icon image for it to be displayed.");
        }
        stage.setScene(scene);

        Controller controller = loader.getController();
        controller.setStage(stage);

        stage.setTitle("GTFS Application");
        stage.setResizable(false);
        stage.show();
    }
}