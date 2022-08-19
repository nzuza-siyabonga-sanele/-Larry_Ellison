//package za.co.wethinkcode.robotworlds.world;
//
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.sql.SQLException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class DBconnectorTest {
//
//    // test connect
//
//    @Test
//    void connectToDb() throws IOException {
//        final String DB_URL = "jdbc:sqlite:test.db";
//        DBconnector Dbtest;
//        try {
//            Dbtest = new DBconnector("test.db");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//        @Test
//        void readConfigFileTestSize() throws IOException {
//            ReadConfigFile read = new ReadConfigFile();
//            assertEquals("1", read.getWorldInfo().getProperty("size"));
//        }
//
//    }
//
