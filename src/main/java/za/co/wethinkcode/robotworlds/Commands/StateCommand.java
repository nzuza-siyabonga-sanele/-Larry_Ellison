package za.co.wethinkcode.robotworlds.Commands;

import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.Server.Server;
import za.co.wethinkcode.robotworlds.world.AbstractWorld;
import za.co.wethinkcode.robotworlds.world.Robot;

import java.util.List;

// added state command
public class StateCommand extends Command{

    public StateCommand(String command, List<String> argument) {
        super("state",command,argument);
    }

    @Override
    public Json execute(Robot target) {
        Json state = new Json();

        if(AbstractWorld.players.containsKey(target.getName())){
            state.setMap("position", new int[]{target.getWorld().getPosition().getX(), target.getWorld().getPosition().getY()});
            state.setMap("direction", target.getWorld().getCurrentDirection());
            state.setMap("shields", target.getShield());
            state.setMap("shots", target.getShots());
            state.setMap("status", "normal");

            Json jData = new Json();
            jData.setMap("result", "OK");
            jData.setMap("state", state.getJsonData());

            AbstractWorld.players.put(target.getName(),jData);
            return jData;
        }else{

        }

        state.setMap("position", new int[]{target.getWorld().getPosition().getX(), target.getWorld().getPosition().getY()});
        state.setMap("direction", target.getWorld().getCurrentDirection());
        state.setMap("shields", target.getShield());
        state.setMap("shots", target.getShots());
        state.setMap("status", "normal");

        Json jData = new Json();
        jData.setMap("result", "OK");
        jData.setMap("state", state.getJsonData());

        AbstractWorld.players.put(target.getName(),jData);
        return jData;
    }
}
