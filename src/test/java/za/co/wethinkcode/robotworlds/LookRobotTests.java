package za.co.wethinkcode.robotworlds;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.server.RobotWorldClient;

import static org.junit.Assert.*;


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

        String request2 = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"look\"," +
                "  \"arguments\": []" +
                "}";


        JsonNode response = serverClient.sendRequest(request);
        response = serverClient.sendRequest(request2);

        // Then I should get a valid response from the server
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());

        // And the position should be (x:0, y:0)
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("position"));
        assertEquals(0, response.get("data").get("position").get(0).asInt());
        assertEquals(0, response.get("data").get("position").get(1).asInt());


        //assertEquals("null", response.get("data").get("objects"));
        System.out.println(response.get("data").get("objects").get(0).get("type"));
        assertEquals(null,response.get("data").get("objects").get(0).get("direction").get("EAST"));
        assertEquals("EDGE", response.get("data").get("objects").get(0).get("type").asText());
        assertEquals(1, response.get("data").get("objects").get(0).get("distance").asInt());
        // And I should also get the state of the robot
        assertNotNull(response.get("state"));
    }
    @Test
    void invalidLookShouldFail(){
        // Given that I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());

        // When I send a invalid launch request with the command "luanch" instead of "launch"
        String request = "{" +
                "\"robot\": \"HAL\"," +
                "\"command\": \"looking\"," +
                "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        JsonNode response = serverClient.sendRequest(request);

        // Then I should get an error response
        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());

        // And the message "Unsupported command"
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("message"));
        assertTrue(response.get("data").get("message").asText().contains("Unsupported command"));
    }
}


