package com.hl.learnmall.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @ClassName ZipUtils
 * @Description: TODO
 * @Author hl
 * @Date 2020/7/30
 * @Version V1.0
 **/
@Component
public class ZipUtils {

    public static String DEFAULT_DATE_PATTERN="yyyy年MM月dd日";//默认日期格式
    public static int DEFAULT_COLOUMN_WIDTH = 17;

    /**
     * 导出Excel 2007 OOXML (.xlsx)格式
     * @param headers 属性-列头
     * @param list 数据集
     * @param datePattern 日期格式，传null值则默认 年月日
     * @param colWidth 列宽 默认 至少17个字节
     * @param out 输出流
     */
    public static void exportExcelX( String []headers, List<T>list, String datePattern, int colWidth, OutputStream out) {
        if(datePattern==null) datePattern = DEFAULT_DATE_PATTERN;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(1000);//缓存
        workbook.setCompressTempFiles(true);
        //表头样式
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        Font titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 20);
        titleFont.setBoldweight((short) 700);
        titleStyle.setFont(titleFont);
        // 列头样式
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        Font headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerStyle.setFont(headerFont);
        // 单元格样式
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        Font cellFont = workbook.createFont();
        cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        cellStyle.setFont(cellFont);
        // 生成一个(带标题)表格
        SXSSFSheet sheet = workbook.createSheet();
        //设置列宽
        int minBytes = colWidth<DEFAULT_COLOUMN_WIDTH?DEFAULT_COLOUMN_WIDTH:colWidth;//至少字节数


        // 产生表格标题行,以及设置列宽
        //这里省略了列宽
        // 遍历集合数据，产生数据行
        Iterator<T> it = (Iterator<T>) list.iterator();//生成迭代器  进行迭代
        int rowIndex = 0;
        while (it.hasNext()) {
            T t=(T) it.next();

            if( rowIndex == 0){
                SXSSFRow headerRow = sheet.createRow(1); //列头 rowIndex =1
                for(int i=0;i<headers.length;i++)//将列投写入
                {
                    headerRow.createCell(i).setCellValue(headers[i]);
                    headerRow.getCell(i).setCellStyle(headerStyle);

                }
                rowIndex = 2;//数据内容从 rowIndex=2开始
            }

            SXSSFRow dataRow = sheet.createRow(rowIndex);
            for (int i = 0; i < headers.length; i++)
            {
                SXSSFCell newCell = dataRow.createCell(i);

                Class tCls = null;
                String fieldName=headers[i];
                String getMethodName = "get"
                        + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);
                System.out.println(getMethodName);
                tCls=t.getClass();
                Object o = null;
                try {
                    o = tCls.getMethod(getMethodName, new Class[]{});
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                String cellValue = "";
                if(o==null) cellValue = "";
                else if(o instanceof Date) cellValue = new SimpleDateFormat(datePattern).format(o);

                else cellValue = o.toString();
                newCell.setCellValue(cellValue);
                newCell.setCellStyle(cellStyle);
            }
            rowIndex++;
        }

        try {
            workbook.write(out);
            workbook.close();
            workbook.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  压缩单个excel文件的输出流 到zip输出流,注意zipOutputStream未关闭，需要交由调用者关闭之
     * @param zipOutputStream zip文件的输出流
     * @param excelOutputStream excel文件的输出流
     * @param excelFilename 文件名可以带目录，例如 TestDir/test1.xlsx
     */
    public static void compressFileToZipStream(ZipOutputStream zipOutputStream,
                                               ByteArrayOutputStream excelOutputStream, String excelFilename) {
        byte[] buf = new byte[1024];
        try {
            // Compress the files
            byte[] content = excelOutputStream.toByteArray();
            ByteArrayInputStream is = new ByteArrayInputStream(content);
            BufferedInputStream bis = new BufferedInputStream(is);
            // Add ZIP entry to output stream.
            zipOutputStream.putNextEntry(new ZipEntry(excelFilename));
            // Transfer bytes from the file to the ZIP file
            int len;
            while ((len = bis.read(buf)) > 0) {
                zipOutputStream.write(buf, 0, len);
            }
            // Complete the entry
            //excelOutputStream.close();//关闭excel输出流
            zipOutputStream.closeEntry();
            bis.close();
            is.close();
            // Complete the ZIP file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
