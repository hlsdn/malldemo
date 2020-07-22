package com.hl.learnmall.contoller;

import com.hl.learnmall.mbg.modal.PmsProductLadder;
import com.hl.learnmall.service.impl.MultiyExcelSeriviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@RequestMapping("/test")
public class ExcelMultiController {
    @Autowired
    MultiyExcelSeriviceImpl multiyExcelSerivice;


@RequestMapping("/daochu")
public  void test(HttpServletResponse response) throws InterruptedException {
   //先统计行数
   int countRow=multiyExcelSerivice.getRoeNum();
   //创建线程
    ExecutorService service= Executors.newCachedThreadPool();
    CountDownLatch latch=new CountDownLatch(10);
    int num=countRow/10;

  for(int i=0 ;i<10;i++){
      int d=i;
      Runnable runnable=new Runnable() {
      @Override
      public void run() {
               if(d!=9){
                        List<PmsProductLadder> list=multiyExcelSerivice.listAllTable(d*num,num);
                    }else{
                        List<PmsProductLadder> list=multiyExcelSerivice.listAllTable(d*num,countRow-d*num);
                    }
                    latch.countDown();
          }
      };
   service.execute(runnable);
   //导出报表


  }
    latch.await();
    service.shutdown();





}


}
