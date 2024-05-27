package com.example.assets.business.entiity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
@TableName("t_asset_import_d")
public class Asset_Import_d {

    @Id
    @Column(name = "bill", nullable = false)
    private String bill;

    @Id
    @Column(name = "rowno", nullable = false)
    private String rowno;

    private String stkid;

    private String name_stk;

    private String Department;

    @Id
    @Column(name = "itemno", nullable = false)
    private String itemno;

    private String itemname;

    private String model;

    private String vendorid;

    private Double purprice;

    private Double depotqty;

    private Double notaxamt;

    private Date StartDate;

    private Double taxrate;

    private String flag_tax;

    private Double CleanValue;

    private Double SumCutAmt;

    private String remark;

    private Double notaxoriginalvalue;

    private Double notaxcurrentvalue;
}
