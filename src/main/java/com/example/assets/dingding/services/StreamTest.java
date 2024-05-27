package com.example.assets.dingding.services;

/**
 * TODO
 *
 * @author GengTianMing
 * @since 2023/02/28 14:31
 **/

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.assets.base.entity.SysCode;

import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamTest {
    public static void main(String[] args) throws SQLException {
        List<SysCode> sysCodeList = new ArrayList<>();
        sysCodeList.add(new SysCode("101",1, "0000", "Descr1", 1, 0,"Notes", "Remark","1","SysTem1"));
        sysCodeList.add(new SysCode("102",1, "0000", "Descr2", 2, 0,"Notes", "Remark","1","SysTem2"));
        sysCodeList.add(new SysCode("103",1, "0000", "Descr3", 3, 0,"Notes", "Remark","1","SysTem3"));
        sysCodeList.add(new SysCode("104",1, "0000", "Descr4", 4, 0,"Notes", "Remark","1","SysTem4"));
        sysCodeList.add(new SysCode("101",1, "0000", "Descr1", 5, 0,"Notes", "Remark","1","SysTem5"));
        sysCodeList.add(new SysCode("101",1, "0000", "Descr6", 6, 0,"Notes", "Remark","1","SysTem6"));

//        System.out.println("sysCodeList:\t" + sysCodeList.size() + "\n" + JSONObject.toJSONString(sysCodeList, SerializerFeature.PrettyFormat,
//        SerializerFeature.WriteDateUseDateFormat));

        System.out.println("sysCodeList:\t" + sysCodeList.size() + "\n" + sysCodeList);

        //去除完全重复的记录
        List<SysCode> distCode = sysCodeList.stream().distinct().collect(Collectors.toList());

//        System.out.println("distCode::\t" + distCode.size() + "\n" + JSONObject.toJSONString(distCode, SerializerFeature.PrettyFormat,
//                SerializerFeature.WriteDateUseDateFormat));

        System.out.println("distCode:\t" + distCode.size() + "\n" + distCode);


//        对一个People类的List集合，如果其中存在姓名、证件号、证件号码一致的，则将数据合并去重，并将所拥有的书籍进行合并
        List<SysCode> codeList = sysCodeList.stream()
                .collect(Collectors.toMap(
                        item -> item.getSyscode() + item.getLevels()
                                + item.getParentcode(),
                        a -> a, (o1, o2) -> {
                            return o1;
                        }))
                .values().stream().collect(Collectors.toList());

        System.out.println("codeList:\t" + codeList.size() + "\n" + codeList);

        //按syscode和descr进行去重,将结果返回给数组
        List<String> sysTest2 = sysCodeList.stream().map(x -> x.getSyscode() + "&" + x.getDescr()).distinct().collect(Collectors.toList());
        System.out.println("sysTest2:\t" + sysTest2.size() + "\n" + sysTest2);

        //按syscode和descr进行去重,将结果返回给List
        List<SysCode> sysTest = sysCodeList.stream()
                .collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getSyscode() + "&" + o.getDescr()))), ArrayList::new));
        System.out.println("sysTest:\t" + sysTest.size() + "\n" + sysTest);

        //按syscode和descr进行去重,将结果返回给List
        List<SysCode> sysList = sysCodeList.stream().collect(Collectors.toMap(x -> x.getSyscode() + x.getDescr(), y -> y, (oldV, newV) -> oldV)).values().stream().collect(Collectors.toList());
        System.out.println("sysList:\t" + sysList.size() + "\n" + sysList);

        //按syscode和descr进行去重,将结果返回给Map
        Map<String, String> collect2 = sysCodeList.stream().collect(Collectors.toMap(SysCode::getSyscode, SysCode::getDescr,(oldV, newV) -> oldV));
        String collectText = collect2.toString().replace('=', '&');
        List<String> collectList = Arrays.asList(collectText.split(","));


        //将字符串转换为数组
        System.out.println("collect2:\t" + collect2.size() + "\n" + collectList.toString());

        //按syscode进行去重  Collection
        Collection<SysCode> values = sysCodeList.stream().collect(Collectors.toMap(SysCode::getSyscode, Function.identity(), (oldV, newV) -> oldV)).values();
        System.out.println("values:\t" + values.size() + "\n" + JSONObject.toJSONString(values, SerializerFeature.PrettyFormat,
                SerializerFeature.WriteDateUseDateFormat));

//        List 转 JSONArray
        JSONArray jsonResult = JSONArray.parseArray(JSONArray.toJSONString(values));
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(values));
        System.out.println("jsonResult:\t" + jsonResult.size() + "\n" + jsonResult.toJSONString());

//        JSONArray 转 List
        List<SysCode> list = JSONArray.parseArray(jsonResult.toJSONString(), SysCode.class);
        System.out.println("list:\t" + list.size() + "\n" + JSONObject.toJSONString(list, SerializerFeature.PrettyFormat,
                SerializerFeature.WriteDateUseDateFormat));
    }
}

