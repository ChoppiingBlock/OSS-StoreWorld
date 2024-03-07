package org.spring.tool;

import java.io.File;

/**
 * TODO
 *
 * @Description
 * @Author ZW
 * @Date 2023/3/5 9:32
 **/
public class FileUtil {
    /**返回文件类型*/
    public static String getFileType(File file){
        if (!file.exists()&&!file.isFile()){
            return "";
        }
        String name = file.getName();
        System.out.println(name);
        int i1 = name.lastIndexOf(".");
        return name.substring(i1 +1);
    }

    /**返回文件类型*/
    public static String getFileType(String filepath){
        return getFileType(new File(filepath));
    }

    /**返回文件全名，文件名+文件类型*/
    public static String getFileName(String filePath){
        File file = new File(filePath);
        if (!file.exists()&&!file.isFile()){
            return "";
        }
        return file.getName();
    }

    /**获取文件名 ，不返回类型*/
    public static String getFileNameNotType(String filePath){
        return getFileNameNotType(new File(filePath));
    }


    public static String getFileNameNotType(File file){
        if (!file.exists()&&!file.isFile()){
            return "";
        }
        String name = file.getName();
        name = name.substring(0, name.lastIndexOf("."));
        return name;
    }


    /**
     * @param fileName 文件名
     * @return String
     * @description 通过文件后缀名获取文件类型
     * @author gyj
     */
    public static String getFileTypeBySuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
