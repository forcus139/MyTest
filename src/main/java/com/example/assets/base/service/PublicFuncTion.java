package com.example.assets.base.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

@Component
public class PublicFuncTion {
    @PersistenceContext
    private EntityManager entityManager;

    public String getSerialno(String billcode, String autoskip, String stkid) throws Exception {

        String proc = "p_get_serialno";
        StoredProcedureQuery call = entityManager.createStoredProcedureQuery(proc);
        call.registerStoredProcedureParameter(1, String.class, ParameterMode.IN).setParameter(1, billcode);
        call.registerStoredProcedureParameter(2, String.class, ParameterMode.IN).setParameter(2, autoskip);
        call.registerStoredProcedureParameter(3, String.class, ParameterMode.IN).setParameter(3, stkid);
        call.registerStoredProcedureParameter(4, String.class, ParameterMode.OUT);

        call.execute();
        String rtncode = call.getOutputParameterValue(4).toString();
        return rtncode;
    }

    public SaResult doLogin() {
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        try {
            if (!StpUtil.isLogin()){
                StpUtil.login("Superor");
            }
        }catch (Exception e){
            e.printStackTrace();
            return SaResult.error("登录失败:" + e.toString());
        }
        return SaResult.ok("登录成功").set("token", StpUtil.getTokenValue());
    }

}

