package za.co.wethinkcode.robotworlds.API.Robot;

import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import io.javalin.http.NotFoundResponse;
import org.json.JSONObject;
import za.co.wethinkcode.robotworlds.API.Quates;

public class RobotApiHandler {

    static final Quates<RobotQuate> database = new AddRobotData();

    /**
     * Get all quotes
     *
     * @param context The Javalin Context for the HTTP GET Request
     */
    public static void getAll(Context context) {
        context.json(database.all());
    }

    /**
     * Get one quote
     *
     * @param context The Javalin Context for the HTTP GET Request
     */
    public static void getOne(Context context) {

        String id = context.pathParamAsClass("id", String.class).get();

        RobotQuate robotQuate = null;
        for(RobotQuate rob : database.all()){
            if(rob.getId().equals(id.strip())) {
                robotQuate = rob;
            }
        }
        if(robotQuate == null){
            context.json("Robot not found");
            return;
        }
        context.json(robotQuate);
    }

    /**
     * Create a new quote
     *
     * @param vs
     * @param context        The Javalin Context for the HTTP POST Request
     * @param request        The Json Object for our Server Request
     * @param serverResponse
     * @return
     */
    public static void create(Context context, JSONObject request, String serverResponse) {

        String id = context.pathParamAsClass("id", String.class).get();
        RobotQuate hal = new RobotQuate();
        hal.setId(id);
        hal.setState(serverResponse);
        database.add(id,hal);

        context.header("Location", "/robot/" + hal.getId());
        //status 201
        context.status(HttpCode.CREATED);
        context.json(hal);
    }


}


