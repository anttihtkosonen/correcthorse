package passwordapplication.gui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import passwordapplication.services.DatabaseListParser;
import passwordapplication.services.ShowList;

/**
 * This class creates the window that allows looking at and deleting wordlists.
 * @author antti
 */
@Component
public class GUIwordlists {

    @Autowired
    ShowList showlist;

    @Autowired
    DatabaseListParser listparser;

    List<String> wordlistlist = new ArrayList();
    
    /**
     * This method creates the view that can be called by the main GUI-class
     * @return pane with window-contents
     */
    public Parent getView() {
        BorderPane pane = new BorderPane();

        HBox deletemenu = new HBox();
        deletemenu.setPadding(new Insets(15, 15, 15, 15));
        deletemenu.setSpacing(15);
        Button deleteButton = new Button("Delete selected");
        Label guide = new Label("To delete a wordlist, select it and press the button.\nThis cannot be undone.");
        deletemenu.getChildren().addAll(deleteButton, guide);

        try {
            wordlistlist = showlist.showAll();
        } catch (SQLException ex) {
            Logger.getLogger(GUIwordlists.class.getName()).log(Level.SEVERE, null, ex);
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Database error");
            info.setHeaderText(null);
            info.setContentText("There was an error while retrieving wordlists from database.");

            info.showAndWait();
        }

        ScrollPane sp = new ScrollPane();
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);

        ListView<String> listView = new ListView();
        ObservableList<String> observablelist = FXCollections.observableList(wordlistlist);
        listView.setItems(observablelist);

        sp.setContent(new Label("Wordlists in database"));
        sp.setContent(listView);

        pane.setTop(deletemenu);
        pane.setCenter(sp);

        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Get the list id number of the list
                int index = listView.getSelectionModel().getSelectedIndex();

                //Get the database id number of the list from the string
                String item = listView.getSelectionModel().getSelectedItem();
                item = item.substring(item.lastIndexOf("Id number of list: ") + 19);
                item = item.substring(0, item.indexOf("\nThis list"));
                int id = Integer.parseInt(item);

                //Delete the list from the database and get the updated list
                try {
                    listparser.removeList(id);
                } catch (SQLException ex) {
                     Logger.getLogger(GUIwordlists.class.getName()).log(Level.SEVERE, null, ex);
                    Alert info = new Alert(Alert.AlertType.INFORMATION);
                    info.setTitle("Database error");
                    info.setHeaderText(null);
                    info.setContentText("There was an error while deleting the list.");
                    info.showAndWait();
                } catch (StringIndexOutOfBoundsException ex) {
                    
                }

                //Refresh view
                wordlistlist.remove(index);
                listView.refresh();
            }
        });

        return pane;
    }

}
