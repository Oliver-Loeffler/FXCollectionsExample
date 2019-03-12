package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = createView();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    private static Parent createView() throws IOException  {
        FXMLLoader loader = new FXMLLoader(Controller.class.getResource("sample.fxml"));
        Controller controller = new Controller();
        loader.setController(controller);
        return loader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
