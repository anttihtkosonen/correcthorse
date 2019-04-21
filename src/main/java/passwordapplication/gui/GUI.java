package passwordapplication.gui;

import static java.lang.Boolean.TRUE;
import java.sql.Connection;
import java.sql.DriverManager;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import passwordapplication.services.InitializeDB;

/**
 * The main class of the GUI program. 
 *
 * @author antti
 */
@SpringBootApplication
@ComponentScan({"passwordapplication.services"})
@ComponentScan({"passwordapplication.domain"})
@ComponentScan({"passwordapplication.dao"})
@ComponentScan({"passwordapplication.models"})
@ComponentScan({"passwordapplication.gui"})
@Component
public class GUI extends FxApplicationSupport {

    @Autowired
    GUIgenerate generate;

    @Autowired
    GUIwordlists wordlists;

    @Autowired
    GUIaddlist addlist;

    @Autowired
    GUIinit initialize;
    
     @Autowired
    GUIhelp help;   

    /**
     * This method starts the main window of the program. The menu is created,
     * and the first contents of the window are loaded
     *
     * @param stage the main window of the application
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {

        //Check if the database exists, and create it if not.
        String database = "jdbc:h2:.//database/passwordDB";
        try {
            Connection c = DriverManager.getConnection(database+";IFEXISTS=TRUE", "sa", "");
        } catch(final Exception e){
            System.out.println("Initializing database");
            InitializeDB.initialize(database);
        }
        
        
        System.out.println("GUI start");
        if (null == generate || null == wordlists) {
            throw new IllegalStateException("Services were not injected properly");
        }
        stage.setTitle("Password generator");
        HBox menu = new HBox();
        menu.setPadding(new Insets(15, 15, 15, 15));
        menu.setSpacing(10);
        Button generateButton = new Button("Generate password");
        Button wordlistsButton = new Button("Wordlists");
        Button addlistButton = new Button("Add wordlist");
        Button initButton = new Button("Initialize database");
        Button helpButton = new Button("Help");
        Button exitButton = new Button("Exit program");
        menu.getChildren().addAll(generateButton, wordlistsButton, addlistButton, initButton, helpButton, exitButton);
        BorderPane pane = new BorderPane();
        pane.setTop(menu);
        generateButton.setOnAction((event) -> pane.setCenter(generate.getView(stage)));
        wordlistsButton.setOnAction((event) -> pane.setCenter(wordlists.getView()));
        addlistButton.setOnAction((event) -> pane.setCenter(addlist.getView(stage)));
        initButton.setOnAction((event) -> pane.setCenter(initialize.getView()));
        helpButton.setOnAction((event) -> pane.setCenter(help.getView()));
        exitButton.setOnAction((event) -> stage.close());
        pane.setCenter(generate.getView(stage));
        Scene selectAction = new Scene(pane, 1000, 800);
        stage.setScene(selectAction);
        stage.show();
    }
}
