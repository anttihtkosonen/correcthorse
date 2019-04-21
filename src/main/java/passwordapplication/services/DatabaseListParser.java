package passwordapplication.services;

import passwordapplication.dao.*;
import passwordapplication.models.Wordlist;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class for list operations: adding list to and removing lists from database.
 * The methods here are used for parsing data for the use of DAOs.
 *
 * @author antti
 */
@Component
public class DatabaseListParser {

    @Autowired
    WhitewordDAO whiteworddao;

    @Autowired
    WordlistDAO wordlistdao;

    @Autowired
    BlackwordDAO blackworddao;

    @Autowired
    FileListParser filelistparser;

    /**
     * Method to add a list to the database from a text-file in the filesystem
     *
     * @param name - name for the list (added to the Wordlist-table)
     * @param location - location of the textfile in the filesystem
     * @param timestamp - when the list was added to database
     * @param blacklist - the information of whether this is a blacklist
     * (true/false)
     * @throws FileNotFoundException if the textfile is not found
     * @throws IOException
     * @throws java.sql.SQLException
     */
    public void addListFromFile(String name, String location, Timestamp timestamp, Boolean blacklist) throws FileNotFoundException, IOException, SQLException {

        String words = filelistparser.getListFromFile(location);
        this.addList(name, words, timestamp, blacklist);
    }

    /**
     * Method to add a list to the database from a list of words
     *
     * @param name - name for the list (added to the Wordlist-table)
     * @param words - list of words, each on its own line
     * @param timestamp - when the list was added to database
     * @param blacklist - the information of whether this is a blacklist
     * (true/false)
     * @throws IOException
     * @throws java.sql.SQLException
     */
    public void addList(String name, String words, Timestamp timestamp, Boolean blacklist) throws IOException, SQLException {
        //Add list information to database (to the wordlist-table)
        Wordlist wordlist = new Wordlist(name, timestamp, blacklist);
        int list_id = 0;

        list_id = wordlistdao.insert(wordlist);

        //add words to database
        BufferedReader br = new BufferedReader(new StringReader(words));
        String line;
        //go through each line in list
        while ((line = br.readLine()) != null) {
            this.parseLine(line, blacklist, list_id);
        }

    }

    /**
     * Method to add a word to the database. The method first checks if the word
     * is acceptable to be added, and if it is, it is added to one of the two
     * database tables for words. Only Ascii letters are accepted in a word - if
     * a character that is not an ascii letter is present in a word, the word is
     * rejected completely. Also words longer than 30 characters are rejected.
     *
     * @param word the word to be added
     * @param blacklist shows if the letter is to be added to the blacklist
     * table (true), or the whiteword table (false)
     * @param list_id the id of the wordlist this word belongs to
     * @throws SQLException
     */
    public void parseLine(String word, Boolean blacklist, Integer list_id) throws SQLException {
        Boolean acceptable = true;
        //ignore line, if it is empty, if it is over 30 characters long or 
        //it has other characters besides letters:
        if (word.length() == 0 || word.length() > 30) {
            acceptable = false;
        } else {
            char[] chars = word.toCharArray();
            for (char c : chars) {
                if (!isAsciiLetter(c)) {
                    acceptable = false;
                    break;
                }
            }
        }
        if (acceptable) {
            //In case the word is a blacklist word, do the following
            if (blacklist) {

                //insert word into database
                blackworddao.insert(word, list_id);

                //after adding, change all instances of the word inactive in the whitelist
                whiteworddao.setInactive(word);

            } //In case the word is a whitelist word, to the following
            else {

                Boolean status;
                //check if the word is on the blacklist, and set status active or inactive accordingly
                if (blackworddao.find(word)) {
                    status = false;
                } else {
                    status = true;
                }
                //insert word into database
                whiteworddao.insert(word, status, list_id);

            }
        }
    }

    /**
     * Method to check whether a character is an ascii letter. Only large
     * letters (ascii decimals 65-90) and small letters (ascii decimals 97-122)
     * return a true value.
     *
     * @param c the character to assess
     */
    private static Boolean isAsciiLetter(char c) {
        //Large ascii letters are accepted
        if (c >= 65 && c <= 90) {
            return true;
        } //Small ascii letters are accepted
        else if (c >= 97 && c <= 122) {
            return true;
        } //Everything else is rejected
        else {
            return false;
        }
    }

    /**
     * Method to remove a list from the database
     *
     * @param id - id-number (table primary key) of the list to be removed
     * @throws SQLException
     */
    public void removeList(Integer id) throws SQLException {
        //get the blacklist-status of the wordlist
        Boolean blacklist = wordlistdao.readBlacklistStatus(id);

        // If the wordlist is a blacklist, we need to a check for each word if it belongs to another blacklist
        // If there is no other instance, we need to go through the whitelist, and set all instances of this word active.  
        if (blacklist) {
            List<String> idlist = blackworddao.listWords(id);
            for (int i = 0; i < idlist.size(); i++) {
                Integer count = blackworddao.count(idlist.get(i));
                if (count == 1) {
                    //If only one instance is found, then removing it will turn 
                    //all the matching words in the whiteword-table active.
                    whiteworddao.setActive(idlist.get(i));
                }
            }
            //After the check has been done for all words, remove the words from the table
            blackworddao.deleteListWords(id);
        } else {
            // For a whitelist, we can just delete the words
            whiteworddao.deleteListWords(id);
        }
        //finally we just remove the wordlist from the wordlist-table
        wordlistdao.deleteList(id);
    }

}
