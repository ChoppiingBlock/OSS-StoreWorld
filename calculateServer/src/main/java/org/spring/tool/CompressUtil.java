package org.spring.tool;

/**
 * TODO
 *
 * @Description
 * @Author ZW
 * @Date 2023/3/3 9:17
 **/
public interface CompressUtil {

    /**
     * 将文件解压
     * @param sourceFile 源文件，如：archive.tar.gz
     * @param targetFile 目标文件，如：archive.tar
     * @return 解缩是否成功
     */
    boolean unCompress(String sourceFile, String targetFile);

    /**
     * 将文件压缩
     * @param sourceFile 源文件，如：archive.tar
     * @param targetFile 目标文件，如：archive.tar.gz
     * @return 压缩是否成功
     */
    boolean compress(String sourceFile, String targetFile);


}
