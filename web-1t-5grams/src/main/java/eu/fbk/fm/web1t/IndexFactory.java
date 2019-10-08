package eu.fbk.fm.web1t;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class IndexFactory {


    public static void createIndex(final String nGramsPath, final String indexPath) throws IOException {

        List<String> fileNames = Files
                .list(Paths.get(nGramsPath))
                .map(x -> x.toString())
                .collect(Collectors.toList());

        createIndex(fileNames, indexPath);
    }

    public static void createIndex(final List<String> fileNames, String indexPath) throws IOException {

        Index index = new Index();
        Collections.sort(fileNames);
        for (String fileName : fileNames) {
            String output = Utils.runShellCommand("/bin/sh", "-c", String.format("zcat %s | tail -n 1", fileName));
            String[] values = output.split( "\t");
            if (values.length == 2) {
                index.add(values[0], fileName);
            }
        }

        index.save(indexPath);
    }
}
