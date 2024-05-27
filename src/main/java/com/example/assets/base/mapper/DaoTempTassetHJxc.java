package com.example.assets.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.assets.base.entity.TempTassetHJxc;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;

/**
 * TODO
 *
 * @author GengTianMing
 * @since 2023/02/15 14:18
 **/
@Mapper
public interface DaoTempTassetHJxc extends BaseMapper<TempTassetHJxc> {

    Integer insertBatchSomeColumn(Collection<TempTassetHJxc> entityList);
}
