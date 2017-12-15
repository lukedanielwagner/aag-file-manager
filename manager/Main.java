package manager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    /*
    get hacked noob
     */

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent init = FXMLLoader.load(getClass().getResource("form.fxml"));
        primaryStage.setTitle("AAG Filer");
        primaryStage.setScene(new Scene(init, 400, 300));
        primaryStage.show();
        primaryStage.setResizable(false);

    }

    public static void main(String[] args) {
        launch(args);
    }

}
