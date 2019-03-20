/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordapplication.domain;

import passwordapplication.domain.Word;
import passwordapplication.domain.Wordlist;
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
public class WordTest {
    
    public WordTest() {
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
     * Test of setWord and getWord method, of class Word.
     */
    @Test
    public void testSetGetWord() {
        System.out.println("getWord");
        Word instance = new Word();
        instance.setWord("testword");
        String expResult = "testword";
        String result = instance.getWord();
        assertEquals(expResult, result);
    }

    /**
     * Test of setWordlist and getWordlist method, of class Word.
     */

    @Test
    public void testSetGetWordlist() {
        System.out.println("getWordlist");
        Word instance = new Word();
        Wordlist wordlist = new Wordlist();
        Wordlist expResult = wordlist;
        instance.setWordlist(wordlist);
        Wordlist result = instance.getWordlist();
        assertEquals(expResult, result);
    }
    
}
