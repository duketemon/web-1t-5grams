package eu.fbk.fm.web1t;

import javafx.util.Pair;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Index {

    private final static Logger LOGGER = Logger.getLogger(Index.class);

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
        LOGGER.debug(String.format("Successfully saved in `%s`", path));
    }

    public void load(String path) throws IOException {

        rows.clear();
        List<String> lines = Files.readAllLines(Paths.get(path), FILE_ENCODING);
        for (String line : lines) {
            String[] values = line.split(VALUE_SEPARATOR);
            rows.add(new Pair<String, String>(values[0], values[1]));
        }
        LOGGER.debug(String.format("Successfully loaded from `%s`", path));
    }

    @Override
    public boolean equals(Object obj) {

        Index targetIndex = (Index)obj;
        List<Pair<String, String>> targetRows = targetIndex.getRows();
        if (targetRows.size() != this.rows.size()) {
            return false;
        }

        for (int i=0; i<this.rows.size(); i++) {
            Pair<String, String> selfPair = this.rows.get(i);
            Pair<String, String> targetPair = this.rows.get(i);

            if (!selfPair.getKey().equals(targetPair.getKey()) || !selfPair.getValue().equals(targetPair.getValue())) {
                return false;
            }
        }
        return true;
    }
}
