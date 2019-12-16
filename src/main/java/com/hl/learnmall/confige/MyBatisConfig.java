package com.hl.learnmall.confige;

/**
 * @ClassName MyBatisConfig
 * @Description: TODO
 * @Author hl
 * @Date 2019/12/11
 * @Version V1.0
 **/

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 * Created by macro on 2019/4/8.
 */
@Configuration
@MapperScan({"com.hl.learnmall.mbg.mapper","com.hl.learnmall.dao"})
public class MyBatisConfig {
}