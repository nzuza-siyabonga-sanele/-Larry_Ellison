package za.co.wethinkcode.robotworlds.Commands;
import org.json.JSONObject;
import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.Server.ErrorHandling;
import za.co.wethinkcode.robotworlds.Server.MultiServer;
import za.co.wethinkcode.robotworlds.world.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LookCommand extends Command {

    int myRobotX;
    int myRobotY;
    private int myShields;
    private int myShots;

    public Json execute(Robot target){

        if(target.getHasLaunched().equals("null")){
            ErrorHandling error = new ErrorHandling("null", getCommand(), getArgument());
            return error.execute(target);
        }

        target.setStatus("looking");

        for (Map.Entry<String, Json> set : MultiServer.players.entrySet()) {
            if(set.getKey().equals(target.getName())){
                myRobotX = (Integer) set.getValue().getJsonData().getJSONObject("state").getJSONArray("position").get(0);
                myRobotY = (Integer) set.getValue().getJsonData().getJSONObject("state").getJSONArray("position").get(1);
                myShields = (Integer) set.getValue().getJsonData().getJSONObject("state").get("shields");
                myShots =  (Integer) set.getValue().getJsonData().getJSONObject("state").get("shots");
            }
        }

        Json data = new Json();

        data.setMap("visibility",target.getVisibility());
        data.setMap("objects",responseSetDirection(target));

        Json state = new Json();
        state.setMap("position", new int[]{myRobotX, myRobotY});
        state.setMap("direction", target.getWorld().getCurrentDirection());
        state.setMap("shields", myShields);
        state.setMap("shots", myShots);
        state.setMap("status", "normal");

        Json jData = new Json();
        jData.setMap("result", "OK");
        jData.setMap("data", data.getJsonData());
        jData.setMap("state", state.getJsonData());

        return jData;
    }
    public JSONObject lookNorth(Robot target){

        Json objects = new Json();

        objects.setMap("direction",target.getWorld().getCurrentDirection());
        int nrsteps = myRobotY + target.getVisibility();
        Position newPosition = new Position(myRobotX, nrsteps);

        IWorld.UpdateResponse response = target.getWorld().isRobotOrObstacleNear(target,newPosition);

        if(response.equals(IWorld.UpdateResponse.FAILED_OBSTRUCTED_ROBOT)){
            objects.setMap("type","ROBOT");
            objects.setMap("distance",responseDistance(target, target.getWorld().getOtherRobotY(), myRobotY));
        }else if(response.equals(IWorld.UpdateResponse.FAILED_OBSTRUCTED)){
            objects.setMap("type","OBSTACLE");
            objects.setMap("distance",responseDistance(target, target.getWorld().getObstacleY(), myRobotY));
        }else{
            objects.setMap("type","EDGE");
            objects.setMap("distance",responseDistance(target, AbstractWorld.TOP_LEFT.getY(), myRobotY));
        }
        target.getWorld().setCurrentDirection(IWorld.Direction.EAST);
        return objects.getJsonData();
    }

    public JSONObject lookEast(Robot target){

        Json objects = new Json();
        objects.setMap("direction",target.getWorld().getCurrentDirection());
        int nrsteps = myRobotX + target.getVisibility();
        Position newPosition = new Position(nrsteps, myRobotY);

        IWorld.UpdateResponse response = target.getWorld().isRobotOrObstacleNear(target,newPosition);

        if(response.equals(IWorld.UpdateResponse.FAILED_OBSTRUCTED_ROBOT)){
            objects.setMap("type","ROBOT");
            objects.setMap("distance",responseDistance(target, target.getWorld().getOtherRobotX(), myRobotX));
        }else if(response.equals(IWorld.UpdateResponse.FAILED_OBSTRUCTED)){
            objects.setMap("type","OBSTACLE");
            objects.setMap("distance",responseDistance(target, target.getWorld().getObstacleX(), myRobotX));
        }else{
            objects.setMap("type","EDGE");
            objects.setMap("distance",responseDistance(target, AbstractWorld.BOTTOM_RIGHT.getX(), myRobotX));
        }
        target.getWorld().setCurrentDirection(IWorld.Direction.SOUTH);
        return objects.getJsonData();

    }
    public JSONObject lookSouth(Robot target){

        Json objects = new Json();

        objects.setMap("direction",target.getWorld().getCurrentDirection());
        int nrsteps = myRobotY - target.getVisibility();
        Position newPosition = new Position(myRobotX, nrsteps);

        IWorld.UpdateResponse response = target.getWorld().isRobotOrObstacleNear(target,newPosition);
        if(response.equals(IWorld.UpdateResponse.FAILED_OBSTRUCTED_ROBOT)){
            objects.setMap("type","ROBOT");
            objects.setMap("distance",responseDistance(target, target.getWorld().getOtherRobotY(), myRobotY));
        }else if(response.equals(IWorld.UpdateResponse.FAILED_OBSTRUCTED)){
            objects.setMap("type","OBSTACLE");
            objects.setMap("distance",responseDistance(target, target.getWorld().getObstacleY(), myRobotY));
        }else{
            objects.setMap("type","EDGE");
            objects.setMap("distance",responseDistance(target, AbstractWorld.BOTTOM_RIGHT.getY(), myRobotY));
        }
        target.getWorld().setCurrentDirection(IWorld.Direction.WEST);
        return objects.getJsonData();
    }
    public JSONObject lookWest(Robot target, IWorld.Direction originalDirection){

        Json objects = new Json();
        objects.setMap("direction",target.getWorld().getCurrentDirection());

        int nrsteps = myRobotX - target.getVisibility();
        Position newPosition = new Position(nrsteps, myRobotY);

        IWorld.UpdateResponse response = target.getWorld().isRobotOrObstacleNear(target,newPosition);
        if(response.equals(IWorld.UpdateResponse.FAILED_OBSTRUCTED_ROBOT)){
            objects.setMap("type","ROBOT");
            objects.setMap("distance",responseDistance(target, target.getWorld().getOtherRobotX(), myRobotX));
        }else if(response.equals(IWorld.UpdateResponse.FAILED_OBSTRUCTED)){
            objects.setMap("type","OBSTACLE");
            objects.setMap("distance",responseDistance(target, target.getWorld().getObstacleX(), myRobotX));
        }else{
            objects.setMap("type","EDGE");
            objects.setMap("distance",responseDistance(target, AbstractWorld.TOP_LEFT.getX(), myRobotX));

        }
        target.getWorld().setCurrentDirection(originalDirection);
        return objects.getJsonData();
    }

    public int responseDistance(Robot target, int objectDistance, int myRobotDistance){
        return (myRobotDistance)-objectDistance;
    }

    public ArrayList<Object> responseSetDirection(Robot target){

        ArrayList <Object> LookAround = new ArrayList<>();
        IWorld.Direction originalDirection = target.getWorld().getCurrentDirection();
        target.getWorld().setCurrentDirection(IWorld.Direction.NORTH);
        LookAround.add(lookNorth(target));
        LookAround.add(lookSouth(target));
        LookAround.add(lookEast(target));
        LookAround.add(lookWest(target, originalDirection));

        return LookAround;
    }

    public LookCommand(String command, List<String> argument) {
        super("look",command,argument);
    }
}

