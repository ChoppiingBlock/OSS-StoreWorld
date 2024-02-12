package org.spring.util;

import java.io.File;
import java.io.RandomAccessFile;

public class MyAsses {
    public static void ifFilePathExistFile(String filePath) throws MyException {
        File file = new File(filePath);
        if(!file.exists()) throw new MyException(ErrorCode.FILE_NOT_EXIST.getCode(),ErrorCode.FILE_NOT_EXIST.getMessage());
    }

    public static Object errorProxy(String className) throws MyException{
        try {
            // 使用 Class.forName() 方法获取类的 Class 对象
            Class<?> cls = Class.forName(className);

            // 使用 Class 对象的 newInstance() 方法实例化对象
            return cls.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e){
            e.printStackTrace();
            throw new MyException(ErrorCode.ERROR_PROXY.getCode(), ErrorCode.ERROR_PROXY.getMessage() );
        }

    }





}
