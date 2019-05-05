/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordapplication.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;
import passwordapplication.models.Password;

/**
 * Class for file-operations - getting strings from files and saving strings to
 * files
 *
 * @author antti
 */
@Component
public class FileListParser {

    /**
     * Method for saving an ArrayList of Password-objects to a file in the
     * filesystem. Only the actual passwords are saved, entropy values are
     * discarded.
     *
     * @param file - the File-object of the file to write
     * @param list - the list of Password-objects to write to the file
     * @throws java.io.IOException if there was an input/output during the operation
     */
    public void saveListToFile(File file, List<Password> list) throws IOException {
        if (file != null) {
            FileWriter writer = new FileWriter(file);
            for (Password password : list) {
                writer.write(password.getPassword());
                writer.write("\n");
            }
            writer.close();
        } else {
            throw new IOException("File was null");
        }

    }

    /**
     * Method to get a list from a file in the filesystem
     *
     * @param location location of the list in the filesystem
     * @return the list as String. No parsing of any kind is done in this
     * method, bur rather the list is returned as-is
     * @throws java.io.IOException if there was an input/output during the operation
     */
    public String getListFromFile(String location) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(location), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } 
        return contentBuilder.toString();
    }

}
