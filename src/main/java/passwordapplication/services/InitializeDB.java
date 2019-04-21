package passwordapplication.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.stereotype.Component;
//import passwordapplication.domain.Passwordapplication;

/**
 * Class for initializing the database. All tables are dropped, and then created
 * again. Use of initialize() deletes all data in database.
 *
 * @author antti
 */
@Component
public class InitializeDB {

    /**
     * Method to initialize database
     * @param database lovation of the database to be initialized
     */
    public static void initialize(String database) {

        try (Connection conn = DriverManager.getConnection(database, "sa", "")) {
            conn.prepareStatement("DROP TABLE Wordlist IF EXISTS;").executeUpdate();
            conn.prepareStatement("CREATE TABLE Wordlist (id INTEGER AUTO_INCREMENT, name VARCHAR(50) NOT NULL, timestamp TIMESTAMP, blacklist BOOLEAN, PRIMARY KEY (id));").executeUpdate();
            conn.prepareStatement("DROP TABLE Whiteword IF EXISTS;").executeUpdate();
            conn.prepareStatement("CREATE TABLE Whiteword (id BIGINT PRIMARY KEY AUTO_INCREMENT, word VARCHAR(30) NOT NULL, active Boolean NOT NULL, list_id INTEGER NOT NULL, FOREIGN KEY (list_id) REFERENCES Wordlist(id));").executeUpdate();
            conn.prepareStatement("DROP TABLE Blackword IF EXISTS;").executeUpdate();
            conn.prepareStatement("CREATE TABLE Blackword (id BIGINT PRIMARY KEY AUTO_INCREMENT, word VARCHAR(30) NOT NULL, list_id INTEGER NOT NULL, FOREIGN KEY (list_id) REFERENCES Wordlist(id));").executeUpdate();

        } catch (SQLException ex) {
            //Logger.getLogger(Passwordapplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
