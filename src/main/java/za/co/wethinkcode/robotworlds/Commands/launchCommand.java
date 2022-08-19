package za.co.wethinkcode.robotworlds.Commands;

import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.Server.MultiServer;
import za.co.wethinkcode.robotworlds.world.AbstractWorld;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.world.Position;
import za.co.wethinkcode.robotworlds.world.Robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class launchCommand extends Command {

    public List<Position> RobotPositions = new ArrayList<Position>();
    private int FreePositionX;
    private int FreePositionY;
    private int myRobotX;
    private int myRobotY;

    @Override
    public Json execute(Robot target) {

        target.setHasLaunched(getCommand());

        if(getArgument().size() > 2){
            target.setShieldAndShots(getArgument().get(1), getArgument().get(2));
        }else{
            target.setShieldAndShots("5", "5");
        }

        if(!MultiServer.listOfplayers.containsKey(target.getName())){

            generate_Random_Starting_Position();
            generateRandomDirection(target);

            // Only checks for free positions if the world size is greater then size = 1x1
            if(AbstractWorld.TOP_LEFT.getY() >= 1){
                checkFreePositions(target,target.getWorld().getCurrentDirection());
            }

            // Only excute ok response if the list size of players required is not reached.
            if (MultiServer.listOfplayers.size() != MultiServer.numberOfPlayers){
                return okResponse(target);
            }else{
                return error_Response_Too_Many();
            }
        }else {
            return error_Response_Too_Many();
        }
    }

    private Json error_Response_Too_Many(){
        Json data = new Json();
        data.setMap("message", "Too many of you in this world");
        Json jData = new Json();
        jData.setMap("result", "ERROR");
        jData.setMap("data", data.getJsonData());
        return jData;
    }
    private Json error_Response_Space_In_The_World(){
        Json data = new Json();
        data.setMap("message", "No more space in this world");
        Json jData = new Json();
        jData.setMap("result", "ERROR");
        jData.setMap("data", data.getJsonData());
        return jData;
    }
    private Json okResponse(Robot target){
        Json state = new Json();
        state.setMap("position", new int[]{FreePositionX, FreePositionY});
        state.setMap("direction", target.getWorld().getCurrentDirection());
        state.setMap("shields", target.getShield());
        state.setMap("shots", target.getShots());
        state.setMap("status", "normal");

        Json jData = new Json();
        jData.setMap("result", "OK");
        jData.setMap("data", "");
        jData.setMap("state", state.getJsonData());

        MultiServer.listOfplayers.put(target.getName(),jData);
        return jData;
    }
    public void generate_Random_Starting_Position(){
        myRobotX = ThreadLocalRandom.current().nextInt(AbstractWorld.TOP_LEFT.getX(), AbstractWorld.BOTTOM_RIGHT.getX() + 1);
        myRobotY = ThreadLocalRandom.current().nextInt(AbstractWorld.BOTTOM_RIGHT.getY(), AbstractWorld.TOP_LEFT.getY() + 1);
    }

    public void generateRandomDirection(Robot target){
        target.getWorld().setCurrentDirection(IWorld.Direction.values()[new Random().nextInt(IWorld.Direction.values().length)]);
    }

    public void checkFreePositions(Robot target, IWorld.Direction directions){
        do{
            Position newPosition = new Position(myRobotX, myRobotY);
            if(target.getWorld().isRobotOrObstacleNear(target,newPosition, directions).equals(IWorld.UpdateResponse.SUCCESS)){
                this.FreePositionX = newPosition.getX();
                this.FreePositionY = newPosition.getY();
                break;
            }
            generate_Random_Starting_Position();
        }while (MultiServer.listOfplayers.size() != MultiServer.numberOfPlayers);
    }
    public launchCommand(String command, List<String> argument) {
        super("launch",command, argument);
    }

}
