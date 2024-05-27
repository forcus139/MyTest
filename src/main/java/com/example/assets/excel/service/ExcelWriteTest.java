package com.example.assets.excel.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.AbstractCellStyleStrategy;
import com.example.assets.excel.entity.ExcelData;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author GengTianMing
 * @since 2023/12/29 08:54
 **/
@Component
public class ExcelWriteTest {
    public static void main(String[] args) {
        // 设置写入文件的地址和名称
        String filename = "E:\\write.xlsx";

        // 调用EasyExcel的write方法
        EasyExcel.write(filename, ExcelData.class)
                // 设置工作表名
                .sheet("stuname")
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
    public void exportExcel(HttpServletResponse response) throws IOException {
        List<ExcelData> excelData = new ArrayList<>();

        // 设置写入文件的地址和名称
        String filename = "E:\\LimitPromotion.xlsx";

        // 创建一个Workbook对象
        Workbook workbook = (Workbook) EasyExcel.write(filename, ExcelData.class).build();

        // 创建一个样式并设置单元格宽度
        WriteCellStyle writeCellStyle = new WriteCellStyle();
        writeCellStyle.setWrapped(true); // 自动换行
//        writeCellStyle.setColWidth(25); // 设置宽度为25个字符

        // 创建字体样式
        WriteFont writeFont = new WriteFont();
        writeFont.setFontHeightInPoints((short) 12);
        writeFont.setColor(IndexedColors.BLACK.getIndex());
        writeCellStyle.setWriteFont(writeFont);

        // 将样式添加到Workbook中
        int styleIndex = workbook.createCellStyle().getIndex();
        CellStyle cellStyle = workbook.getCellStyleAt(styleIndex);
        cellStyle.cloneStyleFrom(workbook.createCellStyle());
        cellStyle.setWrapText(true);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setIndention((short) 0);
        cellStyle.setRotation((short) 0);
        cellStyle.setHidden(false);
        cellStyle.setShrinkToFit(false);
//        cellStyle.setFont(writeFont.getFont());

        try (OutputStream outputStream = response.getOutputStream()) {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            response.setHeader("Content-disposition", String.format("attachment;filename=%s.xlsx", URLEncoder.encode("LimitPromotion", "UTF-8")));
            EasyExcel.write(outputStream, ExcelData.class)
                    .sheet("LimitPromotionGoods")
//                    .registerWriteHandler(new CellStyleTableHandler(styleIndex))
                    .doWrite(excelData);
        } catch (Exception e) {
            throw new RuntimeException("导出失败");
        }
    }

    private static class CellStyleTableHandler extends AbstractCellStyleStrategy {
        private CellStyle cellStyle;

        public CellStyleTableHandler(CellStyle cellStyle) {
            this.cellStyle = cellStyle;
        }

//        @Override
        public WriteCellStyle getHeadCellStyle() {
            WriteCellStyle writeCellStyle = new WriteCellStyle();
//            writeCellStyle.setCellStyles(cellStyle);
            return writeCellStyle;
        }

//        @Override
        public WriteCellStyle getContentCellStyle() {
            WriteCellStyle writeCellStyle = new WriteCellStyle();
//            writeCellStyle.setCellStyles(cellStyle);
            return writeCellStyle;
        }
    }
}
