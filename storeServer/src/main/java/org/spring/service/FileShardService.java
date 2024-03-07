//package org.spring.service;
//
//import com.baomidou.mybatisplus.extension.service.IService;
//import org.apache.ibatis.annotations.Mapper;
//import org.spring.po.FileShardMapping;
//
//@Mapper
//public interface FileShardService extends IService<FileShardMapping> {
//
//    public boolean getFile(FileShardMapping fileShardMapping
//            ,String fileIp
//            ,String filePort
//            ,String filePath);
//
//    public boolean writeDataBase(FileShardMapping fileShardMapping);
//}
package org.spring.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;
import org.spring.po.FileShardMapping;
@Mapper
public interface FileShardService extends IService<FileShardMapping> {
    /**
     * 得到文件分片，保存本地
     *
     * @param fileShardMapping
     * @param fileIp
     * @param filePort
     * @param offset
     * @param filePath
     * @return boolean
     * @date 2024/2/21 10:51
     */

    public boolean getFile(FileShardMapping fileShardMapping
            ,String fileIp
            ,String filePort
            ,int offset
            ,String filePath);

    public boolean writeDataBase(FileShardMapping fileShardMapping);
}

