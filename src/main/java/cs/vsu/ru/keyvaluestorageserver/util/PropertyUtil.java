package cs.vsu.ru.keyvaluestorageserver.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {
    public static void setValue(String key, String value) {
        Properties props = new Properties();
        String path = "C:\\Users\\romse\\Desktop\\4 курс\\8 семестр\\Диплом\\diplom\\src\\main\\resources\\application.properties";
        File f = new File(path);
        try {
            final FileInputStream configStream = new FileInputStream(f);
            props.load(configStream);
            configStream.close();
            props.setProperty(key, value);
            final FileOutputStream output = new FileOutputStream(f);
            props.store(output, "");
            output.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getValue(String key) {
        String value = null;
        try {
            Properties prop = new Properties();
            File f = new File("C:\\Users\\romse\\Desktop\\4 курс\\8 семестр\\Диплом\\diplom\\src\\main\\resources\\application.properties");
            if (f.exists()) {
                prop.load(new FileInputStream(f));
                value = prop.getProperty(key);
            }
        } catch (Exception e) {
            System.out.println("Failed to read from runTime.properties");
        }
        return value;
    }
}
