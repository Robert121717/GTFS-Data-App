/**
 * <one line to give the program's name and a brief idea of what it does.>
 *     Copyright (C) 2022  nairac, atkinsonr, morrowc, schmidtrj
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package GTFS;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
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

        stage.setTitle("GTFS Application");
        stage.setResizable(false);
        stage.show();
    }
}