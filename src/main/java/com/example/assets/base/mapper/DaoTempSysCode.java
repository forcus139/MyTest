package com.example.assets.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.assets.base.entity.TempSysCode;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;

/**
 * TODO
 *
 * @author GengTianMing
 * @since 2023/02/14 16:50
 **/
@Mapper
public interface DaoTempSysCode extends BaseMapper<TempSysCode> {
    /**
     * 批量插入（mysql）
     * @param entityList
     * @return
     */
    Integer insertBatchSomeColumn(Collection<TempSysCode> entityList);

}
