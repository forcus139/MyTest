package com.example.assets.excel.controller;

import com.example.assets.business.entiity.TestUser;
import com.example.assets.excel.service.EasyExcelUtil;
import com.example.assets.excel.service.ExcelWriteTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @author GengTianMing
 * @since 2023/12/29 09:12
 **/
@RestController
@RequestMapping("/Excel")
public class ExcelTest {

    @Resource
    private ExcelWriteTest exportExcel;

    /**
     * 导出促销活动的活动商品信息
     *
     * @return 活动商品信息列表
     */
    @GetMapping("/EasyExcel")
    public void excelPromotionProductList(HttpServletResponse response) throws IOException{
        List<TestUser> userList = new ArrayList<>();
        TestUser testUser1 = new TestUser("101", "张三", "pw001", "M", "138001", 20, 70.00, new Date());
        TestUser testUser2 = new TestUser("102", "赵四", "pw002", "M", "138002", 21, 73.00, new Date());
        TestUser testUser3 = new TestUser("103", "王五", "pw003", "M", "138003", 25, 72.00, new Date());
        TestUser testUser4 = new TestUser("104", "刘六", "pw004", "F", "138003", 27, 72.00, new Date());
        TestUser testUser5 = new TestUser("105", "钱七", "pw005", "F", "138003", 20, 80.00, new Date());
        userList.add(testUser1);
        userList.add(testUser2);
        userList.add(testUser3);
        userList.add(testUser4);
        userList.add(testUser5);

        EasyExcelUtil.exportExcel(userList, response, "123", TestUser.class);
    }


}
