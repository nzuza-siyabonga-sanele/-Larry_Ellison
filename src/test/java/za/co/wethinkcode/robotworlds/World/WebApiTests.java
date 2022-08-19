package za.co.wethinkcode.robotworlds.world;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.API.Server.WebServer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WebApiTests {
    private static WebServer server;

    @BeforeAll
    public static void startServer() {
        server = new WebServer();
        server.start(5000);
    }

    @AfterAll
    public static void stopServer() {
        server.stop();
    }

    @Test
    @DisplayName("GET /world/{id}")
    public void getOneWorld() throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.get("http://localhost:5000/world/1").asJson();
        assertEquals(200, response.getStatus());
        assertEquals("application/json", response.getHeaders().getFirst("Content-Type"));

        JSONObject jsonObject = response.getBody().getObject();
        assertEquals("There is new world that has been created", jsonObject.get("text"));
        assertEquals("World1", jsonObject.get("name"));
    }

    @Test
    @DisplayName("GET /worlds")
    void getAllWorlds() throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.get("http://localhost:5000/world").asJson();
        assertEquals(200, response.getStatus());
        assertEquals("application/json", response.getHeaders().getFirst("Content-Type"));

        JSONArray jsonArray = response.getBody().getArray();
        assertTrue (jsonArray.length() > 1);
    }

//    @Test
//    @DisplayName("POST /worlds")
//    void create() throws UnirestException {
//        HttpResponse<JsonNode> response = Unirest.post("http://localhost:5000/world")
//                .header("Content-Type", "application/json")
//                .body(ApiWorld.create("Created new world", "World4"))
//                .asJson();
//        assertEquals(201, response.getStatus());
//        assertEquals("/world/4", response.getHeaders().getFirst("Location"));
//
//        response = Unirest.get("http://localhost:5000/quote/4").asJson();
//        assertEquals(200, response.getStatus());
//    }
}
