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

    /**
     * Empty constructor method.
     */
    public Wordlist() {
    }

    /**
     * Constructor method
     * @param id number of list
     * @param name name of list
     * @param timestamp time of list creation
     * @param blacklist blacklist status of the list
     */
    public Wordlist(Integer id,
            String name,
            Timestamp timestamp,
            Boolean blacklist) {
        this.id = id;
        this.name = name;
        this.timestamp = timestamp;
        this.blacklist = blacklist;
    }

    /**
     * Constructor method without id number. This is needed to create the object
     * before it is added to the database, since at this point the id is not
     * yet known.
     * @param name name of list
     * @param timestamp time of list creation
     * @param blacklist blacklist status of the list
     */
    public Wordlist(String name,
            Timestamp timestamp,
            Boolean blacklist) {
        this.id = null;
        this.name = name;
        this.timestamp = timestamp;
        this.blacklist = blacklist;
    }
    
    /**
     * Method to get the id-parameter of the object.
     * @return id number
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Method to get the name-parameter of the object.
     * @return name 
     */
    public String getName() {
        return this.name;
    }

    /**
     * Method to get the blacklist-parameter of the object.
     * @return blaclist status
     */
    public Boolean getBlacklist() {
        return this.blacklist;
    }

    /**
     * Method to get the timestamp-parameter of the object.
     * @return creation time
     */
    public Timestamp getTimestamp() {
        return this.timestamp;
    }

}
