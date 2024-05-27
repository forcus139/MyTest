package com.example.assets.base.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;
import java.util.Map;


@Mapper
public interface CommonDao {
    @Select("SELECT SYSCODE, DESCR, IFSTATUS " +
            "FROM s_syscode " +
            "WHERE PARENTCODE = #{parentcode} ORDER BY syscode ASC")
    List<Map<String, Object>> selectSysCode(@Param("parentcode") String parentcode);

    //集团营销节点
    @Select("SELECT drpid, drpname, group_bookid, flag_cancel, flag_group " +
            "FROM t_bp_groupnode " +
            "WHERE drpid = #{drpid} ORDER BY drpid ASC")
    Map<String, Object> selectGroupNode(@Param("drpid") String drpid);

    //门店信息
    @Select("SELECT drpid, stkid, name_stk, flag_stop, sptype, regionid, notes, formats, grp_display, fac_price,\n" +
            "budget_all, budget_used, budget_preused, consignee, consigneetel, shoptel, shopaddr, acctid,\n" +
            "acctname, sparea, email, assadmin, time_start, skt_type, subnetid, stkid_deli, flag_price,\n" +
            "invest_type, flag_cancel, time_cancel, flag_newopen, time_stop, group_drpid, coin_stk,\n" +
            "name_payee, payeeid, zlrzx, sap_stkid, zcbzx, zdaq, remark, district_id" +
            " FROM s_defstk " +
            "WHERE stkid = #{stkid}")
    Map<String, Object> selectDefstk(@Param("stkid") String stkid);

//    @Select("EXECUTE p_create_check_log " +
//            "#{billno, mode=IN, jdbcType=VARCHAR}, " +
//            "#{billcode, mode=IN, jdbcType=VARCHAR}, " +
//            "#{chknum, mode=IN, jdbcType=INTEGER}, " +
//            "#{status, mode=OUT, jdbcType=INTEGER}, " +
//            "#{errmsg, mode=OUT, jdbcType=VARCHAR}")
    @Select("EXECUTE p_create_check_log " +
        "#{billno}, " +
        "#{billcode}, " +
        "#{chknum}, " +
        "#{status, mode=OUT, jdbcType=INTEGER}, " +
        "#{errmsg, mode=OUT, jdbcType=VARCHAR}")
    @Options(statementType = StatementType.CALLABLE)
    void createCheckLog(Map<String, Object> intputMap);
//    void createCheckLog(@Param("billno") String billno,
//                        @Param("billcode") String billcode,
//                        @Param("chknum") int chknum,
//                        @Param("status") int status,
//                        @Param("errmsg") String errmsg);

}
