package com.example.assets.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * TODO
 *
 * @author GengTianMing
 * @since 2023/02/15 11:52
 **/
@Data
@TableName("t_asset_h_jxc")
public
class TassetHjxc {
    private String ym;

    private String orgcode;

    private String nameStk;

    private String acctid;

    private String flagTaxpayers;

    private String department;

    private String billtype;

    private String itemno;

    private String assetid;

    private String itemname;

    private Double oriamtTax;

    private Double oriamtNotax;

    private Double depamtTax;

    private Double depamtNotax;

    private Double curamtTax;

    private Double curamtNotax;

    private Double depotqty;

    private String createuser;

    private Date createtime;

    private String recordId;

    private String groupDrpid;

    private String itemtype;

    private String soleid;
}