package com.example.assets.base.controller;

import org.apache.commons.lang3.StringUtils;
import com.example.assets.base.service.CardQrCodeNewServices;
import com.example.assets.base.service.RtnResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@Component
@Slf4j
@RestController("储值卡接口")
@RequestMapping("/storedValueCard")
public class StoredValueCard {

    @Autowired
    CardQrCodeNewServices memberCardService;

    @PostMapping("/getGiftCardList")
    public RtnResult getGiftCardList(@RequestParam(value = "cdmowner") String cdmowner) {
        if(StringUtils.isBlank(cdmowner)){
            return  RtnResult.fail("查询失败:请求参数为空 ");
        }
        try{
            List<Map<String,Object>> data =memberCardService.getGiftCardList(cdmowner);
            log.info("查询成功: "+data);
            return  RtnResult.success(data);
        }catch(Exception e){
            log.error("查询失败: "+e.getMessage());
            return RtnResult.fail("查询失败: "+e.getMessage());
        }
    }

    @PostMapping("/queryGiftCardTradeLog")
    public RtnResult queryGiftCardTradeLog( String cdlno) {
        if(StringUtils.isBlank(cdlno)){
            return  RtnResult.fail("查询失败:请求参数为空 ");
        }
        try{
            List<Map<String,Object>> data =memberCardService.queryGiftCardTradeLog(cdlno);
            log.info("查询成功: "+data);
            return  RtnResult.success(data);
        }catch(Exception e){
            log.error("查询失败: "+e.getMessage());
            return RtnResult.fail("查询失败: "+e.getMessage());
        }
    }

    @PostMapping("generateBarcode")
    public RtnResult generateBarcode(@RequestParam(value = "cdmno") String cdmno,
                                     @RequestParam(value = "cqroper") String cqroper,
                                     @RequestParam(value = "cdmowner") String cdmowner,
                                     @RequestParam(value = "cqroperdate") String cqroperdate){

        String lsDate = new String();
        String lsTime = new String();

        if(StringUtils.isBlank(cdmno)){
            return  RtnResult.fail("查询失败:请求参数[卡号]为空 ");
        }
        if(StringUtils.isBlank(cqroper)){
            return  RtnResult.fail("查询失败:请求参数[操作人]为空 ");
        }
        if(StringUtils.isBlank(cqroperdate)){
            return  RtnResult.fail("查询失败:请求参数[操作时间]为空 ");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            simpleDateFormat.setLenient(false);
            Date date = simpleDateFormat.parse(cqroperdate);

            lsDate = cqroperdate.split(" ")[0];
            lsTime = cqroperdate.split(" ")[1];

//            LocalDate alD = LocalDate.parse(cqroperdate);
//            System.out.println("Date:" + alD);

//            LocalDateTime aLDT = LocalDateTime.parse(cqroperdate);
//            System.out.println("Date/Time:" + aLDT);

        }catch (Exception e){
            return  RtnResult.fail("查询失败:请求参数[操作时间]不合法");
        }
        if (cqroperdate.length() > 19){
            return  RtnResult.fail("查询失败:请求参数[操作时间]不合法!");
        }

        if (lsDate.length() > 10){
            return  RtnResult.fail("查询失败:请求参数[操作时间]不合法!!");
        }

        if (lsTime.length() > 8){
            return  RtnResult.fail("查询失败:请求参数[操作时间]不合法!!!");
        }

        try{
            String barcode = memberCardService.generateBarcode(cdmno,cdmowner,cqroper,cqroperdate);
            log.info("获取二维码成功: "+barcode);
            return  RtnResult.success("查询成功",barcode);
        }catch(Exception e){
            log.error("获取二维码失败: "+e);
            return RtnResult.fail("获取二维码失败: "+ e);
        }
    }
}

