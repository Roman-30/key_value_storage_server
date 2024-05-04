package cs.vsu.ru.keyvaluestorageserver.connection.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs.vsu.ru.keyvaluestorageserver.connection.ref.ShardState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ConnectionModel {
    @JsonProperty
    private WebAddress address;
    @JsonProperty
    private ShardState shardState;
    @JsonProperty
    private Integer shardId;
}
