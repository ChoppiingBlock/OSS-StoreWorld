package org.spring.util;


import cn.hutool.core.util.ArrayUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;


//io工具，网络输入流可以直接保存进入本地
public class FileTransTool {

    //网络输入流可以直接保存进入本地

    //合并超大本地文件，进入第三个文件路径
    public  void mergeFiles(String fileAPath, String fileBPath, String mergedFilePath , int chunkSize) throws Exception {


        String currentTime = new SimpleDateFormat("MM-dd HH:mm:ss").format(new Date()).replace(" ","").replace(":", "-");
        mergedFilePath += "\\" + currentTime+ ".txt";
        File file = new File(mergedFilePath);


        try{
            file.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }

        //写入a，b文件分块的时候
        try (FileOutputStream fos = new FileOutputStream(mergedFilePath)) {

            int i = 0;
            MyAsses.ifFilePathExistFile(fileAPath);
            MyAsses.ifFilePathExistFile(fileBPath);
            File file1 = new File(fileAPath);
            File file2 = new File(fileBPath);
            long maxfileSize = Math.max(file1.length(), file2.length());
            for(i = 0 ; i < MaxChunkNumber(maxfileSize,chunkSize) ; i++) {
                int allIndex = 0;
                byte[] abuffer = splitFileWithSize(fileAPath,chunkSize,i);
                byte[] bbuffer = splitFileWithSize(fileBPath,chunkSize,i);


                int j = 0;
                byte[] allBuffer = new byte[0];
                //保证获得的buffer非空
                if(!ArrayUtil.isEmpty(abuffer) && !ArrayUtil.isEmpty(bbuffer)){
                    allBuffer = new byte[abuffer.length + bbuffer.length];
                    while(abuffer.length > j && bbuffer.length > j){
                        allBuffer[allIndex++] = abuffer[j];
                        allBuffer[allIndex++] = bbuffer[j];
                        j++;
                    }

                    while(abuffer.length > j){
                       allBuffer[allIndex++] = abuffer[j];
                       j++;
                    }

                    while (bbuffer.length > j){
                        allBuffer[allIndex++] = bbuffer[j];
                        j++;
                    }
                } else if(!ArrayUtil.isEmpty(abuffer) && ArrayUtil.isEmpty(bbuffer)){
                    allBuffer = new byte[abuffer.length];
                    while(abuffer.length > j){
                        allBuffer[allIndex++] = abuffer[j];
                        j++;
                    }
                } else if(!ArrayUtil.isEmpty(bbuffer) && ArrayUtil.isEmpty(abuffer)){
                    allBuffer = new byte[bbuffer.length];
                    while(bbuffer.length > j){
                        allBuffer[allIndex++] = abuffer[j];
                        j++;
                    }
                }
                fos.write(allBuffer);
            }
            System.out.println("文件合并完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //获得文件按照chunksize分块的第index分片
    public byte[] splitFileWithSize(String filePath , int chunkSize , int index) throws MyException, IOException {
        File file = new File(filePath);
        MyAsses.ifFilePathExistFile(filePath);
        if(chunkSize <= 0 || index < 0) throw new MyException(ErrorCode.BLOCKING_PARAM_ERROR.getCode() ,ErrorCode.BLOCKING_PARAM_ERROR.getMessage());
        long fileSize = file.length();
        int temp =MaxChunkNumber(fileSize,chunkSize);
        if(temp <= index) return null;
        RandomAccessFile inputFile = createRandomAccessFile(filePath,"r");

        int readLength;
        if(index == MaxChunkNumber(fileSize,chunkSize) - 1){
            readLength = (int) (fileSize - index * chunkSize);
        } else {
            readLength = chunkSize;
        }
        byte[] buffer = new byte[readLength];
        int startIndex = chunkSize * index;
        inputFile.seek(startIndex);
        inputFile.read(buffer,0,readLength);
        byte b = 0;
        if(buffer[0] == b){
            System.out.println("hello");
        }
        return  buffer;
    }

    //创建CRF
    public RandomAccessFile createRandomAccessFile(String filePath,String mode) throws MyException {
        try {
            return new RandomAccessFile(filePath, mode);
        } catch (IOException e){
            e.printStackTrace();
            throw new MyException(ErrorCode.FILE_NOT_EXIST.getCode(), ErrorCode.FILE_NOT_EXIST.getMessage());
        }
    }

    //文件最多可以分为多少块
    public int MaxChunkNumber(long fileSize , int chunkSize){
        return (int) Math.ceil((double) fileSize / chunkSize);
    }





}
