package cs.vsu.ru.keyvaluestorageserver.connection.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class WebAddress {
    @JsonProperty
    private Integer port;
    @JsonProperty
    private String host;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebAddress that = (WebAddress) o;
        return Objects.equals(port, that.port) && Objects.equals(host, that.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(port, host);
    }
}
