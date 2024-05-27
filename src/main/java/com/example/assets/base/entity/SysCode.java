package com.example.assets.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;


@Data
@TableName("s_syscode")
public class SysCode extends BaseEntity{
    /**
     * 系统代码
     */
    @Id
    @Column(name = "syscode", nullable = false)
    private String syscode;

    private Integer levels;

    /**
     * 上级代码
     */
    @Id
    @Column(name = "parentcode", nullable = false)
    private String parentcode;

    /**
     * 描述
     */
    private String descr;

    /**
     * 是否可见
     */
    private Integer isvisible;

    /**
     * 是否修改
     */
    private Integer ismodify;

    /**
     * 备注
     */
    private String notes;

//    /**
//     * 创建人
//     */
//    private String createuser;
//
//
//    @TableField(fill = FieldFill.INSERT)
//    /**
//     * 创建时间
//     */
//    private Date createtime;


//    private String updateuser;


//    @TableField(fill = FieldFill.INSERT_UPDATE)
//    private Date updatetime;

    /**
     * 备注
     */
    private String remark;


    /**
     * 是否启用
     */
    private String ifstatus;

    /**
     * 操作人
     */
    private String wide_by;

    public SysCode(String syscode, Integer levels, String parentcode,
                   String descr, Integer isvisible, Integer ismodify,
                   String notes, String remark, String ifstatus,
                   String wide_by){
        this.syscode = syscode;
        this.levels = levels;
        this.parentcode = parentcode;
        this.descr = descr;
        this.isvisible = isvisible;
        this.ismodify = ismodify;
        this.notes = notes;
        this.remark = remark;
        this.ifstatus = ifstatus;
        this.wide_by = wide_by;
    }
}
