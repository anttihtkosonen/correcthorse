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
import org.mockito.InjectMocks;
import static org.mockito.Mockito.mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import passwordapplication.models.Password;

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

        List<String> expResultList = new ArrayList();
        expResultList.add("some");
        expResultList.add("mock");
        expResultList.add("words");

        List<Password> mocklist = new ArrayList();
        mocklist.add(new Password("some", 5));
        mocklist.add(new Password("mock", 5));
        mocklist.add(new Password("words", 5));

        File file;
        List<String> resultList = new ArrayList();
        try {
            file = savefolder.newFile("testfile.txt");
            filelistparser.saveListToFile(file, mocklist);
            Stream<String> stream = Files.lines(Paths.get(file.getCanonicalPath()), StandardCharsets.UTF_8);
            stream.forEach(s -> resultList.add(s));
        } catch (IOException ex) {
            fail("SQLException");
        }
        assertEquals(expResultList, resultList);
    }

    /**
     * Test of the saveListToFile-method, when the File is null.
     */
    @Test
    public void testSaveListToNullFile() {
        List<Password> mocklist = mock(ArrayList.class);
        File file = null;

        try {
            filelistparser.saveListToFile(file, mocklist);
            fail( "The method should have thrown an exception" );
        } catch (IOException ex) {
            //If exception is thrown, the test is passed
        }

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
