package za.co.wethinkcode.robotworlds.Server;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;
import za.co.wethinkcode.robotworlds.Commands.Command;
import za.co.wethinkcode.robotworlds.Commands.createCommand;
import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.maze.EmptyMaze;
import za.co.wethinkcode.robotworlds.world.AbstractWorld;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.world.Robot;


public class Server implements Runnable {

    // added new fields and changed them to public and same to private
    public static final int PORT = 5000;
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static HashMap<String, Json> players = new HashMap<String, Json>();
    private BufferedReader din;
    private PrintStream dout ;

    public static String hasLaunched = "null";

    Robot robot;
    public static IWorld world;
    private String in;
    private static Socket socket;
    private String clientMachine;

    static String getInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(prompt);
        String input = scanner.nextLine();
        while (input.isBlank()) {
            System.out.println(prompt);
            input = scanner.nextLine();
        }
        return input;
    }
    void setposition (Robot robot ) throws IOException {
        int bry;
        int brx;
        int tly;
        int tlx;
        tlx = Integer.parseInt(getInput("Input top left X"));
        tly = Integer.parseInt(getInput("Input top left Y"));
        brx = Integer.parseInt(getInput("Input bottom right X"));
        bry = Integer.parseInt(getInput("Input bottom right Y"));

        IWorld world = robot.getWorld();
        world.SetPositions(tlx, tly, brx, bry);
    }

    public Server(Socket socket){

        this.socket = socket;

        // added a try and catch when creating new buffered reader and writer
        try {
            din = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            dout = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        clientMachine = socket.getInetAddress().getHostName();
        System.out.println("Connection from " + clientMachine);
        System.out.println("Waiting for client...");
    }

    public Server(){}

    public void run() {

        JsonNode cont;
        String instruction;

        try {
//                //            System.out.println("qwertyuiop");
//                //            System.out.println(din.readUTF());
//
//                in = din.readLine();
//
//                JSONObject out = new JSONObject(in);
//                System.out.println(out);
//                String name = String.valueOf(out.get("robot"));
//
//                world = new AbstractWorld(new EmptyMaze());
//                robot = new Robot(name, world);
//
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }


            // changed the while true loop to while request is not null
            while ((in = din.readLine()) != null) {

                JSONObject out = new JSONObject(in);
                //                instruction= (out.getString("command"));

                robot = new Robot(out.getString("robot"), world);

                // called the reciverRequest passing the request from the client
                cont = reciverRequest(in);

                ////                Command command = Command.create(instruction);
                ////                cont=robot.handleCommand(command);

                //                if (instruction.split(" ")[0].equalsIgnoreCase( "replay")
                //                        ||instruction.split(" ")[0].equalsIgnoreCase( "mazerun")){
                //                }else {
                //                    robot.appendToHistory(instruction);
                //                }
                //                if (command.getName()== "sprint"){
                //                    System.out.println(robot.getPrint.trim());
                //                    robot.getPrint = "";
                //                }

                dout.println(cont);
                dout.flush();
            }
            robot.getWorld().setPosition(AbstractWorld.CENTRE);
            players.clear();


        } catch (IOException ex) {
            System.out.println("Shutting down single client server");
        }
    }

    // Added this method to the server for all the request from the client
    public JsonNode reciverRequest(String request){

        String argName;
        Command command;
        Json cont = null;
        try {
            argName = ((createCommand)OBJECT_MAPPER.readValue(request, createCommand.class)).returnName();

            command = Command.create(argName, createCommand.returnCommand(), createCommand.returnArgument());
            cont = robot.handleCommand(command);

            return OBJECT_MAPPER.readTree(String.valueOf(cont.getJsonData()));
        } catch (JsonProcessingException e) {
            System.out.println("mmmm");
            throw new RuntimeException(e);
        }
    }

}