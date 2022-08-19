package za.co.wethinkcode.robotworlds.Connect_DataBase_And_API;

import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.ORM_RobotWorld.RobotWorldDO;
import za.co.wethinkcode.robotworlds.ORM_RobotWorld.RobotWorldDb;

import java.util.HashMap;
import java.util.Map;

public class DataBase_To_API {


    public Map<String, Json> getAll_World_Info() {
        return All_World_Info;
    }

    private Map<String, Json> All_World_Info;

    public void add_To_List(String[] request){

        All_World_Info = new HashMap<>();

        RobotWorldDb app = new RobotWorldDb( request );
        RobotWorldDb.listOfWorlds.forEach( p -> add(p, request) );


    }
    private void add(RobotWorldDO p, String[] request){


        Restore_Data_World restore_data_world = new Restore_Data_World(p.getWorld_name(), p.getRobots(), p.getSize_Of_World(), p.getObstacle_Position());

        if(!request.equals("readData")){
            restore_data_world.resetWorld();
        }

        All_World_Info.put(p.getWorld_name(), restore_data_world.response());
    }

}
