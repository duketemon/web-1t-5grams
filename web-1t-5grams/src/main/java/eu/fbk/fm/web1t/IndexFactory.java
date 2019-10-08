package eu.fbk.fm.web1t;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class IndexFactory {

    private final static Logger LOGGER = Logger.getLogger(IndexFactory.class);

    public static String shell = "/bin/sh";

    public static String readCommand = "zcat";

    public static String valueSeparator = "\t";

    /**
     * @param nGramsPath - absolute path to the folder with N-gram files
     * @param indexPath - path for saving the index
     */
    public static void createIndex(final String nGramsPath, final String indexPath) throws IOException {

        List<String> fileNames = Files
                .list(Paths.get(nGramsPath))
                .map(f -> f.toString())
                .collect(Collectors.toList());

        createIndex(fileNames, indexPath);
    }

    public static void createIndex(final List<String> fileNames, String indexPath) throws IOException {

        Index index = new Index();
        Collections.sort(fileNames);
        for (String fileName : fileNames) {
            String output = Utils.runShellCommand(shell, "-c", String.format("%s %s | tail -n 1", readCommand, fileName));
            String[] values = output.split(valueSeparator);
            if (values.length == 2) {
                index.add(values[0], fileName);
            }
        }

        index.save(indexPath);
        LOGGER.debug(String.format("Index successfully saved in `%s`", indexPath));
    }
}
