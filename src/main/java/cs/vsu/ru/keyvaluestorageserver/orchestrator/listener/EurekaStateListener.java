package cs.vsu.ru.keyvaluestorageserver.orchestrator.listener;

import com.netflix.appinfo.InstanceInfo;
import cs.vsu.ru.keyvaluestorageserver.orchestrator.service.ReplicaService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EurekaStateListener {

    private final static Logger logger = LoggerFactory.getLogger(EurekaStateListener.class);
    private final ReplicaService replicaService;
    private final ApplicationContext context;


    /**
     * Это событие запускается, когда экземпляр службы не зарегистрирован или отменен с сервера Eureka.
     *
     * @param event
     */
    @EventListener
    public void listen(EurekaInstanceCanceledEvent event) {
        System.out.println("EurekaInstanceCanceledEvent");
    }

    /**
     * Это событие запускается, когда новый экземпляр службы регистрируется на сервере Eureka.
     *
     * @param event
     */
    @EventListener
    public void listen(EurekaInstanceRegisteredEvent event) {
        InstanceInfo instance = event.getInstanceInfo();
        if (event.isReplication() && event.getInstanceInfo().getStatus() == InstanceInfo.InstanceStatus.UP) {
            if (replicaService.isOwn(instance)) {
                replicaService.addActiveReplica(instance);

                boolean state = replicaService.isRepMaster(instance);

                if (replicaService.isMaster(instance) && !state) {
                    replicaService.makeRepIsMaster(instance);
                }
            } else {
                replicaService.canselInstance(instance);
            }
        }
    }

    /**
     * Это событие запускается, когда экземпляр службы продлевает аренду с сервером Eureka.
     * Сервисам требуется отправлять сообщения на сервер Eureka каждые 30 секунд, чтобы продлить аренду и подтвердить, что они все еще работают.
     *
     * @param event
     */
    @EventListener
    public void listen(EurekaInstanceRenewedEvent event) {
        System.out.println(event.getInstanceInfo());
    }


    /**
     * Это событие запускается, когда реестр сервера Eureka
     * доступен и готов принимать сервисные регистрации и запросы.
     *
     * @param event
     */
    @EventListener
    public void listen(EurekaRegistryAvailableEvent event) {
        System.out.println("EurekaRegistryAvailableEvent");
    }

    /**
     * Это событие запускается, когда сервер Eureka полностью запущен и готов к работе.
     *
     * @param event
     */
    @EventListener
    public void listen(EurekaServerStartedEvent event) {
        System.out.println("EurekaServerStartedEvent");
    }

}