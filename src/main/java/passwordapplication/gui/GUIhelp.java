package passwordapplication.gui;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

/**
 * This class creates the help window.
 * @author antti
 */
@Component
public class GUIhelp {

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
        Label guide = new Label("This is a program to generate passwords that are both secure and easy to remember.\n"
                + "You can load lists of words to the program and use them to generate passwords.\n\n"
                + "You need to first add some wordlists to the database, and after that you can use the words to generate passwords.\n"
                + "There are two kinds of wordlists: whitelists and blacklists. Whitelists include words that are used to generate passwords,\n"
                + "and blacklists include words that are banned from them. Only words that are on a whitelist and are not on any blacklist\n"
                + "are used to generate passwords.\n\n"
                + "In it’s current state of development the application only accepts words made of letters. Words that include numbers,\n"
                + "special characters or any characters outside the ASCII letter range (65-90 and 97-122) are not added to the database,\n"
                + "but rejected altogether. Also, words longer than 30 characters are rejected.\n\n"
                + "To add a wordlist, do the following:\n"
                + "1. Go to the “Add wordlist” tab in the application. \n"
                + "2. Give your wordlist a name (max. 50 characters) in the small text field at the top\n"
                + "3. Select whether it is a blacklist or not, at the dropdown menu\n"
                + "4. Paste the words in the text area below. Each word should be on its own line – if you include words delimited by spaces, tabs,\n"
                + "    semicolons, commas or other characters, they will be rejected. Alternatively you can use the “Load list from file” -button to\n"
                + "    load a list from a .txt -file to the field.\n"
                + "5. Press “Add list”\n\n"
                + "To generate passwords, do the following:\n"
                + "1. Go to the “Generate password” tab in the application\n"
                + "2. Use the dropdown menus to select how many passwords you want to generate, and how many words should be in a password\n"
                + "3. In the text field add the characters that you want to use in the password between words. These will be used randomly.\n"
                + "4. Press “Generate”\n"
                + "5. After the passwords have been generated, you can save them to a file by pressing “Save to file” at the bottom of the window\n\n"
                + "The application also calculates the entropy of each password. The number represents the theoretical minimum number of bits\n"
                + "required to store the password in memory – it is the Shannon-entropy of the characters rounded up and multiplied by the number\n"
                + "of characters. As a rule of thumb a larger number is the better. It should be noted however, that a large number of bits itself\n"
                + "does not ensure that the password is strong – the words used should also be uncommon, otherwise the password is vulnerable to\n"
                + "dictionary-based attacks where an attacker uses lists of common words to brute-force the password. Use of rare or specialized\n"
                + "words is strongly recommended."
        );
        vbox.getChildren().addAll(guide);
        pane.setTop(vbox);

        return pane;
    }
}
