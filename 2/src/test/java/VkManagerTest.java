import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.io.Console;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VkManagerTest {
    private VkClient clientMock;
    private VkManager manager;
    private final String PATH_TO_STATIC = "src/test/static/";

    String readJsonFromFile(String pathString) {
        Path path = Paths.get(PATH_TO_STATIC + pathString);
        try {
            return Files.readString(path, StandardCharsets.US_ASCII);
        } catch (IOException e) {
            fail("Could not read test response data, path: " + path.toAbsolutePath().toString());
        }
        return "fail";
    }

    @BeforeEach
    public void setup() {
        clientMock = mock(VkSDKWrapper.class);
        manager = new VkManager(clientMock);
    }

    @Nested
    @DisplayName("Test arguments")
    public class ArgumentsTest {
        @Test
        @DisplayName("Test negative hours")
        public void testNegativeHours() {
            try {
                manager.getNumNewsByHashTag("#invalid", -1);
            } catch (IllegalArgumentException e) {
                return;
            } catch (IOException e) {
                fail("Did not handle negative hours correctly, produced IOException");
            }
            fail("Did not handle negative hours correctly");
        }

        @Test
        @DisplayName("Test too large hours")
        public void testLargeHours() {
            try {
                manager.getNumNewsByHashTag("#invalid", 25);
            } catch (IllegalArgumentException e) {
                return;
            } catch (IOException e) {
                fail("Did not handle large hours correctly, produced IOException");
            }
            fail("Did not handle large hours correctly");
        }

        @Test
        @DisplayName("Test empty hashtag")
        public void testEmptyHashtag() {
            try {
                manager.getNumNewsByHashTag("", 10);
            } catch (IllegalArgumentException e) {
                return;
            } catch (IOException e) {
                fail("Did not handle empty hashtag correctly, produced IOException");
            }
            fail("Did not handle empty hashtag correctly");
        }

        @Test
        @DisplayName("Test not a hashtag")
        public void testNotHashtag() {
            try {
                manager.getNumNewsByHashTag("definitely a hashtag!", 10);
            } catch (IllegalArgumentException e) {
                return;
            } catch (IOException e) {
                fail("Did not handle non-hashtags correctly, produced IOException");
            }
            fail("Did not handle non-hashtags correctly");
        }
    }

    @Nested
    @DisplayName("Test requests")
    public class RequestTests {
        @Test
        @DisplayName("Test correct response")
        public void testCorrectResponse() {
            String content = readJsonFromFile("correct_response.json");
            Integer expected = 15;
            int numHours = 5;
            try {
                when(clientMock.getNumNewsInInterval(eq("#hello"), anyInt(), anyInt())).thenReturn(content);
                List<Integer> res = manager.getNumNewsByHashTag("#hello", numHours);
                assertEquals(numHours, res.size());
                for (Integer actual : res) {
                    assertEquals(expected, actual);
                }
            } catch (IllegalArgumentException | IOException e) {
                fail("Did not handle correct request and arguments correctly, produced an exception");
            }
        }
        @Test
        @DisplayName("Test bad response")
        public void testBadResponse() {
            String content = "";
            int numHours = 5;
            try {
                when(clientMock.getNumNewsInInterval(eq("#hello"), anyInt(), anyInt())).thenReturn(content);
                List<Integer> res = manager.getNumNewsByHashTag("#hello", numHours);
            } catch (IOException e) {
                return;
            }
            fail("Did not throw exception when received a bad reponse");
        }
    }
}
