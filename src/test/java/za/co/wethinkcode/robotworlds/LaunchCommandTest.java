package za.co.wethinkcode.robotworlds;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class LaunchCommandTest {
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






