/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordapplication.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import passwordapplication.models.Word;
import passwordapplication.models.Wordlist;

/**
 *
 * @author antti
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class BlackwordDaoTest {
    
    @Mock
    Word mockword;
    
    @Mock
    Wordlist mockwordlist;
    
    @Mock
    JdbcTemplate mockjdbcTemplate;
    
    @InjectMocks
    private BlackwordDAO blackworddao = new BlackwordDAO();

    @Test
    public void TestInsert() {
        when(mockword.getWord()).thenReturn("mock");
        when(mockword.getWordlist()).thenReturn(mockwordlist);
        when(mockwordlist.getId()).thenReturn(1);
        try {
            blackworddao.insert(mockword);
        } catch (SQLException ex) {
            fail("SQL error");
        } 
    }
    
        @Test
    public void TestSecondInsert() {
        try {
            blackworddao.insert("mock", 1);
        } catch (SQLException ex) {
            fail("SQL error");
        } 
    }
    
    @Test
    public void TestRead(){
        when(mockjdbcTemplate.queryForObject(anyString(), any(BeanPropertyRowMapper.class), anyInt())).thenReturn(mockword);
        Word retVal = new Word();
        try {
            retVal = blackworddao.read(1);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        assertEquals(mockword, retVal);

    }
    
    @Test
    public void TestDeleteListWords(){
        try {
            blackworddao.deleteListWords(1);
        } catch (SQLException ex) {
            fail("SQL error");
        }         
    }
    
    @Test
    public void TestFindWhenFound(){
        List<Integer> mockList = new ArrayList();
        mockList.add(1);
        when(mockjdbcTemplate.query(anyString(), any(RowMapper.class), anyString())).thenReturn(mockList);
        Boolean retVal = null;
        try {
            retVal = blackworddao.find("mock");
        } catch (SQLException ex) {
            fail("SQL error");
        }
        
        assertEquals(true, retVal);
    }
    
    @Test
    public void TestFindWhenNotFound(){
        List<Integer> mockList = new ArrayList();
        when(mockjdbcTemplate.query(anyString(), any(RowMapper.class), anyString())).thenReturn(mockList);
        Boolean retVal = null;
        try {
            retVal = blackworddao.find("mock");
        } catch (SQLException ex) {
            fail("SQL error");
        }
        assertEquals(retVal, false);
    }
    
    @Test
    public void TestCount(){
        when(mockjdbcTemplate.queryForObject(anyString(), eq(Integer.class), anyString())).thenReturn(1);
        int retVal = 0;
        try {
            retVal = blackworddao.count("mock");
        } catch (SQLException ex) {
            fail("SQL error");
        }
        assertEquals(1, retVal);
    }
    
    @Test
    public void TestListWords(){
        List<String> mockList = new ArrayList();
        mockList.add("mock");
        mockList.add("list");
        when(mockjdbcTemplate.query(anyString(), any(RowMapper.class), anyInt())).thenReturn(mockList);
        List<String> retVal = null;
        try {
            retVal = blackworddao.listWords(1);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        assertEquals(mockList, retVal);
    }
        
    @Test
    public void TestListTenStringsFromList(){
        List<String> mockList = new ArrayList();
        mockList.add("mock");
        mockList.add("list");
        when(mockjdbcTemplate.query(anyString(), any(RowMapper.class), anyInt())).thenReturn(mockList);
        List<String> retVal = null;
        try {
            retVal = blackworddao.listTenStringsFromList(1);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        assertEquals(mockList, retVal);
    }
        
    @Test
    public void TestGetListSize(){
        when(mockjdbcTemplate.queryForObject(anyString(), eq(Integer.class), anyInt())).thenReturn(1);
        int retVal = 0;
        try {
            retVal = blackworddao.getListSize(1);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        assertEquals(1, retVal);
    }
}
