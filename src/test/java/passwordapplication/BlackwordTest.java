/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordapplication;

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
public class BlackwordTest {
    
    public BlackwordTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of setWord and getWord method, of class Blackword.
     */
    @Test
    public void testSetGetWord() {
        System.out.println("getWord");
        Blackword instance = new Blackword();
        instance.setWord("testword");
        String expResult = "testword";
        String result = instance.getWord();
        assertEquals(expResult, result);
    }

    /**
     * Test of setWordlist and getWordlist method, of class Blackword.
     */

    @Test
    public void testSetGetWordlist() {
        System.out.println("getWordlist");
        Blackword instance = new Blackword();
        Wordlist wordlist = new Wordlist();
        Wordlist expResult = wordlist;
        instance.setWordlist(wordlist);
        Wordlist result = instance.getWordlist();
        assertEquals(expResult, result);
    }
    
}
