package com.example.assets.business.entiity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.assets.base.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Data
@TableName("t_asset_import_h")
public class Asset_Import_h extends BaseEntity {

    @Id
    @Column(name = "bill", nullable = false)
    private String bill;

    private Integer status;

    private Date execdate;

    private String group_drpid;

//    private String createuser;
//
//    private Date createtime;
//
//    private String updateuser;
//
//    private Date updatetime;

    private String checker1;

    private Integer checkbit1;

    private Date checktime1;

    private String checker2;

    private Integer checkbit2;

    private Date checktime2;

    private String checker3;

    private Integer checkbit3;

    private Date checktime3;

    private String checker4;

    private Integer checkbit4;

    private Date checktime4;

    private String remark;

    private List<Asset_Import_d> assetimportdetails;
}
