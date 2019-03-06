/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordapplication;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author antti
 */
@Component
public class ListParser {
    
    @Autowired
    WhitewordDao whiteworddao;
    
    @Autowired
    WordlistDao wordlistdao;
    
    @Autowired
    BlackwordDao blackworddao;    
    
    public void addList (String name, String location, Boolean blacklist) throws FileNotFoundException, IOException {
        //Add list information to database
        Wordlist wordlist = new Wordlist(name, blacklist);
        int list_id = 0;
        try {
            list_id = wordlistdao.insert(wordlist);
        } catch (SQLException ex) {
            Logger.getLogger(ListParser.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("There was an error while adding the list to the database");
            return;
        }
        //add words to database
        BufferedReader br = new BufferedReader(new FileReader(location));
            String line;
            //go through each line in list
            while ((line = br.readLine()) != null) {
                Boolean acceptable = true;
                //ignore line, if it has other characters besides letters:
                if (line.length() == 0){
                    acceptable = false;
                } else {
                    char[] chars = line.toCharArray();
                    for (char c : chars) {
                       if(!Character.isLetter(c)) {
                           acceptable = false;
                           break;
                       }
                    }                    
                }
               if (acceptable){
                    //In case the word is a blacklist word, do the following
                    if(blacklist){
                        try {
                            //insert word into database
                            blackworddao.insert(line, list_id);
                        } catch (SQLException ex) {
                            Logger.getLogger(ListParser.class.getName()).log(Level.SEVERE, null, ex);
                            System.out.println("There was an error while adding the words to the database");
                            return;
                        }
                        //after adding change all instances of the word inactive in whitelist
                        whiteworddao.setInactive(line);

                    }
                    //In case the word is a whitelist word, to the following
                    else {
                        Boolean status;
                        //check if the word is on the blacklist, and set status active or inactive accordingly
                        if (blackworddao.find(line)){
                            status = false;
                        }
                        else {
                            status = true;
                        }
                        try {
                            //insert word into database
                            whiteworddao.insert(line, status, list_id);
                        } catch (SQLException ex) {
                            Logger.getLogger(ListParser.class.getName()).log(Level.SEVERE, null, ex);
                            System.out.println("There was an error while adding the words to the database");
                            return;
                        }

                    }
                }
            }
            System.out.println("List successfully added.");
    }
    
    public void removeList (Integer id) throws SQLException {
        //get the wordlist-object
        Boolean blacklist = wordlistdao.read(id).getBlacklist();
        /*  If the wordlist is a blacklist, we need to a check for each word if it belongs to another blacklist
        *   If there is no other instance, we need to go through the whitelist, and set all instances of this word active.
        */  
        if(blacklist){
            List<String> idlist =blackworddao.listWords(id);
            for (int i = 0; i < idlist.size(); i++) {
                Integer count = blackworddao.count(idlist.get(i));
                if(count == 1){
                    //If only one instance is found, then removing it will turn 
                    //all the matching words in the whiteword-table active.
                    whiteworddao.setActive(idlist.get(i));
                }
            }
            //After the check has been done for all words, remove the words from the table
            blackworddao.deleteListWords(id);
        }else{
            // For a whitelist, we can just delete the words
            whiteworddao.deleteListWords(id);
        }
        //finally we just remove the wordlist from the wordlist-table
        wordlistdao.deleteList(id);
    }
    
}
