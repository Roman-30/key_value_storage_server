package cs.vsu.ru.keyvaluestorageserver.connection.mapper;

import com.netflix.appinfo.InstanceInfo;
import cs.vsu.ru.keyvaluestorageserver.connection.model.WebAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "port", source = "info", qualifiedByName = "getPort")
    @Mapping(target = "host", source = "info", qualifiedByName = "getHost")
    WebAddress instanceToAddress(InstanceInfo info);

    @Named("getHost")
    default String getHost(InstanceInfo info) {
        return info.getHostName();
    }

    @Named("getPort")
    default int getPort(InstanceInfo info) {
        return info.getPort();
    }
}
