package com.hl.learnmall.contoller;

import com.hl.learnmall.common.utils.ExcelUtil;
import com.hl.learnmall.mbg.modal.PmsProductLadder;
import com.hl.learnmall.service.impl.MultiyExcelSeriviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@RequestMapping("/test")
public class ExcelMultiController {
    @Autowired
    MultiyExcelSeriviceImpl multiyExcelSerivice;
    @Autowired
    ExcelUtil<PmsProductLadder> util;


@RequestMapping("/daochu")
public  void test(HttpServletResponse response) throws InterruptedException {
   //先统计行数
   int countRow=multiyExcelSerivice.getRoeNum();
   if(countRow<10000){
       //如果行数小于阈值则使用csv导出的方式
       List<PmsProductLadder> list=multiyExcelSerivice.listAllTable(0,countRow);
       String title="测试excel导出";
       String[] headers={"id","product_id","count","discount","price"};
       String[] Col={"id","productId","count","discount","price"};
       util.exportExcel(title,headers,Col,list,"",response);
   }else{



   }








}

}
