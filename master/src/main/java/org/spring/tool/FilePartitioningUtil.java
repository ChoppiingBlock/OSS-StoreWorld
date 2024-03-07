package org.spring.tool;

/**
 * TODO
 *
 * @Description
 * @Author ZW
 * @Date 2023/3/3 9:29
 **/
public interface FilePartitioningUtil {
    /**
     * 合并
     *
     * @param filepath 路径
     * @return boolean 合并成功
     */
    boolean decoder(String filepath);

    /**
     * 分块
     *
     * @param filepath 路径
     * @return boolean 分块成功
     */
    boolean encoder(String filepath);

    /**
     * 分块
     *
     * @param filepath   路径
     * @param dataShards 原数据分块数
     * @param partShards 冗余数据分块数
     * @return boolean 分块成功
     */
    boolean encoder(String filepath, int dataShards, int partShards);

    /**
     * 分块
     *
     * @param filepath   路径
     * @param dataShards 原数据分块数
     * @param partShards 冗余数据分块数
     * @return boolean 分块成功
     */
    boolean decoder(String filepath, int dataShards, int partShards);
}
