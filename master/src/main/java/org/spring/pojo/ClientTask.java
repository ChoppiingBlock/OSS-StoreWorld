package org.spring.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.dto.TaskStatus;
import org.spring.po.FileShardMapping;
import org.spring.server.Impl.FileShardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientTask extends TransTask{
    private FileShardMapping fileShardMapping;
    private String ClientFileIp;
    private String ClientFilePort;
    private String ClientFilePath;

    @Autowired
    FileShardServiceImpl fileShardService;

    @Override
    public void reStart(){
        setStatus(TaskStatus.WAITING.getStatusCode());
        setFailTime(getFailTime() + 1);
    }

    @Override
    public void checkFailTimes(){
        if(getFailTime() > 5) {
            setStatus(TaskStatus.ABANDON.getStatusCode());
        }
    }

    @Override
    public void run(){
        setStatus(TaskStatus.RUNNING.getStatusCode());
        fileShardService.getFeign(fileShardMapping,getClientFileIp(),
                getClientFilePort(),getClientFilePath());
    }

}
