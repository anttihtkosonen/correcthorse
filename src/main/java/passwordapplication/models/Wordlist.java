package passwordapplication.models;

import java.sql.Timestamp;

/**
 * Wordlist is the entity used for information about list of words. This class
 * has only standard getters and setters.
 *
 * @author antti
 */
public class Wordlist {

    Integer id;
    String name;
    Timestamp timestamp;
    Boolean blacklist;

    public Wordlist() {
    }

    public Wordlist(Integer id,
            String name,
            Timestamp timestamp,
            Boolean blacklist) {
        this.id = id;
        this.name = name;
        this.timestamp = timestamp;
        this.blacklist = blacklist;
    }

    public Wordlist(String name,
            Timestamp timestamp,
            Boolean blacklist) {
        this.id = null;
        this.name = name;
        this.timestamp = timestamp;
        this.blacklist = blacklist;
    }
    
    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Boolean getBlacklist() {
        return this.blacklist;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

}
