package za.co.wethinkcode.robotworlds.World2x2;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.RobotWorldClient;
import za.co.wethinkcode.robotworlds.RobotWorldJsonClient;

import static org.junit.jupiter.api.Assertions.*;

/**
 * As a player
 * I want to launch my robot in the online robot world
 * So that I can break the record for the most robot kills
 */

public class LaunchRobotTests {
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
    void launch_8_Robot_ShouldSucceed_WithObstacle(){

        // Given that I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());

        // launch 8 robot to the Robot Worlds server
        for(int i = 1 ; i < 9 ; i++){
            String request = "{" +
                    "\"robot\": \"HAL"+i+"\"," +
                    "\"command\": \"launch\"," +
                    "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
                    "}";

            JsonNode response = serverClient.sendRequest(request);

            assertNotNull(response.get("result"));
            assertEquals("OK", response.get("result").asText());
        }

        String request1 = "{" +
                "\"robot\": \"HAL10\"," +
                "\"command\": \"launch\"," +
                "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";

        // When I launch many robot request to join the sever
        JsonNode response1 = serverClient.sendRequest(request1);

        // Then I should get an error response
        assertNotNull(response1.get("result"));
        assertEquals("ERROR", response1.get("result").asText());

        // And the message "Unsupported command"
        assertNotNull(response1.get("data"));
        assertNotNull(response1.get("data").get("message"));
        assertEquals("No more space in this world",response1.get("data").get("message").asText());
    }

    @Test
    void launchRobotNotOnTopOfObstacle(){

        // Given that I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());

        // launch 8 robot to the Robot Worlds server
        for(int i = 1 ; i < 9 ; i++){
            String request = "{" +
                    "\"robot\": \"HAL"+i+"\"," +
                    "\"command\": \"launch\"," +
                    "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
                    "}";

            JsonNode response = serverClient.sendRequest(request);

            assertNotNull(response.get("result"));
            assertEquals("OK", response.get("result").asText());

            assertNotNull(response.get("state"));
            assertNotNull(response.get("state").get("position"));
            assertNotEquals(2, response.get("state").get("position").get(0).asInt()
                    + response.get("state").get("position").get(1).asInt());

        }
    }

    @Test
    void launchNameAlreadyTaken(){

        // Given that I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());

        // launch two robot with same name to the Robot Worlds server
        // launch 9 robot to the Robot Worlds server
        for(int i = 1 ; i < 5 ; i++){
            String request = "{" +
                    "\"robot\": \"HAL"+i+"\"," +
                    "\"command\": \"launch\"," +
                    "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
                    "}";

            JsonNode response = serverClient.sendRequest(request);

            assertNotNull(response.get("result"));
            assertEquals("OK", response.get("result").asText());

        }

        String request2 = "{" +
                "\"robot\": \"HAL4\"," +
                "\"command\": \"launch\"," +
                "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";

        // When I launch more then one robot request with the same name to join the sever
        JsonNode response2 = serverClient.sendRequest(request2);

        // Then I should get an error response
        assertNotNull(response2.get("result"));
        assertEquals("ERROR", response2.get("result").asText());

        // And the message "Unsupported command"
        assertNotNull(response2.get("data"));
        assertNotNull(response2.get("data").get("message"));
        assertTrue(response2.get("data").get("message").asText().contains("Too many of you in this world"));
    }


    @Test
    void LaunchMoreThen_1_RobotSucceed(){

        assertTrue(serverClient.isConnected());


        String request = "{" +
                "\"robot\":  \"HAL1\"," +
                "\"command\": \"launch\"," +
                "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";

        JsonNode response = serverClient.sendRequest(request);

        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());

        String request1 = "{" +
                "\"robot\": \"HAL2\"," +
                "\"command\": \"launch\"," +
                "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";

        // When I launch many robot request to join the sever
        JsonNode response1 = serverClient.sendRequest(request1);

        assertNotNull(response.get("result"));
        assertEquals("OK", response1.get("result").asText());

        assertNotNull(response1.get("data"));
        assertEquals("",response1.get("data").asText());

    }
}
