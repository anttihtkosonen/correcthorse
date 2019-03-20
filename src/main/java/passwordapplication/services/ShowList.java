package passwordapplication.services;

import passwordapplication.dao.BlackwordDao;
import passwordapplication.dao.WhitewordDao;
import passwordapplication.dao.WordlistDao;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import passwordapplication.domain.Wordlist;

/**
 * Class for operations related to showing user information about wordlists in
 * the database.
 *
 * @author antti
 */
@Component
public class ShowList {

    @Autowired
    WhitewordDao whiteworddao;

    @Autowired
    WordlistDao wordlistdao;

    @Autowired
    BlackwordDao blackworddao;

    /**
     * Method to show information about all lists in the database.
     *
     * @throws SQLException
     */
    public void showAll() throws SQLException {

        List<Wordlist> wordlistlist = new ArrayList();
        try {
            wordlistlist = wordlistdao.list();
        } catch (SQLException ex) {
            System.out.println("There was an error retrieving the information frim the database.");
        }
        if (wordlistlist.size() == 0) {
            System.out.println("There are no wordlists in database.");
        } else {
            for (int i = 0; i < wordlistlist.size(); i++) {
                
                System.out.println("\n\n-----------------");
                System.out.println("Name of list: " + wordlistlist.get(i).getName());
                Date date = wordlistlist.get(i).getTimestamp();
                String dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                System.out.println("When the list was added: " + dateString);
                System.out.println("Id number of list: " + wordlistlist.get(i).getId());
                List<String> samplelist;
                Integer size = 0;
                if (wordlistlist.get(i).getBlacklist()) {
                    System.out.println("This list is a blacklist");
                    samplelist = blackworddao.listTenStringsFromList(wordlistlist.get(i).getId());
                    size = blackworddao.getListSize(wordlistlist.get(i).getId());
                    
                } else {
                    System.out.println("This list is a normal list, not a blacklist");
                    samplelist = whiteworddao.listTenStringsFromList(wordlistlist.get(i).getId());
                    size = whiteworddao.getListSize(wordlistlist.get(i).getId());
                }

                if (samplelist.size() == 0) {
                    System.out.println("There are no words on this list");
                } else {
                    System.out.println("There are "+size+" words on this list\n");
                    System.out.println("Sample of words from this list:\n");
                    for (int j = 0; j < samplelist.size(); j++) {
                        System.out.println(samplelist.get(j));
                    }
                }

            }
        }
    }
}
