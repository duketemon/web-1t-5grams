package eu.fbk.fm.web1t;

import javafx.util.Pair;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Index {

    private static final String VALUE_SEPARATOR = "\t";

    private static final Charset FILE_ENCODING = StandardCharsets.UTF_8;

    private List<Pair<String, String>> rows = new ArrayList<>();

    public List<Pair<String, String>> getRows() {
        return this.rows;
    }

    public void add(String key, String fileName) {

        Pair<String, String> pair = new Pair<>(key, fileName);
        rows.add(pair);
    }

    public void save(String path) throws IOException {

        List<String> outputLines = new ArrayList<>(rows.size());
        for (Pair<String, String> pair : rows) {
            outputLines.add(pair.getKey() + VALUE_SEPARATOR + pair.getValue());
        }

        Files.write(Paths.get(path), outputLines, FILE_ENCODING);
    }

    public void load(String path) {

    }
}
