package passwordapplication.gui;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import passwordapplication.services.DatabaseListParser;
import passwordapplication.services.FileListParser;

/**
 * This class creates the window for adding wordlists to database.
 *
 * @author antti
 */
@Component
public class GUIaddlist {

    @Autowired
    DatabaseListParser listparser;

    @Autowired
    FileListParser filelistparser;

    /**
     * This method creates the view that can be called by the main GUI-class
     *
     * @param stage the window of the application
     * @return pane with window-contents
     */
    public Parent getView(Stage stage) {
        //Create window
        BorderPane pane = new BorderPane();
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(15, 15, 15, 15));
        Label headline = new Label("ADD WORDLIST TO DATABASE");

        //Input field for list name
        Label nameLabel = new Label("Please give the list a name   ");
        TextField nameField = new TextField();
        HBox nameBox = new HBox(nameLabel, nameField);

        //Choicebox for blacklist-info
        Label blacklistLabel = new Label("Is this a blacklist? (blacklist words are not used in passwords)   ");
        ChoiceBox blacklistCB = new ChoiceBox(FXCollections.observableArrayList("no", "yes"));
        blacklistCB.getSelectionModel().selectFirst();
        HBox blacklistBox = new HBox(blacklistLabel, blacklistCB);

        //Textfield for words
        Label wordsLabel = new Label("Add the words in the field below, each on its own line, with no separators.");
        TextArea wordsArea = new TextArea("");
        wordsArea.setPrefHeight(500);

        //Button for loading from file
        Button loadButton = new Button("Load list from file");

        //Button for adding
        VBox bottombox = new VBox();
        bottombox.setPadding(new Insets(20, 20, 20, 20));
        Button addButton = new Button("Add list");

        //populate boxes and add to pane
        vbox.getChildren().addAll(headline, nameBox, blacklistBox, wordsLabel, loadButton, wordsArea);
        bottombox.getChildren().addAll(addButton);
        pane.setTop(vbox);
        pane.setBottom(bottombox);

        //Load list button action
        loadButton.setOnAction((event) -> {
            FileChooser fileChooser = new FileChooser();

            //set and show load file dialog
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showOpenDialog(stage);
            String words = "";

            try {
                words = filelistparser.getListFromFile(file.getCanonicalPath());
            } catch (IOException | UncheckedIOException ex) {
                Alert IOerror = new Alert(Alert.AlertType.INFORMATION);
                IOerror.setTitle("Error");
                IOerror.setHeaderText(null);
                IOerror.setContentText("There was an error while loading from file.\n"
                        + "Please check that the file you are \n"
                        + "trying to load is in plaintext form.");
                IOerror.showAndWait();
            }
            wordsArea.setText(words);
        });

        //Add list button action
        addButton.setOnAction((event) -> {

            //Get the user-inputted info into variables
            String name = nameField.getText();
            //if the name is too long, alert the user and do nothing
            if (name.length() > 50) {
                Alert lengthalert = new Alert(AlertType.INFORMATION);
                lengthalert.setTitle("Name too long");
                lengthalert.setHeaderText(null);
                lengthalert.setContentText("The maximum lenght of the name is 50 characters.");
                lengthalert.showAndWait();
                return;
            }

            //If the name is empty, set it as "[Unnamed list]"
            if (name.length() == 0) {
                name = "[Unnamed list]";
            }

            String words = wordsArea.getText();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String blacklistString = (String) blacklistCB.getValue();
            Boolean blacklist = false;
            if (blacklistString == "yes") {
                blacklist = true;
            }

            //Add the list
            try {
                listparser.addList(name, words, timestamp, blacklist);
            } catch (IOException | SQLException ex) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Database error");
                alert.setHeaderText(null);
                alert.setContentText("There was an error while adding the list to the database.");
                alert.showAndWait();
            }
            Alert success = new Alert(AlertType.INFORMATION);
            success.setTitle("Information Dialog");
            success.setHeaderText(null);
            success.setContentText("Wordlist added successfully.");
            success.showAndWait();

            nameField.clear();
            wordsArea.clear();
        });

        return pane;
    }

}
