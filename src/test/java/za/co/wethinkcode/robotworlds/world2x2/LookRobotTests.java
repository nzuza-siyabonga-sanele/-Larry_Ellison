package za.co.wethinkcode.robotworlds.World2x2;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.RobotWorldClient;
import za.co.wethinkcode.robotworlds.RobotWorldJsonClient;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 *As a player
 * I want to look for other robots, in the online robot world
 * So that I can determine my next move
 */
public class LookRobotTests {
    private final static int DEFAULT_PORT = 5000;
    private final static String DEFAULT_IP = "localhost";
    private final RobotWorldClient serverClient = new RobotWorldJsonClient();

    @BeforeEach
    void connectToServer(){
        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
    }

    @AfterEach
    void disconnectFromServer(){
        serverClient.disconnect();
    }
    // w 1x1 / w 2x2
    @Test
    void validLookShouldSucceed(){
        // Given that I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());
        // And the world is of size 1x1 (The world is configured or hardcoded to this size)assertTrue(serverClient.isConnected());

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

        // Then I should get a valid response from the server
        assertNotNull(response2.get("result"));
        assertEquals("OK", response2.get("result").asText());

        // And the position should be (x:0, y:0)
        assertNotNull(response2.get("data"));
        assertNotNull(response2.get("state"));
    }

    // w 2x2
    @Test
    void LookForObjects(){
        // Given that I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());
        // And the world is of size 1x1 (The world is configured or hardcoded to this size)assertTrue(serverClient.isConnected());

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

        // Then I should get a valid response from the server
        assertNotNull(response2.get("result"));
        assertEquals("OK", response2.get("result").asText());

        // And the position should be (x:0, y:0)
        assertNotNull(response2.get("data"));

        assertNotNull(response2.get("state").get("position"));

        List<String> objectList = Arrays.asList("EDGE", "Pit", "Robot", "Mines", "OBSTACLE");
        // Then I should get a valid response from the server

        assertNotNull(response2.get("result"));
        assertEquals("OK", response2.get("result").asText());

        // And the position should be (x:0, y:5)
        assertNotNull(response2.get("data"));
        assertTrue(objectList.contains(response2.get("data").get("objects").get(0).get("type").asText()));
    }


    @Test
    void LookForDistance(){
        // Given that I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());
        // And the world is of size 1x1 (The world is configured or hardcoded to this size)assertTrue(serverClient.isConnected());

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

        assertNotNull(response2.get("data").get("objects").get(0).get("distance").asInt());
        // And I should also get the state of the robot
        assertNotNull(response2.get("state"));
    }

    @Test
    void AllDirections(){
        // Given that I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());
        // And the world is of size 1x1 (The world is configured or hardcoded to this size)assertTrue(serverClient.isConnected());

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

        assertNotNull(response2.get("data").get("objects").asText().contains("NORTH"));
        assertNotNull(response2.get("data").get("objects").asText().contains("EAST"));
        assertNotNull(response2.get("data").get("objects").asText().contains("WEST"));
        assertNotNull(response2.get("data").get("objects").asText().contains("SOUTH"));

    }


    @Test
    void invalidLookShouldFail(){
        // Given that I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());

        // When I send a invalid launch request with the command "luanch" instead of "launch"
        String request1 = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        String request2 = "{" +
                "\"robot\": \"HAL\"," +
                "\"command\": \"looking\"," +
                "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";

        JsonNode response1 = serverClient.sendRequest(request1);
        JsonNode response2 = serverClient.sendRequest(request2);

        // Then I should get an error response
        assertNotNull(response2.get("result"));
        assertEquals("ERROR", response2.get("result").asText());

        // And the message "Unsupported command"
        assertNotNull(response2.get("data"));
        assertNotNull(response2.get("data").get("message"));
        assertTrue(response2.get("data").get("message").asText().equalsIgnoreCase("Unsupported command"));
    }
}
