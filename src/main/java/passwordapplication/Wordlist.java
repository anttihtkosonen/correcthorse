package passwordapplication;

import java.time.LocalDateTime;


public class Wordlist {
    Integer id;
    String name;
//    LocalDateTime datetime;
    Boolean blacklist;
    
    public Wordlist(){
    }   
    
    
    public Wordlist(Integer id, 
            String name, 
//            LocalDateTime datetime, 
            Boolean blacklist){
        this.id = id;
        this.name = name;
        this.blacklist = blacklist;
//        this.datetime = datetime;
    }
    
    public Wordlist(String name, 
//            LocalDateTime datetime, 
            Boolean blacklist){
        this.id = null;
        this.name = name;
        this.blacklist = blacklist;
//        this.datetime = datetime;
    }

    public Integer getId(){
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Boolean getBlacklist() {
        return this.blacklist;
    }

    public void setBlacklist(Boolean blacklist) {
        this.blacklist = blacklist;
    }
/*    
    public LocalDateTime getDateTime() {
        return this.datetime;
    }
*/

}
