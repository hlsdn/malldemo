package com.hl.learnmall.mbg.mapper;

import com.hl.learnmall.mbg.modal.Cnarea2018New;
import com.hl.learnmall.mbg.modal.Cnarea2018NewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Cnarea2018NewMapper {
    int countByExample(Cnarea2018NewExample example);

    int deleteByExample(Cnarea2018NewExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Cnarea2018New record);

    int insertSelective(Cnarea2018New record);

    List<Cnarea2018New> selectByExample(Cnarea2018NewExample example);

    Cnarea2018New selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Cnarea2018New record, @Param("example") Cnarea2018NewExample example);

    int updateByExample(@Param("record") Cnarea2018New record, @Param("example") Cnarea2018NewExample example);

    int updateByPrimaryKeySelective(Cnarea2018New record);

    int updateByPrimaryKey(Cnarea2018New record);
}