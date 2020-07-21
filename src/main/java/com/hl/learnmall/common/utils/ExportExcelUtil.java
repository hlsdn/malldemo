package com.hl.learnmall.common.utils;

import com.hl.learnmall.mbg.modal.PmsProductFullReduction;
import com.hl.learnmall.mbg.modal.PmsProductLadder;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ExportExcelUtil {
    public  void exportExcel(String title, String[] headers, String[] Col, List<PmsProductLadder> list, HttpServletResponse response) {

        try {
            //创建HSSFWorkbook对象(excel的文档对象)---工作簿
            HSSFWorkbook wb = new HSSFWorkbook();


            //建立新的sheet对象（excel的表单）
            HSSFSheet sheet = wb.createSheet("溢款报表");
          /*  //设置表格列宽度---最好为15
            sheet.setDefaultColumnWidth(15);*/
            //文件名，需要编码成ISO8859-1
            String excelName = "溢款报表.xls";
            String fileName = new String(excelName.getBytes("UTF-8"), "ISO8859-1");
            //列数
            int columnNum = headers.length;
            //对表头进行处理
            HSSFRow rowRowName = sheet.createRow(0);//建立第一行，行号rowNum=0；
            // 将列头设置到sheet的单元格中
            for (int n = 0; n < columnNum; n++) {
          /*      HSSFCell cellRowName = rowRowName.createCell(n);     //创建列头对应个数的单元格//设置列头单元格的数据类型
                HSSFRichTextString text = new HSSFRichTextString(headers[n]);
                cellRowName.setCellValue(text);   //设置列头单元格的值*/
                rowRowName.createCell(n).setCellValue(headers[n]);
            }
            //标题为rowNum=0，所以数据的rowNum从1开始
            int rowNum = 1;
            for(PmsProductLadder vo:list){
                HSSFRow row = sheet.createRow(rowNum);//新建一个行
                row.createCell(0).setCellValue(check(vo.getId()==null?"":vo.getId().toString()));
                row.createCell(1).setCellValue(check(vo.getProductId()==null?"":vo.getProductId().toString()));
                row.createCell(2).setCellValue(check(vo.getCount()==null?"":vo.getCount().toString()));
                row.createCell(3).setCellValue(check(vo.getDiscount()==null?"":vo.getDiscount().toString()));
                row.createCell(4).setCellValue(check(vo.getPrice()==null?"":vo.getPrice().toString()));
                rowNum++;
            }
            response.setContentType("APPLICATION/ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition","attachment;filename=" +  fileName +";filename*=utf-8''"+ URLEncoder.encode(fileName,"UTF-8"));


            response.flushBuffer();


            wb.write(response.getOutputStream());
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static String check(String textValue){
        // 判断值的类型后进行强制类型转换
        //处理逗号
        if (textValue.indexOf(",")>0){
            textValue = "\""+textValue+"\"";
//                            logger.info("处理逗号："+textValue);
        }
        //处理双引号
        if (textValue.indexOf("\"")>0){
            textValue = "\""+textValue.replaceAll("\"","\"\"")+"\"";
//                            logger.info("处理双引号："+textValue);
        }
        //处理日期格式科学计数法
        String date = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
        Pattern p = Pattern.compile(date);
        Matcher matcher = p.matcher(textValue);
        if (matcher.matches()){
            textValue = "\t"+textValue;
        }
        //处理数字格式科学计数法
        String number = "\"^//d+(//.//d+)?$\"";
        p = Pattern.compile(number);
        matcher = p.matcher(textValue);
        if (matcher.matches()){
            textValue = "\t"+textValue;
        }
        return  textValue;
    }

}
