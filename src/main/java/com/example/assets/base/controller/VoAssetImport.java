package com.example.assets.base.controller;

import cn.dev33.satoken.util.SaResult;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.assets.base.entity.AssetImport;
import com.example.assets.base.entity.AssetImportMaster;
import com.example.assets.base.service.AssemImportbler;
import com.example.assets.base.service.AssetImportRepositoryImpl;
import com.example.assets.base.service.AssetImportService;
import com.example.assets.base.service.PublicFuncTion;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@Component
@Slf4j
@RunWith(SpringRunner.class)
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@SpringBootTest
@RestController
@RequestMapping("/AssetImport")
public class VoAssetImport {

    @Autowired
    private AssetImportService assetImportService;

    @Autowired
    private AssemImportbler assemImportbler;

    @Autowired
    private PublicFuncTion publicFuncTion;

    @Autowired
    private AssetImportRepositoryImpl assetImportRepositoryImpl;

    @PostMapping("/Insert")
    public SaResult addAssetImport(@RequestBody AssetImport assetImport) {
//        AssetImportMaster assetImportMaster = assemImportbler.insertDtoToDo(assetImport);
        try {
            publicFuncTion.doLogin();
            assetImportService.saveAssetImport(assetImport);
        }catch (Exception e){
//            e.printStackTrace();
//            String lsResult = e.getCause().getMessage().toString();    // e.toString();
//            System.out.println("lsResult:\t" + lsResult);
            return SaResult.error("失败：" + e.getMessage());
        }
        return SaResult.data(assetImport);
    }

    @RequestMapping(value ="QueryBill", name = "按单号查询资产导入单主表")
    public SaResult queryBill(@RequestParam(value = "bill")
                                          String bill) {
        if (StringUtils.isBlank(bill)){
//        if (bill == null || bill.trim().isEmpty()){
            return SaResult.error("失败：单号不允许为空");
        }
        AssetImportMaster assetimport = new AssetImportMaster();
        try {
            assetimport = assetImportService.querybill(bill);
        }catch (Exception e){
            String lsResult = e.getMessage();
            return SaResult.error("失败：" + lsResult);
        }
//        return SaResult.data(assemImportbler.poToDo(assetimport));
        return SaResult.data(assetimport);

//        AssetImport assetimport = new AssetImport();
//        try {
//            assetimport = assemImportbler.poToDo(assetImportRepositoryImpl.queryMaster(bill));
//            List<AssetImportDetail> assetImportDetail = assetImportRepositoryImpl.queryDetail(bill);
//            assetimport.setAssetList(assetImportDetail);
//        }catch (Exception e){
//            e.printStackTrace();
//            String lsResult = e.toString();
//            return SaResult.error("失败：" + lsResult);
//        }
//        return SaResult.data(assetimport);
    }

    @RequestMapping(value = "QryBill", name = "分页查询资产导入单主表")
    public SaResult qryBill(@RequestParam(value = "currentPage") long currentPage,
                            @RequestParam(value = "pageSize") long pageSize,
                            @RequestParam(value = "group_drpid") String group_drpid) {

        IPage<AssetImportMaster> assetimport = new Page(currentPage, pageSize);
        try {
            assetimport = assetImportService.queryHdr(currentPage, pageSize, group_drpid);
        }catch (Exception e){
            e.printStackTrace();
            return SaResult.error("失败：" + e.getMessage());
        }

        return SaResult.data(assetimport);
    }

    @RequestMapping("PageQryMaster")
    public SaResult selectMaster(long currentPage,
                                 long pageSize,
                                 String groupDrpid,
                                 Integer status) {
        IPage<AssetImportMaster> assetimport = new Page<>(currentPage, pageSize);
        try {
            QueryWrapper<AssetImportMaster> queryMaster = new QueryWrapper<>();
//            queryMaster.eq("group_drpid", group_drpid)
//            .eq("status", status);
            queryMaster.eq(StringUtils.isNotBlank(groupDrpid), "group_drpid", groupDrpid)
                    .eq(Objects.nonNull(status), "status", status);
//                    .or(i -> i.eq("status", status)); StringUtils.isNotBlank(status),
            queryMaster.orderByAsc("bill");
            assetimport = assetImportService.selectMaster(currentPage, pageSize, queryMaster);
        }catch (Exception e){
            e.printStackTrace();
            return SaResult.error("失败：" + e.getMessage());
        }

        return SaResult.data(assetimport);
    }

    @RequestMapping("PageJsonMaster")
    public SaResult jsonHdr(@RequestBody JSONObject json) {
        Integer currentPage = json.getInteger("currentPage");
        Integer pageSize = json.getInteger("pageSize");
        String  groupDrpid = json.getString("group_drpid");
        Integer status = json.getInteger("status");
        IPage<AssetImportMaster> assetimport;
        try {
            QueryWrapper<AssetImportMaster> queryMaster = new QueryWrapper<>();
            queryMaster.eq(StringUtils.isNotBlank(groupDrpid), "group_drpid", groupDrpid)
                    .eq(status != null, "status", status);
            queryMaster.orderByAsc("bill");
            assetimport = assetImportService.selectMaster(1L, 5L, queryMaster);
        }catch (Exception e){
            e.printStackTrace();
            return SaResult.error("失败：" + e.getMessage());
        }

        return SaResult.data(assetimport);
    }


}
