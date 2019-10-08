package eu.fbk.fm.web1t;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearcherTest {
    @Test
    public void searchUniGrams() throws IOException {

        final String pathToUniGramIndex = "searchUniGrams.test";
        final List<String> fileNames = Arrays.asList(
                "src/test/java/eu/fbk/fm/web1t/resources/1grams/uni-grams.tsv.gz"
        );
        IndexFactory.createIndex(fileNames, pathToUniGramIndex);

        Map<Integer, String> indexes = new HashMap<>();
        indexes.put(1, pathToUniGramIndex);
        Searcher searcher = new Searcher(indexes);

        assert searcher.getScore(1, "a") == 123;
        assert searcher.getScore(1, "b") == 321;
        assert searcher.getScore(1, "c") == 444;

        assert searcher.getScore(1, "be") == 4321;
        assert searcher.getScore(1, "have") == 8888;
        assert searcher.getScore(1, "to") == 1234;

        assert searcher.getScore(1, "too") == 0;
        assert searcher.getScore(2, "too") == 0;

        Files.deleteIfExists(Paths.get(pathToUniGramIndex));
    }

    @Test
    public void searchBiGrams() throws IOException {

        final String pathToBiGramIndex = "searchBiGrams.test";
        final List<String> fileNames = Arrays.asList(
                "src/test/java/eu/fbk/fm/web1t/resources/2grams/bi-grams-1.tsv.gz",
                "src/test/java/eu/fbk/fm/web1t/resources/2grams/bi-grams-2.tsv.gz"
        );
        IndexFactory.createIndex(fileNames, pathToBiGramIndex);

        Map<Integer, String> indexes = new HashMap<>();
        indexes.put(2, pathToBiGramIndex);
        Searcher searcher = new Searcher(indexes);

        assert searcher.getScore(2, "a a") == 1;
        assert searcher.getScore(2, "a aa") == 10;
        assert searcher.getScore(2, "a bb") == 22;

        assert searcher.getScore(2, "have to") == 777;
        assert searcher.getScore(2, "to be") == 101;
        assert searcher.getScore(2, "to go") == 100;

        assert searcher.getScore(2, "to went") == 0;
        assert searcher.getScore(6, "to went") == 0;

        Files.deleteIfExists(Paths.get(pathToBiGramIndex));
    }
}
