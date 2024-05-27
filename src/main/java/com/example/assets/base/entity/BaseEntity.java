package com.example.assets.base.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Date;

public class BaseEntity {
    /**
     * 创建者 填充策略为插入自动填充
     */
//    @TableField(fill = FieldFill.INSERT)
    private String createuser;

    /**
     * 创建时间 填充策略为插入自动填充
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createtime;

    /**
     * 更新者 填充策略为插入和更新填充字段
     */
//    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateuser;

    /**
     * 更新时间 填充策略为插入和更新填充字段
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatetime;

}
