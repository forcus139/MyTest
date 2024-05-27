package com.example.assets.base.service;

import com.example.assets.base.mapper.DaoAssetImportDetail;
import com.example.assets.base.mapper.DaoAssetImportMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//import com.yuepong.jdev.ddd.domain.IAggregateRoot;

/**
 * @author GTM
 */
@Component
public class AssetImportRepositoryImpl  {       //implements DaoAssetImport
    @Autowired
    DaoAssetImportMaster daoAssetImportMaster;
    @Autowired
    DaoAssetImportDetail daoAssetImportDetail;
    @Autowired
    AssemImportbler assemImportbler;
    @Autowired
    PublicFuncTion publicFuncTion;


//    @Override
//    public void saveMaster(AssetImport assetImport){
//        AssetImportMaster master = assemImportbler.doToPo(assetImport);
//        if (master.getBill() == null || master.getBill().isEmpty()) {
//            try{
//                master.setBill(publicFuncTion.getSerialno("8030", "0", master.getGroup_drpid()));
//            }catch (Exception e){
//                e.printStackTrace();
////                throw new Exception("流水号生成失败");
////                throw new ResourceNotFoundException("流水号生成失败");
//            }
//            daoAssetImportMaster.insert(master);
//        }else{
//            UpdateWrapper<AssetImportMaster> updateWrapper = new UpdateWrapper<>();
//            updateWrapper.eq("bill", assetImport.getBill());
//            daoAssetImportMaster.update(master, updateWrapper);
//        }
//    }
//
//    @Override
//    public void saveDetail(List<AssetImportDetail> assetImportDetail) {
//
//    }
//
//    @Override
//    public AssetImportMaster queryMaster(String bill) {
//        QueryWrapper<AssetImportMaster> queryMaster = new QueryWrapper<>();
//        queryMaster.eq("bill", bill);
//        AssetImportMaster assetImport = daoAssetImportMaster.selectOne(queryMaster);
//        return assetImport;
//    }
//
//    @Override
//    public List<AssetImportDetail> queryDetail(String bill) {
//        QueryWrapper<AssetImportDetail> queryDtl = new QueryWrapper<>();
//        queryDtl.eq("bill",bill);
//        List<AssetImportDetail> assetImportDetail = daoAssetImportDetail.selectMaps(queryDtl);
//        return assetImportDetail;
//    }
//
//    @Override
//    public void remove(Serializable serializable) {
//
//    }

//    @Override
//    public IAggregateRoot find(Serializable serializable) {
//        return null;
//    }
//
//    @Override
//    public void save(IAggregateRoot aggregate) {
//
//    }
//
//    @Override
//    public void modify(IAggregateRoot aggregate) {
//
//    }
}
