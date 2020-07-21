package com.hl.learnmall.contoller;

import com.hl.learnmall.common.utils.ExcelUtils;
import com.hl.learnmall.mbg.modal.Cnarea2018New;
import com.hl.learnmall.mbg.modal.TaxiGps;
import com.hl.learnmall.service.Excel2Service;
import io.swagger.annotations.Api;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


   @Api(tags = "Excel2Controller", description = "测试excel")
   @Controller
    @RequestMapping("/excel")
    public class Excel2Controller {
   @Autowired
   Excel2Service excel2Service;
    @RequestMapping(value="/excel2",method = RequestMethod.GET)
    public void testExcel2(HttpServletResponse response) throws IOException {
        //查询出需要导出的数据
        List<TaxiGps> lisx=excel2Service.queryFromTables();
       // List<TaxiGps> lisx=excel2Service.queryFromTables1();
        //创建报表数据头
        List<String> head = new ArrayList<>();
        head.add("1");
        head.add("2");
        head.add("3");
        head.add("4");
        head.add("5");
        head.add("6");
        head.add("7");
        head.add("8");
        head.add("9");
        //创建报表体
        List<List<String>> body = new ArrayList<>();
        for (TaxiGps stu : lisx) {
            List<String> bodyValue = new ArrayList<>();
            bodyValue.add(String.valueOf(stu.getId()));
            bodyValue.add(String.valueOf(stu.getEvent()));
            bodyValue.add(String.valueOf(stu.getGpsStatus()));
            bodyValue.add(String.valueOf(stu.getDirection()));
            bodyValue.add(String.valueOf(stu.getLat()));
            bodyValue.add(String.valueOf(stu.getLng()));
            //将数据添加到报表体中
            body.add(bodyValue);
        }
        String fileName = "学生信息统计.xls";
        HSSFWorkbook excel = ExcelUtils.expExcel(head,body);
        response.setContentType("application/octet-stream");
        response.setHeader("content-disposition", "attachment;filename=" + new
                String(fileName.getBytes("ISO8859-1")));
        response.setHeader("filename", fileName);
        excel.write(response.getOutputStream());

    }

}

