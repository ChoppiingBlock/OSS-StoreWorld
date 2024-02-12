package org.spring.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName(value = "user")
@Data
@AllArgsConstructor
@Builder
public class User implements Serializable {

    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户账号
     */
    @TableField(value = "username")
    private String username;

    /**
     * 用户密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 创建日期
     */
    @TableField(value = "createdate")
    private LocalDateTime createDate;

    /**
     * 更新日期
     */
    @TableField(value = "updateDate")
    private LocalDateTime updateDate;

    /**
     * 行版本号
     */
    @TableField(value = "rowversion")
    private LocalDateTime rowVersion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", rowVersion=" + rowVersion +
                '}';
    }
}
