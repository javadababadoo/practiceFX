package co.com.nebulae.course;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static javafx.application.Application.launch;


public class MainApp extends Application {
    
    public static Stage MAIN_STAGE;

    @Override
    public void start(Stage stage) throws Exception {
        MAIN_STAGE = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainScene.fxml"));
        
        Scene scene = new Scene(root);        
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("3D - Test");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
