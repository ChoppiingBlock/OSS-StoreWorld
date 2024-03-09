package org.spring.pojo;

import lombok.Data;

@Data
public class InstanceIndex {
    private String instanceName;
    private String instanceId;
    public InstanceIndex(){}
    public InstanceIndex(String instanceName , String instanceId){
        this.instanceId = instanceId;
        this.instanceName  = instanceName;
    }
}
