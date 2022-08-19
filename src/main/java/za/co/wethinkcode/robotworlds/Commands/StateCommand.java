package za.co.wethinkcode.robotworlds.Commands;

import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.Server.MultiServer;
import za.co.wethinkcode.robotworlds.world.Robot;

import java.util.List;
import java.util.Map;

// added state command
public class StateCommand extends Command{

    private Integer myRobotX;
    private Integer myRobotY;
    private Integer myShields;
    private Integer myShots;
    private Object myDirection;

    public StateCommand(String command, List<String> argument) {
        super("state",command,argument);
    }

    @Override
    public Json execute(Robot target) {
        Json state = new Json();

        for (Map.Entry<String, Json> set : MultiServer.listOfplayers.entrySet()) {
            if(set.getKey().equals(target.getName())){
                myRobotX = (Integer) set.getValue().getJsonData().getJSONObject("state").getJSONArray("position").get(0);
                myRobotY = (Integer) set.getValue().getJsonData().getJSONObject("state").getJSONArray("position").get(1);
                myShields = (Integer) set.getValue().getJsonData().getJSONObject("state").get("shields");
                myShots =  (Integer) set.getValue().getJsonData().getJSONObject("state").get("shots");
                myDirection = set.getValue().getJsonData().getJSONObject("state").get("direction");
            }
        }

        state.setMap("position", new int[]{myRobotX, myRobotY});
        state.setMap("direction", myDirection);
        state.setMap("shields", myShields);
        state.setMap("shots", myShots);
        state.setMap("status", "normal");

        Json jData = new Json();
        jData.setMap("result", "OK");
        jData.setMap("state", state.getJsonData());

        return jData;
    }
}
