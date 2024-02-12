package org.spring.util;

public class ExceptionHandler {
    public static void execute(Runnable task, int code , int describe) {
        try {
            task.run();
        } catch (Exception e) {
            // 处理异常逻辑
            System.err.println("执行任务出现异常：" + e.getMessage() +"错误代码" +code + describe );
        }
    }

    public static void execute(Runnable task) {
        try {
            task.run();
        } catch (Exception e) {
            // 处理异常逻辑
            System.err.println("执行任务出现异常：" + e.getMessage());
        }
    }

    public static void throwError(int code , int describe) throws Exception {
        throw new Exception("Error code : " + code + " describe : " + describe);
    }


}

