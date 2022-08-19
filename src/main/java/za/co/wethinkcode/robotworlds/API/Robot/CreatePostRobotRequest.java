package za.co.wethinkcode.robotworlds.API.Robot;

import io.javalin.http.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CreatePostRobotRequest {

    private Context context;
    static String robots;
    static String commands;
    static String argument;

    public CreatePostRobotRequest(Context context) {
        this.context = context;
        robots = context.pathParamAsClass("id",String.class).get();

    }

    public void read_Post_Command_Line_Argument(){

        String json = context.body();
        JSONObject js;
        try{
            js = new JSONObject(json);
            commands = js.getString("command");
            argument = js.getString("arguments");
        }catch (JSONException e){
            System.out.println(e);
        }
    }

    public JSONObject Request(){

        JSONObject request = new JSONObject();
        JSONArray arguments = new JSONArray();
        RobotQuate apiRobotInfo = new RobotQuate();

        arguments.put(argument);

        request.put("robot", robots);
        request.put("command", commands.toLowerCase());
        request.put("arguments", arguments);

        return request;
    }
}
