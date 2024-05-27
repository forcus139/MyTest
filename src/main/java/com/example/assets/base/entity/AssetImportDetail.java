package com.example.assets.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
@TableName("t_asset_import_d")
public class AssetImportDetail {
    @Id
    @Column(name = "bill", nullable = false)
//    @TableId(type = IdType.INPUT)
    private String bill;

    private String rowno;

    @Id
    @Column(name = "stkid", nullable = false)
//    @TableId(type = IdType.INPUT)
    private String stkid;

    private String name_stk;

    @Id
    @Column(name = "Department", nullable = false)
//    @TableId(type = IdType.INPUT)
    private String department;

    @Id
    @Column(name = "itemno", nullable = false)
//    @TableId(type = IdType.INPUT)
    private String itemno;

    private String itemname;

    private String model;

    private String vendorid;

    private Double purprice;

    private Double depotqty;

    private Double notaxamt;

    @Id
    @Column(name = "StartDate", nullable = false)
    @TableId(type = IdType.INPUT)
    private Date startdate;

    private Double taxrate;

    private String flag_tax;

    @Column(name = "CleanValue")
    private Double cleanvalue;

    @Column(name = "SumCutAmt")
    private Double sumcutamt;

    private String remark;

    private Double notaxoriginalvalue;

    private Double notaxcurrentvalue;
}
