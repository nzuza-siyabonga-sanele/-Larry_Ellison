package za.co.wethinkcode.robotworlds.World_Commands;

import za.co.wethinkcode.robotworlds.SQL_DataBase.CreateSQLTable;
import za.co.wethinkcode.robotworlds.SQL_DataBase.RestoreWorld;
import za.co.wethinkcode.robotworlds.world.Robot;

import java.util.Scanner;

public class executeCommand {
    private String command;

    public executeCommand(String command) {
        this.command = command;
        runCommand();
    }

    public void runCommand(){

        switch (this.command){
            case "save":
                new saveCommand("insert");
                break;
            case "restore":
                new RestoreCommand("restore");
                break;
            case "stop":
                new ShutdownCommand().stop();
                break;
            case "quit":
                break;
        }
    }

}



