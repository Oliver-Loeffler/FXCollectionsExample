package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Products extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = createView();
        primaryStage.setTitle("Products: Sortable and Filterable ListView");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    private static Parent createView() throws IOException  {
        FXMLLoader loader = new FXMLLoader(ProductsController.class.getResource("ProductsView.fxml"));
        ProductsController controller = new ProductsController();
        loader.setController(controller);
        return loader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
