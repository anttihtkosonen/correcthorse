package passwordapplication.domain;

import passwordapplication.services.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;


/**
 * Class for generating the text-based user interface. The methods here are used
 * for receiving user commands and calling the corresponding classes of the
 * application.
 *
 * @author antti
 */
@Component
public class Textinterface {

    @Autowired
    InitializeDB initializeDB;

    @Autowired
    ListParser listparser;

    @Autowired
    ShowList showlist;

    @Autowired
    PasswordGenerator passwordgenerator;

    /**
     * Method that gets user input and calls the appropriate method accordingly
     * @param input Scanner-object for getting user input.
     */
    public void start(Scanner input) {
        while (true) {
            System.out.println("\n\n\nCommands: ");
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
                this.generate(input);
            } else if (command.equals("a")) {
                this.add(input);
            } else if (command.equals("s")) {
                this.show();
            } else if (command.equals("r")) {
                this.remove(input);
            } else if (command.equals("d")) {
                this.deleteDB(input);
            }
        }
    }

    /**
     * Method for adding a Wordlist
     * @param input Scanner-object for getting user input.
     */
    public void add(Scanner input) {
        System.out.println("Please note:\nThe words in the list need to be each on it's own line");
        System.out.println("Please give the list a name");
        String name = input.nextLine();
        Boolean blacklist = null;
        while (blacklist == null) {
            System.out.println("Is this a blacklist? [y/n]");
            String activestring = input.nextLine();
            if (activestring.toLowerCase().equals("y")
                    || activestring.toLowerCase().equals("yes")) {
                blacklist = true;
            } else if (activestring.toLowerCase().equals("n")
                    || activestring.toLowerCase().equals("no")) {
                blacklist = false;
            } else {
                System.out.println("Input not recognized as [y/n]\nPlease try again");
            }
        }

        String location = null;
        while (true) {
            System.out.println("Please give the location of the wordlist file");
            location = input.nextLine();
            File f = new File(location);
            if (f.exists() && !f.isDirectory()) {
                break;
            }
            System.out.println("\nFile not found!\n");
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            listparser.addList(name, location, timestamp, blacklist);
        } catch (IOException ex) {
            System.out.println("File not found.");
        } catch (SQLException ex) {
            System.out.println("There was a database error while adding the list.");
        }
        return;
    }

    /**
     * Method for generating passwords
     * @param input Scanner-object for getting user input.
     */
    public void generate(Scanner input) {
        System.out.println("How many passwords do you want to generate?");
        String amountString = input.nextLine();
        Integer amount = Integer.parseInt(amountString);
        System.out.println("How many words per password?");
        String wordnumberString = input.nextLine();
        Integer wordnumber = Integer.parseInt(wordnumberString);
        System.out.println("What characters do you want to use between words?");
        System.out.println("You can add as many as you like, press enter when done");
        List<String> dividers = new ArrayList();
        while (true) {
            String divider = input.nextLine();
            if (divider.isEmpty()){
                break;
            } else {
                dividers.add(divider);
            }
        }
        List<String> passwords = new ArrayList();
        try {
            passwords = passwordgenerator.getPasswords(amount, wordnumber, dividers);
        } catch (SQLException ex) {
            System.out.println("There was an error while generating passwords");
        }
        for (int i = 0; i < amount; i++) {
            System.out.println(passwords.get(i));
        }
        return;
    }

    /**
     * Method for showing the lists in the database
     */
    public void show() {
        try {
            showlist.showAll();
        } catch (SQLException ex) {
            Logger.getLogger(ListParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return;
    }

    /**
     * Method for removing lists from database
     * @param input Scanner-object for getting user input.
     */
    public void remove(Scanner input) {
        do {
            try {
                System.out.println("Please give the id number of the list to be removed");
                String idString = input.nextLine();
                int id = Integer.parseInt(idString);
                listparser.removeList(id);
                break;
            } catch (EmptyResultDataAccessException e) {
                System.out.println("List id not found. Please try again.");
            } catch (SQLException e) {
                System.out.println("There was an error while trying to execute command. Please try again.");
            }
        } while (true);
        System.out.println("List successfully removed.");
        return;
    }

    /**
     * Method for clearing the database
     * @param input Scanner-object for getting user input.
     */
    public void deleteDB(Scanner input) {
        String database = "jdbc:h2:.//database/passwordDB";
        System.out.println("All data in database will be lost.\nAre you sure, that you want to do that? [y/N]");
        String confirmation = input.nextLine();
        if (confirmation.toLowerCase().equals("y")
                || confirmation.toLowerCase().equals("yes")) {
            initializeDB.initialize(database);
        }
        return;
    }

}
