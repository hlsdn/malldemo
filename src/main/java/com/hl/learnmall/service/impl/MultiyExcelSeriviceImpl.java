package com.hl.learnmall.service.impl;

import com.hl.learnmall.mbg.mapper.PmsProductFullReductionMapper;
import com.hl.learnmall.mbg.mapper.TaxiGpsMapper;
import com.hl.learnmall.mbg.modal.PmsProductLadder;
import com.hl.learnmall.service.MultiyExcelSerivice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MultiyExcelSeriviceImpl implements MultiyExcelSerivice {

    @Autowired
    PmsProductFullReductionMapper pmsProductFullReductionMapper;

    @Override
    public int getRoeNum() {
        return pmsProductFullReductionMapper.CountRow() ;
    }

    @Override
    public List<PmsProductLadder> listAllTable(int l1, int l2) {
        return pmsProductFullReductionMapper.listAllTable(l1,l2);
    }
}
