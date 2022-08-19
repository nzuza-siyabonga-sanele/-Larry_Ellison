package za.co.wethinkcode.robotworlds.World_Commands;

import za.co.wethinkcode.robotworlds.Connect_DataBase_And_API.Restore_Data_World;
import za.co.wethinkcode.robotworlds.ORM_RobotWorld.RobotWorldDb;
import za.co.wethinkcode.robotworlds.world.World;

import java.util.Scanner;

public class saveCommand {

    private final String prompt = "How would you like to save your world? e.g. World1  ";

    public saveCommand(String insert) {
        String command = getInput();

        String[] args = {insert, command, String.valueOf(Restore_Data_World.sizeOfWorld)
                , String.valueOf(Restore_Data_World.obstaclePosition)};

        execute(args);
    }

    public String getInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(prompt);
        String input = scanner.nextLine();
        while (input.isBlank()) {
            input = scanner.nextLine();
        }
        return input;
    }
    public void execute(String[] command){
        RobotWorldDb fd = new RobotWorldDb( command );

    }
}
