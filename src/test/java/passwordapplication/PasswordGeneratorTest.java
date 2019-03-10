/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordapplication;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author antti
 */
public class PasswordGeneratorTest {
    
    public PasswordGeneratorTest() {
    }

    /**
     * Test of generate method, of class PasswordGenerator.
     */
    @Test
    public void testGenerate() {
        System.out.println("generate");
        Integer amount = 2;
        ArrayList<String> words = new ArrayList();
        words.add("bashment");
        words.add("bawbee");
        words.add("benthos");
        words.add("bergschrund");
        words.add("bezoar");
        words.add("bibliopole");
        
        PasswordGenerator instance = new PasswordGenerator();
        
        List<String> expResult = new ArrayList();
        expResult.add("bashment-bawbee-benthos");
        expResult.add("bergschrund-bezoar-bibliopole");

        
        List<String> result = instance.generate(amount, words);
        assertEquals(expResult, result);


    }
    
}
