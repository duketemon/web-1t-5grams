package eu.fbk.fm.web1t;

import javafx.util.Pair;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Searcher {

    private final static Logger LOGGER = Logger.getLogger(Searcher.class);

    public static String shell = "/bin/sh";

    public static String readCommand = "zcat";

    public static String valueSeparator = "\t";

    public static String newLineSeparator = "\n";

    private Map<Integer, Index> indexes = new HashMap<>();

    public Searcher(final Map<Integer, String> indexesInfo) throws IOException {

        for (Map.Entry<Integer, String> info : indexesInfo.entrySet()) {
            Index index = new Index();
            index.load(info.getValue());
            indexes.put(info.getKey(), index);
        }
    }

    public long getScore(final int N, final String key) throws IOException {

        final Index index = indexes.get(N);
        if (index == null) {
            LOGGER.debug(String.format("Wrong N (N=%s) value.", N));
            return 0;
        }
        if (N != key.split(" ").length) {
            LOGGER.debug("Length of tokens not equals to the number of N-grams.");
            return 0;
        }

        final String fileName = getFileName(index, key);
        if (index == null) {
            LOGGER.debug(String.format("Cannot find any file (key=%s).", key));
            return 0;
        }
        LOGGER.debug(String.format("`%s` - file name for the phrase `%s`", key, fileName));
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

        final String command = String.format("%s %s | grep --max-count=1 -P \"^%s\t\"", readCommand, fileName, key);
        final String output = Utils.runShellCommand(shell, "-c", command);
        if (output != null) {
            final String[] values = output.replace(newLineSeparator, "").split(valueSeparator);
            if (values.length == 2 && values[0].equals(key)) {
                return Long.valueOf(values[1]);
            }
        }
        return 0;
    }
}
