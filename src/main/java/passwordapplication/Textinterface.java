package passwordapplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Textinterface {
    
    @Autowired
    InitializeDB initializeDB;

    public void start(Scanner input) {
        while (true) {
            System.out.println("Commands: ");
            System.out.println(" x - exit");
            System.out.println(" g - generate passwords");
            System.out.println(" a - add wordlist");
            System.out.println(" s - show wordlists");
            System.out.println(" r - remove wordlists");
            System.out.println(" d - delete database tables and create new ones");
            System.out.println("");

            String command = input.nextLine();
            if (command.equals("x")) {
                break;
            }

            if (command.equals("g")) {
                System.out.println("Not implemented yet");
            } 
            
            else if (command.equals("a")) {
                System.out.println("The words in the list need to be each on it's own line");
                System.out.println("Please give the location of the wordlist file");
                String location = input.nextLine();
                System.out.println("Please give the list a name");
                String name = input.nextLine();
                Boolean blacklist = null;
                while (blacklist = null){
                    System.out.println("Is this a blacklist? [y/n]");
                    String activestring = input.nextLine();
                        if (activestring.toLowerCase().equals("y") ||
                            activestring.toLowerCase().equals("yes")){
                            blacklist = true;
                        }
                        else if (activestring.toLowerCase().equals("n") ||
                            activestring.toLowerCase().equals("no")){
                            blacklist = true;
                        }
                        else {
                            System.out.println("Input not recognized as [y/n]");
                        }
                }
                    listParser.addList(name, location, blacklist);
                    

            } 
            
            else if (command.equals("s")) {
                System.out.println("Not implemented yet");
            } 
            
            else if (command.equals("r")) {
                System.out.println("Not implemented yet");
            } 
            
            else if (command.equals("d")) {
                System.out.println("All data in database will be lost.\nAre you sure, that you want to do that? [y/N]");
                String confirmation = input.nextLine();
                    if (confirmation.toLowerCase().equals("y") ||
                        confirmation.toLowerCase().equals("yes")) {
                        initializeDB.initialize();
                }
                
            }
        }
    }

}
