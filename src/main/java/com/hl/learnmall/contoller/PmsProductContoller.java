package com.hl.learnmall.contoller;

import com.hl.learnmall.common.api.CommonResult;
import com.hl.learnmall.common.utils.ExcelUtil;
import com.hl.learnmall.common.utils.ExportExcelUtil;
import com.hl.learnmall.common.utils.HeadUtils;
import com.hl.learnmall.mbg.modal.PmsProductFullReduction;
import com.hl.learnmall.mbg.modal.PmsProductLadder;
import com.hl.learnmall.service.CnareaService;
import com.hl.learnmall.service.PmsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Api(tags = "PmsProductContoller", description = "测试延迟加载和流加载")
@Controller
@RequestMapping("/test")
public class PmsProductContoller {
    @Autowired
    private PmsProductService pmsProductService;
    @Autowired
    private CnareaService cnareaService;
    @Autowired
    ExcelUtil<Map<String,Object>> util;
    @Autowired
    HeadUtils headUtils;
    @Autowired
    private ExportExcelUtil exportExcelUtil;
    @Value("${wordExtend}")
    protected String wordExtend;

    @Value(value="${base_product_path}")
    private String filePath;
    @Value("${imageExtend}")
    protected String imageExtend;


    @ApiOperation("测试延迟加载和流加载")
    @RequestMapping(value = "/listAll", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<List<PmsProductFullReduction>> getBrandList() {
        return CommonResult.success(pmsProductService.listAll());
    }


    @ApiOperation("文件导出功能")
    @RequestMapping(value = "/listFile", method = RequestMethod.POST)
    @ResponseBody
    public void Excel(HttpServletResponse response) {

        try {
            List<PmsProductLadder>list=pmsProductService.listAllTable();
            //  List  list=pmsProductService.listAllTable();
            exportExcelUtil.exportExcel("测试文件",headUtils.getPageHeader(),
                    headUtils.getPageClo(), list,response );
           /* util.exportExcel("测试文件",headUtils.getPageHeader(),
                    headUtils.getPageClo(), list,null,response);*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



/*    @ApiOperation("测试SQL分解")
    @RequestMapping(value = "listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Cnarea2018>> getList() {
        return CommonResult.success(cnareaService.getList());
    }*/
@ApiOperation("word上传")
@ResponseBody
@RequestMapping(value = {"/uploadWord"},method = RequestMethod.POST)
public String  uploadWord(@RequestParam("file" ) MultipartFile file) {
    String str;
    try {
        //将文件存放路径给前端
        if (file.isEmpty()) {
            str="文件为空";
            System.out.println(str);
            return str ;
        }
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        String extend = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

        if (!wordExtend.contains(extend)) {
            str="请上传word文件";
            System.out.println(str);
            return str;
        }

        String url = filePath + fileName; //这里filePath=E：ceshi/   这样生成的文件在E ceshi 文件夹下
        File file1 = new File(url);

        if (!file1.getParentFile().exists()) {
            file1.getParentFile().mkdir();
        }
        file.transferTo(file1);
        str="上传成功";
        System.out.println(str);

    } catch (Exception e) {
        e.printStackTrace();
        str="上传zz";
    }
    return str;
}
    @ApiOperation("批量上传")
    @ResponseBody
    @RequestMapping(value ={"/multifile"}, method=RequestMethod.POST)
    public String handFileUpload(HttpServletRequest httpServletRequest) throws IOException {
        List<MultipartFile> list=((MultipartHttpServletRequest) httpServletRequest).getFiles("file");
        MultipartFile multipartFile=null;
        BufferedOutputStream stream=null;

        for(int i=0;i<list.size();i++){
            multipartFile=list.get(i);
            if(!multipartFile.isEmpty()){
                String extend=multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
                if(!wordExtend.contains(extend)){
                    return "此文件非word文件，请上传word文件";
                }
                String url=filePath+multipartFile.getOriginalFilename();
                File file=new File(url);
                if(!file.getParentFile().exists()){
                    file.getParentFile().mkdir();
                }
                multipartFile.transferTo(file);
                System.out.println("第"+i+"个文件上传成功");
            }else{
                return "第"+i+"个文件为空";
            }
        }

        return  "成功";
    }





}
