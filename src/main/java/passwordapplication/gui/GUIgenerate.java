package passwordapplication.gui;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import passwordapplication.services.FileListParser;
import passwordapplication.services.PasswordGenerator;

/**
 * This class creates the window for generating passwords.
 *
 * @author antti
 */
@Component
public class GUIgenerate {

    @Autowired
    PasswordGenerator passwordgenerator;

    @Autowired
    FileListParser filelistparser;

    List<String> passwords = new ArrayList();

    /**
     * This method creates the view that can be called by the main GUI-class
     *
     * @param stage the application window
     * @return pane with window-contents
     */
    public Parent getView(Stage stage) {

        //generate view
        BorderPane pane = new BorderPane();
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(15, 15, 15, 15));
        Label headline = new Label("GENERATE PASSWORDS");
        Label guide = new Label("Select the settings and press 'Generate' to get passwords");
        Separator separator1 = new Separator();

        //generate user input tools
        Label amountLabel = new Label("Select the amount of passwords you want to generate:   ");
        ChoiceBox amountCB = new ChoiceBox(FXCollections.observableArrayList(1, 5, 10, 15, 20));
        amountCB.getSelectionModel().selectFirst();
        HBox amountBox = new HBox(amountLabel, amountCB);
        Label numberLabel = new Label("Select the number of words in a password:   ");
        ChoiceBox numberCB = new ChoiceBox(FXCollections.observableArrayList(2, 3, 4, 5));
        numberCB.getSelectionModel().selectFirst();
        HBox numberBox = new HBox(numberLabel, numberCB);
        Label dividerLabel = new Label("Add the dividers you want to use between "
                + "words in the passwords. You can use any printable ASCII characters. Add space between each.\n"
                + "The maximum lenght is three characters - strings longer than "
                + "this are discarded. If no acceptable strings are supplied, '-' will be used");
        TextField dividerString = new TextField();
        Button generate = new Button("Generate");

        //insert the tools to the view shown to user
        vbox.getChildren().addAll(headline, guide, separator1, amountBox, numberBox, dividerLabel, dividerString, generate);
        pane.setTop(vbox);

        //initialize variables for the button events
        Button save = new Button("Save to file");
        Text list = new Text();

        //Action, when generate-button is pressed
        generate.setOnAction((event) -> {
            Integer amount = 1;
            Integer wordnumber = 2;
            List<String> dividers = new ArrayList();
            amount = (Integer) amountCB.getValue();
            wordnumber = (Integer) numberCB.getValue();
            if (0 < dividerString.getText().trim().length()) {
                dividers = Arrays.asList(dividerString.getText().split("\\s+"));
            }

            try {
                passwords = passwordgenerator.getPasswords(amount, wordnumber, dividers);
            } catch (SQLException ex) {
                Logger.getLogger(GUIwordlists.class.getName()).log(Level.SEVERE, null, ex);
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Database error");
                info.setHeaderText(null);
                info.setContentText("There was an error while generating passwords.");

                info.showAndWait();
            }

            //if the list is empty, then there are an insufficient number of
            //words in the database. Tell this to user
            if (passwords.isEmpty()) {
                Alert nowords = new Alert(Alert.AlertType.INFORMATION);
                nowords.setTitle("Insufficient words");
                nowords.setHeaderText(null);
                nowords.setContentText("There are not enough words in the database"+
                        " to create the requested set of passwords.");
                nowords.showAndWait();
            }

            //Create pane for results
            ScrollPane output = new ScrollPane();
            output.setFitToHeight(true);
            output.setFitToWidth(true);
            output.setMaxHeight(500);
            output.setMinHeight(400);

            //Create result list and show it
            ListView<String> listview = new ListView();
            ObservableList<String> observablelist = FXCollections.observableList(passwords);
            listview.setItems(observablelist);
            output.setContent(new Label("Generated passwords"));
            output.setContent(listview);
            pane.setCenter(output);

            //create button for saving list
            VBox bottom = new VBox();
            bottom.setPadding(new Insets(15, 15, 15, 15));
            bottom.getChildren().addAll(save);
            pane.setBottom(bottom);

        });

        //Action when save-button is pressed
        save.setOnAction((event) -> {
            FileChooser fileChooser = new FileChooser();

            //set and show save file dialog
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showSaveDialog(stage);

            try {
                //call FileListParser to save file
                filelistparser.saveListToFile(file, passwords);
            } catch (IOException ex) {
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Error");
                info.setHeaderText(null);
                info.setContentText("There was an error while saving passwords to file.");
            }

        });

        return pane;
    }

}
