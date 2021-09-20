package com.joha.exercise.filehandling;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;
/*
    Used simulate data being updated. Provides a buffered reader of the newest file in the input directory
 */
@Component
public class ScheduledFileLoader {

    private Path newestPath;

    @Scheduled(initialDelay = 0, fixedDelay = 1000)
    public void checkForNewDataFile() throws IOException {
        Stream<Path> filePaths = Files.list(Paths.get("inboundJsonData"));
        Optional<Path> newestPath = filePaths
                .filter(file -> !Files.isDirectory(file))
                .max(Comparator.comparingLong(file -> file.toFile().lastModified()));
        newestPath.ifPresent(path -> this.newestPath = path);
    }

    public BufferedReader getBufferedReaderLatestJsonDataFile() throws IOException {
        return Files.newBufferedReader(newestPath);
    }

    public BufferedReader getBufferedReaderFromBenchmarkFile() throws IOException {
        Path path = Paths.get("inboundJsonData/testFile5m.json");
        return Files.newBufferedReader(path);
    }

    public BufferedReader getBufferedReaderLatestJsonDataFileByName(String fileName) throws IOException {
        Path path = Paths.get("inboundJsonData/" + fileName);
        return Files.newBufferedReader(path);
    }

}
