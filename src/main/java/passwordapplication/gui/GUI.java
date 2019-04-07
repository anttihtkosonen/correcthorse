package passwordapplication.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GUI {

    @Autowired
    GUIgenerate generate;

    @Autowired
    GUIwordlists wordlists;

    @Autowired
    GUIaddlist addlist;

    public void run(Stage stage) {
        stage.setTitle("Password generator");

        GUIgenerate generate = new GUIgenerate();
        GUIwordlists wordlists = new GUIwordlists();
        GUIaddlist addlist = new GUIaddlist();
        GUIinit initialize = new GUIinit();

        HBox menu = new HBox();
        menu.setPadding(new Insets(15, 15, 15, 15));
        menu.setSpacing(10);

        Button generateButton = new Button("Generate password");
        Button wordlistsButton = new Button("Wordlists");
        Button addlistButton = new Button("Add wordlist");
        Button initButton = new Button("Delete database");

        menu.getChildren().addAll(generateButton, wordlistsButton, initButton);

        BorderPane pane = new BorderPane();
        pane.setTop(menu);

        generateButton.setOnAction((event) -> pane.setCenter(generate.getView()));
        wordlistsButton.setOnAction((event) -> pane.setCenter(wordlists.getView()));
        //addButton.setOnAction((event) -> pane.setCenter(add.getView()));
        //deleteButton.setOnAction((event) -> pane.setCenter(delete.getView()));
        //initButton.setOnAction((event) -> pane.setCenter(initialize.getView()));
        pane.setCenter(generate.getView());

        Scene selectAction = new Scene(pane, 1000, 800);

        stage.setScene(selectAction);

        stage.show();
    }

}
