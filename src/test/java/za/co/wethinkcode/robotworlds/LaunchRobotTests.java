package za.co.wethinkcode.robotworlds;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Client.Play;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * As a player
 * I want to launch my robot in the online robot world
 * So that I can break the record for the most robot kills
 */
class LaunchRobotTests {
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
    void invalidLaunchShouldFail(){
        // Given that I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());

        // When I send a invalid launch request with the command "luanch" instead of "launch"
        String request = "{" +
                "\"robot\": \"HAL\"," +
                "\"command\": \"luanch\"," +
                "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        JsonNode response = serverClient.sendRequest(request);

        // Then I should get an error response
        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());

        // And the message "Unsupported command"
        System.out.println(response);
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("message"));
        assertTrue(response.get("data").get("message").asText().contains("Unsupported command"));
    }

    @Test
    void validlaunchNoFreeLocation(){

        // Given that I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());

        // launch more than 4 robot to the Robot Worlds server
        // launch Robot 1 and add to the list of players
        String request1 = "{" +
                "\"robot\": \"HAL\"," +
                "\"command\": \"launch\"," +
                "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        // launch Robot 2 and add to the list of players
        String request2 = "{" +
                "\"robot\": \"HAL1\"," +
                "\"command\": \"launch\"," +
                "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        // launch Robot 3 and add to the list of players
        String request3 = "{" +
                "\"robot\": \"HAL2\"," +
                "\"command\": \"launch\"," +
                "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        // launch Robot 4 and add to the list of players
        String request4 = "{" +
                "\"robot\": \"HAL3\"," +
                "\"command\": \"launch\"," +
                "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        // launch Robot 5 and add to the list of players
        String request5 = "{" +
                "\"robot\": \"HAL4\"," +
                "\"command\": \"launch\"," +
                "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";

        // When I launch many robot request to join the sever
        JsonNode response = serverClient.sendRequest(request1);
        JsonNode response1 = serverClient.sendRequest(request2);
        JsonNode response2 = serverClient.sendRequest(request3);
        JsonNode response3 = serverClient.sendRequest(request4);
        JsonNode response4 = serverClient.sendRequest(request5);

        System.out.println(response4);

        // Then I should get an error response
        assertNotNull(response4.get("result"));
        assertEquals("ERROR", response4.get("result").asText());

        // And the message "Unsupported command"
        assertNotNull(response4.get("data"));
        assertNotNull(response4.get("data").get("message"));
        assertTrue(response4.get("data").get("message").asText().contains("No more space in this world"));

    }

    @Test
    void launchNameAlreadyTaken(){

        // Given that I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());

        // launch two robot with same name to the Robot Worlds server
        // launch Robot 1 and add to the list of players
        String request1 = "{" +
                "\"robot\": \"HAL\"," +
                "\"command\": \"launch\"," +
                "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        // launch Robot 2
        String request2 = "{" +
                "\"robot\": \"HAL\"," +
                "\"command\": \"launch\"," +
                "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";

        // When I launch more then one robot request with the same name to join the sever
        JsonNode response = serverClient.sendRequest(request1);
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
    void validLaunchShouldSucceed(){
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 1x1 (The world is configured or hardcoded to this size)
        assertTrue(serverClient.isConnected());

        // When I send a valid launch request to the server
        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        JsonNode response = serverClient.sendRequest(request);

        // Then I should get a valid response from the server
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());

        // And the position should be (x:0, y:0)
        assertNotNull(response.get("state"));
        assertNotNull(response.get("state").get("position"));
        assertEquals(0, response.get("state").get("position").get(0).asInt());
        assertEquals(0, response.get("state").get("position").get(1).asInt());

        // And I should also get the state of the robot
        assertNotNull(response.get("state"));
    }

    @Test
    void WorldWithoutObstacleIsFull() {

        // Given that I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());

        // launch more than 9 robots to the Robot Worlds server


        for(int i = 0; i < 9; i++) {
            String request1 = "{" +
                    "\"robot\": " + i + "," +
                    "\"command\": \"launch\"," +
                    "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
                    "}";

            JsonNode response = serverClient.sendRequest(request1);

            assertNotNull(response.get("result"));
            assertEquals("OK", response.get("result").asText());

            assertNotNull(response.get("data"));
            assertNotNull(response.get("data").get("position"));

            assertNotNull(response.get("state"));

        }

        String request10 = "{" +
                "\"robot\": \"BOB\"," +
                "\"command\": \"launch\"," +
                "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";

        JsonNode response = serverClient.sendRequest(request10);

        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());

        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("message"));
        assertTrue(response.get("data").get("message").asText().contains("No more space in this world"));

    }

    @Test
    void CanLaunchANotherRobot() {


        // Given that I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());

        // launch robot to the Robot Worlds server
        for (int i = 0; i < 2; i++) {
            String request1 = "{" +
                    "\"robot\": " + i + "," +
                    "\"command\": \"launch\"," +
                    "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
                    "}";


            JsonNode response = serverClient.sendRequest(request1);

            assertNotNull(response.get("result"));
            assertEquals("OK", response.get("result").asText());

            assertNotNull(response.get("data"));
            assertNotNull(response.get("data").get("position"));

//            assertEquals(0, response.get("data").get("position").get(0).asInt());
//            assertEquals(0, response.get("data").get("position").get(1).asInt());

            assertNotNull(response.get("state"));
        }
        // launch Robot 2 and add to the list of players
        String request2 = "{" +
                "\"robot\": \"HAL1\"," +
                "\"command\": \"launch\"," +
                "\"arguments\": [\"sniper\",\"5\",\"5\"]" +
                "}";

        JsonNode response = serverClient.sendRequest(request2);
        System.out.println(response);
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());

        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("position"));

//            assertEquals(-1, response.get("data").get("position").get(0).asInt());
//            assertEquals(-1, response.get("data").get("position").get(1).asInt());

        assertNotNull(response.get("state"));

    }

    @Test
    void WorldWithAnObstacleIsFull(){
        // Given that I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());

        // launch more than 9 robots to the Robot Worlds server


        for(int i = 0; i < 8; i++) {
            String request1 = "{" +
                    "\"robot\": " + i + "," +
                    "\"command\": \"launch\"," +
                    "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
                    "}";

            JsonNode response = serverClient.sendRequest(request1);

            assertNotNull(response.get("result"));
            assertEquals("OK", response.get("result").asText());

            assertNotNull(response.get("data"));
            assertNotNull(response.get("data").get("position"));

            assertNotNull(response.get("state"));

        }

        String request9 = "{" +
                "\"robot\": \"BOB\"," +
                "\"command\": \"launch\"," +
                "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";

        JsonNode response = serverClient.sendRequest(request9);
        System.out.println(response);

        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());

        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("message"));
        assertTrue(response.get("data").get("message").asText().contains("No more space in this world"));

    }

    @Test
    void LaunchRobotsIntoWorldWithAnObstacle(){

        assertTrue(serverClient.isConnected());

        for(int i = 0; i < 8; i++) {
            String request1 = "{" +
                    "\"robot\": " + i + "," +
                    "\"command\": \"launch\"," +
                    "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
                    "}";

            JsonNode response = serverClient.sendRequest(request1);

            assertNotNull(response.get("result"));
            assertEquals("OK", response.get("result").asText());

            assertNotNull(response.get("data"));
            assertNotNull(response.get("data").get("position"));

            assertNotNull(response.get("state"));

        }
    }

}



