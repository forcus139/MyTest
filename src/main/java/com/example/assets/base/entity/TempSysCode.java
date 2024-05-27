package com.example.assets.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * TODO
 *
 * @author GengTianMing
 * @since 2023/02/14 16:47
 **/
@Data
@TableName("s_syscode_temp")
public class TempSysCode{
    /**
     * 系统代码
     */
    private String syscode;

    private Integer levels;

    /**
     * 上级代码
     */
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

    /**
     * 创建人
     */
    private String createuser;

    /**
     * 创建时间
     */
    private Date createtime;


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

    /**
     * 修改人
     */
    private String updateuser;

    /**
     * 修改时间
     */
    private Date updatetime;
}
