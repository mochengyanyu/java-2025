package com.wk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class UserEntity {
    @TableId(value = "id",type= IdType.AUTO)
    private Long id;
    @TableField(value = "user_name")
    private String userName;
    @TableField(value = "pass_word")
    private String pwd;
    @TableField(value = "email")
    private String email;
    @TableField(value = "phone")
    private String phone;
    @TableField(value = "address")
    private String address;
    @TableField(value = "disabled")
    private String disabled;
    @TableField(value = "expired")
    private String expired;

    public UserEntity(String userName, String pwd, String email, String phone, String address, String disabled, String expired) {
        this.userName = userName;
        this.pwd = pwd;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.disabled = disabled;
        this.expired = expired;
    }
}
