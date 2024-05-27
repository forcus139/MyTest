package com.example.assets.base.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author GTM
 */
public class db_oper {
    public static final String DRIVER_NAME ="oracle.jdbc.OracleDriver";
    public static final String DBURL ="jdbc:oracle:thin:@192.168.11.60:1521:ftcard";
    public static final String USER_NAME ="dbusrcard001";
    public static final String USER_PWD ="futurecard";
    public static Connection dbConn = null;

    public static JSONArray dataObjectInfo(ResultSetMetaData data) {
        JSONArray infojson  = new JSONArray();
        try {
            for (int i = 1; i <= data.getColumnCount(); i++) {
                JSONObject datainfo = new JSONObject(true);

                    // 获得所有列的数⽬及实际列数
                    int columnCount = data.getColumnCount();
                    // 获得指定列的列名
                    String columnName = data.getColumnName(i);

                    // 获得指定列的列值
                    int columnType = data.getColumnType(i);
                    // 获得指定列的数据类型名
                    String columnTypeName = data.getColumnTypeName(i);

                    // 所在的Catalog名字
                    String catalogName = data.getCatalogName(i);
                    // 对应数据类型的类
                    String columnClassName = data.getColumnClassName(i);
                    // 在数据库中类型的最⼤字符个数
                    int columnDisplaySize = data.getColumnDisplaySize(i);

                    // 默认的列的标题
                    String columnLabel = data.getColumnLabel(i);
                    // 获得列的模式
                    String schemaName = data.getSchemaName(i);
                    // 某列类型的精确度(类型的长度)
                    int precision = data.getPrecision(i);
                    // ⼩数点后的位数
                    int scale = data.getScale(i);
                    // 获取某列对应的表名
                    String tableName = data.getTableName(i);
                    // 是否⾃动递增
                    boolean isAutoInctement = data.isAutoIncrement(i);
                    // 在数据库中是否为货币型
                    boolean isCurrency = data.isCurrency(i);
                    // 是否为空
                    int isNullable = data.isNullable(i);
                    // 是否为只读
                    boolean isReadOnly = data.isReadOnly(i);
                    // 能否出现在where中
                    boolean isSearchable = data.isSearchable(i);

                    datainfo.put("COLUMNNAME", columnName);
                    datainfo.put("COLUMNTYPE", columnType);
                    datainfo.put("COLUMNTYPENAME", columnTypeName);
                    datainfo.put("COLUMNCLASSNAME", columnClassName);
                    datainfo.put("COLUMNDISPLAYSIZE", columnDisplaySize);
                    datainfo.put("COLUMNLABEL", columnLabel);
                    datainfo.put("SCHEMANAME", schemaName);
                    datainfo.put("PRECISION", precision);
                    datainfo.put("TABLENAME", tableName);
                    datainfo.put("ISAUTOINCTEMENT", isAutoInctement);
                    datainfo.put("ISCURRENCY", isCurrency);
                    datainfo.put("ISNULLABLE", isNullable);
                    datainfo.put("ISREADONLY", isReadOnly);
                    datainfo.put("ISSEARCHABLE", isSearchable);
                    infojson.add(datainfo);
                }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return infojson;
    }
    public static JSONObject dbConnection() {
        JSONObject resultjson = new JSONObject(true);
        try {
            Class.forName(DRIVER_NAME);
            dbConn = DriverManager.getConnection(DBURL, USER_NAME, USER_PWD);
            resultjson.put("STATUS", 0);
            resultjson.put("MESSAGE", null);
        }catch(Exception e)
        {
            try {
                dbConn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
                String lsResult = e1.toString();
                resultjson.put("STATUS", -1);
                resultjson.put("MESSAGE", lsResult);
            }
        }
        return resultjson;
    }

    public static JSONObject dbQuery(String asSql) {
        JSONObject resultjson = new JSONObject(true);
        if (db_IsClose() == false){
            resultjson = dbConnection();
            String status = resultjson.getString("STATUS");
            if (Integer.parseInt(status) != 0){
                return resultjson;
            }
        }

        JSONArray infojson = new JSONArray();
        JSONArray datajson = new JSONArray();
        try {
            Statement stat = dbConn.createStatement();
            ResultSet rs = stat.executeQuery(asSql);
            ResultSetMetaData objectinfo = rs.getMetaData();
            while (rs.next()) {
                JSONObject jsonObj = new JSONObject(true);
                StringBuffer buffer = new StringBuffer();
                for (int i = 1; i <= objectinfo.getColumnCount(); i++) {
                    String columnName = objectinfo.getColumnLabel(i);
//                    String value = rs.getString(columnName);
//                    String value = new String(String.valueOf(rs.getString(columnName)).getBytes("gbk"),"ISO-8859-1");
                    String value = new String(rs.getString(columnName).getBytes("ISO-8859-1"),"GBK");
                    String timeByDate = "";
                    if (columnName.isEmpty()){
                        columnName = String.valueOf(i);
                    }

                    if (value != null) {
                        value = value.trim();
                    } else {
                        jsonObj.put(columnName, null);
                        continue;
                    }
                    String lsEcotype = objectinfo.getColumnTypeName(i);

                    if (lsEcotype == "char" || lsEcotype == "varchar") {
                        jsonObj.put(columnName, value);
                    } else if (lsEcotype == "smallint" || lsEcotype == "tinyint") {
                        jsonObj.put(columnName, rs.getInt(i));
                    } else if (lsEcotype == "numeric" || lsEcotype == "decimal") {
                        jsonObj.put(columnName, rs.getDouble(i));
                    } else if (lsEcotype == "datetime") {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        timeByDate = sdf.format(rs.getTimestamp(i));
                        jsonObj.put(columnName, timeByDate);
                    } else {
                        jsonObj.put(columnName, new String(rs.getString(i).getBytes("ISO-8859-1"),"GBK"));
                    }
                }
                datajson.add(jsonObj);
            }
            resultjson.put("STATUS", 0);
            resultjson.put("MESSAGE", null);
            infojson = db_oper.dataObjectInfo(objectinfo);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            String lsResult = e.toString();
            resultjson.put("STATUS", -1);
            resultjson.put("MESSAGE", lsResult);
        }

        resultjson.put("DATA", datajson);
        resultjson.put("INFO", infojson);
        return resultjson;
    }

    public static JSONObject dbUpdate(String asSql) {
        JSONObject resultjson = new JSONObject(true);
        if (db_IsClose() == false){
            resultjson = dbConnection();
            String status = resultjson.getString("STATUS");
            if (Integer.parseInt(status) != 0){
                return resultjson;
            }
        }
        try {
            dbConn.setAutoCommit(false);
            Statement stat = dbConn.createStatement();
            stat.executeUpdate(asSql);
            dbConn.commit();
            resultjson.put("STATUS", 0);
            resultjson.put("MESSAGE", null);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            String lsResult = e.getMessage();
            resultjson.put("STATUS", -1);
            resultjson.put("MESSAGE", lsResult);
            //回滚事务
            try {
                dbConn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
                lsResult = e1.toString();
                resultjson.put("STATUS", -2);
                resultjson.put("MESSAGE", lsResult);
            }
        }
        return resultjson;
    }


    public static JSONObject dbProcedure(String asProc, Vector<String> asParm, Vector<Integer> asRtn) {
        JSONObject resultjson = new JSONObject(true);
        CallableStatement cs = null;
        StringBuilder lsProcedure = new StringBuilder();
        JSONObject rowdata = new JSONObject(true);
        JSONArray datajson = new JSONArray();
        if (db_IsClose() == false){
            resultjson = dbConnection();
            String status = resultjson.getString("STATUS");
            if (Integer.parseInt(status) != 0){
                return resultjson;
            }
        }

        if (asParm.size() > 0){
            for (int i = 0; i < asParm.size(); i++) {
                if (i == 0){
                    lsProcedure = new StringBuilder("?");
                }else{
                    lsProcedure.append(",?");
                }
            }
            lsProcedure = new StringBuilder("{call " + asProc + "(" + lsProcedure + ")}");
//            System.out.println("ls_procedure:" + ls_procedure);
        }else{
            lsProcedure = new StringBuilder("{call " + asProc + "}");
        }


        try {
            cs = dbConn.prepareCall(lsProcedure.toString());
            if (asParm.size() > 0) {
                Iterator iter = asParm.iterator();
                for (int i = 0; i < asParm.size(); i++) {
                    String values = asParm.get(i);
                    cs.setString(i + 1, values);
                }
            }
            if (asRtn.size() > 0) {
                for (int i = 0; i < asRtn.size(); i++) {
                    cs.registerOutParameter(asRtn.get(i), java.sql.Types.VARCHAR);
                }
            }
            cs.execute();
            if (asRtn.size() > 0) {
                for (int i = 0; i < asRtn.size(); i++) {
//                    String lsRtnvlaue = cs.getString(asRtn.get(i));
                    if (cs.getString(asRtn.get(i)) == null){
                        rowdata.put(String.valueOf(i + 1), null);
                    }else{
                        rowdata.put(String.valueOf(i + 1), new String(cs.getString(asRtn.get(i)).getBytes("ISO-8859-1"),"GBK"));
                    }
                }
                datajson.add(rowdata);
            }else{
                ResultSet rs = cs.getResultSet();
                ResultSetMetaData objectinfo = rs.getMetaData();
                while(rs.next()) {
                    JSONObject jsonObj = new JSONObject(true);
                    StringBuffer buffer = new StringBuffer();
                    for (int i = 1; i <= objectinfo.getColumnCount(); i++) {
                        String columnName = objectinfo.getColumnLabel(i);
//                        String value = rs.getString(columnName);
                        String value = new String(rs.getString(columnName).getBytes("ISO-8859-1"),"GBK");
                        String timeByDate = "";

                        if (value != null) {
                            value = value.trim();
                        } else {
                            jsonObj.put(columnName, null);
                            continue;
                        }
                        String lsColtype = objectinfo.getColumnTypeName(i);

                        if (lsColtype == "char" || lsColtype == "varchar") {
                            jsonObj.put(columnName, value);
                        } else if (lsColtype == "smallint" || lsColtype == "tinyint") {
                            jsonObj.put(columnName, rs.getInt(i));
                        } else if (lsColtype == "numeric" || lsColtype == "decimal") {
                            jsonObj.put(columnName, rs.getDouble(i));
                        } else if (lsColtype == "datetime") {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            timeByDate = sdf.format(rs.getTimestamp(i));
                            jsonObj.put(columnName, timeByDate);
                        } else {
                            jsonObj.put(columnName, new String(rs.getString(i).getBytes("ISO-8859-1"),"GBK"));
                        }
                    }
                    datajson.add(jsonObj);
                }
            }
            resultjson.put("STATUS", 0);
            resultjson.put("MESSAGE", null);
            resultjson.put("DATA", datajson);
        }
        catch (Exception e) {
                String lsResult = e.getMessage();
                resultjson.put("STATUS", -1);
                resultjson.put("MESSAGE", lsResult);
                resultjson.put("DATA", null);
            }
        return resultjson;
    }

    public static String anysJson(JSONArray jsonArray, String lsColumn, String lsAttribute) {
        String lsResult = new String();
        try
        {
            lsAttribute = lsAttribute.toUpperCase(Locale.ROOT);
            for (int i = 0;i<jsonArray.size();i++){
                String rowtext = jsonArray.getString(i);
                JSONObject rowjson = JSONObject.parseObject(rowtext);
                String values = rowjson.getString("COLUMNNAME");
                if (lsColumn.compareToIgnoreCase( values ) == 0){
                    lsResult = rowjson.getString(lsAttribute);
                    break;
                }
            }
            return lsResult;
        }
        catch(Exception e)
        {
            lsResult = e.toString();
            return lsResult;
        }
    }
    public static String getStringFrom(String asText, String asSign, int aiOrder) {
        String asResult = null;
        int i = 1;
        for (String retval: asText.split(asSign)){
            if (i == aiOrder){
                asResult = retval;
                break;
            };
        }
        return asResult;
    }

    public static String getValue(JSONArray jsonArray, int aiOrder) {
        String lsResult = new String();
        try
        {
            if (jsonArray.size() == 1){
                String lsValue = new String();
                JSONObject rowjson = (JSONObject)jsonArray.get(0);
                Iterator iter = rowjson.entrySet().iterator();
                int i = 0;
                while (iter.hasNext()) {
                    i ++;
                    Map.Entry entry = (Map.Entry) iter.next();
                    if(entry.getValue() == null) {
                        lsValue = null;
                    }else{
                        lsValue = entry.getValue().toString();
                    }
                    if (i == aiOrder){
                        lsResult = lsValue;
                        break;
                    }
                }
            }
            return lsResult;
        }
        catch(Exception e)
        {
            lsResult = e.toString();
            return lsResult;
        }
    }
    public static ArrayList<String> getValue(JSONArray jsonArray) {
        ArrayList<String> result = new ArrayList<String>();
        try
        {
            if (jsonArray.size() == 1){
                String rowtext = jsonArray.getString(0);
                JSONObject rowjson = JSONObject.parseObject(rowtext);
                Iterator iter = rowjson.entrySet().iterator();
                int i = 0;
                while (iter.hasNext()) {
                    i ++;
                    Map.Entry entry = (Map.Entry) iter.next();
                    String ls_value = entry.getValue().toString();
                    result.add(ls_value);
                }
            }
            return result;
        }
        catch(Exception e)
        {
            result.add(e.toString());
            return result;
        }
    }

    public static JSONObject db_update(ArrayList<String> as_sql) {
        JSONObject resultjson = new JSONObject(true);
        Statement stat = null;
        String  ls_execsql = new String();
        int li_status = 1;
        if (db_IsClose() == false){
            resultjson = dbConnection();
            String status = resultjson.getString("STATUS");
            if (Integer.parseInt(status) != 0){
                return resultjson;
            }
        }
//        System.out.println("continct\n" + dbConn.nativeSQL());
        try {
            dbConn.setAutoCommit(false);
            stat = dbConn.createStatement();//创建一个 Statement 对象来将 SQL 语句发送到数据库。
        }catch (Exception ex){
            String ls_result = ex.getMessage();
            resultjson.put("STATUS", -1);
            resultjson.put("MESSAGE", ls_result);
            return resultjson;
        }

        for (int i = 0; i < as_sql.size(); i++) {
            try {
                if (as_sql.get(i).trim().isEmpty()){
                    continue;
                }
                ls_execsql = as_sql.get(i);
                stat.executeUpdate(ls_execsql);
            }
            catch(Exception e)
            {
                li_status = 0;
//                e.printStackTrace();
                String ls_result = e.getMessage();
//                ls_result = ls_result.trim().isEmpty();
                resultjson.put("STATUS", -1);
                if (ls_result.trim().isEmpty()){
                    ls_result = "执行失败";
                }
                resultjson.put("MESSAGE", ls_result);
                resultjson.put("RUNSQL", ls_execsql);
//                System.out.println("ls_result\t" + ls_result);
                break;
            }
        }
        if (li_status == 1){
            try {
                dbConn.commit();
            }catch (Exception e){
                String ls_result = e.getMessage();
                resultjson.put("STATUS", -1);
//                resultjson.put("MESSAGE", ls_result);
                return resultjson;
            }
            resultjson.put("STATUS", 0);
            resultjson.put("MESSAGE", null);
        }else{
            //回滚事务
            try {
                dbConn.rollback();
            } catch (SQLException e1) {
                String ls_result = e1.getMessage();
                resultjson.put("STATUS", -2);
                resultjson.put("MESSAGE", ls_result);
            }
//            System.out.println("error");
        }
        return resultjson;
    }

    public static JSONObject db_Close() {
        JSONObject resultjson = new JSONObject(true);
        try{
            dbConn.close();
        }catch (Exception e){
            String ls_result = e.getMessage();
            resultjson.put("STATUS", -1);
            resultjson.put("MESSAGE", ls_result);
            return resultjson;
        }
        resultjson.put("STATUS", 0);
        resultjson.put("MESSAGE", null);
        return resultjson;
    }

    public static boolean db_IsClose() {
        String ls_sql = "select Connection from (select 1 as Connection) a";
        try {
            Statement stat = dbConn.createStatement();//创建一个 Statement 对象来将 SQL 语句发送到数据库。
            ResultSet rs = stat.executeQuery(ls_sql);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public static JSONObject db_fail(Integer ai_code, String as_msg) {
        JSONObject resultjson = new JSONObject(true);
        resultjson.put("code", ai_code);
        resultjson.put("msg", as_msg);
        resultjson.put("data", null);
        return resultjson;
    }

    public static JSONObject db_succ(String as_data) {
        JSONObject resultjson = new JSONObject(true);
        resultjson.put("code", 200);
        resultjson.put("msg", "查询成功");
        resultjson.put("data", as_data);
        return resultjson;
    }

}
