package za.co.wethinkcode.robotworlds.World_Commands;

import za.co.wethinkcode.robotworlds.Connect_DataBase_And_API.DataBase_To_API;
import za.co.wethinkcode.robotworlds.Connect_DataBase_And_API.Restore_Data_World;
import za.co.wethinkcode.robotworlds.ORM_RobotWorld.RobotWorldDb;

import java.util.Scanner;

public class RestoreCommand {

    private final String prompt = "Name the world you world like to restore..? ";
    private String select;
    public RestoreCommand(String selected) {
        this.select = selected;

        String[] args = {select};
        RobotWorldDb fd = new RobotWorldDb( args );
        String command = getInput();
        String[] getOne = {"selected", command};

        execute(getOne);
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
        DataBase_To_API as = new DataBase_To_API();
        as.add_To_List(command);
    }
}
