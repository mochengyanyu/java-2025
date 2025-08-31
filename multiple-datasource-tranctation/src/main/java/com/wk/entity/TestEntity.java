package com.wk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("test")
@AllArgsConstructor
@NoArgsConstructor
public class TestEntity {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField(value = "user_name")
    private String userName;

    @TableField(value = "pwd")
    private String pwd;

}
