package com.example.assets.base.service;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CardQrCodeNewServices {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Map<String,Object>> getGiftCardList(String cdmowner){
        String sql ="select \n" +
                "  a.cdmno,\n" +
                "  a.cdmtype,\n" +
                "  a.cdmstatus,\n" +
                "  a.cdmflag,\n" +
                "  a.cdmmoney,\n" +
                "  a.cdmye,\n" +
                "  b.cdechr1,\n" +
                "  b.cdechr2,\n" +
                "  b.cdemindate,\n" +
                "  b.cdemaxdate\n" +
                "  from cardmain a,\n" +
                "       cardextent b\n" +
                " where a.cdmno = b.cdeno\n" +
                "   and a.cdmchr6 = :cdmchr6";
        Query qD = entityManager.createNativeQuery(sql);
        qD.setParameter("cdmchr6", cdmowner);
        qD.unwrap(org.hibernate.SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String,Object>> list =qD.getResultList();
        return list;
    }

    public List<Map<String, Object>> queryGiftCardTradeLog(String cdlno) {
        String sql = " SELECT CDLZY, CDLDATE, " +
                "     CDLMONEY * DECODE(CDLZY,'P',-1,'U',-1,'7',-1,'*',-1,'#',-1,'C',-1,'^',-1,'/',-1,1)  CDLMONEY, " +
                "     CDLMKT " +
                " FROM CARDLOG " +
                " WHERE CDLNO = :cdlno ";
        Query qD = entityManager.createNativeQuery(sql);
        qD.setParameter("cdlno", cdlno);
        qD.unwrap(org.hibernate.SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return (List<Map<String, Object>>) Optional.ofNullable(qD.getResultList()).orElse(Collections.emptyList());
    }

    public String generateBarcode(String cdmno, String cdmowner, String cqroper, String cqroperdate) throws Exception {

        String proc = "CARDQRCODENEW";
        //Query qD = entityManager.createNativeQuery(proc);
        StoredProcedureQuery call = entityManager.createStoredProcedureQuery(proc);
        call.registerStoredProcedureParameter(1, String.class, ParameterMode.IN).setParameter(1, cdmno);
        call.registerStoredProcedureParameter(2, String.class, ParameterMode.IN).setParameter(2, cdmowner);
        call.registerStoredProcedureParameter(3, String.class, ParameterMode.IN).setParameter(3, cqroper);
        call.registerStoredProcedureParameter(4, String.class, ParameterMode.IN).setParameter(4, cqroperdate);
        call.registerStoredProcedureParameter(5, String.class, ParameterMode.OUT);
        call.registerStoredProcedureParameter(6, String.class, ParameterMode.OUT);
        call.registerStoredProcedureParameter(7, String.class, ParameterMode.OUT);
        call.execute();
        String rtncode = call.getOutputParameterValue(5).toString();
        if ("0".equals(rtncode)) {
            String rtnValue = new String(call.getOutputParameterValue(7).toString().getBytes("ISO-8859-1"),"GBK");
            return rtnValue;
//            return call.getOutputParameterValue(7).toString();
        } else {
            throw new Exception(call.getOutputParameterValue(6).toString());
        }
    }
}
