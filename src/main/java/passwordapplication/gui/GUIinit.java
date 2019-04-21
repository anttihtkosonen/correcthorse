package passwordapplication.gui;

import java.util.Optional;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import passwordapplication.services.InitializeDB;

/**
 * This class creates the window for initializing the database.
 *
 * @author antti
 */
@Component
public class GUIinit {

    @Autowired
    InitializeDB initializeDB;

    /**
     * This method creates the view that can be called by the main GUI-class
     *
     * @return pane with window-contents
     */
    public Parent getView() {

        //generate view
        BorderPane pane = new BorderPane();
        VBox vbox = new VBox();
        vbox.setSpacing(30);
        vbox.setPadding(new Insets(30, 30, 30, 30));
        Label headline = new Label("INITIALIZE DATABASE");
        Label guide = new Label("Press the button below, if you wish to initialize the database.\n"
                + "All tables in the database will be dropped and they will be created again.\n"
                + "All data in an an existing database will be lost. This cannot be undone.");
        Button initialize = new Button("Initialize database");
        vbox.getChildren().addAll(headline, guide, initialize);
        pane.setTop(vbox);

        //Action, when generate-button is pressed
        initialize.setOnAction((event) -> {
            String database = "jdbc:h2:.//database/passwordDB";

            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("All data will be lost");
            alert.setContentText("Do you want to proceed?");
            ButtonType buttonTypeOK = new ButtonType("OK");
            ButtonType buttonTypeCancel = new ButtonType("Cancel");
            alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOK) {

                InitializeDB.initialize(database);
                
                Alert info = new Alert(AlertType.INFORMATION);
                info.setTitle("Information Dialog");
                info.setHeaderText(null);
                info.setContentText("Database initialized successfully.");

                info.showAndWait();
            } else {
                return;
            }
        });

        return pane;
    }
}
