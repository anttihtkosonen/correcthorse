/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordapplication.gui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import org.springframework.beans.factory.annotation.Autowired;
import passwordapplication.services.ShowList;

/**
 *
 * @author antti
 */
public class GUIwordlists {
    
    @Autowired
    ShowList showlist;

    public Parent getView() {
        List<String> list = new ArrayList();
        try {
            list = showlist.getAll();
        } catch (SQLException ex) {
            Logger.getLogger(GUIwordlists.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ScrollPane sp = new ScrollPane();
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
        
        ListView<String> listView = new ListView();
        ObservableList<String> observablelist = FXCollections.observableList(list);
        listView.setItems(observablelist);
        
        sp.setContent(new Label("Wordlists in database"));
        sp.setContent(listView);
        
        return sp;
    }
}
