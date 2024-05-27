package com.example.assets.excel.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.AbstractCellStyleStrategy;
import com.example.assets.excel.entity.ExcelData;
import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;
import java.util.List;

public class EasyExcelTest {
    public static void main(String[] args) {
        // 设置写入文件的地址和名称
        String filename = "E:\\write.xlsx";

        // 创建一个Workbook对象
        Workbook workbook = (Workbook) EasyExcel.write(filename, ExcelData.class).build();
//        Workbook workbook = EasyExcel.write(filename, ExcelData.class).build();


        // 创建一个样式并设置字体样式
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 12);
        headWriteFont.setColor(IndexedColors.BLACK.getIndex());
        headWriteCellStyle.setWriteFont(headWriteFont);

        // 将样式添加到Workbook中
        int headStyleIndex = workbook.createCellStyle().getIndex();
        CellStyle headCellStyle = workbook.getCellStyleAt(headStyleIndex);
        headCellStyle.cloneStyleFrom(workbook.createCellStyle());
        headCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headCellStyle.setBorderTop(BorderStyle.THIN);
        headCellStyle.setBorderBottom(BorderStyle.THIN);
        headCellStyle.setBorderLeft(BorderStyle.THIN);
        headCellStyle.setBorderRight(BorderStyle.THIN);
        headCellStyle.setIndention((short) 0);
        headCellStyle.setRotation((short) 0);
        headCellStyle.setHidden(false);
        headCellStyle.setShrinkToFit(false);
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setColor(IndexedColors.BLACK.getIndex());
        headCellStyle.setFont(font);

        // 创建内容样式
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontHeightInPoints((short) 12);
        contentWriteFont.setColor(IndexedColors.BLACK.getIndex());
        contentWriteCellStyle.setWriteFont(contentWriteFont);

        // 将样式添加到Workbook中
        int contentStyleIndex = workbook.createCellStyle().getIndex();
        CellStyle contentCellStyle = workbook.getCellStyleAt(contentStyleIndex);
        contentCellStyle.cloneStyleFrom(workbook.createCellStyle());
        contentCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        contentCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        contentCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        contentCellStyle.setBorderTop(BorderStyle.THIN);
        contentCellStyle.setBorderBottom(BorderStyle.THIN);
        contentCellStyle.setBorderLeft(BorderStyle.THIN);
        contentCellStyle.setBorderRight(BorderStyle.THIN);
        contentCellStyle.setIndention((short) 0);
        contentCellStyle.setRotation((short) 0);
        contentCellStyle.setHidden(false);
        contentCellStyle.setShrinkToFit(false);
        Font contentFont = workbook.createFont();
        contentFont.setFontHeightInPoints((short) 12);
        contentFont.setColor(IndexedColors.BLACK.getIndex());
        contentCellStyle.setFont(contentFont);


        // 调用EasyExcel的write方法
        EasyExcel.write(filename, ExcelData.class)
                // 设置工作表名
                .sheet("stuname")
                // 设置样式
                .registerWriteHandler(new CellStyleTableHandler(headWriteCellStyle, contentWriteCellStyle))
                // 执行写操作，传入数据源
                .doWrite(getData());
    }

    private static List<ExcelData> getData() {
        List<ExcelData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ExcelData ed = new ExcelData();
            ed.setSno(i);
            ed.setSname("lsr" + i);
            list.add(ed);
        }
        return list;
    }

    private static class CellStyleTableHandler extends AbstractCellStyleStrategy {
        private WriteCellStyle headWriteCellStyle;
        private WriteCellStyle contentWriteCellStyle;

        public CellStyleTableHandler(WriteCellStyle headWriteCellStyle, WriteCellStyle contentWriteCellStyle) {
            this.headWriteCellStyle = headWriteCellStyle;
            this.contentWriteCellStyle = contentWriteCellStyle;
        }

        public WriteCellStyle getHeadCellStyle() {
            return headWriteCellStyle;
        }

        public WriteCellStyle getContentCellStyle() {
            return contentWriteCellStyle;
        }
    }
}