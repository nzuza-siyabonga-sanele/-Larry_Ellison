package za.co.wethinkcode.robotworlds.Commands;

import org.json.JSONObject;
import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.Server.ErrorHandling;
import za.co.wethinkcode.robotworlds.Server.MultiServer;
import za.co.wethinkcode.robotworlds.world.AbstractWorld;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.world.Position;
import za.co.wethinkcode.robotworlds.world.Robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LookCommand extends Command {

    int myRobotX;
    int myRobotY;
    private int myShields;
    private int myShots;

    ArrayList <Object> LookAround = new ArrayList<>();

    public Json execute(Robot target){

        if(target.getHasLaunched().equals("null")){
            ErrorHandling error = new ErrorHandling("null", getCommand(), getArgument());
            return error.execute(target);
        }

        target.setStatus("looking");

        for (Map.Entry<String, Json> set : MultiServer.listOfplayers.entrySet()) {
            if(set.getKey().equals(target.getName())){
                myRobotX = (Integer) set.getValue().getJsonData().getJSONObject("state").getJSONArray("position").get(0);
                myRobotY = (Integer) set.getValue().getJsonData().getJSONObject("state").getJSONArray("position").get(1);
                myShields = (Integer) set.getValue().getJsonData().getJSONObject("state").get("shields");
                myShots =  (Integer) set.getValue().getJsonData().getJSONObject("state").get("shots");
            }
        }

        lookAround(target);

        Json data = new Json();

        data.setMap("visibility",target.getVisibility());
        data.setMap("objects",LookAround);

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
    // check every direction by running a forloop in the Enum Class of directions
    public void lookAround(Robot target){

        for( Enum directions: IWorld.Direction.values()){

            if (IWorld.Direction.NORTH.equals(directions)) {
                northAndSouth(target, (IWorld.Direction) directions);
            } else if (IWorld.Direction.SOUTH.equals(directions)) {
                northAndSouth(target, (IWorld.Direction) directions);
            } else if (IWorld.Direction.EAST.equals(directions)) {
                eastAndWest(target, (IWorld.Direction) directions);
            } else if (IWorld.Direction.WEST.equals(directions)) {
                eastAndWest(target, (IWorld.Direction) directions);
            }
        }
    }
    // set a new position by adding a visibility to your Y-axes and check if that position is allowed for Y-axes
    public void northAndSouth(Robot target, IWorld.Direction directions){

        int nrsteps;
        if(directions.equals(IWorld.Direction.NORTH)){
            nrsteps = myRobotY + target.getVisibility();
        }else{
            nrsteps = myRobotY - target.getVisibility();
        }
        Position newPosition = new Position(myRobotX, nrsteps);
        IWorld.UpdateResponse response = target.getWorld().isRobotOrObstacleNear(target,newPosition,directions);

        LookAround.add(setNorthAndSouthResponce(target, response, directions));
    }
    // set a responce for north and south directions.
    // types and distance of objects found in both directions [north, south]
    public JSONObject setNorthAndSouthResponce(Robot target, IWorld.UpdateResponse response, IWorld.Direction directions){

        Json objects = new Json();

        objects.setMap("direction",directions);

        if(response.equals(IWorld.UpdateResponse.FAILED_OBSTRUCTED)){
            objects.setMap("type",target.getWorld().getObjectType());
            objects.setMap("distance",responseDistance(target, target.getWorld().getObjectY(), myRobotY));
        }else{
            objects.setMap("type","EDGE");
            if(directions.equals(IWorld.Direction.NORTH)){
                objects.setMap("distance",responseDistance(target, AbstractWorld.TOP_LEFT.getY(), myRobotY));
            }else{
                objects.setMap("distance",responseDistance(target, AbstractWorld.BOTTOM_RIGHT.getY(), myRobotY));
            }
        }
        return objects.getJsonData();
    }
    // set a new position by adding a visibility to your X-axes and check if that position is allowed for X-axes
    public void eastAndWest(Robot target, IWorld.Direction directions){

        int nrsteps;
        if(directions.equals(IWorld.Direction.EAST)){
            nrsteps = myRobotX + target.getVisibility();
        }else{
            nrsteps = myRobotX - target.getVisibility();
        }
        Position newPosition = new Position(nrsteps, myRobotY);
        IWorld.UpdateResponse response = target.getWorld().isRobotOrObstacleNear(target,newPosition, directions);

        LookAround.add(setEastAndWestResponce(target, response, directions));
    }

    // set a responce for east and west directions.
    // types and distance of objects found in both directions [east, west]
    public JSONObject setEastAndWestResponce(Robot target, IWorld.UpdateResponse response, IWorld.Direction directions){

        Json objects = new Json();

        objects.setMap("direction",directions);

        if(response.equals(IWorld.UpdateResponse.FAILED_OBSTRUCTED)){
            objects.setMap("type",target.getWorld().getObjectType());
            objects.setMap("distance",responseDistance(target, target.getWorld().getObjectX(), myRobotX));
        }else{
            objects.setMap("type","EDGE");
            if(directions.equals(IWorld.Direction.EAST)){
                objects.setMap("distance",responseDistance(target, AbstractWorld.BOTTOM_RIGHT.getX(), myRobotX));
            }else{
                objects.setMap("distance",responseDistance(target, AbstractWorld.TOP_LEFT.getX(), myRobotX));
            }
        }
        return objects.getJsonData();
    }

    public int responseDistance(Robot target, int objectDistance, int myRobotDistance){
        if(objectDistance < 0){
            return myRobotDistance - objectDistance;
        }
        return objectDistance - myRobotDistance;
    }

    public LookCommand(String command, List<String> argument) {
        super("look",command,argument);
    }
}
