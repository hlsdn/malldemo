package com.hl.learnmall.common.utils;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ExcelUtil<T> {

    /**
     * @param title   表格标题名
     * @param headers 表格属性列名数组 （第一行标题）
     * @param Col     需要显示的表格属性列名数组 如果是javabean 必须和字段名字一直 如果为Map 必须为Map的key名字对应
     * @param list    需要显示的数据集合,集合泛型支持两种，1：符合javabean风格的类的对象 2：Map类型。此方法支持的
     *                javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     */
    public  void exportExcel(String title, String[] headers, String[] Col, List<T> list, String pattern, HttpServletResponse response) {
        //
        try {

            title = title + ".csv";
            //设置下载弹出框
            response.setContentType("application/csv;charset=gbk");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(title, "UTF-8"));
        /*    response.setHeader("Content-Disposition", "attachment;filename=" + new String (title.getBytes(), "UTF-8"));*/
            //新建打印输出对象
            PrintWriter out = response.getWriter();
            StringBuffer headSb = new StringBuffer();
            for (int i = 0; i < headers.length; i++) {//将行的名字放在StringBuffer中
                if (i == headers.length-1) {
                    headSb.append(headers[i]);
                } else {
                    headSb.append(headers[i] + ",");//不是最后一个后面要加，
                }
        }
        out.write(headSb.toString() + "\n");//最后一个加\n


            // 遍历集合数据，产生数据行
            Iterator<T> it = (Iterator<T>) list.iterator();
            StringBuffer sb = new StringBuffer();
            while (it.hasNext()) {
                sb.setLength(0);
                T t = (T) it.next();
                String[] fields = Col;//根据列来取值
                for (short i = 0; i < fields.length; i++) {
                    String fieldName = fields[i];
                    try {
                        Object value = "";
                        Class tCls = null;
                        Map map = null;
                        if (t instanceof Map) {//判断是否为Map类型
                            map = (Map) t;
                            value = map.get(fieldName);
                        } else {
                            String getMethodName = "get"
                                    + fieldName.substring(0, 1).toUpperCase()
                                    + fieldName.substring(1);
                            System.out.println(getMethodName);
                            //Object value = "";
                            //Class tCls = null;
                            tCls = t.getClass();
                            Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                            value = getMethod.invoke(t, new Object[]{});
                        }
                        if (value == null) {
                            value = "";
                        }
                        // 判断值的类型后进行强制类型转换
                        //处理逗号
                        String textValue = value.toString();
                        if (textValue.indexOf(",")>0){
                            textValue = "\""+textValue+"\"";
//                            logger.info("处理逗号："+textValue);
                        }
                        //处理双引号
                        if (textValue.indexOf("\"")>0){
                            textValue = "\""+textValue.replaceAll("\"","\"\"")+"\"";
//                            logger.info("处理双引号："+textValue);
                        }
                        //处理日期格式科学计数法
                        String date = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
                        Pattern p = Pattern.compile(date);
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()){
                            textValue = "\t"+textValue;
                        }
                        //处理数字格式科学计数法
                        String number = "\"^//d+(//.//d+)?$\"";
                        p = Pattern.compile(number);
                        matcher = p.matcher(textValue);
                        if (matcher.matches()){
                            textValue = "\t"+textValue;
                        }
                        if (i == fields.length-1) {
                            sb.append(textValue);
                        } else {
                            sb.append(textValue + ",");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                out.write(sb.toString() + "\n");
            }
        } catch (Exception e) {
             e.toString();
        }
    }
}
