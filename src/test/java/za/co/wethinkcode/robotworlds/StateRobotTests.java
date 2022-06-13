package za.co.wethinkcode.robotworlds;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Client.Play;

import static org.junit.jupiter.api.Assertions.*;

public class StateRobotTests {

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
    void validStateRequestShouldSucceed(){
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 1x1 (The world is configured or hardcoded to this size)
        assertTrue(serverClient.isConnected());

        String request1 = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";

        // When I send a valid state request to the server
        String request2 = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"state\"," +
                "  \"arguments\": []" +
                "}";

        JsonNode response1 = serverClient.sendRequest(request1);
        JsonNode response2 = serverClient.sendRequest(request2);

        // Then I should get a valid response from the server
        assertNotNull(response2.get("result"));
        assertEquals("OK", response2.get("result").asText());

        // And the position should be (x:0, y:0)
        assertEquals(0, response2.get("state").get("shields").asInt());
        assertEquals(0, response2.get("state").get("shots").asInt());

        // And I should also get the state of the robot
        assertNotNull(response2.get("state"));
    }

    @Test
    void invalidStateRequestShouldFAil(){
        assertTrue(serverClient.isConnected());

        String request1 = "{" +
                " \"robot\": \" Hal\"," +
                " \"command\": \"launch\"," +
                "  \"arguments\": [\"sniper\",\"5\",\"5\"]" +
                "}";


        String request2 = "{" +
                " \"robot\": \" Hal\"," +
                " \"command\": \"stale\"," +
                "  \"arguments\": []" +
                "}";

        JsonNode response1 = serverClient.sendRequest(request1);
        JsonNode response2 = serverClient.sendRequest(request2);

        //I should get an invalid response from the server.
        assertNotNull(response2.get("result"));
        assertEquals("ERROR", response2.get("result").asText());


        // And the message "Unsupported command"S
        assertNotNull(response2.get("data"));
        assertNotNull(response2.get("data").get("message"));
        assertTrue(response2.get("data").get("message").asText().contains("Unsupported command"));
    }

}

