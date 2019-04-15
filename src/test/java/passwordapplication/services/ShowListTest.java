/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordapplication.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;
import passwordapplication.dao.BlackwordDAO;
import passwordapplication.dao.WhitewordDAO;
import passwordapplication.dao.WordlistDAO;
import passwordapplication.models.Wordlist;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ShowListTest {


    @Mock
    WhitewordDAO mockwhiteworddao;

    @Mock
    BlackwordDAO mockblackworddao;

    @Mock
    WordlistDAO mockwordlistdao;

    
    @InjectMocks
    ShowList showlist;

    @Test
    public void testShowAll() throws Exception {
        Wordlist mocklist1 = mock(Wordlist.class, RETURNS_DEEP_STUBS);
        Wordlist mocklist2 = mock(Wordlist.class, RETURNS_DEEP_STUBS);
        List<Wordlist> mocklistlist = new ArrayList();
        mocklistlist.add(mocklist1);
        mocklistlist.add(mocklist2);
        
        //WordlistDAO mockwordlistdao = mock(WordlistDAO.class, RETURNS_DEEP_STUBS);
        when(mockwordlistdao.list()).thenReturn(mocklistlist);

        when(mocklist1.getName()).thenReturn("mockname1");
        when(mocklist2.getName()).thenReturn("mockname2");


        Timestamp mockstamp1 = new Timestamp(1546300800);
        when(mocklist1.getTimestamp()).thenReturn(mockstamp1);

        Timestamp mockstamp2 = new Timestamp(1514764800);
        when(mocklist2.getTimestamp()).thenReturn(mockstamp2);

        when(mocklist1.getId()).thenReturn(1);
        when(mocklist2.getId()).thenReturn(2);

        when(mocklist1.getBlacklist()).thenReturn(true);
        when(mocklist2.getBlacklist()).thenReturn(false);

        //BlackwordDAO mockblackworddao = mock(BlackwordDAO.class, RETURNS_DEEP_STUBS);
        List<String> mocksamplelist1 = new ArrayList();
        when(mockblackworddao.listTenStringsFromList(1)).thenReturn(mocksamplelist1);
        when(mockblackworddao.getListSize(1)).thenReturn(0);

        //WhitewordDAO mockwhiteworddao = mock(WhitewordDAO.class, RETURNS_DEEP_STUBS);
        List<String> mocksamplelist2 = new ArrayList();
        mocksamplelist2.add("mockword");
        when(mockwhiteworddao.listTenStringsFromList(2)).thenReturn(mocksamplelist2);
        when(mockwhiteworddao.getListSize(2)).thenReturn(1);
        
        List<String> expResult = new ArrayList();
        expResult.add(
            "\nName of list: mockname1\nWhen the list was added: 1970-01-18 23:31:40"
            +"\nId number of list: 1\nThis list is a blacklist"
            +"\nThere are no words on this list"
            );
        expResult.add(
            "\nName of list: mockname2\nWhen the list was added: 1970-01-18 14:46:04"
            +"\nId number of list: 2\nThis list is a normal list, not a blacklist"
            +"\nThere are 1 words on this list\nSample of words from this list:"
            +"\nmockword"
            );
        
        List<String> result = showlist.showAll();
        assertEquals(expResult, result);

    }

}
