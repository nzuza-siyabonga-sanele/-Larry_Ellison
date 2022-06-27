package za.co.wethinkcode.robotworlds.Commands;
import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.world.*;

import java.util.ArrayList;
import java.util.List;

public class LookCommand extends Command {
    ArrayList <Object> LookAround = new ArrayList<>();

    public Json execute(Robot target) {

        if(target.getHasLaunched().equals("null")){
            ErrorHandling error = new ErrorHandling("null", getCommand(), getArgument());
            return error.execute(target);
        }


        Json data = new Json();

        IWorld.Direction oldDirection = target.getWorld().getCurrentDirection();
        target.getWorld().setCurrentDirection(IWorld.Direction.NORTH);

        data.setMap("position", new int[]{target.getWorld().getPosition().getX(), target.getWorld().getPosition().getY()});


        if(target.getWorld().getCurrentDirection().equals(IWorld.Direction.NORTH)){
            Json objects = new Json();
            objects.setMap("direction",target.getWorld().getCurrentDirection());

            Position newPosition = new Position(target.getWorld().getPosition().getX(),
                    target.getWorld().getPosition().getY() + target.getVisibility());

            for(Obstacle obstacle : target.getWorld().getObstacles()){

                if(obstacle.blocksPath(target.getWorld().getPosition(),newPosition)){
                    objects.setMap("type","OBSTACLE");
                    break;
                }else {
                    objects.setMap("type","EDGE");
                }
            }

            objects.setMap("distance", (AbstractWorld.TOP_LEFT.getY() - target.getWorld().getPosition().getY()));

            this.LookAround.add(objects.getJsonData());
            target.getWorld().setCurrentDirection(IWorld.Direction.EAST);
        }
        if(target.getWorld().getCurrentDirection().equals(IWorld.Direction.EAST)){
            Json objects = new Json();
            objects.setMap("direction",target.getWorld().getCurrentDirection());
            Position newPosition = new Position(target.getWorld().getPosition().getX() + target.getVisibility(),
                    target.getWorld().getPosition().getY());
            for(Obstacle obstacle: target.getWorld().getObstacles()){
                if(obstacle.blocksPath(target.getWorld().getPosition(),newPosition)){
                    objects.setMap("type","OBSTACLE");
                    break;
                }else {
                    objects.setMap("type","EDGE");
                }
            }

            objects.setMap("distance",(AbstractWorld.BOTTOM_RIGHT.getX() - target.getWorld().getPosition().getX()));

            this.LookAround.add(objects.getJsonData());
            target.getWorld().setCurrentDirection(IWorld.Direction.SOUTH);
        }
        if(target.getWorld().getCurrentDirection().equals(IWorld.Direction.SOUTH)){
            Json objects = new Json();
            objects.setMap("direction",target.getWorld().getCurrentDirection());
            Position newPosition = new Position(target.getWorld().getPosition().getX(),
                    target.getWorld().getPosition().getY() - target.getVisibility());
            for(Obstacle obstacle: target.getWorld().getObstacles()){
                if(obstacle.blocksPath(target.getWorld().getPosition(),newPosition)){
                    objects.setMap("type","OBSTACLE");
                    break;
                }else {
                    objects.setMap("type","EDGE");
                }
            }

            if (target.getWorld().getPosition().getY() < 0){
                objects.setMap("distance",(AbstractWorld.BOTTOM_RIGHT.getY() - target.getWorld().getPosition().getY()));
            }else{
                objects.setMap("distance",(AbstractWorld.BOTTOM_RIGHT.getY() + target.getWorld().getPosition().getY()));
            }


            this.LookAround.add(objects.getJsonData());
            target.getWorld().setCurrentDirection(IWorld.Direction.WEST);
        }
        if(target.getWorld().getCurrentDirection().equals(IWorld.Direction.WEST)){
            Json objects = new Json();
            objects.setMap("direction",target.getWorld().getCurrentDirection());
            Position newPosition = new Position(target.getWorld().getPosition().getX()- target.getVisibility(),
                    target.getWorld().getPosition().getY());
            for(Obstacle obstacle: target.getWorld().getObstacles()){
                if(obstacle.blocksPath(target.getWorld().getPosition(),newPosition)){
                    objects.setMap("type","OBSTACLE");
                    break;
                }else {
                    objects.setMap("type","EDGE");
                }
            }

            if(target.getWorld().getPosition().getX() > 0){
                objects.setMap("distance",(AbstractWorld.TOP_LEFT.getX() - target.getWorld().getPosition().getX()));
            }else{
                objects.setMap("distance",(AbstractWorld.TOP_LEFT.getX() + target.getWorld().getPosition().getX()));
            }

            this.LookAround.add(objects.getJsonData());
            target.getWorld().setCurrentDirection(oldDirection);
        }

        data.setMap("visibility",target.getVisibility());
        data.setMap("objects",this.LookAround);

        Json state = new Json();
        state.setMap("position", new int[]{target.getWorld().getPosition().getX(), target.getWorld().getPosition().getY()});
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
    public LookCommand(String command, List<String> argument) {
        super("look",command,argument);
    }
}