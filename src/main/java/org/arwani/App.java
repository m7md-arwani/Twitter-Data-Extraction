package org.arwani;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * JavaFX App
 */
public class App extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("queryBuilder.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Twitter Data Extraction");
        stage.setScene(scene);
        stage.show();





    }







    public static void main(String[] args) {
        launch();


    }

}