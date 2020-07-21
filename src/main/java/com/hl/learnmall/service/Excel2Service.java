package com.hl.learnmall.service;

import com.hl.learnmall.mbg.modal.TaxiGps;
import org.springframework.stereotype.Service;

import java.util.List;


public interface Excel2Service {
    List<TaxiGps> queryFromTables();
    List<TaxiGps> queryFromTables1();
}
