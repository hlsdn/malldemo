package com.hl.learnmall.mbg.mapper;

import com.hl.learnmall.mbg.modal.PmsProductFullReduction;
import com.hl.learnmall.mbg.modal.TaxiGps;
import com.hl.learnmall.mbg.modal.TaxiGpsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.ResultHandler;

public interface TaxiGpsMapper {
    int countByExample(TaxiGpsExample example);

    int deleteByExample(TaxiGpsExample example);

    int insert(TaxiGps record);

    int insertSelective(TaxiGps record);

    List<TaxiGps> selectByExample(TaxiGpsExample example);

    int updateByExampleSelective(@Param("record") TaxiGps record, @Param("example") TaxiGpsExample example);

    int updateByExample(@Param("record") TaxiGps record, @Param("example") TaxiGpsExample example);

    void select (ResultHandler<TaxiGps> handler);
    List<TaxiGps> select1 ();
    int CountRow();
}