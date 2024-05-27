package com.example.assets.base.controller;

import cn.dev33.satoken.util.SaResult;
import com.example.assets.base.service.TassetService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO
 *
 * @author GengTianMing
 * @since 2023/02/15 14:27
 **/
@RestController
@RequestMapping("/Tasset")
public class ApiTasset {
    @Resource
    private TassetService tassetService;

    @RequestMapping("transferData")
    public SaResult transferData(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String startDate = dateFormat.format(new Date());

        Integer resultcnt = tassetService.transferData();
        String endDate = dateFormat.format(new Date());
        return SaResult.data("转存：" + resultcnt + " 用时：" + startDate + " -- " + endDate);
    }
}
