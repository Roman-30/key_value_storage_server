package cs.vsu.ru.keyvaluestorageserver.orchestrator.service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.eureka.registry.InstanceRegistry;
import cs.vsu.ru.keyvaluestorageserver.connection.mapper.AddressMapper;
import cs.vsu.ru.keyvaluestorageserver.connection.model.ConnectionConfigModel;
import cs.vsu.ru.keyvaluestorageserver.connection.model.ConnectionModel;
import cs.vsu.ru.keyvaluestorageserver.connection.model.WebAddress;
import cs.vsu.ru.keyvaluestorageserver.connection.ref.ShardState;
import cs.vsu.ru.keyvaluestorageserver.util.WebUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static cs.vsu.ru.keyvaluestorageserver.util.WebUtil.createAddressByInstance;
import static cs.vsu.ru.keyvaluestorageserver.util.WebUtil.makeRequestUrl;


@Service
@RequiredArgsConstructor
public class ReplicaService {
    private final ConnectionConfigModel connectionConfigModel;
    private final ConnectionConfigModel.HelpConnectionData helpConnectionData;
    private final InstanceRegistry instanceRegistry;
    private final AddressMapper addressMapper;
    private final ApplicationContext context;
    private final HttpService httpService;

    private final Map<String, String> activAddressMap = new HashMap<>();

    public boolean isRepMaster(InstanceInfo instance) {
        System.out.println();
        System.out.println(httpService.getRepState(makeRequestUrl(instance, "/param/state")));
        System.out.println();
        return ShardState.valueOf(httpService.getRepState(makeRequestUrl(instance, "/param/state"))).getState();
    }


    public boolean canselInstance(InstanceInfo instance) {
        return instanceRegistry.cancel(
                instance.getAppName(),
                instance.getInstanceId(),
                true
        );
    }

    public boolean isOwn(InstanceInfo instance) {
        WebAddress webAddress = addressMapper.instanceToAddress(instance);
        for (Map.Entry<WebAddress, ConnectionModel> val : helpConnectionData.getAddressConnectionModelMap().entrySet()) {
            var cur = val.getKey().equals(webAddress);
            if (val.getKey().equals(webAddress)) {
                return helpConnectionData.getAddressConnectionModelMap().containsKey(webAddress);
            }
        }
        return false;
    }

    public void makeRepIsMaster(InstanceInfo instance) {
        String url = activAddressMap.get(instance.getAppName());
        sendActiveRep(instance);
        httpService.makeRepIsMaster(String.format("http://%s/param/true/master", url));
    }

    private void sendActiveRep(InstanceInfo instance) {
        Set<String> activeRepNames = activAddressMap.keySet();
        activeRepNames.remove(instance.getAppName());
        httpService.sendActualName(activeRepNames, WebUtil.makeRequestUrl(instance, "/connection/rep-name"));
    }

    public boolean isMaster(InstanceInfo instance) {
        WebAddress webAddress = addressMapper.instanceToAddress(instance);
        return helpConnectionData
                .getAddressConnectionModelMap()
                .get(webAddress)
                .getShardState()
                .getState();
    }



    public void addActiveReplica(InstanceInfo instance) {
        if (!activAddressMap.containsKey(instance.getAppName())) {
            activAddressMap.put(instance.getAppName(), createAddressByInstance(instance));
        }
    }
}
