package org.spring.tool.Impl;

import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.tool.CompressUtil;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author ShinomiyaKiYoru
 */
@Component
public class GzipUtil implements CompressUtil {

    Logger logger  = LoggerFactory.getLogger(CompressUtil.class);
    final static int BUFFER_SIZE = 1024;
    /**
     * 将文件压缩成gzip
     * @param sourceFile 源文件，如：archive.tar
     * @param targetFile 目标文件，如：archive.tar.gz
     */
    public boolean compress(String sourceFile, String targetFile) {
        long d1 = System.currentTimeMillis();
        try (InputStream in = Files.newInputStream(Paths.get(sourceFile));
             OutputStream fout = Files.newOutputStream(Paths.get(targetFile));
             BufferedOutputStream out = new BufferedOutputStream(fout);
             GzipCompressorOutputStream gzOut = new GzipCompressorOutputStream(out);){
            final byte[] buffer = new byte[BUFFER_SIZE];
            int n;
            while (-1 != (n = in.read(buffer))) {
                gzOut.write(buffer, 0, n);
            }
        } catch (IOException e) {
            logger.error("压缩失败，原因：{}" , e.getMessage());
            return false;
        }
        logger.info("压缩完毕，耗时: {}" , (System.currentTimeMillis() - d1) + " ms");
        return true;
    }

    /**
     * 将gzip文件解压
     * @param sourceFile 源文件，如：archive.tar.gz
     * @param targetFile 目标文件，如：archive.tar
     */
    public  boolean unCompress(String sourceFile , String targetFile) {
        long d1 = System.currentTimeMillis();
        try (InputStream fin = Files.newInputStream(Paths.get(sourceFile));
             BufferedInputStream in = new BufferedInputStream(fin);
             OutputStream out = Files.newOutputStream(Paths.get(targetFile));
             GzipCompressorInputStream gzIn = new GzipCompressorInputStream(in);){
            final byte[] buffer = new byte[BUFFER_SIZE];
            int n = 0;
            while (-1 != (n = gzIn.read(buffer))) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            logger.error("解压失败，原因：{}" , e.getMessage());
            return false;
        }
        logger.info("解压完毕，耗时: {}" , (System.currentTimeMillis() - d1) + " ms");
        return true;
    }
}
