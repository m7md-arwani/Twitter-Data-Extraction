package org.arwani;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TweetSearchResultsController {
    //keeping results accessible for later usage.
    public static ArrayList<Tweet> twee;

    @FXML
    private Button csvButton;



    @FXML
    private Button GoBackButton;

    @FXML
    private TableView<Tweet> tweetTable;

    @FXML
    private Button searchingButton;

    @FXML
    void StartSearching(ActionEvent event)  {
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

        tweetTable.getColumns().addAll(columnAuthor_id, columnCreated_at, columnId, columnLang, columnLike,
                columnQuote, columnReply, columnRetweet, columnText);
        tweetTable.setVisible(true);
        ///time consuming process
        TweetSearch tweetSearch = new TweetSearch();
        twee = tweetSearch.search(QueryBuilderController.getSearchQuery());


        if (twee != null) {
            ObservableList<Tweet> tableData = FXCollections.observableArrayList(List.copyOf(twee));
            tweetTable.setItems(tableData);
        }
        columnAuthor_id.setCellValueFactory(new PropertyValueFactory<>("author_id"));
        columnCreated_at.setCellValueFactory(new PropertyValueFactory<>("created_at"));
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnLang.setCellValueFactory(new PropertyValueFactory<>("lang"));
        columnLike.setCellValueFactory(new PropertyValueFactory<>("like_count"));
        columnQuote.setCellValueFactory(new PropertyValueFactory<>("quote_count"));
        columnReply.setCellValueFactory(new PropertyValueFactory<>("reply_count"));
        columnRetweet.setCellValueFactory(new PropertyValueFactory<>("retweet_count"));
        columnText.setCellValueFactory(new PropertyValueFactory<>("text"));
        if(twee.size() >= 1) {
            csvButton.setDisable(false);
        }


    }
    //todo complete the extraction work
    @FXML
    void generateCsv(ActionEvent event) throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog((Stage) ((Node) event.getSource()).getScene().getWindow());

        if(selectedDirectory == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Path not found");
            alert.setHeaderText("A path is required");
            alert.setContentText("Please select a path");
            alert.show();
        }else{
            CsvWriter.writeCsv(selectedDirectory.getAbsolutePath());
        }

    }



    @FXML
    void switchToPreviousSence(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("queryBuilder.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}
