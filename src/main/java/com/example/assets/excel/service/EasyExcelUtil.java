package com.example.assets.excel.service;

/**
 * TODO
 *
 * @author GengTianMing
 * @since 2023/12/29 10:27
 **/
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

public class EasyExcelUtil {
    public static <T> void exportExcel(List<T> list, HttpServletResponse response, String sheetName, Class<T> clz){
        try(OutputStream fileOutputStream = response.getOutputStream()){
            //文件以流形式返回前端下载
            response.setHeader("Content-Disposition", "attachment;filename=" + sheetName + ".xlsx");
            response.setContentType("application/x-download");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
            response.flushBuffer();
            ExcelWriter excelWriter = EasyExcel.write(fileOutputStream,clz)
                    .registerWriteHandler(new CustomCellWriteUtil())
                    .registerWriteHandler(getStyleStrategy())
                    .build();
            WriteSheet sheet = EasyExcel.writerSheet(0, "导出数据").build();
            excelWriter.write(list, sheet);
            excelWriter.finish();
            fileOutputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置excel样式
     *
     * @return
     */
    public static HorizontalCellStyleStrategy getStyleStrategy() {
        // 头的策略  样式调整
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 头背景 浅绿
        headWriteCellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        WriteFont headWriteFont = new WriteFont();
        // 头字号
        headWriteFont.setFontHeightInPoints((short) 12);
        // 字体样式
        headWriteFont.setFontName("宋体");
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 自动换行
        headWriteCellStyle.setWrapped(true);
        // 设置细边框
        headWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        headWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        headWriteCellStyle.setBorderRight(BorderStyle.THIN);
        headWriteCellStyle.setBorderTop(BorderStyle.THIN);
        // 设置边框颜色 25灰度
        headWriteCellStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headWriteCellStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headWriteCellStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headWriteCellStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        // 水平对齐方式
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 垂直对齐方式
        headWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 内容的策略 宋体
        WriteCellStyle contentStyle = new WriteCellStyle();
        // 设置垂直居中
        contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置 水平居中
        contentStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        WriteFont contentWriteFont = new WriteFont();
        // 内容字号
        contentWriteFont.setFontHeightInPoints((short) 12);
        // 字体样式
        contentWriteFont.setFontName("宋体");
        contentStyle.setWriteFont(contentWriteFont);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentStyle);
    }
}

