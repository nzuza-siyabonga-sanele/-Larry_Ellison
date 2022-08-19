package za.co.wethinkcode.robotworlds.SQL_DataBase;

import za.co.wethinkcode.robotworlds.Connect_DataBase_And_API.Restore_Data_World;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestoreWorld implements DBconnection {

    public static List<String> worlds_name = new ArrayList<>();

    public RestoreWorld() {
        DBconnector();
    }

    public void restoreWorld(Connection connection, Statement stmt) throws SQLException {

        try  {
            PreparedStatement p = connection.prepareStatement("SELECT * FROM My_world");
            ResultSet rs = p.executeQuery();

            System.out.println("All saved worlds");
            while (rs.next()){
                String world_name = rs.getString("World_name");
                worlds_name.add(world_name);
                System.out.println("worlds: "+world_name);
            }
            resetWorld(connection, p);
        }catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public void resetWorld(Connection connection, PreparedStatement preparedStatement) throws SQLException {
        String nameOfChosenWorld;
        do{

            System.out.println("choose world to restore!");
            nameOfChosenWorld = getInput();

            if(worlds_name.contains(nameOfChosenWorld)){
                String sql1 =  "SELECT * FROM My_world WHERE World_name='"+nameOfChosenWorld+"'";
                preparedStatement = connection.prepareStatement(sql1);
                if(preparedStatement.execute()){

                    ResultSet rs = preparedStatement.executeQuery();
                    String worldName = rs.getString("World_name");
                    String robotCount = rs.getString("Robots");
                    String sizeOfWorld = rs.getString("Size_Of_World");
                    String obstacleSize = rs.getString("Obstacle_Size");
                    String obstaclePosition = rs.getString("Obstacle_Position");

                    System.out.println(worldName+" | "+robotCount+" | "+sizeOfWorld+" | "+obstacleSize+" | "+obstaclePosition);

                    Restore_Data_World restore_data_world = new Restore_Data_World(worldName, Integer.parseInt(robotCount), Integer.parseInt(sizeOfWorld)
                            , obstaclePosition);

                    restore_data_world.resetWorld();

                    preparedStatement.close();
                    connection.close();
                    break;
                }
            }
        }while(!worlds_name.contains(nameOfChosenWorld));
    }

    @Override
    public void DBconnector() {
        // Open a connection
        try(Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
        ) {
            restoreWorld(conn, stmt);
            conn.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void DBconnector(String[] args) {

    }

}
