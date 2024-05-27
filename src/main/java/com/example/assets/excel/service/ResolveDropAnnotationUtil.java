package com.example.assets.excel.service;

/**
 * TODO
 *
 * @author GengTianMing
 * @since 2023/12/29 10:31
 **/

import com.example.assets.excel.entity.DropDownSetField;

import java.util.Map;
import java.util.Optional;

/**
 * 处理导入excel下拉框注解的工具类
 */
public class ResolveDropAnnotationUtil {

    public static String[] resove(DropDownSetField dropDownSetField, String[] strings) {
        if (!Optional.ofNullable(dropDownSetField).isPresent()) {
            return null;
        }

        // 获取固定下拉信息
        String[] source = dropDownSetField.source();
        if (null != source && source.length > 0) {
            return source;
        }

        if (null != strings && strings.length > 0) {
            try {
                String[] dynamicSource = strings;
                if (null != dynamicSource && dynamicSource.length > 0) {
                    return dynamicSource;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //插入到map中
    public static void insertMap(Map<Integer, String[]> map, String[] params, DropDownSetField dropDownSetField, int i) {
        String[] sources = ResolveDropAnnotationUtil.resove(dropDownSetField, params);
        if (null != sources && sources.length > 0) {
            map.put(i, sources);
        }
    }
}

