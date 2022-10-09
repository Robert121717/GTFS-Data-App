package GTFS;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author nairac
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
        stage.setTitle("GTFS Application");
        stage.setScene(scene);
//        TODO keep
//        stage.setMaximized(true);
//        stage.getIcons().add([insert image path here] to add icon to application
        //hi
        Controller controller = loader.getController();
        controller.setStage(stage);
        stage.show();
    }
}