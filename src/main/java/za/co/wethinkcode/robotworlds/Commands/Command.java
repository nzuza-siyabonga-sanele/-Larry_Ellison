package za.co.wethinkcode.robotworlds.Commands;

import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.Server.ErrorHandling;
import za.co.wethinkcode.robotworlds.world.Robot;

import java.util.List;

public abstract class Command {
    private String name;
    private String command;
    private List<String> arguments;

    public String getCommand() {
        return command;
    }

    public abstract Json execute(Robot target);

    public Command(String robot, String command, List<String> arguments) {
        this.name = robot;
        this.command = command;
        this.arguments = arguments;
    }

    public String getName() {                                                                           //<2>
        return name;
    }

    public List<String> getArgument() {
        return arguments;
    }

    public static Command create(String argName, String command, List<String> argument) {

        switch (command){
//            case "shutdown":
//            case "off":
//                return new ShutdownCommand();
//            case "help":
//                return new HelpCommand();
            case "forward":
                return new ForwardCommand(command, argument);
            case "back":
                return new BackCommand(command, argument);
            case "left":
                return new LeftCommand(command, argument);
            case "right":
                return new RightCommand(command, argument);
//            case "sprint":
//                return new SprintCommand(command, argument);
//            case "fire":
//                return new FireCommand();
            case "launch":
                return new launchCommand(command, argument);
            case "look":
                return new LookCommand(command, argument);
            case "state":
                return new StateCommand(command, argument);
//            case "reload":
//                return new ReloadCommand();
            default:
                // added error handling command class
                return new ErrorHandling("Unsupported command",command, argument);
        }
    }
}

