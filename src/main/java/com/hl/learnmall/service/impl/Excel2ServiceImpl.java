package com.hl.learnmall.service.impl;

import com.hl.learnmall.mbg.mapper.TaxiGpsMapper;
import com.hl.learnmall.mbg.modal.PmsProductFullReduction;
import com.hl.learnmall.mbg.modal.TaxiGps;
import com.hl.learnmall.service.Excel2Service;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Excel2ServiceImpl implements Excel2Service {
    @Autowired
    TaxiGpsMapper taxiGpsMapper;


    @Override
    public List<TaxiGps> queryFromTables() {

        final List<TaxiGps> list=new ArrayList<>();
        taxiGpsMapper.select( new ResultHandler<TaxiGps>(){
            @Override
            public void handleResult(ResultContext<? extends TaxiGps> resultContext) {
                list.add(resultContext.getResultObject());
            }
        });
        return list;

    }

    @Override
    public List<TaxiGps> queryFromTables1() {
        return taxiGpsMapper.select1();
    }
}
