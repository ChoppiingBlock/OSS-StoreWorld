package org.spring.tool.Impl;


import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.spring.tool.Md5Util;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Description
 * @Author ZW
 * @Date 2023/3/2 10:35
 **/
@Component
public class CodecMd5Util implements Md5Util {
    /**md5的最小长度*/
    final int MD5_MIN_LENGTH = 20;
    /**文件的层数*/
    final int FILEPATH_PLIES_LENGTH = 5;
    /**md5的取模值 单个文件夹下的文件夹数*/
    final int DIR_LENGTH = 5;
    Logger logger = LoggerFactory.getLogger(Md5Util.class);

    @Override
    public String getFileMd5(File file) {
        String md5 = "";
        if (!file.exists() || !file.isFile()) {
            return md5;
        }
        try (FileInputStream fileInputStream = new FileInputStream(file);) {
            md5 = DigestUtils.md5Hex(fileInputStream);
        } catch (IOException e) {
            return "";
        }
        return md5;
    }

    @Override
    public String[] md5MapFilePath(String fileMd5) {
        if (fileMd5 == null || fileMd5.length() < MD5_MIN_LENGTH) {
            logger.info("文件的md5值不符合预期结构 md5 : {}" + fileMd5);
            return new String[0];
        }
        String substring = fileMd5.substring(0, MD5_MIN_LENGTH );
        char[] chars = substring.toCharArray();
        int flag = MD5_MIN_LENGTH / FILEPATH_PLIES_LENGTH;
        String[] paths = new String[FILEPATH_PLIES_LENGTH];
        for (int i = 0; i < FILEPATH_PLIES_LENGTH; i++) {
            paths[i] = indexToEndGetMod(chars, i * flag, (i + 1) * flag);
        }
        return paths;
    }

    @Override
    public String md5MapFilePathStr(String fileMd5) {
        if (fileMd5 == null || fileMd5.length() < MD5_MIN_LENGTH) {
            logger.info("文件的md5值不符合预期结构 md5 : {}" + fileMd5);
            return "";
        }
        String substring = fileMd5.substring(0, MD5_MIN_LENGTH );
        char[] chars = substring.toCharArray();
        int flag = MD5_MIN_LENGTH / FILEPATH_PLIES_LENGTH;
        StringBuilder path = new StringBuilder();
        for (int i = 0; i < FILEPATH_PLIES_LENGTH; i++) {
            path.append(indexToEndGetMod(chars, i * flag, (i + 1) * flag)).append(File.separator);
        }
        return path.toString();
    }

    @Override
    public String md5GetFileMapName(String fileMd5) {
        return fileMd5.substring(MD5_MIN_LENGTH) +  ".temp";
    }

    /**
     * 获取 index 到end位置的字符串总和的mod
     *
     * @param chars 需要截取的字符串
     * @param index 起始位置
     * @param end   结束位置
     * @return 文件对应的mod
     */
    protected String indexToEndGetMod(char[] chars, int index, int end) {
        int sum = 0;
        for (int i = index; i < end; i++) {
            sum += chars[i];
        }
        return "" + (sum % DIR_LENGTH);
    }

}
