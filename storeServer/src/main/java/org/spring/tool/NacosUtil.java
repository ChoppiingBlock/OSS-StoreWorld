package org.spring.tool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ShinomiyaKiYoru
 */
@Slf4j
public class NacosUtil {
    public static  DiscoveryClient discoveryClient;
    public static List<ServiceInstance> getServiceInstances(String thisServiceName){
        return discoveryClient.getInstances(thisServiceName);

    }

    public static List<String> getIpPort(String serverName, String serverId){
        List<String> ipPort = new ArrayList<>();
        List<ServiceInstance> instances = discoveryClient.getInstances(serverName);
        for (int i = 0; i < instances.size(); i++) {
            Map<String, String> metadata = instances.get(i).getMetadata();
            if(metadata.get("serverId").equals(serverId)){
                ipPort.add(instances.get(i).getHost());
                ipPort.add(String.valueOf(instances.get(i).getPort()));
            }
        }
        return ipPort;
    }
}
