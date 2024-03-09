package org.spring.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.dto.StatusCode;
import org.spring.dto.TaskStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class TaskMap {
    private Map<InstanceIndex , TransTask> transTaskMap
            = new ConcurrentHashMap<>();


    public void finish(InstanceIndex instanceIndex){
        if(transTaskMap.containsKey(instanceIndex)){
            transTaskMap.remove(instanceIndex);
        }
    }

    public void fail(InstanceIndex instanceIndex){
        if(transTaskMap.containsKey(instanceIndex)){
            TransTask transTask = transTaskMap.get(instanceIndex);
            if(transTask.getFailTime() > 5){
                transTask.setStatus(TaskStatus.ABANDON.getStatusCode());
            } else {
                transTask.reStart();
            }
        }
    }
}
