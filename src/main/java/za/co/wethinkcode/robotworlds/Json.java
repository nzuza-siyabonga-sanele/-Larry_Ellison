package za.co.wethinkcode.robotworlds;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Json {

    private static  ObjectMapper defaultnode = new ObjectMapper();;
    private final HashMap<String, Object> map;
    private final ArrayList<Object> argument;

    private static ObjectMapper getNode(){
        return defaultnode;
    }

    public static JsonNode perse(String command) throws JsonProcessingException {
        return getNode().readTree(command);
    }

    public Json() {
        this.map = new HashMap<>();
        this.argument = new ArrayList<>();

    }

    public void setMap(String key, Object value) {
        this.map.put(key, value);
    }

    public void setArgument(Object argument) {
        this.argument.add(argument);
    }

    private Map<String, Object> getMap() {
        return map;
    }

    private ArrayList<Object> getArgument() {
        return argument;
    }

    public JSONObject getJsonData() {
        return new JSONObject(map);
    }
}
