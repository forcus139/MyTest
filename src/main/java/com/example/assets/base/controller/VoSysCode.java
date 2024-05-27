package com.example.assets.base.controller;


import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.assets.base.entity.CardQrCodeNew;
import com.example.assets.base.entity.SysCode;
import com.example.assets.base.entity.TempSysCode;
import com.example.assets.base.service.DataOper;
import com.example.assets.base.service.DataOperTest;
import com.example.assets.base.service.SysCodeService;
import com.example.assets.base.service.db_oper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author GTM
 */
@Controller
@Component
@Slf4j
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@SpringBootTest
@RestController
@RequestMapping("/SysCode")
public class VoSysCode {
    @Autowired
//    private DaoSysCode daoSysCode;
    private SysCodeService syscodeservice;

    @Autowired
    private DataOperTest dataOperTest;

    @Autowired
    private DataOper dataOper;

//    public SaResult doLogin(String name, String pwd) {
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
//        if("zhang".equals(name) && "123456".equals(pwd)) {
//            StpUtil.login(10001);
//            return SaResult.ok("登录成功").set("token", StpUtil.getTokenValue());
//        }
//        return SaResult.error("登录失败");
    }

    @PostMapping("Insert")
    public SaResult addSysCode(@RequestBody SysCode syscode) {
        doLogin();
        try {
            int orderId = syscodeservice.insert(syscode);
        }catch (Exception e){
            e.printStackTrace();
            String lsResult = e.toString();
            return SaResult.error("失败：" + lsResult);
        }
        StpUtil.logout();
        return SaResult.data(syscode);
    }

    @PutMapping("Modify")
    public SaResult modSysCode(@RequestBody SysCode syscode) {
        doLogin();
        try {
            int orderId = syscodeservice.modify(syscode);
        }catch (Exception e){
            e.printStackTrace();
            StpUtil.logout();
            String lsResult = e.toString();
            return SaResult.error("失败：" + lsResult);
        }
        StpUtil.logout();
        return SaResult.data(syscode);
    }

    @DeleteMapping("Delete")
    public SaResult deleteUser(@RequestParam String parentcode, @RequestParam String syscode){
        HashMap<String, Object> map=new HashMap();
        try{
            map.put("parentcode",parentcode);
            map.put("syscode",syscode);
            int orderId = syscodeservice.delete(map);
        }catch (Exception e){
            e.printStackTrace();
            String lsResult = e.toString();
            return SaResult.error("失败：" + lsResult);
        }
        return SaResult.ok("删除成功");
    }

    @RequestMapping("Query")
    public SaResult queryGoods(@RequestParam(value = "parentcode")String parentcode,
                              @RequestParam(value = "syscode")String parmsyscode ){
        List<SysCode> listsyscode = new ArrayList();
        try{
            listsyscode = syscodeservice.query(parentcode, parmsyscode);
        }catch (Exception e){
            e.printStackTrace();
            String lsResult = e.toString();
            return SaResult.error("失败：" + lsResult);
        }
        return SaResult.data(listsyscode);
    }

    @RequestMapping("QueryParent")
    public SaResult queryParent(@RequestParam(value = "parentcode")String parentcode){
        List<SysCode> listsyscode = new ArrayList();
        try{
            listsyscode = syscodeservice.queryParent(parentcode);
        }catch (Exception e){
            e.printStackTrace();
            String lsResult = e.toString();
            return SaResult.error("失败：" + lsResult);
        }
        return SaResult.data(listsyscode);
    }

    @RequestMapping("querySysCode")
    public SaResult querySysCode(@RequestParam(value = "parentcode")String parentcode){
        List<Map<String, Object>> listsyscode = new ArrayList();
        try{
            listsyscode = syscodeservice.querySysCode(parentcode);
        }catch (Exception e){
            e.printStackTrace();
            String lsResult = e.toString();
            return SaResult.error("失败：" + lsResult);
        }
        return SaResult.data(listsyscode);
    }

    @RequestMapping("selectGroupNode")
    public SaResult selectGroupNode(@RequestParam(value = "parentcode")String parentcode){
        Map<String, Object> listsyscode = new HashMap<>();
        try{
            listsyscode = syscodeservice.selectGroupNode(parentcode);
        }catch (Exception e){
            e.printStackTrace();
            String lsResult = e.toString();
            return SaResult.error("失败：" + lsResult);
        }
        return SaResult.data(listsyscode);
    }

    @RequestMapping("selectDefstk")
    public SaResult selectDefstk(@RequestParam(value = "parentcode")String parentcode){
        Map<String, Object> listsyscode = new HashMap<>();
        try{
            listsyscode = syscodeservice.selectDefstk(parentcode);
        }catch (Exception e){
            e.printStackTrace();
            String lsResult = e.toString();
            return SaResult.error("失败：" + lsResult);
        }
        return SaResult.data(listsyscode);
    }

    public void createCheckLog(String billno,
                               String billcode,
                               int chknum,
                               int status,
                               String errmsg) {

    }
    @RequestMapping("createCheckLog")
    public SaResult createCheckLog(@RequestParam(value = "billno") String billno,
                                   @RequestParam(value = "billcode") String billcode,
                                   @RequestParam(value = "chknum") int chknum){

        Map<String, Object> inputMap = new HashMap<String, Object>();
        inputMap.put("billno", billno);
        inputMap.put("billcode", billcode);
        inputMap.put("chknum", chknum);
        try{
            syscodeservice.createCheckLog(inputMap);
        }catch (Exception e){
            e.printStackTrace();
            String lsResult = e.toString();
            return SaResult.error("失败：" + lsResult);
        }
        System.out.println("status:\t" + inputMap.get("status"));
        System.out.println("errmsg:\t" + inputMap.get("errmsg"));
        System.out.println("inputMap:\t" + inputMap);
        return SaResult.data(inputMap);
    }

    @RequestMapping("crtCheckLog")
    public SaResult crtCheckLog(@RequestParam(value = "billno") String billno,
                                   @RequestParam(value = "billcode") String billcode,
                                   @RequestParam(value = "chknum") int chknum){

        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        String procName = "p_create_check_log";
        List<Object> inParm = new ArrayList<Object>();
        inParm.add(billno);
        inParm.add(billcode);
        inParm.add(chknum);
        inParm.add("");
        inParm.add("");

        List<Integer> outParm = new ArrayList<Integer>();
        outParm.add(4);
        outParm.add(5);

        try{
            result = dataOper.procedure(procName, inParm, outParm);
        }catch (Exception e){
            e.printStackTrace();
            String lsResult = e.toString();
            return SaResult.error("失败：" + lsResult);
        }
        System.out.println("status:\t" + result.get(0).get("1"));
        System.out.println("errmsg:\t" + result.get(0).get("2"));
        System.out.println("inputMap:\t" + result);
        return SaResult.data(result);
    }


    @RequestMapping("QueryAll")
    public SaResult queryAll(){
        List<SysCode> listsyscode = new ArrayList();
        List<SysCode> test = new ArrayList();
        List<TempSysCode> tempSysCodes = new ArrayList<>();
        try{
            listsyscode = syscodeservice.queryAll();

            syscodeservice.transferData();

            test = Optional.ofNullable(listsyscode).orElse(new ArrayList<>()).
                    stream().filter(x -> "443".equals(x.getParentcode()) && "1".equals(x.getIfstatus())).
                    collect(Collectors.toList());
            test.sort(Comparator.comparing(SysCode::getSyscode, Comparator.reverseOrder()));
        }catch (Exception e){
            e.printStackTrace();
            String lsResult = e.toString();
            return SaResult.error("失败：" + lsResult);
        }
        return SaResult.data(test);
    }

    @RequestMapping("transferData")
    public SaResult transferData(){
        Integer resultcnt = syscodeservice.transferData();
        return SaResult.data("转存：" + resultcnt);
    }

    @RequestMapping("QueryValue")
    public SaResult QueryValue(@RequestParam(value = "parentcode")String parentcode,
                                    @RequestParam(value = "syscode")String parmsyscode ){
        List<SysCode> listsyscode = new ArrayList();
        try{
            listsyscode = syscodeservice.query(parentcode, parmsyscode);
        }catch (Exception e){
            String lsResult = e.toString();
            return SaResult.error("失败：" + lsResult);
        }
        return SaResult.data(listsyscode);
    }

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @RequestMapping("CrtQrCode")
    public JSONObject CrtQrCode(@RequestParam(value = "cdmno")String cdmno,
                              @RequestParam(value = "cqroper")String cqroper,
                              @RequestParam(value = "cqroperdate")String cqroperdate){

//        String driverClass="oracle.jdbc.driver.OracleDriver"; //oracle的驱动
//        String url="jdbc:oracle:thin:@localhost:1521:orcl";// port sid
//        String user="A201805550101";   //user是数据库的用户名
//        String password="123456";  //用户登录密码

//        String driver = "oracle.jdbc.OracleDriver";
//        String url = "jdbc:oracle:thin:@192.168.11.60:1521:ftcard";
//        String user="dbusrcard001";
//        String pwd="futurecard";
//
//        Connection conn=null;
//        try {
//            //首先建立驱动
//            Class.forName(driver);//反射
//            //驱动成功后进行连接
//            conn= DriverManager.getConnection(url, user, pwd);
//            System.out.println("连接成功");
//            Statement st = conn.createStatement();
//            String sql = "select sysdate from dual";
//            //4、执行sql语句并且换回一个查询的结果集
//            ResultSet rs = st.executeQuery(sql);
//            System.out.println("sysdate:" + rs);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        String ls_data = new String();
        String ls_procname = "CARDQRCODENEW";
        JSONObject parmjson = new JSONObject(true);
        Vector<String> as_parm = new Vector<String>();
        Vector<Integer> as_rtn = new Vector<Integer>();

//        SqlSession session = getSqlSession();
//        try {
//            CallableStatement pst = session.getConnection().prepareCall("select 1 from dual;");
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        CardQrCodeNew newqrcode = new CardQrCodeNew();
        try{
            as_parm.add(cdmno);
            as_parm.add("");
            as_parm.add(cqroper);
            as_parm.add(cqroperdate);
            as_parm.add("");
            as_parm.add("");
            as_parm.add("");

            as_rtn.add(5);
            as_rtn.add(6);
            as_rtn.add(7);
            JSONObject procjson = db_oper.dbProcedure(ls_procname, as_parm, as_rtn);
            String procdata = JSONArray.toJSONString(procjson, SerializerFeature.WriteMapNullValue);

            String status = procjson.getString("STATUS");
            if (Integer.parseInt(status) != 0){
                String message = procjson.getString("MESSAGE");
                return db_oper.db_fail(404, message);
            }
            JSONArray DATA = procjson.getJSONArray("DATA");
            String rtncode = db_oper.getValue(DATA, 1);
            String rtnmsg = db_oper.getValue(DATA, 2);
            String qrcode = db_oper.getValue(DATA, 3);
            if (Integer.parseInt(rtncode) != 0){
                return db_oper.db_fail(405, rtnmsg);
            }
            ls_data = qrcode;
                    //new String(qrcode.getByte(serverEncoding), clientEncoding);
            SaResult.ok("查询成功");
//            HashMap<String ,String> map=new HashMap<>();
//            map.put("p_channel",channel);
//            map.put("p_cdmno",cdmno);
//            map.put("p_cdmowner",cdmowner);
//            map.put("p_cqroper",cqroper);
//            map.put("p_cqroperdate",cqroperdate);
//            newqrcode = syscodeservice.newqrcode(map);
        }catch (Exception e){
            String lsResult = e.toString();
            return db_oper.db_fail(406, lsResult);
        }
        return db_oper.db_succ(ls_data);
    }

    public SqlSession getSqlSession() {
        return SqlSessionUtils.getSqlSession(sqlSessionTemplate.getSqlSessionFactory(),
                sqlSessionTemplate.getExecutorType(), sqlSessionTemplate.getPersistenceExceptionTranslator());
    }

    @RequestMapping("UpdateTest")
    public SaResult UpdateTest(){
        List<SysCode> listsyscode = new ArrayList();
        try{
           dataOperTest.updateTest();
        }catch (Exception e){
            String lsResult = e.toString();
            return SaResult.error("失败：" + lsResult);
        }
        return SaResult.ok("succ");
    }

    @RequestMapping("QueryTest")
    public SaResult QueryTest(){
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        try{
            result = dataOperTest.queryTest();
        }catch (Exception e){
            String lsResult = e.toString();
            return SaResult.error("失败：" + lsResult);
        }
        return SaResult.data(result);
    }

    @RequestMapping("QuerySingle")
    public SaResult QuerySingle(){
        Map<String, Object> result = new HashMap<>();
        try{
            result = dataOperTest.QuerySingle();
        }catch (Exception e){
            String lsResult = e.toString();
            return SaResult.error("失败：" + lsResult);
        }
        return SaResult.data(result);
    }

    @RequestMapping("jsonArrayTest")
    public SaResult jsonArrayTest(){
        JSONArray result = new JSONArray();;
        try{
            result = dataOperTest.jsonArrayTest();
        }catch (Exception e){
            String lsResult = e.toString();
            return SaResult.error("失败：" + lsResult);
        }
        return SaResult.data(result);
    }

    @RequestMapping("JSONObjectTest")
    public SaResult JSONObjectTest(){
        JSONObject result = new JSONObject(true);;
        try{
            result = dataOperTest.JSONObjectTest();
        }catch (Exception e){
            String lsResult = e.toString();
            return SaResult.error("失败：" + lsResult);
        }
        return SaResult.data(result);
    }

    @RequestMapping("pagingselect")
    public SaResult pagingQuery(@RequestBody JSONObject json){
        Integer curpage = json.getInteger("curpage");
        Integer rowpage = json.getInteger("rowpage");
        String  tabname = json.getString("tabname");
        String  condition = json.getString("condition");
        String  column = json.getString("column");
        String  order = json.getString("order");

        JSONObject result = new JSONObject(true);
        try{
            result = dataOperTest.pageQuery(curpage, rowpage, tabname, condition, column, order);
        }catch (Exception e){
            String lsResult = e.toString();
            return SaResult.error("失败：" + lsResult);
        }
        String jsonStr = new String();

//        JSONArray  listJJsonArr = JSONArray.parseArray(String.valueOf(listJson));

//        List<SysCode> syscode = JSON.parseObject(result.toJSONString(), new TypeReference<List<SysCode>>() {});
//        List<Model> models = JSON.parseObject(jsonStr, new TypeReference<List<Model>>() {});
        System.out.println("syscode:\t" + result);
        return SaResult.data(result);
    }

    @RequestMapping("pagequery")
    public SaResult pageQuery(@RequestBody JSONObject json){
        Integer curpage = json.getInteger("curpage");
        Integer rowpage = json.getInteger("rowpage");
        String  funcno = json.getString("funcno");
        String  dwobject = json.getString("dwobject");
        String  column = json.getString("column");
        String  order = json.getString("order");
        JSONArray listCondition = json.getJSONArray("CONTENT");

        String tabname = new String();
        String condition = new String();
        JSONObject result = new JSONObject(true);
        try{
            result = dataOperTest.getTabname(funcno);
        }catch (Exception e){
            return SaResult.error(e.getMessage());
        }
        tabname = result.getString("tabname");
        if (StringUtils.isBlank(order)){
            if (StringUtils.isBlank(result.getString("onlycolumn"))){
                try{
                    order = dataOperTest.getFirstColumn(tabname);
                }catch (Exception e){
                    return SaResult.error(e.getMessage());
                }
            }else{
                order = result.getString("onlycolumn");
            }
        }

        condition = resolveCondition(listCondition);
        if (StringUtils.isBlank(condition)){
            return SaResult.error("查询失败：输入条件有误");
        }

        try{
            result = dataOperTest.pageQuery(curpage, rowpage, tabname, condition, column, order);
        }catch (Exception e){
            String lsResult = e.toString();
            return SaResult.error("失败：" + lsResult);
        }
        String jsonStr = new String();

        System.out.println("syscode:\t" + result);
        return SaResult.data(result);
    }

    public String resolveCondition(JSONArray listCondtiion){
        List<JSONObject> list=listCondtiion.toJavaList(JSONObject.class);
        System.out.println("Begin\n" + listCondtiion);
        sort(list,false);
        System.out.println("End\n" + list);

        String condition = new String();
        for (int i = 0; i < list.size(); i++) {
            JSONObject datainfo = new JSONObject(true);
            datainfo = list.get(i);
            String rowCondition = new String();
            if (!StringUtils.isBlank(datainfo.getString("leftpar"))){
                rowCondition += datainfo.getString("leftpar");
            }
            if (StringUtils.isBlank(datainfo.getString("coleng"))){
                continue;
            }else{
                if (datainfo.getString("coltype").equalsIgnoreCase("string") ||
                        datainfo.getString("coltype").equalsIgnoreCase("date") ||
                        datainfo.getString("coltype").equalsIgnoreCase("datetime")){
                    rowCondition = datainfo.getString("coleng") + " " +
                            datainfo.getString("operator") + " '" +
                            datainfo.getString("realvalues") + "' ";
                }else if (datainfo.getString("coltype").equalsIgnoreCase("int")){
                    rowCondition = datainfo.getString("coleng") + " " +
                            datainfo.getString("operator") + " " +
                            datainfo.getString("realvalues") + " ";
                }
            }
            if (!StringUtils.isBlank(datainfo.getString("rightpar"))){
                rowCondition += datainfo.getString("rightpar");
            }
            if (!StringUtils.isBlank(datainfo.getString("relation"))){
                if (i + 1 < listCondtiion.size()){
                    rowCondition += " \n\t And ";
                }
            }
            condition = condition + rowCondition;
        }
        return condition;
    }

    private void sort(List<JSONObject> jsonValues,Boolean is_desc){
        Collections.sort(jsonValues, new Comparator<JSONObject>() {
            //排序字段
            private final String KEY_NAME = "qryno";

            //重写compare方法
            @Override
            public int compare(JSONObject a, JSONObject b) {

                //如果用String接会导致一位数和两位数混合比对的时候不能准确比出来，要用int类型接
                //String valA = new String();
                //String valB = new String();

                Integer valA = a.getInteger(KEY_NAME);
                Integer valB = b.getInteger(KEY_NAME);

                //是升序还是降序
                if (is_desc) {
                    return -valA.compareTo(valB);
                } else {
                    return -valB.compareTo(valA);
                }
            }
        });
    }

    @RequestMapping("sysCodeselectByMyWrapper")
    public SaResult selectByMyWrapper(){
        List<SysCode> result = new ArrayList<>();
        try{
            result = syscodeservice.sysCodeselectByMyWrapper();
        }catch (Exception e){
            String lsResult = e.toString();
            return SaResult.error("失败：" + lsResult);
        }
        return SaResult.data(result);
    }

}

