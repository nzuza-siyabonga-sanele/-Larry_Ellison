package za.co.wethinkcode.robotworlds.API.World;

import io.javalin.http.Context;
import org.json.JSONException;
import org.json.JSONObject;

public class CreateWorld {

    private final Context context;
    private String name;
//    private int size;
//    private String obstacles;
//
//    private String ObstacleSize = "0.1x0.1";

    public CreateWorld(Context context) {
        this.context = context;
        name = context.pathParamAsClass("id",String.class).get();
    }

    public String[] getCommandLine(){
        String[] args = {"selected", name};
        return args;
    }

//    public void read_Post_Command_Line_Argument(){
//
//        String json = context.body();
//        JSONObject js;
//        try{
//            js = new JSONObject(json);
//            name = js.getString("id");
//            size = js.getInt("Size");
//            obstacles = js.getString("Obstacles");
//        }catch (JSONException e){
//            System.out.println(e);
//        }
//    }
//
//    public String[] Request(){
//
//        String[] args = {"insert", name, String.valueOf(size), obstacles};
//        return args;
//    }
}
