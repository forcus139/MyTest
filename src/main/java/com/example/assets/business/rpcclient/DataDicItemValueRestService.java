//package com.example.assets.business.rpcclient;
//
//import com.alibaba.fastjson.JSONObject;
//import org.mapstruct.Mapper;
//import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
///**
// * TODO
// *
// * @author GengTianMing
// * @since 2023/01/09 09:28
// **/
////@Mapper
//@FeignClient(value = "http://172.30.19.91:8888/api-gateway/organization-service/api/v1.0")
//public interface DataDicItemValueRestService {
//    @RequestMapping(value = "/dataDicitemValues",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
//    JSONObject selectDataDicItemValue(JSONObject json);
//}