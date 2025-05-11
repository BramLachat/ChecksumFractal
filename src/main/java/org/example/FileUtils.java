package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileUtils {

    public static String readInputFile(Path filePath) {
        try {
            String input = Files.readString(filePath);
            System.out.println("input: " + input);
            return input;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the input data from file. Please provide the input file at the following path: " + filePath.toAbsolutePath(),e);
        }
    }

    public static List<String> readInputFileByLine(Path filePath) {
        try {
            List<String> input = Files.readAllLines(filePath);
            System.out.println("input: " + input);
            return input;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the input data from file. Please provide the input file at the following path: " + filePath.toAbsolutePath(),e);
        }
    }
}
