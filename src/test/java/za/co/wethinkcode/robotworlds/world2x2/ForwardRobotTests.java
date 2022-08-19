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

public class ForwardRobotTests {

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
    void validForwardShouldSucceed(){

        assertTrue(serverClient.isConnected());

        // When I send a valid launch request to the server
        String request1 = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";

        // When I send a valid launch request to the server
        String request2 = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"forward\"," +
                "  \"arguments\": [\"5\"]" +
                "}";

        JsonNode response1 = serverClient.sendRequest(request1);
        JsonNode response2 = serverClient.sendRequest(request2);

        // Then I should get a valid response from the server

        assertNotNull(response2.get("result"));
        assertEquals("OK", response2.get("result").asText());

    }

    @Test
    void TheRobotState(){

        assertTrue(serverClient.isConnected());

        // When I send a valid launch request to the server
        String request1 = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";

        // When I send a valid launch request to the server
        String request2 = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"forward\"," +
                "  \"arguments\": [\"5\"]" +
                "}";

        JsonNode response1 = serverClient.sendRequest(request1);
        JsonNode response2 = serverClient.sendRequest(request2);

        // And I should also get the state of the robot
        assertNotNull(response2.get("state"));

    }

    @Test
    void ThePathIsBlocked(){

        assertTrue(serverClient.isConnected());

        // When I send a valid launch request to the server
        String request1 = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";

        // When I send a valid launch request to the server
        String request2 = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"forward\"," +
                "  \"arguments\": [\"5\"]" +
                "}";

        JsonNode response1 = serverClient.sendRequest(request1);
        JsonNode response2 = serverClient.sendRequest(request2);

        List<String> objectList = Arrays.asList("At the NORTH edge", "At the SOUTH edge", "At the EAST edge",
                "At the WEST edge", "Pit", "Robot", "Mines", "OBSTACLE");
        // Then I should get a valid response from the server

        assertNotNull(response2.get("result"));
        assertEquals("OK", response2.get("result").asText());

        // And the position should be (x:0, y:5)
        assertNotNull(response2.get("data"));
        assertTrue(objectList.contains(response2.get("data").get("message").asText()));

    }

    @Test
    void invalidForwardShouldFail(){

        assertTrue(serverClient.isConnected());

        // When I send a valid launch request to the server
        String request1 = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";

        // When I send a valid launch request to the server
        String request2 = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"forword\"," +
                "  \"arguments\": [\"5\"]" +
                "}";

        JsonNode response1 = serverClient.sendRequest(request1);
        JsonNode response2 = serverClient.sendRequest(request2);

        // Then I should get a valid response from the server

        assertNotNull(response2.get("result"));
        assertEquals("ERROR", response2.get("result").asText());

        assertNotNull(response2.get("data"));
        assertNotNull(response2.get("data").get("message"));
        assertEquals("Unsupported command",response2.get("data").get("message").asText());

    }
}
