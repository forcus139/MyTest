package com.example.assets.base.service;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;


@Slf4j
@Component
public class MpMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入时的填充策略
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
//        this.setFieldValByName("createuser", StpUtil.getLoginId(),metaObject);
        this.setFieldValByName("createtime", new Date(), metaObject);
//        this.setFieldValByName("updateuser", StpUtil.getLoginId(),metaObject);
        this.setFieldValByName("updatetime", new Date(), metaObject);
    }

    /**
     * 更新时的填充策略
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
//        this.setFieldValByName("updateuser", StpUtil.getLoginId(),metaObject);
        this.setFieldValByName("updatetime", new Date(), metaObject);
    }
}
