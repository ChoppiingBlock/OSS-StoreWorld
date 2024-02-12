package org.spring.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@TableName("permission")
public class Permission implements Serializable {
    /**
     * 权限ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 权限路径
     */
    @TableField("permission_path")
    private String permissionPath;

    /**
     * 权限类型
     */
    @TableField("permission_type")
    private String permissionType;

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

    public Permission(Integer id, Integer userId, String permissionPath, String permissionType,
                      LocalDateTime createDate, LocalDateTime updateDate, LocalDateTime rowVersion) {
        this.id = id;
        this.userId = userId;
        this.permissionPath = permissionPath;
        this.permissionType = permissionType;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.rowVersion = rowVersion;
    }

    // Equals, HashCode, ToString

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(permissionPath, that.permissionPath) &&
                Objects.equals(permissionType, that.permissionType) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(updateDate, that.updateDate) &&
                Objects.equals(rowVersion, that.rowVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, permissionPath, permissionType, createDate, updateDate, rowVersion);
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", userId=" + userId +
                ", permissionPath='" + permissionPath + '\'' +
                ", permissionType='" + permissionType + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", rowVersion=" + rowVersion +
                '}';
    }
}

