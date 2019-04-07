package passwordapplication.domain;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import passwordapplication.gui.GUI;


/**
 * The main class of the application. Used for starting the program.
 *
 * @author antti
 */


@SpringBootApplication
@ComponentScan({"passwordapplication.services"})
@ComponentScan({"passwordapplication.domain"})
@ComponentScan({"passwordapplication.dao"})
@ComponentScan({"passwordapplication.models"})
@ComponentScan({"passwordapplication.gui"})
//public class Passwordapplication implements CommandLineRunner {
public class Passwordapplication extends Application {

    @Autowired
    GUI gui;
    
    public static void main(String[] args) {
        System.out.println("main");
        launch(Passwordapplication.class, args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        
        System.out.println("start");
        //Stage stage = new Stage();
        gui.run(stage);
    }
}
    
    
/*
    @Autowired
    Textinterface textinterface;

    public void run(String... args) throws Exception {
        System.out.println("run");
        Scanner input = new Scanner(System.in);
        textinterface.start(input);
    }
*/
