package de.kneissja.katanine.fileutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Helps to load CSV files
 */
public class CSVLoader {

    /**
     * Loads the CSV content from the file
     * @param path path to load
     * @return List of all CSV entries
     * @throws IOException if the file could not be loaded
     */
    public List<List<String>> loadFile(Path path) throws IOException {
        return Files.lines(path)
                .map(line -> Arrays.asList(line.split(",")))
                .collect(Collectors.toList());
    }
}
