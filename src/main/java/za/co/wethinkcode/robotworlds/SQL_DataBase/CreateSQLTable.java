package za.co.wethinkcode.robotworlds.SQL_DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateSQLTable implements DBconnection {


    public CreateSQLTable() {
        DBconnector();
    }

    private void create_SQL_Table(Statement statement) throws SQLException {

        String sql = "CREATE TABLE IF NOT EXISTS My_world" +
                "(world_id INTEGER not NULL PRIMARY KEY AUTOINCREMENT, " +
                " World_name VARCHAR(50) not NULL UNIQUE, " +
                " Robots INTEGER not NULL," +
                " Size_Of_World INTEGER not NULL, " +
                " Obstacle_Size VARCHAR(50), " +
                " Obstacle_Position VARCHAR(50)) ";

        statement.executeUpdate(sql);
    }

    @Override
    public void DBconnector() {
        // Open a connection
        try(Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
        ) {
            create_SQL_Table(stmt);
            new InsertSQLData();
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
