package com.example.assets.base.service;

import com.google.common.collect.ImmutableList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Coding
 * <p>
 * <br/>
 *
 * @author apr
 * @date 2021/04/23 15:04:12
 **/
public class Test {

    /**
     * 入口方法
     */
    public static void main(String[] args) {
        People p1 = new People("赵", "备注一", 12);
        People p2 = new People("钱", "备注二", 13);
        People p3 = new People("孙", "备注三", 14);
        People p4 = new People("赵", "备注一", 15);

        checkA(ImmutableList.of(p1, p2, p3, p4));
        System.out.println("*******************************");
        checkB(ImmutableList.of(p1, p2, p3, p4));
    }

    /**
     * 重复检测方式一
     */
    private static void checkA(List<People> ps) {
        ps.forEach(p -> {
            String check1 = p.name+p.remark;
            ps.forEach(pp -> {
                String check2 = pp.name+pp.remark;
                if(check1.equals(check2) && pp != p){
                    System.out.println("check_a 发现重复:("+p+") -> ("+pp+")");
                }
            });
        });
    }

    /**
     * 重复检测方式二
     */
    private static void checkB(List<People> ps) {
        Map<String, List<People>> repeat = new HashMap<>(100);

        ps.forEach(p -> {
            String check1 = p.name+p.remark;
            ps.forEach(pp -> {
                String check2 = pp.name+pp.remark;
                if(check1.equals(check2) && pp != p){
                    repeat.put(pp.name+"~"+pp.remark, ImmutableList.of(p, pp));
                }
            });
        });

        repeat.forEach((k,v) -> System.out.println("check_b 发现重复:("+k+") -> ("+v+")"));

    }


    /**
     * 人对象, 做重复测试用
     */
    static class People{
        public String name;
        public String remark;
        public int age;

        public People(String name, String remark, int age){
            this.name = name;
            this.remark = remark;
            this.age = age;
        }

        @Override
        public String toString() {
            return "People{" +
                    "name='" + name + '\'' +
                    ", remark='" + remark + '\'' +
                    ", age=" + age +
                    '}';
        }
    }


}
