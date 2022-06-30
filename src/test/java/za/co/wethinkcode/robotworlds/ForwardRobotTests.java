package za.co.wethinkcode.robotworlds;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Client.Play;

import static org.junit.jupiter.api.Assertions.*;

public class ForwardRobotTests {

    private final static int DEFAULT_PORT = 5000;
    private final static String DEFAULT_IP = "localhost";
    private final Play serverClient = new Play();



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
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 1x1 (The world is configured or hardcoded to this size)
        assertTrue(serverClient.isConnected());
//        assertTrue(serverClient_.isConnected());


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

        // And the position should be (x:0, y:5)
        assertNotNull(response2.get("data"));
        assertNotNull(response2.get("data").get("message"));

        // And I should also get the state of the robot
        assertNotNull(response2.get("state"));
        assertTrue(response2.get("state").get("position").asText().contains("0"));
        assertTrue(response2.get("state").get("position").asText().contains("5"));
    }

    @Test
    void atTheEdgeOfTheWorld(){
        // Given that I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());


        String request = "{" +
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

        JsonNode response = serverClient.sendRequest(request);
        JsonNode response2 = serverClient.sendRequest(request2);

        // Then I should get an error response
        assertNotNull(response2.get("result"));
        assertEquals("OK", response.get("result").asText());

        // And the message "Unsupported command"
        System.out.println(response2);
        assertNotNull(response2.get("data"));
        assertNotNull(response2.get("data").get("message"));
        assertTrue(response2.get("data").get("message").asText().contains("At the NORTH edge"));
    }
}


