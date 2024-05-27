package com.example.assets.base.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 直接进行数据库操作，执行SQL
 */
@Slf4j
@Component
public class DataOper {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 获取sqlSession
     * @return
     */
    public SqlSession getSqlSession() {
        return SqlSessionUtils.getSqlSession(sqlSessionTemplate.getSqlSessionFactory(),
                sqlSessionTemplate.getExecutorType(), sqlSessionTemplate.getPersistenceExceptionTranslator());
    }

    /**
     * 关闭sqlSession
     * @param session
     */
    public void closeSqlSession(SqlSession session){
        SqlSessionUtils.closeSqlSession(session, sqlSessionTemplate.getSqlSessionFactory());
    }

    public void dbUpdate(String runsql) throws Exception  {
        SqlSession session = getSqlSession();
        Connection connection = session.getConnection();

        connection.setAutoCommit(false);
        try{
            log.info("Run:\t"+runsql);
            PreparedStatement stat = connection.prepareStatement(runsql);
            int rowCnt = stat.executeUpdate();
            if (rowCnt < 1){
                throw new RuntimeException("执行失败：" + runsql);
            }
        }catch (SQLException e){
            session.rollback();
            throw new RuntimeException(e.getMessage());
        }
        session.commit();
        closeSqlSession(session);
    }

    public void dbUpdate(List<String> runsql){
        JSONObject resultjson = new JSONObject(true);
        SqlSession session = getSqlSession();
        Connection connection = session.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }

