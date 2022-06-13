package za.co.wethinkcode.robotworlds.Commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class createCommand {


    @JsonCreator
    public createCommand(@JsonProperty("robot") String robot, @JsonProperty("command") String command, @JsonProperty("arguments") List<String> arguments) {
        this.name = robot;
        this.commands = command;
        this.argument = arguments;

    }

    static String name;
    static List<String> argument;
    static String commands;


    public static String returnName() {
        return name;
    }

    public static List<String> returnArgument() {
        return argument;
    }

    public static String returnCommand() {
        return commands;
    }
}
