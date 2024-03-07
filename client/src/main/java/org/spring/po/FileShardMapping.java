package org.spring.po;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Builder
public class FileShardMapping implements Serializable {
    /**
     * 分片ID
     */

    private Integer id;

    /**
     * 文件ID
     */

    private Integer fileId;

    /**
     * 分片索引
     */

    private Integer shardIndex;

    /**
     * 分片位置
     */

    private String shardLocation;

    /**
     * 创建日期
     */

    private LocalDateTime createDate;

    /**
     * 更新日期
     */

    private LocalDateTime updateDate;

    /**
     * 行版本
     */

    private Integer rowVersion;

    /**
     * 分片存储服务器ID
     */

    private String shardStoreServerId;

    // 全参数构造方法
    public FileShardMapping(Integer id, Integer fileId, Integer shardIndex, String shardLocation,
                            LocalDateTime createDate, LocalDateTime updateDate, Integer rowVersion, String shardStoreServerId) {
        this.id = id;
        this.fileId = fileId;
        this.shardIndex = shardIndex;
        this.shardLocation = shardLocation;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.rowVersion = rowVersion;
        this.shardStoreServerId = shardStoreServerId;
    }

    @Override
    public String toString() {
        return "FileShardMapping{" +
                "id=" + id +
                ", fileId=" + fileId +
                ", shardIndex=" + shardIndex +
                ", shardLocation='" + shardLocation + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", rowVersion=" + rowVersion +
                ", shardStoreServerId='" + shardStoreServerId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileShardMapping that = (FileShardMapping) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(fileId, that.fileId) &&
                Objects.equals(shardIndex, that.shardIndex) &&
                Objects.equals(shardLocation, that.shardLocation) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(updateDate, that.updateDate) &&
                Objects.equals(rowVersion, that.rowVersion) &&
                Objects.equals(shardStoreServerId, that.shardStoreServerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fileId, shardIndex, shardLocation, createDate, updateDate, rowVersion, shardStoreServerId);
    }

}

