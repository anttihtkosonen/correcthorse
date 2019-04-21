package passwordapplication.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Tests of the FileListParser-class.
 *
 * @author antti
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class FileListParserTest {

    /**
     * A temporary folder used for testing saving a file.
     */
    @Rule
    public TemporaryFolder savefolder = new TemporaryFolder();

    /**
     * A temporary folder used for testing reading a file.
     */
    @Rule
    public TemporaryFolder readfolder = new TemporaryFolder();

    @InjectMocks
    @Spy
    FileListParser filelistparser;

    /**
     * Test of the saveListToFile-method. The resulting file should match what
     * was saved.
     */
    @Test
    public void testSaveListToFile() {
        List<String> mocklist = new ArrayList();
        mocklist.add("some");
        mocklist.add("mock");
        mocklist.add("words");
        List<String> resultList = new ArrayList();
        File file;
        try {
            file = savefolder.newFile("testfile.txt");
            filelistparser.saveListToFile(file, mocklist);
            Stream<String> stream = Files.lines(Paths.get(file.getCanonicalPath()), StandardCharsets.UTF_8);
            stream.forEach(s -> resultList.add(s));
        } catch (IOException ex) {
            fail("SQLException");
        }
        assertEquals(mocklist, resultList);
    }

    /**
     * Test of the getListFromFile-method. The method should return the contents
     * of the file.
     */
    @Test
    public void testGetListFromFile() {
        String readTestText = "some\nmock\nwords\n";
        String result = null;
        try {
            File readtestfile = readfolder.newFile("readtestfile.txt");
            FileWriter writer = new FileWriter(readtestfile);
            writer.write(readTestText);
            writer.close();
            result = filelistparser.getListFromFile(readtestfile.getCanonicalPath());
        } catch (IOException ex) {
            fail("IOException");
        }
        assertEquals(readTestText, result);
    }
}
