package de.kneissja.katanine.fileutil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Helps to load CSV files
 */
public class CSVLoader {

    /**
     * Loads the CSV content from the file
     * @param filePath file to load
     * @return List of all CSV entries
     * @throws IOException if the file could not be loaded
     */
    public List<List<String>> loadFile(String filePath) throws IOException {

        InputStream resourceAsStream = this.getClass().getResourceAsStream(filePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream));

        String line = null;
        List<List<String>> result = new ArrayList<>();
        while((line = br.readLine()) != null) {
            List<String> lineData = Arrays.asList(line.split(","));
            result.add(lineData);
        }

        return result;
    }
}
