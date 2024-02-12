package org.spring.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@TableName("file_shard_mapping")
public class FileShardMapping implements Serializable {
    /**
     * 分片ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 文件ID，外键指向文件表的id字段
     */
    @TableField("file_id")
    private Integer fileId;

    /**
     * 分片索引
     */
    @TableField("shard_index")
    private Integer shardIndex;

    /**
     * 分片服务ip
     */
    @TableField("shard_ip")
    private String shardIp;

    /**
     * 分片服务port
     */
    @TableField("shard_port")
    private String shardPort;

    /**
     * 分片服务器文件位置
     */
    @TableField("shard_location")
    private String shardLocation;

    /**
     * 创建日期
     */
    @TableField(value = "createdate", fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 更新日期
     */
    @TableField(value = "updateDate", fill = FieldFill.INSERT_UPDATE,
            update = "CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updateDate;

    /**
     * 行版本号
     */
    @TableField(value = "rowversion", fill = FieldFill.INSERT_UPDATE,
            update = "CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime rowVersion;

    public FileShardMapping(Integer id, Integer fileId, Integer shardIndex, String shardIp, String shardPort,
                            String shardLocation, LocalDateTime createDate, LocalDateTime updateDate,
                            LocalDateTime rowVersion) {
        this.id = id;
        this.fileId = fileId;
        this.shardIndex = shardIndex;
        this.shardIp = shardIp;
        this.shardPort = shardPort;
        this.shardLocation = shardLocation;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.rowVersion = rowVersion;
    }

    // Equals, HashCode, ToString

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileShardMapping that = (FileShardMapping) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(fileId, that.fileId) &&
                Objects.equals(shardIndex, that.shardIndex) &&
                Objects.equals(shardIp, that.shardIp) &&
                Objects.equals(shardPort, that.shardPort) &&
                Objects.equals(shardLocation, that.shardLocation) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(updateDate, that.updateDate) &&
                Objects.equals(rowVersion, that.rowVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fileId, shardIndex, shardIp, shardPort, shardLocation, createDate, updateDate, rowVersion);
    }

    @Override
    public String toString() {
        return "FileShardMapping{" +
                "id=" + id +
                ", fileId=" + fileId +
                ", shardIndex=" + shardIndex +
                ", shardIp='" + shardIp + '\'' +
                ", shardPort='" + shardPort + '\'' +
                ", shardLocation='" + shardLocation + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", rowVersion=" + rowVersion +
                '}';
    }
}

