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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author antti
 */
public class listParser {
    
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
            Logger.getLogger(listParser.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("There was an error while adding the list to the database");
            return;
        }
        //add words to database
        BufferedReader br = new BufferedReader(new FileReader(location));
            String line;
            //go through each line in list
            while ((line = br.readLine()) != null) {
                //ignore line, if it has other characters besides letters:
                char[] chars = line.toCharArray();
                for (char c : chars) {
                   if(!Character.isLetter(c)) {
                       continue;
                   }
                }
               

                //In case the word is a blacklist word, do the following
                if(blacklist){
                    try {
                        //insert word into database
                        blackworddao.insert(line, list_id);
                    } catch (SQLException ex) {
                        Logger.getLogger(listParser.class.getName()).log(Level.SEVERE, null, ex);
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
                        Logger.getLogger(listParser.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("There was an error while adding the words to the database");
                        return;
                    }

                }

            }
    }
}
