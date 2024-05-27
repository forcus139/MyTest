package com.example.assets.base.mapper;

import com.example.assets.base.entity.AssetImport;
import com.example.assets.base.entity.AssetImportDetail;
import com.example.assets.base.entity.AssetImportMaster;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DaoAssetImport {
    /**
     * @Description: 新增
     * @author FangCheng
     * @Date: 2021/9/13 10:02
     * @methodName saveMaster
     * @param assetImport
     * @returns void
     */
    void saveMaster(AssetImport assetImport);

    /**
     * @Description: 保存明细
     * @author FangCheng
     * @Date: 2021/9/13 10:02
     * @methodName saveDetail
     * @param assetImportDetail
     * @returns void
     */
    void saveDetail(List<AssetImportDetail> assetImportDetail);

    /**
     * @Description: 根据ID查询
     * @author FangCheng
     * @Date: 2021/9/13 10:02
     * @methodName queryMaster
     * @param bill
     * @returns com.yuepong.order.domain.order.entity.Order
     */
    AssetImportMaster queryMaster(String bill);

    /**
     * @Description: 根据ID查询
     * @author FangCheng
     * @Date: 2021/9/13 10:02
     * @methodName queryDetail
     * @param bill
     * @returns com.yuepong.order.domain.order.entity.Order
     */
    List<AssetImportDetail> queryDetail(String bill);
}
