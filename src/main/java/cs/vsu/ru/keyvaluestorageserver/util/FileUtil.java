package cs.vsu.ru.keyvaluestorageserver.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class FileUtil {

    public static <T> T readYaml(String path, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        File file = new File(path);

        T data = null;

        try {
            data = mapper.readValue(file, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static void rewriteConfigFile(String field, String value) throws IOException {
//        File propertiesFile = new File(getClass().getClassLoader().getResource(fileName).getFile());
//        Property config = new Property(propertiesFile);
//        config..setProperty("hibernate.show_sql", "true");
//        config.save();

        Properties properties = new Properties();

        // In the name of userCreated.properties, in the
        // current directory location, the file is created
        FileOutputStream fileOutputStream
                = new FileOutputStream(
                "application.properties");

        // As an example, given steps how
        // to keep username and password
        properties.setProperty(field, value);


        // writing properties into properties file
        // from Java As we are writing text format,
        // store() method is used
        properties.store(
                fileOutputStream,
                "Sample way of creating Properties file from Java program");

        fileOutputStream.close();
    }
}
