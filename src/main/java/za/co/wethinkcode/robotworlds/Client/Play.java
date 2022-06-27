package za.co.wethinkcode.robotworlds.Client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import za.co.wethinkcode.robotworlds.Json;
import za.co.wethinkcode.robotworlds.world.*;
import za.co.wethinkcode.robotworlds.maze.EmptyMaze;
import za.co.wethinkcode.robotworlds.maze.RandomMaze;
import za.co.wethinkcode.robotworlds.maze.SimpleMaze;
import za.co.wethinkcode.robotworlds.world.AbstractWorld;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import static za.co.wethinkcode.robotworlds.Server.Server.OBJECT_MAPPER;


public class Play {

    // added new fields and changed them to public and same to private
    static Scanner scanner;
    public static BufferedReader din;
    private static PrintStream dout ;
    private static Socket socket;


    public static void main(String[] args) throws Exception {

        scanner = new Scanner(System.in);
        // create socket and connecting to the server through connect method
        Play play = new Play();
        play.connect("localhost", 5000);


//        DataInputStream din=new DataInputStream(s.getInputStream());
//        DataOutputStream dout=new DataOutputStream(s.getOutputStream());

        //set robot name
        String name = getInput("What do you want to name your robot?");
        System.out.println("Hello Kiddo!");


        //creating and sending json with starting info for robot

        Json jData = new Json();
        jData.setMap("robot", name);

//        while (count ==0){
//            count++;
//            dout.println(json.toString());
//            dout.flush();
//        }
//
//        JsonNode shouldContinue = null;


        // re-aranged the code to handly to send new required commands and arguments
        do {
            String instruction = getInput(name + "> What must I do next?").strip().toLowerCase();

            String[] cmd = instruction.split(" ");
            jData.setMap("command", cmd[0]);

            ArrayList<String> arguments = new ArrayList<>();
            if (cmd.length > 3) {
                arguments.add(cmd[1]);
                arguments.add(cmd[2]);
                arguments.add(cmd[3]);
            } else if (cmd.length > 1 ) {
                arguments.add(cmd[1]);
            } else {
                arguments.add("");
            }
            jData.setMap("arguments", arguments);

            try {

                JsonNode worlds = play.sendRequest(String.valueOf(jData.getJsonData()));
                System.out.println(worlds);

//                dout.println(json.toString());
//                dout.flush();

//                String in;

//                shouldContinue = din.readUTF();
//                System.out.println("doing thing");
//                System.out.println(shouldContinue);


            } catch (IllegalArgumentException e) {
                play.disconnect();
            }
//            System.out.println(robot);
        } while (socket.isConnected());

    }

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

//    public static AbstractWorld worldSelector(String []args){
//        AbstractWorld world = null;
//
//        if (args.length == 0 || args[0].equalsIgnoreCase("text"))
//        {
//            if (args.length == 0 || args.length==1 && args[0].equalsIgnoreCase("text") || args[1].equalsIgnoreCase("emptymaze") )
//            {
//                System.out.println("Loaded EmptyMaze.");
//                 world = new TextWorld(new EmptyMaze(args));
//
//            }
//            else if(args[1].equalsIgnoreCase("simplemaze"))
//            {
//                System.out.println("Loaded SimpleMaze.");
//                world = new TextWorld(new SimpleMaze());
//
//            }
//            else if(args[1].equalsIgnoreCase("randommaze"))
//            {
//                System.out.println("Loaded RandomMaze.");
//                 world = new TextWorld(new RandomMaze());
//            }}else if (args[0].equalsIgnoreCase("turtle"))
//        {
//            if ( args.length==1 && args[0].equalsIgnoreCase("turtle") ||args[1].equalsIgnoreCase("randommaze"))
//            {
//                System.out.println("Loaded RandomMaze.");
//                 world = new TurtleWorld(new RandomMaze());
//
//            }
//            else if(args[1].equalsIgnoreCase("simplemaze"))
//            {
//                System.out.println("Loaded SimpleMaze.");
//                 world = new TurtleWorld(new SimpleMaze());
//
//            }
//            else if(args[1].equalsIgnoreCase("emptymaze"))
//            {
//                System.out.println("Loaded EmptyMaze.");
//                 world = new TurtleWorld(new EmptyMaze(args));
//
//            }
//        }
//        return world;
//    }

    // added new method that helps create new socket, buffered reader and print stream.
    public boolean connect(String defaultIp, int defaultPort) {

        try {
            socket = new Socket(defaultIp, defaultPort);
            dout = new PrintStream(socket.getOutputStream());
            din = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // added new method that helps disconnect socket, buffered reader and print stream.
    public void disconnect() {

        try {
            dout.close();
            din.close();
            socket.close();
        } catch (IOException e) {
            //error connecting should just throw Runtime error and fail test
            throw new RuntimeException("Error disconnecting from Robot Worlds server.", e);
        }
    }
    // added new method that helps discoonect socket, buffered reader and print stream.
    public boolean isConnected() {
        return this.socket.isConnected();
    }

    // added new method that helps send all the request from the client to the server.
    // read the responce from the server.
    public JsonNode sendRequest(String request) {
        try {
            dout.println(request);
            dout.flush();
            return OBJECT_MAPPER.readTree(din.readLine());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing server response as JSON.", e);
        } catch (IOException e) {
            throw new RuntimeException("Error reading server response.", e);
        }

    }
}
