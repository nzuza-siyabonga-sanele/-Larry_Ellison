package za.co.wethinkcode.robotworlds.Commands;

import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.Server.MultiServer;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.world.Robot;

import java.util.List;
import java.util.Map;

public class ForwardCommand extends Command{
    private Integer myShields;
    private Integer myShots;
    private Object myDirection;

    @Override
    public Json execute(Robot target) {

        int nrSteps = Integer.parseInt(getArgument().get(0));

        Json data = new Json();

        for (Map.Entry<String, Json> set : MultiServer.listOfplayers.entrySet()) {
            if(set.getKey().equals(target.getName())){

                myShields = (Integer) set.getValue().getJsonData().getJSONObject("state").get("shields");
                myShots =  (Integer) set.getValue().getJsonData().getJSONObject("state").get("shots");
                myDirection =  set.getValue().getJsonData().getJSONObject("state").get("direction");
            }
        }

        if (target.getWorld().updatePosition(nrSteps, target).equals(IWorld.UpdateResponse.SUCCESS)) {
            data.setMap("message", "Done");

        }else if (target.getWorld().updatePosition(nrSteps, target).equals(IWorld.UpdateResponse.FAILED_OUTSIDE_WORLD)) {

            data.setMap("message", "At the "+ myDirection+" edge");

        }else if (target.getWorld().updatePosition(nrSteps, target).equals(IWorld.UpdateResponse.FAILED_OBSTRUCTED)) {
            data.setMap("message", "Obstructed");
        }

        Json state = new Json();

        state.setMap("position", new int[]{target.getWorld().getPosition().getX(), target.getWorld().getPosition().getY()});
        state.setMap("direction", myDirection);
        state.setMap("shields", myShields);
        state.setMap("shots", myShots);
        state.setMap("status", "normal");

        Json jData = new Json();
        jData.setMap("result", "OK");
        jData.setMap("data", data.getJsonData());
        jData.setMap("state", state.getJsonData());

        MultiServer.listOfplayers.put(target.getName(),jData);
        return jData;
    }

    public ForwardCommand(String command, List<String> argument) {
        super("forward",command,argument);
    }
}

