package eu.fbk.fm.web1t;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IndexTest {
    @Test
    public void correctSaveAndLoad() throws IOException {

        String pathToIndex = "correctSaveTest.txt";
        Index sourceIndex = new Index();
        Index targetIndex = new Index();
        sourceIndex.add("test", "some/path");
        sourceIndex.save(pathToIndex);
        targetIndex.load(pathToIndex);
        Files.deleteIfExists(Paths.get(pathToIndex));
        assert sourceIndex.equals(targetIndex);
    }
}
