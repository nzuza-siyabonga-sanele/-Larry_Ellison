package za.co.wethinkcode.robotworlds.API.Server;

import com.fasterxml.jackson.databind.JsonNode;
import io.javalin.Javalin;
import io.javalin.http.Context;
import za.co.wethinkcode.robotworlds.API.Robot.CreatePostRobotRequest;
import za.co.wethinkcode.robotworlds.API.Robot.RobotApiHandler;
import za.co.wethinkcode.robotworlds.API.Robot.RobotQuate;
import za.co.wethinkcode.robotworlds.API.World.CreateWorld;
import za.co.wethinkcode.robotworlds.API.World.WorldApiHandler;
import za.co.wethinkcode.robotworlds.Server.Server;

public class WebServer {

    private final Javalin server;

    public static String[] args = {};

    public WebServer() {

        server = Javalin.create(config -> {
            config.defaultContentType = "application/json";
        });

        // We get all the worlds from the SQLite DataBase
        this.server.get("/worlds/", context -> WorldApiHandler.getAll(context));
        // We get one world from the SQLite DataBase
        this.server.get("/world/{id}", context -> {
            CreateWorld createWorld = new CreateWorld(context);
            args = createWorld.getCommandLine();
            WorldApiHandler.getOne(context);
        });

        this.server.get("/robot/{id}",context -> {
            RobotApiHandler.getOne(context);
        });
        // We post the robot to the world
        this.server.post("/robot/{id}", context -> {

            CreatePostRobotRequest set_request = new CreatePostRobotRequest(context);
            set_request.read_Post_Command_Line_Argument();

            String serverResponse = Server.receiverRequest(String.valueOf(set_request.Request())).toString();
            RobotApiHandler.create(context, set_request.Request(),serverResponse);

                });

    }

    public void start(int port) {
        this.server.start(port);
    }

    public void stop() {
        this.server.stop();
    }
}
