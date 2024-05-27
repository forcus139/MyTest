package com.example.assets.base.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.assets.base.entity.SysCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


//
@Mapper
public interface DaoSysCode<T> extends BaseMapper<SysCode> {

    @Select("select * from s_syscode ${ew.customSqlSegment}")
    List<SysCode> selectByMyWrapper(@Param(Constants.WRAPPER) Wrapper<SysCode> userWrapper);
}
