package za.co.wethinkcode.robotworlds;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import za.co.wethinkcode.robots.domain.Position;
import za.co.wethinkcode.robots.server.RobotWorldClient;
import za.co.wethinkcode.robotworlds.Client.Play;
import za.co.wethinkcode.robotworlds.RobotWorldJsonClient;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * As a player
 * I want to launch my robot in the online robot world
 * So that I can break the record for the most robot kills
 */
class ForwardRobotTests {
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
    void validForward10ShouldSucceed(){
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 1x1 (The world is configured or hardcoded to this size)
        assertTrue(serverClient.isConnected());

        // When I send a valid launch request to the server
        String request = "{" +
                "  \"robot\": \"HALO\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        JsonNode respons = serverClient.sendRequest(request);
        assertNotNull(respons.get("result"));
        assertEquals("OK", respons.get("result").asText());



        request = "{" +
                "  \"robot\": \"HALO\"," +
                "  \"command\": \"forward\"," +
                "  \"arguments\": [\"10\"]" +
                "}";
//        System.out.println(request);
        JsonNode response = serverClient.sendRequest(request);
        response = serverClient.sendRequest(request);

//        Position pos = new Position(0, 0);
        // Then I should get an error response
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());

        // And the message "Unsupported command"
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("message"));
//        System.out.println(response.get("data").get("message").asText());
        assertTrue(response.get("data").get("message").asText().contains("At the NORTH edge"));
    }
    @Test
    void validForward101BlockedEdge(){
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 1x1 (The world is configured or hardcoded to this size)
        assertTrue(serverClient.isConnected());

        // When I send a valid launch request to the server
        String request = "{" +
                "  \"robot\": \"HALO\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        JsonNode respons = serverClient.sendRequest(request);
        assertNotNull(respons.get("result"));
        assertEquals("OK", respons.get("result").asText());



        request = "{" +
                "  \"robot\": \"HALO\"," +
                "  \"command\": \"forward\"," +
                "  \"arguments\": [\"110\"]" +
                "}";
//        System.out.println(request);
        JsonNode response = serverClient.sendRequest(request);
        response = serverClient.sendRequest(request);

//        Position pos = new Position(0, 0);
        // Then I should get an error response
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());

        // And the message "Unsupported command"
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("message"));
//        System.out.println(response.get("data").get("message").asText());
        assertTrue(response.get("data").get("message").asText().contains("At the NORTH edge"));
    }

    @Test
    void validForwardKCommandDoesNotExist(){
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 1x1 (The world is configured or hardcoded to this size)
        assertTrue(serverClient.isConnected());

        // When I send a valid launch request to the server
        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"forward\"," +
                "  \"arguments\": [\"ju\"]" +
                "}";
//        System.out.println(request);
        JsonNode response = serverClient.sendRequest(request);
        response = serverClient.sendRequest(request);

//        Position pos = new Position(0, 0);
        // Then I should get an error response
        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());

        // And the message "Unsupported command"
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("message"));

        assertTrue(response.get("data").get("message").asText().contains("Error while executing command"));
    }

    @Test
    void forwardCommandIntAndStrings(){

        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 1x1 (The world is configured or hardcoded to this size)
        assertTrue(serverClient.isConnected());

        // When I send a valid launch request to the server
        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"forward\"," +
                "  \"arguments\": [\"10ju\"]" +
                "}";
//        System.out.println(request);
        JsonNode response = serverClient.sendRequest(request);
        response = serverClient.sendRequest(request);

//        Position pos = new Position(0, 0);
        // Then I should get an error response
        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());

        // And the message "Unsupported command"
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("message"));

        assertTrue(response.get("data").get("message").asText().contains("Error while executing command"));
    }

}
