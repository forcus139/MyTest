package com.example.assets.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@TableName("t_asset_import_h")
public class AssetImport {
    /**
     * 单号
     */
    private String bill;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 生效时间
     */
    private Date execdate;

    /**
     * 集团节点
     */
    private String group_drpid;

    /**
     * 创建人
     */
    private String createuser;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createtime;

    /**
     * 修改人
     */
    private String updateuser;

    /**
     * 修改时间
     */
    private Date updatetime;

    /**
     * 审核人1
     */
    private String checker1;

    /**
     * 审核人1意见
     */
    private Integer checkbit1;

    /**
     * 审核人1时间
     */
    private Date checktime1;

    /**
     * 审核人2
     */
    private String checker2;

    /**
     * 审核人2意见
     */
    private Integer checkbit2;

    /**
     * 审核人2时间
     */
    private Date checktime2;

    /**
     * 审核人3
     */
    private String checker3;

    /**
     * 审核人3意见
     */
    private Integer checkbit3;

    /**
     * 审核人3时间
     */
    private Date checktime3;

    /**
     * 审核人4
     */
    private String checker4;

    /**
     * 审核人4意见
     */
    private Integer checkbit4;

    /**
     * 审核人4时间
     */
    private Date checktime4;

    /**
     * 备注
     */
    private String remark;

    @TableField(exist = false)
    private List<AssetImportDetail> assetList;
}
