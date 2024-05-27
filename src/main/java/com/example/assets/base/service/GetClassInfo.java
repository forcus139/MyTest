package com.example.assets.base.service;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.assets.base.entity.AssetImportDetail;
import com.example.assets.base.entity.AssetImportMaster;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.*;

public class GetClassInfo {
//    /**
//     * T：实体类
//     */
//    public class BaseQuery<T> {

        /**
         * 获取实体类的表名
         */
        public static String getTableName(Object o){
            TableName a =o.getClass().getAnnotation( TableName.class);

            String tableName =a.value();

            List<String> columnList = getAnnotationFieldList(o, Id.class);
            System.out.println("TabName\t"+tableName+"\tPriKey:\t" + columnList.toString());

//            finalParameterizedType paraType = (ParameterizedType)
//                    this.getClass().getGenericSuperclass();
//            final Type[] types = paraType.getActualTypeArguments();
//            String tableName = null;
//            for (final Type type : types) {
//                final Annotation annotation = ((Class) type).getAnnotation(Table.class);
//                if (annotation == null) {
//                    continue;
//                }
//                tableName = ((Table) annotation).name();
//                break;
//            }
            return tableName;
        }

        public static Object setValue(Object entity, String key, Object value) throws IllegalAccessException, NoSuchFieldException {
            Field field =entity.getClass().getDeclaredField(key);
            field.setAccessible(true);
            field.set(entity,value);
            field.setAccessible(false);
//            System.out.println("Key:\t" + field.getName() + "\tValue:"+ field.get(entity.getClass()));      //field.get(entity).toString()
            return entity;
        }

        public static void main(String[] args) throws InterruptedException {
            AssetImportMaster assetImportMaster = new AssetImportMaster();
            String tableName = getTableName(assetImportMaster);
            System.out.println("TableName:\t" + tableName.toUpperCase(Locale.ROOT));
            assetImportMaster.setBill(new Date().toString());
            System.out.println("Bill:\t" + assetImportMaster.getBill());
//            assetImportMaster = (AssetImportMaster) setValue(assetImportMaster, "group_drpid", "123456789");
            try{
                assetImportMaster = (AssetImportMaster) setValue(assetImportMaster, "price", 3.131415926);
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("Group_Drpid:\t" + assetImportMaster.getPrice());

            AssetImportDetail assetImportDetail = new AssetImportDetail();
            String dtltableName = getTableName(assetImportDetail);

            HashMap<String, Integer> clsitem = new HashMap<>();
            clsitem.put("a", 1);
            clsitem.put("b", 2);
            System.out.println("clsitem:\t" + clsitem);
        }

        public static List<String> getAnnotationFieldList(Object object, Class annotation){
            List<String> list = new ArrayList<String>(32);
            Class clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields){
                field.setAccessible(true);
                Boolean isAnon = field.isAnnotationPresent(annotation);
                if (isAnon){
                    list.add(field.getName());
                }
            }
            return list;
        }


}
