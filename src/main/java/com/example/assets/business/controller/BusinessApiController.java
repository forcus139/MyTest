//package com.example.assets.business.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import com.example.assets.business.rpcclient.DataDicItemValueRestService;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
///**
// * TODO
// *
// * @author GengTianMing
// * @since 2023/01/09 09:34
// **/
//@RestController("business")
//@RequestMapping("/business")
//public class BusinessApiController {
//
//    @Resource
//    DataDicItemValueRestService dataDicItemValueRestService;
//
//    public void doLogin() {
//        JSONObject dataInfo = new JSONObject(true);
//        dataInfo.put("ddcode", "IVCT");
//        JSONObject test = dataDicItemValueRestService.selectDataDicItemValue(dataInfo);
//        System.out.println("test\t" +test);
//    }
//}
