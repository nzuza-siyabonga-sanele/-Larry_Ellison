package za.co.wethinkcode.robotworlds.Read_World_Info;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadConfigFile {

    public ReadConfigFile() {}

    public Properties getWorldInfo() throws IOException {

        String configFilePath = "config.properties";
        FileInputStream propsInput = new FileInputStream(configFilePath);
        Properties prop = new Properties();
        prop.load(propsInput);
        return prop;
    }
}
