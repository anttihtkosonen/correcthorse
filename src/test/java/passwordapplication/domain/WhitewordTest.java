/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordapplication.domain;

import passwordapplication.models.Whiteword;
import passwordapplication.models.Wordlist;
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
public class WhitewordTest {
    
    public WhitewordTest() {
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
     * Test of setWord and getWord method, of class Whiteword.
     */
    @Test
    public void testGetWord() {
        System.out.println("getWord");
        Whiteword instance = new Whiteword();
        instance.setWord("testword");
        String expResult = "testword";
        String result = instance.getWord();
        assertEquals(expResult, result);
    }

    /**
     * Test of setWordlist and getWordlist method, of class Whiteword.
     */
    @Test
    public void testGetWordlist() {
        System.out.println("getWordlist");
        Whiteword instance = new Whiteword();
        Wordlist wordlist = new Wordlist();
        Wordlist expResult = wordlist;
        instance.setWordlist(wordlist);
        Wordlist result = instance.getWordlist();
        assertEquals(expResult, result);
    }

    /**
     * Test of setActive and getActive method, of class Whiteword.
     */
    @Test
    public void testSetGetActive() {
        System.out.println("getActive");
        Whiteword instance = new Whiteword();
        instance.setActive(true);
        Boolean expResult = true;
        Boolean result = instance.getActive();
        assertEquals(expResult, result);
    }
    
}
