package za.co.wethinkcode.robotworlds.Server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import za.co.wethinkcode.robotworlds.Commands.Command;
import za.co.wethinkcode.robotworlds.Commands.createCommand;
import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.MultiServer;
import za.co.wethinkcode.robotworlds.world.AbstractWorld;
import za.co.wethinkcode.robotworlds.world.IWorld;
import za.co.wethinkcode.robotworlds.world.Robot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Server implements Runnable {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
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
    public static void setposition(String[] arguments){
        //-p 5000 -s 100 -o 10,5
        int bry = -1 * Integer.parseInt(arguments[1])/2;
        int brx = Integer.parseInt(arguments[0])/2;
        int tly = Integer.parseInt(arguments[1])/2;
        int tlx = -1 * Integer.parseInt(arguments[0])/2;

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

    public void run() {

        JsonNode cont;
        String instruction;

        try {

            // changed the while true loop to while request is not null
            while ((in = din.readLine()) != null) {

                JSONObject out = new JSONObject(in);
                robot = new Robot(out.getString("robot"), world, MultiServer.configFile);

                // called the reciverRequest passing the request from the client
                cont = reciverRequest(in);

                dout.println(cont);
                dout.flush();
            }
            robot.getWorld().setPosition(AbstractWorld.CENTRE);
            AbstractWorld.players.clear();

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
            throw new RuntimeException(e);
        }
    }

}