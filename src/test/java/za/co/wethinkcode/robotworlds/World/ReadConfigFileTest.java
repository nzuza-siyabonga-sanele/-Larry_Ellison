package za.co.wethinkcode.robotworlds.world;


import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Read_World_Info.ReadConfigFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReadConfigFileTest {

    @Test
    void readConfigFileTestSize() throws IOException {
        ReadConfigFile read = new ReadConfigFile();
        assertEquals("1", read.getWorldInfo().getProperty("size"));
    }
    @Test
    void readConfigFileTestPort() throws IOException {
        ReadConfigFile read = new ReadConfigFile();
        assertEquals("5000", read.getWorldInfo().getProperty("port"));
    }
    @Test
    void readConfigFileTestObstacles() throws IOException {
        ReadConfigFile read = new ReadConfigFile();
        assertEquals("none", read.getWorldInfo().getProperty("obstacles"));
    }
    @Test
    void readConfigFileTestWidth() throws IOException {
        ReadConfigFile read = new ReadConfigFile();
        assertEquals("1", read.getWorldInfo().getProperty("width"));
    }
    @Test
    void readConfigFileTestHeight() throws IOException {
        ReadConfigFile read = new ReadConfigFile();
        assertEquals("1", read.getWorldInfo().getProperty("height"));
    }




}