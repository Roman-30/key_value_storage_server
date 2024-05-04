package cs.vsu.ru.keyvaluestorageserver.connection.config;

import cs.vsu.ru.keyvaluestorageserver.connection.model.ConnectionConfigModel;
import cs.vsu.ru.keyvaluestorageserver.connection.model.ConnectionModel;
import cs.vsu.ru.keyvaluestorageserver.connection.model.WebAddress;
import cs.vsu.ru.keyvaluestorageserver.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ConnectionConfig {
    @Bean("connectionConfigModel")
    public ConnectionConfigModel getConnection() {
        return FileUtil.readYaml(
                "C:\\Users\\romse\\Desktop\\Test eurica\\TestServer\\src\\main\\resources\\connection\\connection_cofig.yaml",
                ConnectionConfigModel.class
        );
    }

    @Bean("helpConnectionData")
    public ConnectionConfigModel.HelpConnectionData getHelpConnectionData(@Autowired ConnectionConfigModel connectionConfigModel) {
        Map<WebAddress, ConnectionModel> connection = new HashMap<>();

        connectionConfigModel.getConnectionModels().forEach(val -> connection.put(val.getAddress(), val));

        return new ConnectionConfigModel.HelpConnectionData(connection);
    }
}
