package com.hl.learnmall.service;

import com.hl.learnmall.mbg.modal.PmsProductLadder;

import java.util.ArrayList;
import java.util.List;

public interface MultiyExcelSerivice {
    int getRoeNum();
    List<PmsProductLadder> listAllTable(int l1, int l2);

}
