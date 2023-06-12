import gui.Gui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import windowing.Windowing;

import java.util.Objects;
/**
 * The TestApp class represents the main class of the application. It is responsible for initializing the GUI and
 * Windowing classes.
 */
public class TestApp extends Application {

    /**
     * The main method of the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
    /**
     * Initializes the GUI and Windowing classes.
     *
     * @param primaryStage the primary stage of the application
     * @throws Exception if an error occurs
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("java-fx/windowing.fxml")));
        Parent root = loader.load();
        Gui gui = loader.getController();
        Windowing.init(gui);
        gui.init(primaryStage, root);
    }
}