        try {
            for (Integer i = 0; i < runsql.size(); i++) {
                log.info("Run:\t" + runsql.get(i));

                    preparedStatement = connection.prepareStatement(runsql.get(i));
                    int rowCnt = preparedStatement.executeUpdate();
                    if (rowCnt < 1){
//                        session.rollback();
                        throw new RuntimeException("执行失败：没有数据被影响-->" + runsql);
                    }
            }
        }catch (SQLException e){
            session.rollback();
            throw new RuntimeException(e.getMessage());
        }
        session.commit();
        closeSqlSession(session);
    }

    public JSONArray dbQuery(String assql) {
        JSONArray datajson = new JSONArray();

        log.info("执行查询sql:\n"+assql);
        PreparedStatement pst = null;
        SqlSession session = getSqlSession();
        ResultSet result = null;
        try {
            pst = session.getConnection().prepareStatement(assql);
            result = pst.executeQuery();
            ResultSetMetaData md = result.getMetaData(); //获得结果集结构信息,元数据
            int columnCount = md.getColumnCount();   //获得列数
            while (result.next()) {
                JSONObject resultjson = new JSONObject(true);
                for (int i = 1; i <= columnCount; i++) {
                    String key = new String();
                    String value = new String();
                    if ("__row_number__".equalsIgnoreCase(md.getColumnName(i))){
                        continue;
                    }
                    if (md.getColumnName(i).isEmpty() || md.getColumnName(i) == null){
                        key = String.valueOf(i);
                    }else {
                        key = md.getColumnName(i).toLowerCase(Locale.ROOT);
                    }
                    resultjson.put(key, result.getObject(i));
                }
                datajson.add(resultjson);
            }
        } catch (SQLException e) {
            log.error("查询出错:\n "+e.getMessage()+ "\n 执行脚本：" + assql);
//            e.printStackTrace();
            throw new RuntimeException("查询出错:" + e.getMessage() + " 执行脚本：" + assql);
        }finally {
            if(pst!=null){
                try {
                    pst.close();
                } catch (SQLException e) {
                    log.error("关闭会话失败");
                    e.printStackTrace();
                }
            }
            closeSqlSession(session);
        }
        return datajson;
    }

    public JSONObject SingleQuery(String assql) {
        JSONObject resultjson = new JSONObject(true);

        log.info("执行查询sql:\n"+assql);
        PreparedStatement pst = null;
        SqlSession session = getSqlSession();
        ResultSet result = null;
        try {
            pst = session.getConnection().prepareStatement(assql);
            result = pst.executeQuery();
            ResultSetMetaData md = result.getMetaData(); //获得结果集结构信息,元数据
            int columnCount = md.getColumnCount();   //获得列数
            while (result.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String key = new String();
                    String value = new String();
                    if (md.getColumnName(i).isEmpty() || md.getColumnName(i) == null){
                        key = String.valueOf(i);
                    }else {
                        key = md.getColumnName(i);
                    }
                    resultjson.put(key, result.getObject(i));
                }
                break;
            }
        } catch (SQLException e) {
            log.error("查询出错:\n "+e.getMessage()+ "\n 执行脚本：" + assql);
//            e.printStackTrace();
            throw new RuntimeException("查询出错:" + e.getMessage() + " 执行脚本：" + assql);
        }finally {
            if(pst!=null){
                try {
                    pst.close();
                } catch (SQLException e) {
                    log.error("关闭会话失败");
                    e.printStackTrace();
                }
            }
            closeSqlSession(session);
        }
        return resultjson;
    }

    public List<String> dbQryStringList(String assql) throws Exception  {
        List<String> result = new ArrayList<String>();
        SqlSession session = getSqlSession();
        Connection connection = session.getConnection();
        Statement stat = connection.createStatement();
        try {
            ResultSet rs = stat.executeQuery(assql);
            ResultSetMetaData objectinfo = rs.getMetaData();
            while (rs.next()) {
                JSONObject jsonObj = new JSONObject(true);
                StringBuffer buffer = new StringBuffer();
                for (int i = 1; i <= objectinfo.getColumnCount(); i++) {
                    String columnName = objectinfo.getColumnLabel(i).toLowerCase();
                    String value = rs.getString(columnName);
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
                    result.add(rs.getString(i));
                }
            }
        }
        catch(Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
        if (result == null || result.size() < 1){
            throw new RuntimeException("没有相关数据");
        }
        return result;
    }


    public JSONArray dbQtyList(String as_sql) throws Exception  {
        JSONObject resultjson = new JSONObject(true);
        JSONArray infojson = new JSONArray();
        JSONArray datajson = new JSONArray();
        SqlSession session = getSqlSession();
        Connection connection = session.getConnection();
        Statement stat = connection.createStatement();
        try {
            ResultSet rs = stat.executeQuery(as_sql);
            ResultSetMetaData objectinfo = rs.getMetaData();
            while (rs.next()) {
                JSONObject jsonObj = new JSONObject(true);
                StringBuffer buffer = new StringBuffer();
                for (int i = 1; i <= objectinfo.getColumnCount(); i++) {
                    String columnName = objectinfo.getColumnLabel(i).toLowerCase();
                    String value = rs.getString(columnName);
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
                    String ls_coltype = objectinfo.getColumnTypeName(i);

                    if (ls_coltype == "char" || ls_coltype == "varchar") {
                        jsonObj.put(columnName, value);
                    } else if (ls_coltype == "smallint" || ls_coltype == "tinyint") {
                        jsonObj.put(columnName, rs.getInt(i));
                    } else if (ls_coltype == "numeric" || ls_coltype == "decimal") {
                        jsonObj.put(columnName, rs.getDouble(i));
                    } else if (ls_coltype == "datetime") {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        timeByDate = sdf.format(rs.getTimestamp(i));
                        jsonObj.put(columnName, timeByDate);
                    } else {
                        jsonObj.put(columnName, rs.getString(i));
                    }
                }
                datajson.add(jsonObj);
            }
        }
        catch(Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
        if (datajson == null || datajson.size() < 1){
            throw new RuntimeException("没有相关数据");
        }
        return datajson;
    }

    public JSONArray tabNameInfo(ResultSetMetaData data) {
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

    public String getValue(JSONObject jsondata, String key) {
        Iterator iter = jsondata.entrySet().iterator();
        String result = new String();
        int i = 0;
        while (iter.hasNext()) {
            i ++;
            Map.Entry entry = (Map.Entry) iter.next();
            String column = entry.getKey().toString();
            if (column.equalsIgnoreCase(key)) {
                if (entry.getValue().toString() == null){
                    result = null;
                }else{
                    result = entry.getValue().toString();
                }
                break;
            }
        }
        return result;
    }


    public List<Map<String, Object>> excuteQuerySql(String sql){
        log.info("执行查询sql:"+sql);
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        PreparedStatement pst = null;
        SqlSession session = getSqlSession();
        ResultSet result = null;
        try {
            pst = session.getConnection().prepareStatement(sql);
            result = pst.executeQuery();
            ResultSetMetaData md = result.getMetaData(); //获得结果集结构信息,元数据
            int columnCount = md.getColumnCount();   //获得列数
            while (result.next()) {
                Map<String,Object> rowData = new HashMap<String,Object>();
                for (int i = 1; i <= columnCount; i++) {
                    String key = new String();
                    String value = new String();
                    if (md.getColumnName(i).isEmpty() || md.getColumnName(i) == null){
                        key = String.valueOf(i);
                    }else {
                        key = md.getColumnName(i);
                    }
                    System.out.println("ColumnName\t"+key+"\nvalue\t" + result.getObject(i)+"\n\n");
                    rowData.put(key, result.getObject(i));
                }
                list.add(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(pst!=null){
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            closeSqlSession(session);
        }
        return list;
    }


    public Map<String, Object> QuerySingle(String sql){
        log.info("执行查询sql:"+sql);
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        PreparedStatement pst = null;
        SqlSession session = getSqlSession();
        ResultSet result = null;
        try {
            pst = session.getConnection().prepareStatement(sql);
            result = pst.executeQuery();
            ResultSetMetaData md = result.getMetaData(); //获得结果集结构信息,元数据
            int columnCount = md.getColumnCount();   //获得列数
            while (result.next()) {
                Map<String,Object> rowData = new HashMap<String,Object>();
                for (int i = 1; i <= columnCount; i++) {
                    String key = new String();
                    String value = new String();
                    if (md.getColumnName(i).isEmpty() || md.getColumnName(i) == null){
                        key = String.valueOf(i);
                    }else {
                        key = md.getColumnName(i);
                    }
                    rowData.put(key, result.getObject(i));
                }
                list.add(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(pst!=null){
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            closeSqlSession(session);
        }
        return list.get(0);
    }

    public List<Map<String, Object>> procedure(String asProcName, List<Object> asParm, List<Integer> asRtn) {
        CallableStatement cs = null;
        StringBuilder lsProcedure = new StringBuilder();
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        SqlSession session = getSqlSession();

        if (asParm.size() > 0){
            for (int i = 0; i < asParm.size(); i++) {
                if (i == 0){
                    lsProcedure = new StringBuilder("?");
                }else{
                    lsProcedure.append(",?");
                }
            }
            lsProcedure = new StringBuilder("{call " + asProcName + "(" + lsProcedure + ")}");
        }else{
            lsProcedure = new StringBuilder("{call " + asProcName + "}");
        }

        log.info("执行查询sql:\n"+lsProcedure);
        try {
            cs = session.getConnection().prepareCall(lsProcedure.toString());
            if (asParm.size() > 0) {
                Iterator iter = asParm.iterator();
                for (int i = 0; i < asParm.size(); i++) {
                    cs.setObject(i + 1, asParm.get(i));
                }
            }
            if (asRtn.size() > 0) {
                for (int i = 0; i < asRtn.size(); i++) {
                    cs.registerOutParameter(asRtn.get(i), java.sql.Types.VARCHAR);
                }
            }
            cs.execute();
            if (asRtn.size() > 0) {
                Map<String,Object> rowData = new HashMap<String,Object>();
                for (int i = 0; i < asRtn.size(); i++) {
                    if (cs.getString(asRtn.get(i)) == null){
                        rowData.put(String.valueOf(i + 1), null);
                    }else{
                        rowData.put(String.valueOf(i + 1), cs.getObject(asRtn.get(i)));
                    }
                }
                result.add(rowData);
            }else{
                ResultSet dataRs = cs.getResultSet();
                ResultSetMetaData columnInfo = dataRs.getMetaData();
                while (dataRs.next()) {
                    Map<String,Object> rowData = new HashMap<String,Object>();
                    for (int i = 1; i <= columnInfo.getColumnCount(); i++) {
                        String key = new String();
                        String value = new String();
                        if (columnInfo.getColumnName(i).isEmpty() || columnInfo.getColumnName(i) == null){
                            key = String.valueOf(i);
                        }else {
                            key = columnInfo.getColumnName(i).toLowerCase(Locale.ROOT);
                        }
                        rowData.put(key, dataRs.getObject(i));
                    }
                    result.add(rowData);
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException("查询出错:" + e.getMessage() + " 执行脚本：" + lsProcedure);
        }
        return result;
    }
}
