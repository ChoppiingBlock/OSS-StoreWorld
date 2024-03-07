package org.spring.tool.Impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.tool.StreamUtil;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Random;

/**
 * TODO
 *
 * @Description
 * @Author ZW
 * @Date 2023/3/3 8:39
 **/
@Component
public class SafetyStreamUtil implements StreamUtil {
    Logger logger = LoggerFactory.getLogger(StreamUtil.class);
    //流数组的长度
    public static final int BYTE_ARRAY_LENGTH = 1024;
    //流的长度
    public static final int BYTE_ARRAY_SIZE = 1024;
    private static final byte[][] bytes = new byte[BYTE_ARRAY_LENGTH][BYTE_ARRAY_SIZE];

    /**
     * 分配指定长度的 byte数组
     *
     * @return 分配的byte数组
     */
    private byte[] getThreadAllotByteArray() {
        Random random = new Random();
        int i = random.nextInt(BYTE_ARRAY_LENGTH);
        return bytes[i];
    }

    @Override
    public boolean inputStreamToOutput(InputStream inputStream, OutputStream outputStream) {
        boolean b = readAllData(inputStream, outputStream);
        return b;
    }

    private boolean readAllData(InputStream inputStream, OutputStream outputStream) {
        int read = 0;
        byte[] threadBytes = getThreadAllotByteArray();
        try {
            while (true) {
                if ((read = inputStream.read(threadBytes)) != -1) {
                    synchronized (threadBytes) {
                        outputStream.write(threadBytes, 0, read);
                    }
                } else {
                    break;
                }
            }
            return true;
        } catch (IOException e) {

            return false;
        } finally {
            streamClose(outputStream);
            streamClose(inputStream);
        }
    }


    @Override
    public boolean inputStreamToFile(InputStream inputStream, File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                logger.error("文件创建失败 ,创建文件为: {}", file.getPath());
                return false;
            }
        }
        if (!file.isFile()) {
            logger.error("非法文件:{}", file.getPath());
            return false;
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            return inputStreamToOutput(inputStream, fileOutputStream);
        } catch (IOException e) {
            logger.error("文件输出流错误");
            return false;
        }
    }

    @Override
    public boolean readOffsetToOut(File file, OutputStream outputStream, long offset) {
        byte[] threadAllotByteArray = getThreadAllotByteArray();

        try (RandomAccessFile fileInputStream = new RandomAccessFile(file, "rw")) {
            if (offset != 0) {
                fileInputStream.seek(offset);
            }
            int read = 0;
            while (true) {
                if ((read = fileInputStream.read(threadAllotByteArray)) != -1) {
                    synchronized (threadAllotByteArray) {
                        System.out.println("文件写入" + read);
                        outputStream.write(threadAllotByteArray, 0, read);
                    }
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            return false;
        }

        return false;
    }

    /**
     * 关闭流并处理异常
     *
     * @param closeable 需要关闭的流
     */
    private void streamClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                logger.error("流关闭异常");
            }
        }
    }
}