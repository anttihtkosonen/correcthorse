package passwordapplication.services;

import passwordapplication.dao.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import passwordapplication.models.Wordlist;

/**
 * Class for operations related to showing user information about wordlists in
 * the database.
 *
 * @author antti
 */
@Component
public class ShowList {

    @Autowired
    WhitewordDAO whiteworddao;

    @Autowired
    WordlistDAO wordlistdao;

    @Autowired
    BlackwordDAO blackworddao;

    /**
     * Method to get the information about all lists in the database. This
     * prints out the information of each list as a list of strings Used in the
     * graphical user interface.
     *
     * @return list of strings, each of which has the information about a single
     * wordlist
     * @throws SQLException
     */
    public List<String> showAll() throws SQLException {

        List<Wordlist> wordlistlist = new ArrayList();
        wordlistlist = wordlistdao.list();

        List<String> list = new ArrayList();

        for (int i = 0; i < wordlistlist.size(); i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\nName of list: ").append(wordlistlist.get(i).getName());
            Date date = wordlistlist.get(i).getTimestamp();
            String dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
            stringBuilder.append("\nWhen the list was added: ").append(dateString);
            stringBuilder.append("\nId number of list: ").append(wordlistlist.get(i).getId());
            List<String> samplelist;
            Integer size = 0;
            if (wordlistlist.get(i).getBlacklist()) {
                stringBuilder.append("\nThis list is a blacklist");
                samplelist = blackworddao.listTenStringsFromList(wordlistlist.get(i).getId());
                size = blackworddao.getListSize(wordlistlist.get(i).getId());

            } else {
                stringBuilder.append("\nThis list is a normal list, not a blacklist");
                samplelist = whiteworddao.listTenStringsFromList(wordlistlist.get(i).getId());
                size = whiteworddao.getListSize(wordlistlist.get(i).getId());
            }

            if (samplelist.isEmpty()) {
                stringBuilder.append("\nThere are no words on this list");
            } else {
                stringBuilder.append("\nThere are ").append(size).append(" words on this list");
                stringBuilder.append("\nSample of words from this list:");
                for (int j = 0; j < samplelist.size(); j++) {
                    stringBuilder.append("\n").append(samplelist.get(j));
                }
            }
            list.add(i, stringBuilder.toString());
        }
        return list;
    }

}
