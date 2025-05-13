package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileUtils {

    public static String readInputFile(Path filePath) {
        try {
            return Files.readString(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the input data from file. Please provide the input file at the following path: " + filePath.toAbsolutePath(), e);
        }
    }

    public static List<String> readInputFileByLine(Path filePath) {
        try {
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the input data from file. Please provide the input file at the following path: " + filePath.toAbsolutePath(), e);
        }
    }
}
