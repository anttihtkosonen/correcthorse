package passwordapplication.models;

/**
 * A password-object is an entity used for storing the password and its entropy.
 *
 * @author antti
 */
public class Password {

    String password;
    Integer entropy;

    /**
     * Empty constructor method.
     */
    public Password() {
    }

    /**
     * Constructor method
     * @param password the password string
     * @param entropy the entropy-value of the string
     */
    public Password(String password, int entropy) {
        this.password = password;
        this.entropy = entropy;
    }

    /**
     * Method to get the entropy-value
     * @return the entropy value
     */
    public Integer getEntropy() {
        return entropy;
    }

    /**
     * Method to get the password-value
     * @return the password
     */
    public String getPassword() {
        return password;
    }
}
