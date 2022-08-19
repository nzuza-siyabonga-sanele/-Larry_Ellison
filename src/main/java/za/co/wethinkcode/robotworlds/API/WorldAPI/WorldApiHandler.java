package za.co.wethinkcode.robotworlds.API.World;

import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import io.javalin.http.NotFoundResponse;
import za.co.wethinkcode.robotworlds.API.Quates;
import za.co.wethinkcode.robotworlds.API.Robot.RobotQuate;

public class WorldApiHandler {
    static final Quates<ApiWorld> database = new TestWorldData();

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
     * @param context     The Javalin Context for the HTTP GET Request
     */
    public static void getOne(Context context) {

        String id = context.pathParamAsClass("id", String.class).get();

        ApiWorld apiWorld = null;
        for(ApiWorld world : database.all()){
            if(world.getId().equals(id.strip())) {
                apiWorld = world;
            }
        }
        if(apiWorld == null){
            context.json("RobotWorld not found");
            return;
        }
        context.json(apiWorld);


        ApiWorld world = database.get(id);
        context.json(world);
    }

    /**
     * Create a new quote
     *
     * @param context The Javalin Context for the HTTP POST Request
     * @param request
     * @return
     */
    public static String create(Context context, String[] request) {


//        ApiWorld world = context.bodyAsClass(ApiWorld.class);

////        RobotWorldDb app = new RobotWorldDb( request );
//        ApiWorld world = database.get(request[1]);
//
//        boolean newWorld = database.add(world);
//        context.header("Location", "world " + newWorld.getId());
//        //status 201
//        context.status(HttpCode.CREATED);
//        context.json(newWorld);
//        return newWorld.getState();
        return "";

    }
    public static void main(Context context) {
        String id = context.pathParamAsClass("id", String.class).get();
        ApiWorld world = database.get(id);
        if(world == null) {
            throw new NotFoundResponse("World not found: " + id);
        }
        context.json(world);
    }
}

