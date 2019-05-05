package passwordapplication.models;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Test for the Password-class
 *
 * @author antti
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class PasswordTest {
    
    @InjectMocks
    Password password;
    
        /**
     * Test of the costructor and getters. The parameters should be set
     * correctly, and returned correctly by the getters.
     */
    @Test
    public void testPassword() {
        String password = "mockword";
        Integer entropy = 666;
        Password testword = new Password(password, entropy);
        assertEquals(testword.getPassword() , password);
        assertEquals(testword.getEntropy() , entropy);
    }
    
}
