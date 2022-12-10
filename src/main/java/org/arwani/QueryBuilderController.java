package org.arwani;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class QueryBuilderController {
    private double xOffset = 0;
    private double yOffset = 0;


    private static String queryText;
    @FXML
    private Text limitText;

    @FXML
    private Button closeButton;

    @FXML
    private Hyperlink linkToQueryBuilder;

    @FXML
    private Button nextButton;

    @FXML
    private TextField queryHolder;

    @FXML
    void checkLength(KeyEvent event) {
        if (queryHolder.getLength() <= 525) {
            limitText.setText("525/" + queryHolder.getLength());
            queryText = queryHolder.getText();
            nextButton.setDisable(queryHolder.getLength() <= 0);
        } else {
            nextButton.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Query Limit exceeded");
            alert.setHeaderText("Your query reached " + queryHolder.getLength() + "!");
            alert.setContentText("The length of your search query should not exceed 525 characters in total");
            alert.show();
        }

    }

    @FXML
    void clicked(ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://developer.twitter.com/apitools/query?query="));
    }

    @FXML
    void switchToNextScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("tweetSearchResults.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        //scene.getStylesheets().clear();
        //scene.getStylesheets().add("C:/Users/Arwani/Desktop/Fall Semester 2022-2023/Graduation Project- Serious stuff/javafx/Twitter Data Extraction/src/main/java/org/arwani/css/tableStyle.css");
        stage.setScene(scene);
        root.setOnMousePressed(event1 -> {
            xOffset = event1.getSceneX();
            yOffset = event1.getSceneY();
        });
        root.setOnMouseDragged(event12 -> {
            stage.setX(event12.getScreenX() - xOffset);
            stage.setY(event12.getScreenY() - yOffset);
        });
        stage.show();

    }

    @FXML
    void closeStage(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public static String getSearchQuery() {
        return queryText;
    }


}