//package za.co.wethinkcode.robotworlds;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import za.co.wethinkcode.robots.server.RobotWorldClient;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
//import static org.junit.jupiter.api.Assertions.*;
//package za.co.wethinkcode.robotworlds;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.Assert.*;
//
//
///**
// *As a player
// * I want to look for other robots, in the online robot world
// * So that I can determine my next move
// */
//public class LookRobotTests {
//    private final static int DEFAULT_PORT = 5000;
//    private final static String DEFAULT_IP = "localhost";
//    private final RobotWorldClient serverClient = new RobotWorldJsonClient();
//
//    @BeforeEach
//    void connectToServer(){
//        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
//    }
//
//    @AfterEach
//    void disconnectFromServer(){
//        serverClient.disconnect();
//    }
//    @Test
//    void validLaunchShouldSucceed(){
//        // Given that I am connected to a running Robot Worlds server
//        // And the world is of size 1x1 (The world is configured or hardcoded to this size)
//        assertTrue(serverClient.isConnected());
//
//        // When I send a valid launch request to the server
//        String request = "{" +
//                "  \"robot\": \"HAL\"," +
//                "  \"command\": \"launch\"," +
//                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
//                "}";
//
//        String request2 = "{" +
//                "  \"robot\": \"HAL\"," +
//                "  \"command\": \"look\"," +
//                "  \"arguments\": []" +
//                "}";
//
//
//        JsonNode response = serverClient.sendRequest(request);
//        response = serverClient.sendRequest(request2);
//
//        // Then I should get a valid response from the server
//        assertNotNull(response.get("result"));
//        assertEquals("OK", response.get("result").asText());
//
//        // And the position should be (x:0, y:0)
//        assertNotNull(response.get("data"));
//        assertNotNull(response.get("data").get("position"));
//        assertEquals(0, response.get("data").get("position").get(0).asInt());
//        assertEquals(0, response.get("data").get("position").get(1).asInt());
//
//
//        //assertEquals("null", response.get("data").get("objects"));
//        System.out.println(response.get("data").get("objects").get(0).get("type"));
//        assertEquals(null,response.get("data").get("objects").get(0).get("direction").get("EAST"));
//        assertEquals("EDGE", response.get("data").get("objects").get(0).get("type").asText());
//        assertEquals(1, response.get("data").get("objects").get(0).get("distance").asInt());
//        // And I should also get the state of the robot
//        assertNotNull(response.get("state"));
//    }
//    @Test
//    void invalidLookShouldFail(){
//        // Given that I am connected to a running Robot Worlds server
//        assertTrue(serverClient.isConnected());
//
//        // When I send a invalid launch request with the command "loko" instead of "launch"
//        String request = "{" +
//                "\"robot\": \"HAL\"," +
//                "\"command\": \"loko\"," +
//                "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
//                "}";
//        JsonNode response = serverClient.sendRequest(request);
//
//        // Then I should get an error response
//        assertNotNull(response.get("result"));
//        assertEquals("ERROR", response.get("result").asText());
//
//        // And the message "Unsupported command"
//        assertNotNull(response.get("data"));
//        assertNotNull(response.get("data").get("message"));
//        assertTrue(response.get("data").get("message").asText().contains("Unsupported command"));
//    }
//
//
//
//}package za.co.wethinkcode.robotworlds;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.Assert.*;
//
//
///**
// *As a player
// * I want to look for other robots, in the online robot world
// * So that I can determine my next move
// */
//public class LookRobotTests {
//    private final static int DEFAULT_PORT = 5000;
//    private final static String DEFAULT_IP = "localhost";
//    private final RobotWorldClient serverClient = new RobotWorldJsonClient();
//
//    @BeforeEach
//    void connectToServer(){
//        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
//    }
//
//    @AfterEach
//    void disconnectFromServer(){
//        serverClient.disconnect();
//    }
//    @Test
//    void validLaunchShouldSucceed(){
//        // Given that I am connected to a running Robot Worlds server
//        // And the world is of size 1x1 (The world is configured or hardcoded to this size)
//        assertTrue(serverClient.isConnected());
//
//        // When I send a valid launch request to the server
//        String request = "{" +
//                "  \"robot\": \"HAL\"," +
//                "  \"command\": \"launch\"," +
//                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
//                "}";
//
//        String request2 = "{" +
//                "  \"robot\": \"HAL\"," +
//                "  \"command\": \"look\"," +
//                "  \"arguments\": []" +
//                "}";
//
//
//        JsonNode response = serverClient.sendRequest(request);
//        response = serverClient.sendRequest(request2);
//
//        // Then I should get a valid response from the server
//        assertNotNull(response.get("result"));
//        assertEquals("OK", response.get("result").asText());
//
//        // And the position should be (x:0, y:0)
//        assertNotNull(response.get("data"));
//        assertNotNull(response.get("data").get("position"));
//        assertEquals(0, response.get("data").get("position").get(0).asInt());
//        assertEquals(0, response.get("data").get("position").get(1).asInt());
//
//
//        //assertEquals("null", response.get("data").get("objects"));
//        System.out.println(response.get("data").get("objects").get(0).get("type"));
//        assertEquals(null,response.get("data").get("objects").get(0).get("direction").get("EAST"));
//        assertEquals("EDGE", response.get("data").get("objects").get(0).get("type").asText());
//        assertEquals(1, response.get("data").get("objects").get(0).get("distance").asInt());
//        // And I should also get the state of the robot
//        assertNotNull(response.get("state"));
//    }
//    @Test
//    void invalidLookShouldFail(){
//        // Given that I am connected to a running Robot Worlds server
//        assertTrue(serverClient.isConnected());
//
//        // When I send a invalid launch request with the command "luanch" instead of "launch"
//        String request = "{" +
//                "\"robot\": \"HAL\"," +
//                "\"command\": \"looking\"," +
//                "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
//                "}";
//        JsonNode response = serverClient.sendRequest(request);
//
//        // Then I should get an error response
//        assertNotNull(response.get("result"));
//        assertEquals("ERROR", response.get("result").asText());
//
//        // And the message "Unsupported command"
//        assertNotNull(response.get("data"));
//        assertNotNull(response.get("data").get("message"));
//        assertTrue(response.get("data").get("message").asText().contains("Unsupported command"));
//    }
//}
//
//
////package za.co.wethinkcode.robotworlds;
////
////import com.fasterxml.jackson.databind.JsonNode;
////import org.junit.jupiter.api.AfterEach;
////import org.junit.jupiter.api.BeforeEach;
////import org.junit.jupiter.api.Test;
////import za.co.wethinkcode.robots.server.RobotWorldClient;
////
////import static org.junit.Assert.assertEquals;
////import static org.junit.Assert.assertNotNull;
////import static org.junit.Assert.assertTrue;
////import static org.junit.jupiter.api.Assertions.*;
////package za.co.wethinkcode.robotworlds;
////
////import com.fasterxml.jackson.databind.JsonNode;
////import org.junit.jupiter.api.AfterEach;
////import org.junit.jupiter.api.BeforeEach;
////import org.junit.jupiter.api.Test;
////
////import static org.junit.Assert.*;
////
////
/////**
//// *As a player
//// * I want to look for other robots, in the online robot world
//// * So that I can determine my next move
//// */
////public class LookRobotTests {
////    private final static int DEFAULT_PORT = 5000;
////    private final static String DEFAULT_IP = "localhost";
////    private final RobotWorldClient serverClient = new RobotWorldJsonClient();
////
////    @BeforeEach
////    void connectToServer(){
////        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
////    }
////
////    @AfterEach
////    void disconnectFromServer(){
////        serverClient.disconnect();
////    }
////    @Test
////    void validLaunchShouldSucceed(){
////        // Given that I am connected to a running Robot Worlds server
////        // And the world is of size 1x1 (The world is configured or hardcoded to this size)
////        assertTrue(serverClient.isConnected());
////
////        // When I send a valid launch request to the server
////        String request = "{" +
////                "  \"robot\": \"HAL\"," +
////                "  \"command\": \"launch\"," +
////                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
////                "}";
////
////        String request2 = "{" +
////                "  \"robot\": \"HAL\"," +
////                "  \"command\": \"look\"," +
////                "  \"arguments\": []" +
////                "}";
////
////
////        JsonNode response = serverClient.sendRequest(request);
////        response = serverClient.sendRequest(request2);
////
////        // Then I should get a valid response from the server
////        assertNotNull(response.get("result"));
////        assertEquals("OK", response.get("result").asText());
////
////        // And the position should be (x:0, y:0)
////        assertNotNull(response.get("data"));
////        assertNotNull(response.get("data").get("position"));
////        assertEquals(0, response.get("data").get("position").get(0).asInt());
////        assertEquals(0, response.get("data").get("position").get(1).asInt());
////
////
////        //assertEquals("null", response.get("data").get("objects"));
////        System.out.println(response.get("data").get("objects").get(0).get("type"));
////        assertEquals(null,response.get("data").get("objects").get(0).get("direction").get("EAST"));
////        assertEquals("EDGE", response.get("data").get("objects").get(0).get("type").asText());
////        assertEquals(1, response.get("data").get("objects").get(0).get("distance").asInt());
////        // And I should also get the state of the robot
////        assertNotNull(response.get("state"));
////    }
////    @Test
////    void invalidLookShouldFail(){
////        // Given that I am connected to a running Robot Worlds server
////        assertTrue(serverClient.isConnected());
////
////        // When I send a invalid launch request with the command "loko" instead of "launch"
////        String request = "{" +
////                "\"robot\": \"HAL\"," +
////                "\"command\": \"loko\"," +
////                "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
////                "}";
////        JsonNode response = serverClient.sendRequest(request);
////
////        // Then I should get an error response
////        assertNotNull(response.get("result"));
////        assertEquals("ERROR", response.get("result").asText());
////
////        // And the message "Unsupported command"
////        assertNotNull(response.get("data"));
////        assertNotNull(response.get("data").get("message"));
////        assertTrue(response.get("data").get("message").asText().contains("Unsupported command"));
////    }
////
////
////
////}