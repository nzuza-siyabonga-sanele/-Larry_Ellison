package za.co.wethinkcode.robotworlds.Commands;

import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.world.Robot;
import za.co.wethinkcode.robotworlds.world.IWorld;

import java.util.List;

public class ForwardCommand extends Command{


    @Override
    public Json execute(Robot target) {

        int nrSteps = Integer.parseInt(getArgument().get(0));

        Json data = new Json();

        if (target.getWorld().updatePosition(nrSteps).equals(IWorld.UpdateResponse.SUCCESS)) {

            data.setMap("message", "Done");
            target.setStatus("Moved forward by "+nrSteps+" steps.");


        }else if (target.getWorld().updatePosition(nrSteps).equals(IWorld.UpdateResponse.FAILED_OUTSIDE_WORLD)) {

            data.setMap("message", "EDGE");
            target.setStatus("Sorry, I cannot go outside my safe zone.");

        }else if (target.getWorld().updatePosition(nrSteps).equals(IWorld.UpdateResponse.FAILED_OBSTRUCTED)) {

            data.setMap("message", "Done");
            target.setStatus("Sorry, there is an obstacle in the way.");}

        Json state = new Json();

        state.setMap("position", "[" + String.valueOf(target.getWorld().getPosition().getX()) + "," + String.valueOf(target.getWorld().getPosition().getY()) + "]");
        state.setMap("direction", target.getWorld().getCurrentDirection());
        state.setMap("shields", target.getShield());
        state.setMap("shots", target.getShots());
        state.setMap("status", "normal");

        Json jData = new Json();
        jData.setMap("result", "OK");
        jData.setMap("data", data.getJsonData());
        jData.setMap("state", state.getJsonData());

        return jData;
    }

    public ForwardCommand(String command, List<String> argument) {
        super("forward",command,argument);
    }
}

