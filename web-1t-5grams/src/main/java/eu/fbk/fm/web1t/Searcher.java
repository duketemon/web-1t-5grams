package eu.fbk.fm.web1t;

import javafx.util.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Searcher {

    private Map<Integer, Index> indexes = new HashMap<>();

    public Searcher(final Map<Integer, String> indexesInfo) {

        for (Map.Entry<Integer, String> info : indexesInfo.entrySet()) {
            Index index = new Index();
            index.load(info.getValue());
            indexes.put(info.getKey(), index);
        }
    }

    public long getScore(final int N, final String key) throws IOException {

        final Index index = indexes.get(N);
        final String fileName = getFileName(index, key);
        final long score = getScoreFromFile(fileName, key);
        return score;
    }

    private String getFileName(final Index index, final String key) {

        for (Pair<String, String> row : index.getRows()) {
            if (key.compareTo(row.getKey()) <= 0) {
                return row.getValue();
            }
        }
        return null;
    }

    private long getScoreFromFile(final String fileName, final String key) throws IOException {

        String output = Utils.runShellCommand("/bin/sh", "-c", String.format("zcat %s | grep %s", fileName, key));
        for (String line : output.split("\n")) {
            String[] values = line.split( "\t");
            if (values.length == 2 && values[0].equals(key)) {
                return Long.valueOf(values[1]);
            }
        }
        return 0;
    }
}
