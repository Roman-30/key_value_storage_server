package cs.vsu.ru.keyvaluestorageserver.connection.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ConnectionConfigModel {
    @JsonProperty
    private Integer shardCount;
    @JsonProperty
    private List<ConnectionModel> connectionModels;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HelpConnectionData {
        private Map<WebAddress, ConnectionModel> addressConnectionModelMap;
    }
}
