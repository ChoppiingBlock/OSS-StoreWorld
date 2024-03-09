package org.spring.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.dto.TaskStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public  abstract class TransTask implements Runnable{
    Logger logger = LoggerFactory.getLogger(TransTask.class);
    private InstanceIndex instanceIndex;

    private int status;

    private int failTime;

    public void reStart(){
        logger.info("ReStare ");
        setStatus(TaskStatus.WAITING.getStatusCode());
        setFailTime(getFailTime() + 1);
    }

    public void checkFailTimes(){
        if(getFailTime() > 5) {
            setStatus(TaskStatus.ABANDON.getStatusCode());
        }
    }

    public int checkStatus(){
        if(this.status == (TaskStatus.FAIL.getStatusCode())){
            checkFailTimes();
        }
        if(this.status == (TaskStatus.FAIL.getStatusCode())){
            reStart();
        }
        return status;
    }



    @Override
    public void run(){}

}
