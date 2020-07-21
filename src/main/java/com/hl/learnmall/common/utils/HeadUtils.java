package com.hl.learnmall.common.utils;

import org.springframework.stereotype.Component;

@Component
public class HeadUtils {
    public  String[] getPageHeader() {
        return new String[]{"id","productId","count","discount","price"};
    }
    public   String[] getPageClo(){
        return new String[]{"id","productId","count","discount","price"};
    }
}
