package org.spring.po;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Builder
public class FileMetadata implements Serializable {
    /**
     * 文件ID
     */
    private Integer id;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件MD5哈希值
     */
    private String md5;

    /**
     * 所在目录ID
     */
    private Integer parentId;

    /**
     * 桶世界目录路径
     */
    private String path;

    /**
     * 分片类型
     */
    private String shadeType;

    /**
     * 分片参数m
     */
    private Integer shadeParamM;

    /**
     * 分片参数n
     */
    private Integer shadeParamN;

    /**
     * 创建者，外键指向用户表的id字段
     */
    private Integer creator;

    /**
     * 创建日期
     */
    private LocalDateTime createDate;

    /**
     * 更新日期
     */
    private LocalDateTime updateDate;

    /**
     * 行版本号
     */
    private LocalDateTime rowVersion;

    public FileMetadata(Integer id, String name, Long size, String type, String md5, Integer parentId, String path,
                String shadeType, Integer shadeParamM, Integer shadeParamN, Integer creator, LocalDateTime createDate,
                LocalDateTime updateDate, LocalDateTime rowVersion) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.type = type;
        this.md5 = md5;
        this.parentId = parentId;
        this.path = path;
        this.shadeType = shadeType;
        this.shadeParamM = shadeParamM;
        this.shadeParamN = shadeParamN;
        this.creator = creator;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.rowVersion = rowVersion;
    }

    // Equals, HashCode, ToString

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileMetadata file = (FileMetadata) o;
        return Objects.equals(id, file.id) &&
                Objects.equals(name, file.name) &&
                Objects.equals(size, file.size) &&
                Objects.equals(type, file.type) &&
                Objects.equals(md5, file.md5) &&
                Objects.equals(parentId, file.parentId) &&
                Objects.equals(path, file.path) &&
                Objects.equals(shadeType, file.shadeType) &&
                Objects.equals(shadeParamM, file.shadeParamM) &&
                Objects.equals(shadeParamN, file.shadeParamN) &&
                Objects.equals(creator, file.creator) &&
                Objects.equals(createDate, file.createDate) &&
                Objects.equals(updateDate, file.updateDate) &&
                Objects.equals(rowVersion, file.rowVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, size, type, md5, parentId, path, shadeType, shadeParamM, shadeParamN, creator,
                createDate, updateDate, rowVersion);
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", type='" + type + '\'' +
                ", md5='" + md5 + '\'' +
                ", parentId=" + parentId +
                ", path='" + path + '\'' +
                ", shadeType='" + shadeType + '\'' +
                ", shadeParamM=" + shadeParamM +
                ", shadeParamN=" + shadeParamN +
                ", creator=" + creator +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", rowVersion=" + rowVersion +
                '}';
    }
}

