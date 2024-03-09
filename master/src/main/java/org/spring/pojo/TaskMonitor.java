package org.spring.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.dto.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;


@Data
@AllArgsConstructor
public class TaskMonitor implements Runnable{
    @Autowired
    TaskMap taskMap;
    Logger logger = LoggerFactory.getLogger(TaskMonitor.class);

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;

    @Override
    public void run() {
        Map<InstanceIndex,TransTask> transTaskMap = taskMap.getTransTaskMap();
        while (true) {
            try {
                for (InstanceIndex key : transTaskMap.keySet()) {
                    int status = transTaskMap.get(key).checkStatus();
                    switch (status) {
                        case(5) : {
                            logger.info("遗弃任务{}",key);
                            transTaskMap.remove(key);
                            break;
                        }
                        case(4) : {
                            logger.info("Task {} fild {} times",
                                    key,transTaskMap.get(key).getFailTime());
                            taskMap.fail(key);
                            break;
                        }
                        case(3) : {
                            logger.info("完成任务{}",key);
                            transTaskMap.remove(key);
                            break;
                        }
                        case(1) : {
                            ThreadPoolExecutor threadPoolExecutor = taskExecutor.getThreadPoolExecutor();
                            BlockingQueue<Runnable> queue = threadPoolExecutor.getQueue();
                            int queueSize = queue.size();
                            int capacity = threadPoolExecutor.getQueue().remainingCapacity();

                            if(queueSize >= capacity) {
                                break;
                            } else {
                                taskExecutor.execute(transTaskMap.get(key));
                            }
                        }
                    }
                }
                Thread.sleep(1000); // 每1秒遍历一次taskMap
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
