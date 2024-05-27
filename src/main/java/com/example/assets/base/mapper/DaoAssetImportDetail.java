package com.example.assets.base.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.assets.base.entity.AssetImportDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DaoAssetImportDetail<T> extends BaseMapper<AssetImportDetail> {
//    /**
//     * 自定义批量插入
//     * 如果要自动填充，@Param(xx) xx参数名必须是 list/collection/array 3个的其中之一
//     */
//    int insertBatch(@Param("list") List<T> list);
//
//    /**
//     * 自定义批量更新，条件为主键
//     * 如果要自动填充，@Param(xx) xx参数名必须是 list/collection/array 3个的其中之一
//     */
//    int updateBatch(@Param("list") List<T> list);

}
