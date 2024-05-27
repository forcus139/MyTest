package com.example.assets.base.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataOperTest {
    @Autowired
    DataOper dataOper;

    public void updateTest() throws InterruptedException {
        List<String> runSql = new ArrayList<String>();
        runSql.add("update s_user set liccard = '1' where userid = '100009'");
        runSql.add("update s_user set liccard = '2' where userid = '0000014'");
        runSql.add("update t_commodity_input_h set status = '1' where bill = '19907CDIP2210140003'");

        dataOper.dbUpdate(runSql);
    }

    public List<Map<String, Object>> queryTest() throws InterruptedException {
        String runSql = new String();
//        runSql = "select db_name() dbname, getdate() curdate, 3.00/2.00 test";
        runSql = "select db_name(), getdate(), 3.00/2.00, null";
        runSql = "select * from s_syscode where parentcode = '312'";

        List<Map<String, Object>> result =  dataOper.excuteQuerySql(runSql);
        return result;
    }

    public Map<String, Object> QuerySingle() throws InterruptedException {
        String runSql = new String();
//        runSql = "select db_name() dbname, getdate() curdate, 3.00/2.00 test";
        runSql = "select db_name(), getdate(), 3.00/2.00, null";
//        runSql = "select count(1) from s_syscode where 1=2";

        Map<String, Object> result =  dataOper.QuerySingle(runSql);
        return result;
    }

    public JSONArray jsonArrayTest() throws InterruptedException {
        String runSql = new String();
//        runSql = "select db_name() dbname, getdate() curdate, 3.00/2.00 test";
        runSql = "select db_name(), getdate(), 3.00/2.00, null";
        runSql = "select * from s_syscode where parentcode = '312'";

        JSONArray result =  dataOper.dbQuery(runSql);
        return result;
    }

    public JSONObject JSONObjectTest() throws InterruptedException {
        String runSql = new String();
//        runSql = "select db_name() dbname, getdate() curdate, 3.00/2.00 test";
        runSql = "select db_name(), getdate(), 3.00/2.00, null";
        runSql = "select * from s_syscode where parentcode = '312'";

        JSONObject result =  dataOper.SingleQuery(runSql);
        System.out.println("size:\t" + result.size());
        return result;
    }

    public JSONObject pageQuery(int curpage,
                               int rowpage,
                               String tabname,
                               String condition,
                               String column,
                               String order) throws InterruptedException {
        String runSql = new String();
        JSONObject resultjson = new JSONObject(true);
        JSONArray result =  new JSONArray();

        if (!StringUtils.isBlank(order)){
            runSql = runSql + " AND " + condition;
        }
        runSql = "SELECT COUNT(1) ROWCNT \nFROM " + tabname;
        if (!StringUtils.isBlank(condition)){
            runSql = runSql + " \nWHERE " + condition;
        }
        resultjson =  dataOper.SingleQuery(runSql);
        int rowcnt = resultjson.getInteger("ROWCNT");
        if (rowcnt > 0){
            runSql = "SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY "+ order + ") CURRENT_ROW, * FROM " +
                    tabname + " )" + tabname +
                    " WHERE CURRENT_ROW > " + String.valueOf((curpage - 1) * rowpage) +
                    " AND CURRENT_ROW <= " + String.valueOf(curpage * rowpage);

            if (StringUtils.isBlank(column)){
                column = "*";
            }
            if (StringUtils.isBlank(condition)){
                runSql = "WITH selectTemp AS \n" +
                        "( \n" +
                        " \t SELECT TOP 100 PERCENT ROW_NUMBER() OVER (ORDER BY "+ order + ") as __row_number__, " + column +
                        " \n\t FROM " + tabname +
                        " \n\t ORDER BY "+ order +
                        " \n)\n" +
                        "SELECT * FROM selectTemp WHERE __row_number__ BETWEEN " +
                        String.valueOf((curpage - 1) * rowpage + 1) +
                        " AND " + String.valueOf(curpage * rowpage) + " ORDER BY __row_number__";
            }else{
                runSql = "WITH selectTemp AS \n" +
                        "( \n" +
                        " \t SELECT TOP 100 PERCENT ROW_NUMBER() OVER (ORDER BY "+ order + ") as __row_number__, " + column +
                        " \n\t FROM " + tabname +
                        " \n\t WHERE " + condition +
                        " \n\t ORDER BY "+ order +
                        " \n)\n" +
                        "SELECT * FROM selectTemp WHERE __row_number__ BETWEEN " +
                        String.valueOf((curpage - 1) * rowpage + 1) +
                        " AND " + String.valueOf(curpage * rowpage) + " ORDER BY __row_number__";
            }
//            try{
//                result =  dataOper.dbQuery(runSql);
//            }catch (Exception e){
//                throw new RuntimeException(e.getMessage());
//            }
            result =  dataOper.dbQuery(runSql);
            resultjson.clear();
            resultjson.put("records", result);
            resultjson.put("total", rowcnt);
            resultjson.put("size", rowpage);
            resultjson.put("current", curpage);
            resultjson.put("pages", (int) Math.ceil(((double)rowcnt)/rowpage));
        }

        System.out.println("resultjson\t" + resultjson);
        return resultjson;
    }

    public JSONObject getTabname(String funcno) throws InterruptedException {
        String runSql = "SELECT tabname, onlycolumn FROM s_function_h WHERE funcno = '" + funcno + "'";
        JSONObject result = new JSONObject(true);
        result =  dataOper.SingleQuery(runSql);
        if (result.isEmpty() || result.size() < 1){
            throw new RuntimeException("功能[ "+funcno+" ]未定义");
        }
        if (StringUtils.isBlank(result.getString("tabname"))){
            throw new RuntimeException("系统功能[ "+funcno+" ]表未定义");
        }
        return result;
    }

    public String getFirstColumn(String tabname) throws InterruptedException {
        String runSql = "SELECT name FROM syscolumns WHERE  ID = OBJECT_ID(" + tabname + ") AND COLID = 1";
        JSONObject resultjson = new JSONObject(true);
        resultjson =  dataOper.SingleQuery(runSql);
        if (resultjson.isEmpty() || resultjson.size() < 1){
            throw new RuntimeException("取第一个字段出错");
        }
        String result = resultjson.getString("name");
        return result;
    }

}
