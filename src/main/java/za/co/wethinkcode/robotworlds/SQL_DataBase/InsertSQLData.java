package za.co.wethinkcode.robotworlds.SQL_DataBase;

import za.co.wethinkcode.robotworlds.Server.MultiServer;
import za.co.wethinkcode.robotworlds.world.AbstractWorld;
import za.co.wethinkcode.robotworlds.world.Obstacles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertSQLData implements DBconnection {


    public InsertSQLData() {
        DBconnector();
    }

    private void insert_Data_Into_Table(Statement statement, Connection connection) throws SQLException {

        System.out.println("How would you like to save your world? e.g. World1  ");
        String input = getInput();

        String sql  = "INSERT OR REPLACE INTO My_world (World_name, Robots, Size_Of_World, Obstacle_Size, Obstacle_Position)" +
                "VALUES ('"+input+"','"+ MultiServer.listOfplayers.size()+"', '"+ AbstractWorld.TOP_LEFT.getY()*2 +"', '0.1x0.1', '"+ Obstacles.obstacle +"')";

        statement.executeUpdate(sql);
    }

    @Override
    public void DBconnector() {
        // Open a connection
        try(Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
        ) {
            insert_Data_Into_Table(stmt,conn);
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
