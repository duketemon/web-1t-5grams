package eu.fbk.fm.web1t;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class IndexFactoryTest {

    @Test
    public void createIndexFromPath() throws IOException {

        final String pathToNgrams = "src/test/java/eu/fbk/fm/web1t/resources/1grams";
        final String pathToIndex = "createIndexFromPath.test";
        IndexFactory.createIndex(pathToNgrams, pathToIndex);
        boolean isDeleted = Files.deleteIfExists(Paths.get(pathToIndex));
        assert isDeleted;
    }

    @Test
    public void createIndexFromListOfFiles() throws IOException {

        final String pathToIndex = "createIndexFromListOfFiles.test";
        final List<String> fileNames = Arrays.asList(
                "src/test/java/eu/fbk/fm/web1t/resources/2grams/bi-grams-1.tsv.gz",
                "src/test/java/eu/fbk/fm/web1t/resources/2grams/bi-grams-2.tsv.gz"
        );
        IndexFactory.createIndex(fileNames, pathToIndex);
        boolean isDeleted = Files.deleteIfExists(Paths.get(pathToIndex));
        assert isDeleted;
    }
}
