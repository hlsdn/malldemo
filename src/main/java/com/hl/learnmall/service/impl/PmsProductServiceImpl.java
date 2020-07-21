package com.hl.learnmall.service.impl;

import com.hl.learnmall.mbg.mapper.PmsMemberPriceMapper;
import com.hl.learnmall.mbg.mapper.PmsProductFullReductionMapper;
import com.hl.learnmall.mbg.modal.PmsProductFullReduction;
import com.hl.learnmall.mbg.modal.PmsProductLadder;
import com.hl.learnmall.service.PmsProductService;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class PmsProductServiceImpl implements PmsProductService {
    @Autowired
   private PmsProductFullReductionMapper pmsProductFullReductionMapper;
    @Override
    public List<PmsProductFullReduction> listAll() {
 /*       final List<PmsProductFullReduction> list=new ArrayList<>();
         pmsProductFullReductionMapper.listAll(new ResultHandler<PmsProductFullReduction>(){
             @Override
             public void handleResult(ResultContext<? extends PmsProductFullReduction> resultContext) {
                 list.add(resultContext.getResultObject());
             }
         });
         return list;*/
     return pmsProductFullReductionMapper.listAll();
    }

    @Override
    public List<PmsProductLadder>listAllTable() {
       // return pmsProductFullReductionMapper.listAllTable();
        return  null;
    }


}
