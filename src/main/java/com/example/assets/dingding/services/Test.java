package com.example.assets.dingding.services;

/**
 * TODO
 *
 * @author GengTianMing
 * @since 2023/02/28 14:31
 **/

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.assets.dingding.Config.MyDataSource;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) throws SQLException {
        MyDataSource myDataSource = new MyDataSource();
        System.out.println(myDataSource.getSize());
        Connection con=myDataSource.getConnection();

        System.out.println(myDataSource.getSize());
        System.out.println(con.getClass());

        List<Map<String, Object>> userList = new ArrayList<Map<String,Object>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        String sql="select * from t_asset_receive_h where CHECKER4 = '200789' ";
        PreparedStatement pst = con.prepareStatement(sql);
//        Statement statement = con.createStatement();
        ResultSet result = pst.executeQuery();
        ResultSetMetaData column = result.getMetaData();
        Integer columnCount = column.getColumnCount();   //获得列数
        while (result.next()){
            Map<String,Object> rowData = new HashMap<String,Object>();
            for (int i = 1; i <= columnCount; i++) {
                String key = new String();
                String value = new String();
                if (column.getColumnName(i).isEmpty() || column.getColumnName(i) == null){
                    key = String.valueOf(i);
                }else {
                    key = column.getColumnName(i);
                }
//                System.out.println("ColumnName\t"+key+"\nvalue\t" + result.getObject(i)+"\n\n");
                String colType = column.getColumnTypeName(i);

                if ( "datetime".equalsIgnoreCase(colType) ) {
                    if ( StringUtils.isNotEmpty(result.getString(i)) ) {
                        rowData.put(key.toUpperCase(), sdf.format(result.getTimestamp(i)));
                    } else {
                        rowData.put(key.toUpperCase(), null);
                    }

                } else {
                    rowData.put(key.toUpperCase(), result.getObject(i));
                }

            }
            userList.add(rowData);

//            System.out.println(result.getString("userid")+" "+result.getString("username"));
        }
        List<Map<String, String>> billList = new ArrayList<>();
//        Map<String, String> one = Stream.of(new String[][] {
//                { "9908AORD2207160001", "9908AORD2207160002" },
//                { "9908AORD2207160003", "9908AORD2207160004" },
//        }).collect(Collectors.toMap(data -> data[0], data -> data[1]));
        Map<String, String> one = new HashMap<>();
        one.put("bill", "9908AORD2207160001");
        billList.add(one);
        Map<String, String> two = new HashMap<>();
        two.put("bill", "9908AORD2207160002");
        billList.add(two);
        Map<String, String> three = new HashMap<>();
        three.put("bill", "9908AORD2207160003");
        billList.add(three);
        Map<String, String> fore = new HashMap<>();
        fore.put("bill", "9908AORD2206090001");
        billList.add(fore);
        System.out.println("billList:\t" + billList);

        List<Map<String, Object>> existsList =
                userList.stream().filter(x -> billList.stream().anyMatch(y -> x.get("ORD_ID").equals(y.get("bill")))).collect(Collectors.toList());

        List<Map<String, Object>> noExistsList =
                userList.stream().filter(x -> billList.stream().noneMatch(y -> x.get("ORD_ID").equals(y.get("bill")))).collect(Collectors.toList());

//        List<Map<String, Object>> existsList =
//                userList.stream().filter(x -> billList.contains(x.get("ORD_ID"))).collect(Collectors.toList());
//
//        List<Map<String, Object>> noExistsList =
//                userList.stream().filter(x -> !billList.contains(x.get("ORD_ID"))).collect(Collectors.toList());

//        System.out.println("existsList:\n" + JSON.toJSONString(existsList));
        System.out.println("existsSize:\t" + existsList.size());

//        System.out.println("noExistsList:\n" + JSON.toJSONString(noExistsList));
        System.out.println("noExistsSize:\t" + noExistsList.size());

        System.out.println("userSize:\t" + userList.size());

        System.out.println("userSize:\n" + JSONObject.toJSONString(userList, SerializerFeature.PrettyFormat,
                SerializerFeature.WriteDateUseDateFormat));

//        List<SysCode> sysList = sysCodeList.stream().collect(Collectors.toMap(x -> x.getSyscode() + x.getDescr(), y -> y, (oldV, newV) -> oldV)).values().stream().collect(Collectors.toList());
//        System.out.println("sysList:\t" + sysList.size() + "\n" + sysList);

        result.close();
        pst.close();
        con.close();
    }
}

