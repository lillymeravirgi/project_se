package data_management;

import com.cardio_generator.outputs.FileOutputStrategy;
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
 * the test creates a temporary file and uses the FileOutPutSTrategy to add patient data to it
 * then the file is tested for output, the right amount of lines and then the right content
 */
class FileOutputStrategyTest {

    private Path tempDir;

    @BeforeEach
    void setUp() throws IOException {
        tempDir = Files.createTempDirectory("file_output_test");
    }

    @AfterEach
    void tearDown() throws IOException {
       
        Files.walk(tempDir)
             .map(Path::toFile)
             .forEach(java.io.File::delete);
    }

    @Test
    void testOutputWritesToFile() throws IOException {
        FileOutputStrategy strategy = new FileOutputStrategy(tempDir.toString());

        int patientId = 42;
        long timestamp = 1234567890L;
        String label = "ECG";
        String data = "98.6";

        strategy.output(patientId, timestamp, label, data);

        Path outputFile = tempDir.resolve(label + ".txt");

        assertTrue(Files.exists(outputFile), "Expected output file to exist");

        List<String> lines = Files.readAllLines(outputFile);
        assertEquals(1, lines.size(), "Expected one line in the output file");

        String expectedLine = String.format("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s", 
                                             patientId, timestamp, label, data);
        assertEquals(expectedLine, lines.get(0), "Output line does not match expected format");
    }
}

