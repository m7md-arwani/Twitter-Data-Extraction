package org.arwani;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TweetSearchResultsController {
    private double xOffset = 0;
    private double yOffset = 0;
    //keeping results accessible for later usage.
    public static ArrayList<Tweet> twee;


    @FXML
    private Button csvButton;

    @FXML
    private Rectangle animationRec;

    @FXML
    private Text loadingText;



    @FXML
    private Button closeButton;


    @FXML
    private Button GoBackButton;

    @FXML
    private TableView<Tweet> tweetTable;

    @FXML
    private Button searchingButton;

    @FXML
    void StartSearching(ActionEvent event) {
        searchingButton.setDisable(true);
        searchingButton.setVisible(false);


        TableColumn columnAuthor_id = new TableColumn("Author ID");
        TableColumn columnCreated_at = new TableColumn("Created At");
        TableColumn columnId = new TableColumn("Tweet ID");
        TableColumn columnLang = new TableColumn("Language");
        TableColumn columnLike = new TableColumn("Likes");
        TableColumn columnQuote = new TableColumn("Quotes");
        TableColumn columnReply = new TableColumn("Replies");
        TableColumn columnRetweet = new TableColumn("Retweets");
        TableColumn columnText = new TableColumn("Text");

        columnText.setPrefWidth(300);
        columnAuthor_id.setPrefWidth(133);
        columnCreated_at.setPrefWidth(133);
        columnId.setPrefWidth(133);
        columnLang.setPrefWidth(50);
        columnLike.setPrefWidth(50);
        columnQuote.setPrefWidth(50);
        columnReply.setPrefWidth(50);
        columnRetweet.setPrefWidth(50);

        tweetTable.setStyle("-fx-background-color:  #90E0EF");
        columnText.setStyle(" -fx-alignment: CENTER; -fx-background-color:  #90E0EF;");
        columnLang.setStyle(" -fx-alignment: CENTER; -fx-background-color:  #90E0EF");
        columnAuthor_id.setStyle(" -fx-alignment: CENTER; -fx-background-color:  #90E0EF");
        columnCreated_at.setStyle(" -fx-alignment: CENTER; -fx-background-color:  #90E0EF");
        columnLike.setStyle(" -fx-alignment: CENTER; -fx-background-color:  #90E0EF");
        columnQuote.setStyle(" -fx-alignment: CENTER; -fx-background-color:  #90E0EF");
        columnReply.setStyle(" -fx-alignment: CENTER; -fx-background-color:  #90E0EF");
        columnId.setStyle(" -fx-alignment: CENTER; -fx-background-color:  #90E0EF");
        columnRetweet.setStyle(" -fx-alignment: CENTER; -fx-background-color:  #90E0EF");



        tweetTable.getColumns().addAll(columnAuthor_id, columnCreated_at, columnId, columnLang, columnLike,
                columnQuote, columnReply, columnRetweet, columnText);

        columnAuthor_id.setCellValueFactory(new PropertyValueFactory<>("author_id"));
        columnCreated_at.setCellValueFactory(new PropertyValueFactory<>("created_at"));
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnLang.setCellValueFactory(new PropertyValueFactory<>("lang"));
        columnLike.setCellValueFactory(new PropertyValueFactory<>("like_count"));
        columnQuote.setCellValueFactory(new PropertyValueFactory<>("quote_count"));
        columnReply.setCellValueFactory(new PropertyValueFactory<>("reply_count"));
        columnRetweet.setCellValueFactory(new PropertyValueFactory<>("retweet_count"));
        columnText.setCellValueFactory(new PropertyValueFactory<>("text"));



        Runnable animateThread = () ->{
           animation();
        };
        Thread animate = new Thread(animateThread);
        animate.start();

        SearchTask searchTask = new SearchTask();
        Thread thread = new Thread(searchTask);
        thread.setDaemon(true);
        thread.start();
        searchTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                twee = searchTask.getValue();
                if (twee != null) {
                    ObservableList<Tweet> tableData = FXCollections.observableArrayList(List.copyOf(twee));
                    tweetTable.setItems(tableData);


                }
                tweetTable.setVisible(true);
                if (twee.size() >= 1) {
                    csvButton.setDisable(false);
                }
                animate.interrupt();
                loadingText.setDisable(true);
                loadingText.setVisible(false);
                animationRec.setDisable(true);
                animationRec.setVisible(false);

            }


        });






    }


    @FXML
    void generateCsv(ActionEvent event) throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog((Stage) ((Node) event.getSource()).getScene().getWindow());

        if (selectedDirectory == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Path not found");
            alert.setHeaderText("A path is required");
            alert.setContentText("Please select a path");
            alert.show();
        } else {
            CsvWriter.writeCsv(selectedDirectory.getAbsolutePath());
        }

    }


    @FXML
    void switchToPreviousSence(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("queryBuilder.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        root.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
        stage.show();

    }

    @FXML
    void closeStage(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();

    }

    public void  animation() {
        loadingText.setVisible(true);
        animationRec.setVisible(true);
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setDuration(Duration.seconds(1.5));
        rotateTransition.setNode(animationRec);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(150);
        rotateTransition.setAutoReverse(true);
        rotateTransition.play();

    }






}


