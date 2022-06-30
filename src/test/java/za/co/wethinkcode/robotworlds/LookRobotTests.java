package za.co.wethinkcode.robotworlds;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Client.Play;
import za.co.wethinkcode.robotworlds.Server.Server;

import java.lang.reflect.Array;
import java.util.ArrayList;
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

        System.out.println(response2.get("data"));
        assertNotNull(response2.get("data").get("position"));
        assertEquals(0, response2.get("data").get("position").get(0).asInt());
        assertEquals(0, response2.get("data").get("position").get(1).asInt());



        List<String> directionList = Arrays.asList("NORTH", "EAST", "SOUTH", "WEST");
        List<String> objectList = Arrays.asList("EDGE", "Pit", "Robot", "Mines", "OBSTACLE");

//
        assertTrue(directionList.contains(response2.get("data").get("objects").get(0).get("direction").asText()));
        assertTrue(objectList.contains(response2.get("data").get("objects").get(0).get("type").asText()));

        System.out.println(response2.get("data").get("objects").findValuesAsText("type").indexOf("OBSTACLE"));
        System.out.println(response2.get("data").get("objects").get(response2.get("data").get("objects").findValuesAsText("type").indexOf("OBSTACLE")));


        assertTrue(response2.get("data").get("objects").findValuesAsText("type").contains("OBSTACLE"));
        assertTrue(response2.get("data").get("objects").findValuesAsText("type").contains("OBSTACLE"));
        ///
        assertNotNull(response2.get("data").get("objects").get(0).get("distance").asInt());
        // And I should also get the state of the robot
        assertNotNull(response2.get("state"));
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
