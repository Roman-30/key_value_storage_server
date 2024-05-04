package cs.vsu.ru.keyvaluestorageserver.util;

import com.netflix.appinfo.InstanceInfo;

public class WebUtil {

    public static String createAddressByInstance(InstanceInfo instance) {
        return String.format(
                "%s:%s", instance.getHostName(), instance.getPort()
        );
    }

    public static String makeRequestUrl(InstanceInfo instance, String endpoint) {
        return String.format("http://%s%s", createAddressByInstance(instance), endpoint);
    }
}
