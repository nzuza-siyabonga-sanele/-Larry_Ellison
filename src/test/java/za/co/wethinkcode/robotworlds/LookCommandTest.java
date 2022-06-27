package za.co.wethinkcode.robotworlds;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.DataInput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

public class LookCommandTest {

    private final static int DEFAULT_PORT = 5000;
    private final static String DEFAULT_IP = "localhost";
    private final RobotWorldClient serverClient = new RobotWorldJsonClient();

    @BeforeEach
    void connectToServer() {
        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
    }

    @AfterEach
    void disconnectFromServer() {
        serverClient.disconnect();
    }

    @Test
    void seeAnObstacle() {
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 2x2 (The world is configured or hardcoded to this size)
        assertTrue(serverClient.isConnected());

        // When I send a valid launch request to the server
        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";

        String request2 = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"look\"," +
                "  \"arguments\": []" +
                "}";


        JsonNode response1 = serverClient.sendRequest(request);
        JsonNode response2 = serverClient.sendRequest(request2);

        // System.out.println(response2);
        // Then I should get a valid response from the server
        assertNotNull(response2.get("result"));
        assertEquals("OK", response2.get("result").asText());

        // And the position should be (x:0, y:0)
        assertNotNull(response2.get("data"));


        System.out.println(response2.get("data").get("objects"));
        for (int i = 0; i < response2.get("data").get("objects").size(); i++) {
            String obstacle = (response2.get("data").get("objects").get(i).get("type").asText());
            if (obstacle.equals("OBSTACLE")) {
                assertEquals("OBSTACLE", obstacle);
                assertEquals(1,response2.get("data").get("objects").get(i).get("distance").asInt());
            }

        }
    }


}