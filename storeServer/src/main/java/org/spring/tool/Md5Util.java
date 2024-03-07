package org.spring.tool;

import java.io.File;

/**
 * TODO
 *
 * @Description
 * @Author ZW
 * @Date 2023/3/2 10:26
 **/
public interface Md5Util {
    /**
     * 获取文件的md5值
     *
     * @param file 需要进行计算的文件
     * @return 文件的md5值 若文件非法则返回空<H></>
     */
    public String getFileMd5(File file);

    default String getFileMd5(String filePath) {
        return getFileMd5(new File(filePath));
    }

    /**
     * 根据文件的md5值返回文件的存储位置,每一层文件夹代表一个
     *
     * @param fileMd5 文件的md5值
     * @return 文件的存储位置 每一层代表一个文件夹
     */
    @Deprecated
    public String[] md5MapFilePath(String fileMd5);

    /**
     * 根据文件的md5值返回文件的存储位置,每一层文件夹代表一个
     *
     * @param fileMd5 文件的md5值
     * @return 文件的存储位置 每一层代表一个文件夹
     */

    String md5MapFilePathStr(String fileMd5);

    /**
     * 通过文件的md5值映射唯一的名字
     * @param fileMd5 文件的md5值
     * @return 文件的md5值
     */
    String md5GetFileMapName(String fileMd5);
}
