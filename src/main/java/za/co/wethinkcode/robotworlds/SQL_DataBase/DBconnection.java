package za.co.wethinkcode.robotworlds.SQL_DataBase;

import java.util.Scanner;

public interface DBconnection {

    public final String DB_URL = "jdbc:sqlite:my_world.db";

    void DBconnector();
    void DBconnector(String[] args);

    default String getInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (input.isBlank()) {
            input = scanner.nextLine();
        }
        return input;
    }

}
