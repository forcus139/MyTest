package com.example.assets.base.service;

import com.example.assets.base.entity.AssetImport;
import com.example.assets.base.entity.AssetImportDetail;
import com.example.assets.base.entity.AssetImportMaster;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AssemImportbler {
    public AssetImportMaster doToPo(AssetImport assetImportDto) {
        AssetImportMaster assetImportMaster = new AssetImportMaster();
        BeanUtils.copyProperties(assetImportDto, assetImportMaster);
        List<AssetImportDetail> importDetailList = assetImportDto.getAssetList().stream().map(x -> {
            AssetImportDetail importDetail = new AssetImportDetail();
            BeanUtils.copyProperties(x, importDetail);
            return importDetail;
        }).collect(Collectors.toList());
        assetImportMaster.setAssetList(importDetailList);
        return assetImportMaster;
    }

    public AssetImport poToDo(AssetImportMaster assetImportMaster) {
        AssetImport assetImportDto = new AssetImport();
        BeanUtils.copyProperties(assetImportMaster, assetImportDto);
//        List<AssetImportDetail> importDetailList = assetImportMaster.getAssetList().stream().map(x -> {
//            AssetImportDetail importDetail = new AssetImportDetail();
//            BeanUtils.copyProperties(x, importDetail);
//            return importDetail;
//        }).collect(Collectors.toList());
        assetImportDto.setAssetList(assetImportMaster.getAssetList());
        return assetImportDto;
    }
}


