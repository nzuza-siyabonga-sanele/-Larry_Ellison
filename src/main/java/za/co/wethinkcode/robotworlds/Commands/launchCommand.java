package za.co.wethinkcode.robotworlds.Commands;

import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.MultiServer;
import za.co.wethinkcode.robotworlds.world.Robot;
import za.co.wethinkcode.robotworlds.Server;

import java.util.List;

// added launch command
// added a json class to covert all responce to a json string
public class launchCommand extends Command {


    @Override
    public Json execute(Robot target) {

        target.setHasLaunched(getCommand());

        if(getArgument().size() > 2){
            target.setShieldAndShots(getArgument().get(1), getArgument().get(2));
        }else{
            target.setShieldAndShots("5", "5");
        }

        Json data = new Json();

        if(!Server.players.containsKey(target.getName())){

            if (Server.players.size() < 4){

                Json state = new Json();

                state.setMap("position", new int[]{target.getWorld().getPosition().getX(), target.getWorld().getPosition().getY()});
                state.setMap("direction", target.getWorld().getCurrentDirection());
                state.setMap("shields", target.getShield());
                state.setMap("shots", target.getShots());
                state.setMap("status", "normal");

                Json jData = new Json();
                jData.setMap("result", "OK");
                jData.setMap("data", "{}");
                jData.setMap("state", state.getJsonData());

                Server.players.put(target.getName(),jData);
                return jData;

            }else{

                data.setMap("message", "No more space in this world");

                Json jData = new Json();
                jData.setMap("result", "ERROR");
                jData.setMap("data", data.getJsonData());
                return jData;
            }

        }else {
            data.setMap("message", "Too many of you in this world");

            Json jData = new Json();
            jData.setMap("result", "ERROR");
            jData.setMap("data", data.getJsonData());
            return jData;
        }
    }
    public launchCommand(String command, List<String> argument) {
        super("launch",command, argument);
    }
}
