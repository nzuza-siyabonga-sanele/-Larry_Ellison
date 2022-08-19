package za.co.wethinkcode.robotworlds.Connect_DataBase_And_API;

import za.co.wethinkcode.robotworlds.ORM_RobotWorld.RobotWorldDO;

import java.util.ArrayList;
import java.util.List;

public class Response_To_WebServer_Get_World extends restoreResponse {

    public List<RobotWorldDO> response_Json_World(){

        RobotWorldDO robotWorldDO = new RobotWorldDO();
        robotWorldDO.World_name = super.worldName;
        robotWorldDO.world_id = 1;
        robotWorldDO.Size_Of_World = sizeOfWorld;
        robotWorldDO.Obstacle_Position = String.valueOf(obstaclePosition);
        robotWorldDO.Obstacle_Size = obstacleSize;

        return add(robotWorldDO);
    }

    public List<RobotWorldDO> add(RobotWorldDO robotWorldDO){

        List<RobotWorldDO> listOfRobotWroldDO = new ArrayList<>();
        listOfRobotWroldDO.add(robotWorldDO);

        return listOfRobotWroldDO;
    }
}
