package com.example.assets.business.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.assets.business.entiity.BillStatus;
import com.example.assets.business.entiity.TestUser;
import com.example.assets.util.Cryptography;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class test {
    public void main(String[] args) {
        String body="{\"billPageNo\":0,\"billPageSize\":10,\"endAt\":\"2021-05-02 18:25:12\",\"startAt\":\"2021-05-02 18:20:12\",\"stkId\":\"1272\"}";

//        System.out.println(body);

        String name = BillStatus.getStatusName("4");
//        System.out.println("name\t"+ name);

        List<TestUser> userList = new ArrayList<>();
        TestUser testUser1 = new TestUser("101", "张三", "pw001", "M", "138001", 20, 70.00, new Date());
        TestUser testUser2 = new TestUser("102", "赵四", "pw002", "M", "138002", 21, 73.00, new Date());
        TestUser testUser3 = new TestUser("103", "王五", "pw003", "M", "138003", 25, 72.00, new Date());
        TestUser testUser4 = new TestUser("104", "刘六", "pw004", "F", "138003", 27, 72.00, new Date());
        TestUser testUser5 = new TestUser("105", "钱七", "pw005", "F", "138003", 20, 80.00, new Date());
        userList.add(testUser1);
//        userList.add(testUser2);
//        userList.add(testUser3);
//        userList.add(testUser4);
//        userList.add(testUser5);

        List<String> txtList = new ArrayList<>();
        for (TestUser t : userList) {
            txtList.add(t.getUserid() + "-" + t.getUserName());
        }

//        System.out.println("isequle:" + isequle(m, n));






//        List<TestUser> slist = userList.stream().sorted().collect(Collectors.toList());


        System.out.println("----------使用stream和sort--weight默认升序----------");
        List<TestUser> sortList1 = userList.stream().sorted((a, b) -> a.getWeight().compareTo(b.getWeight())).collect(Collectors.toList());
//        sortList1.stream().forEach(s-> System.out.println(s.getWeight()));

        System.out.println("sortList1\n" + JSONObject.toJSONString(sortList1, SerializerFeature.PrettyFormat,
                SerializerFeature.WriteDateUseDateFormat));

        System.out.println("---------使用stream和sort--weight降序排列-----------");
        List<TestUser> sortList2 = userList.stream().sorted(Comparator.comparing(TestUser::getWeight).reversed()).collect(Collectors.toList());
        System.out.println("sortList2\n" + JSON.toJSONString(sortList2));

        System.out.println("----------不使用stream和sort--升序----------");
        // 使用集合的sort排序，集合自身排序发生变化
        userList.sort((a,b)->a.getWeight().compareTo(b.getWeight()));
        System.out.println("sortList3\n" + JSON.toJSONString(userList));

        System.out.println("----------使用stream和sort--weight默认升序----------");
        List<TestUser> sortList3 = userList.stream().sorted(Comparator.comparing(TestUser::getWeight)).collect(Collectors.toList());
        System.out.println("sortList4\n" + JSON.toJSONString(sortList3));

        List<TestUser> sortList4 = userList;

        // 先按Weight倒序排列、再按Age倒序排列、最后按Userid正序排列
        System.out.println("----------先按Weight倒序排列、再按Age倒序排列、最后按Userid正序排列----------");
        sortList4.sort(Comparator.comparing(TestUser::getWeight, Comparator.reverseOrder())
                .thenComparing(TestUser::getAge, Comparator.reverseOrder())
                .thenComparing(TestUser::getUserid));

//        sortList4.stream().forEach(x -> x.setUserid(null));

        String jsontext = JSON.toJSONString(sortList4);
        System.out.println("jsontext\n" + jsontext);

        List<TestUser> sortList5 = JSONObject.parseArray(jsontext, TestUser.class);
        System.out.println("sortList5\n" + sortList5);

        String jsontext2 = JSONObject.toJSONString(sortList4, SerializerFeature.PrettyFormat);
        System.out.println("jsontext2\n" + jsontext2); //, SerializerFeature.PrettyFormat);  //, SerializerFeature.PrettyFormat, SerializerFeature.WriteDateUseDateFormat)
        List<TestUser> sortList6 = JSONObject.parseArray(jsontext2, TestUser.class);
        System.out.println("sortList6\n" + sortList6);

        String[] temp = new String[]{"Geng", "Geng", "Tian", "Tian", "Ming"};
        List<String> arrays = Arrays.stream(temp).map(res -> res.split(",")).flatMap(Arrays::stream).distinct().collect(Collectors.toList());
        System.out.println("arrays\t" + arrays.toString());
        List<String> arrays1 = Arrays.stream(temp).distinct().collect(Collectors.toList());
        System.out.println("arrays1\t" + arrays1.toString());

        String[] temp1 = new String[]{"geng", "geng", "tian", "tian", "ming"};
        List<String> arraysA = Arrays.stream(temp).map(x -> StrUtil.toCamelCase(x)).distinct().collect(Collectors.toList());
        System.out.println("arraysA\t" + arraysA.toString());
        System.out.println("Cryptography\t" + Cryptography.encryptToMD5("1"));



//, Feature.OrderedField





//        List<TestUser> orderUser = userList.stream().sorted(Comparator.reverseOrder()).stream().sorted(Comparator.reverseOrder());

//        //按sex进行分组
//        Map<String, List<TestUser>> listMap = userList.stream().collect(Collectors.groupingBy(TestUser::getSex));
//        System.out.println("listMap:\n" + listMap);
//        List<TestUser> templist1 = new ArrayList<>();
//        Set<Map.Entry<String, List<TestUser>>> entries = listMap.entrySet();
//        entries.forEach(item -> {
//            String gender = item.getKey();
//            List<TestUser> usersList = item.getValue();
//            System.out.println(gender);
//            usersList.forEach(user -> System.out.println(user));
//        });
//        // 求最大年龄
//        Integer maxAge = userList.stream().map(TestUser::getAge).max(Integer::compareTo).get();
//        System.out.println("最大年龄\t" +maxAge);
//
//        // 求最小年龄
//        Integer minAge = userList.stream().map(TestUser::getAge).min(Integer::compareTo).get();
//        System.out.println("最大年龄\t" +minAge);
//
//        // 得到最大年龄对象
//        TestUser usersMaxAge = userList.stream().max(Comparator.comparingInt(TestUser::getAge)).get();
//        System.out.println("最大年龄对象\t" +usersMaxAge);
//
//        // 求最大重量
//        Double maxWeight = userList.stream().map(TestUser::getWeight).max(Double::compareTo).get();
//        System.out.println("最大重量\t" +maxWeight);
//
//        //按sex进行分组统计age
//        Map<String, Integer> listInteger = userList.stream().collect(Collectors.groupingBy(TestUser::getSex, Collectors.summingInt(TestUser::getAge)));
//        System.out.println("listInteger:\n" + listInteger);
//
//        //按sex进行分组行数
//        Map<String, Long> listCnt = userList.stream().collect(Collectors.groupingBy(TestUser::getSex, Collectors.counting()));
//        System.out.println("listCnt:\n" + listCnt);
//
//        //按sex和age进行分组
//        Map<String, List<TestUser>> tmap = userList.stream().collect(Collectors.groupingBy(x -> x.getSex() + "|" + x.getAge()));
//        System.out.println("tmap:\n" + tmap);
//        tmap.forEach((k, v) -> {
//            System.out.println("tmap_k:\t" + k);
//            System.out.println("tmap_v:\n" + v);
//        });





//        Map<TestUser.getSex, Double> averageAgeByGender = userList.stream()
//                .collect(Collectors.groupingBy(TestUser::getSex, Collectors.averagingInt(TestUser::getWeight)));


//        Map<TestUser.Sex, List<TestUser>> collect = userList.stream().collect(Collectors.groupingBy(TestUser::getSex));

//        System.out.println(decimal);

//        BigDecimal total = userList.stream().map(TestUser::getWeight).reduce(BigDecimal.ZERO, BigDecimal::add);

//        Map<TestUser.Sex, Double> averageLikesPerType = userList.stream().collect(Collectors.groupingBy(TestUser::getSex, Collectors.averagingInt(TestUser::getWeight)));


//        Map<String, Map<TestUser, List>> map = userList.stream().collect(Collectors.groupingBy(TestUser::getSex, Collectors.groupingBy(TestUser::getAge)));


//        JSONArray objects = jsonArray.stream()
//                .filter(iter -> "IVCT".equals(((JSONObject) iter).getString("ddcode")) && "A".equals(((JSONObject) iter).getString("code")))
//                .sorted(Comparator.comparing(iter -> ((JSONObject) iter).getString("code")).reversed())
//                .collect(Collectors.toCollection(JSONArray::new));



        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        //如：获取当前时间60秒以后的时间：
        Date oldTime = new Date(); //获取当前时间
        Long changeValue = 60L;
        Date newTime =new Date(oldTime.getTime() + 1000L * changeValue);
        String firstString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(newTime);
        System.out.println("OldTime:\t" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(oldTime));
        System.out.println("NewTime:\t" + firstString);

        // 创建一个当前的Date对象
        Date currentDate = new Date();

        // 将Date对象转换为毫秒级时间戳
        long millisTimestamp = currentDate.getTime();

        // 将毫秒级时间戳转换为秒级时间戳
        long secondsTimestamp = millisTimestamp / 1000;

        // 为了再次转换为Date，我们需要将秒级时间戳转回为毫秒级时间戳
        long millisTimestampAgain = secondsTimestamp * 1000;

        // 使用转换后的毫秒级时间戳创建一个新的Date对象
        Date dateFromTimestamp = new Date(millisTimestampAgain);

        // 输出原始Date和从时间戳转换回来的Date
        System.out.println("原始Date: " + sdf.format(currentDate));
        System.out.println("从秒级时间戳转换回来的Date: " + sdf.format(dateFromTimestamp));




        long time = 30 * 60 * 1000;//30分钟

        Date afterDate = new Date(oldTime.getTime() + time);//30分钟后的时间
        Date beforeDate = new Date(oldTime.getTime() - time);//30分钟前的时间
        System.out.println("当前的时间    :\t" + sdf.format(oldTime));
        System.out.println("30分钟前的时间:\t" + sdf.format(beforeDate));
        System.out.println("30分钟后的时间:\t" + sdf.format(afterDate ));
    }

    public Date getChangeTime(Date nowTime,Long changeValue)
    {
        //nowTime=基础时间,将在此时间上做加减
        //changeValue=时间变换量，如changeValue=60,则获取[基础时间]60秒后的时间
        //如 changeValue=-60 ,则获取[基础时间]60秒前的时间
        Date newTime =new Date(nowTime.getTime()+ 1000L * changeValue);
        return newTime;
    }

    public LocalDateTime DateToLocalDateTime(Date locTime) {
        String firstString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(locTime);
        LocalDateTime firstTime = LocalDateTime.parse(firstString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return firstTime;
    }

//    public HashMap<String, String> toMapLabelValue(String value, String column) {
//        HashMap<String, String> map = new HashMap();
//        map.put(column, value);
////        map.put("label", label);
//        return map;
//    }
//
//    public Boolean isequle(Integer a, Integer b){
//        return a.equals(b);
//    }
}
