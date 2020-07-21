package com.hl.learnmall.service;

import com.hl.learnmall.mbg.modal.PmsProductFullReduction;
import com.hl.learnmall.mbg.modal.PmsProductLadder;

import java.util.List;

public interface PmsProductService {

    List<PmsProductFullReduction> listAll();
    //List  listAllTable();
    List<PmsProductLadder> listAllTable();
}
