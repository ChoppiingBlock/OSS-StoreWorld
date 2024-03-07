package org.spring.po;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class DirectoryMetadata implements Serializable {
    /**
     * 目录ID
     */
    private Integer id;

    /**
     * 目录名称
     */
    private String name;

    /**
     * 父目录ID
     */
    private Integer parentId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 桶世界目录路径
     */
    private String path;

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

    public DirectoryMetadata(Integer id, String name, Integer parentId, Integer userId, String path, LocalDateTime createDate,
                     LocalDateTime updateDate, LocalDateTime rowVersion) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.userId = userId;
        this.path = path;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.rowVersion = rowVersion;
    }

    // Equals, HashCode, ToString

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirectoryMetadata directory = (DirectoryMetadata) o;
        return Objects.equals(id, directory.id) &&
                Objects.equals(name, directory.name) &&
                Objects.equals(parentId, directory.parentId) &&
                Objects.equals(userId, directory.userId) &&
                Objects.equals(path, directory.path) &&
                Objects.equals(createDate, directory.createDate) &&
                Objects.equals(updateDate, directory.updateDate) &&
                Objects.equals(rowVersion, directory.rowVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, parentId, userId, path, createDate, updateDate, rowVersion);
    }

    @Override
    public String toString() {
        return "Directory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", userId=" + userId +
                ", path='" + path + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", rowVersion=" + rowVersion +
                '}';
    }
}

