/*
 * To change this license vboxer, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordapplication.gui;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import passwordapplication.services.PasswordGenerator;

/**
 *
 * @author antti
 */
@Component
public class GUIgenerate {
    
    @Autowired
    PasswordGenerator passwordgenerator;

    public GUIgenerate() {

    }

    public Parent getView() {
        
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
        Label dividerLabel = new Label("Add the dividers you want to use between words in the passwords. Add space between each.");
        TextField dividerString = new TextField();
        
        
        Button generate = new Button("Generate");

        vbox.getChildren().addAll(headline, guide, separator1, amountBox, numberBox, dividerLabel, dividerString, generate);

        pane.setTop(vbox);

        generate.setOnAction((event) -> {
            Integer amount = 1;
            Integer wordnumber = 2;
            List<String> dividers = new ArrayList();

            amount = (Integer) amountCB.getValue();
            wordnumber = (Integer) numberCB.getValue();
            dividers = Arrays.asList(dividerString.getText().split("\\s+"));
            
            List<String> passwords = new ArrayList();
            try {
                passwords = passwordgenerator.getPasswords(amount, wordnumber, dividers);
            } catch (SQLException ex) {
                System.out.println("There was an error while generating passwords");
            }
            
            ScrollPane output = new ScrollPane();
            output.setFitToHeight(true);
            output.setFitToWidth(true);
            output.setMaxHeight(500);
            output.setMinHeight(500);
            
            ListView<String> listview = new ListView();
            ObservableList<String> observablelist = FXCollections.observableList(passwords);
            listview.setItems(observablelist);

            output.setContent(new Label("Generated passwords"));
            output.setContent(listview);
            pane.setBottom(output);
        });

        return pane;
    }
}