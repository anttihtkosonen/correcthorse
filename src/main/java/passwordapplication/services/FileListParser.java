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

/**
 * Class for file-operations - getting strings from files and saving strings to
 * files
 *
 * @author antti
 */
@Component
public class FileListParser {

    /**
     * Method for saving an ArrayList of strings to a file in the filesystem
     *
     * @param file
     * @param list
     * @throws java.io.IOException
     */
    public void saveListToFile(File file, List<String> list) throws IOException {
        if (file != null) {
            FileWriter writer = new FileWriter(file);
            for (String str : list) {
                writer.write(str);
                writer.write("\n");
            }
            writer.close();
        }

    }

    /**
     * Method to get a list from a file in the filesystem
     *
     * @param location location of the list in the filesystem
     * @return the list as String. No parsing of any kind is done in this
     * method, bur rather the list is returned as-is
     */
    public String getListFromFile(String location) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(location), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

}
