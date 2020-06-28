package com.rysh.module.activity.controller.serverController;

import com.github.pagehelper.PageInfo;
import com.rysh.api.serverControllerApi.ServerUserControllerApi;
import com.rysh.module.activity.beans.*;
import com.rysh.module.activity.service.ActivityService;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Log4j2
@RequestMapping("/server/activity")
@Api(description = "活动接口")
public class ActivityController implements ServerUserControllerApi {

    @Autowired
    private ActivityService service;

    @ApiOperation("添加活动")
    @PostMapping("/add")
    public QueryResponseResult add(@RequestBody ActivityVo activity){
        int row = service.addActivity(activity);
        if (row == 1 + activity.getContents().size() + activity.getImgs().size() ){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            log.error("===添加活动失败===");
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("删除活动")
    @DeleteMapping("/delete/{id}")
    public QueryResponseResult delete(@PathVariable String id){
        int row = service.delete(id);
        if (row == 1 ){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            log.error("===删除活动失败===");
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("编辑回显")
    @GetMapping("/info/{id}")
    public QueryResponseResult getOneById(@PathVariable String id){
        try {
            ActivityVo activityVo = service.getActivityVoById(id);
            QueryResult<ActivityVo> result = new QueryResult<>();
            result.setData(activityVo);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error("活动回显失败");
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("更新活动信息")
    @PutMapping("/update")
    public QueryResponseResult update(@RequestBody ActivityVo activityVo){
        try {
            service.update(activityVo);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            log.error("===更新活动失败===");
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("查询所有")
    @GetMapping("/all")
    public QueryResponseResult all(ParamBean paramBean,int option){
        PageInfo<ActivityVo> activityList = null;
        try {
            activityList = service.all(paramBean,option);
        } catch (Exception e) {
            log.error(e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
        QueryResult<Activity> result = new QueryResult<>();
        result.setData(activityList);
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

    @ApiOperation("查询所有未通过审核")
    @GetMapping("/uncheck")
    public QueryResponseResult uncheck(ParamBean paramBean){
        try {
            PageInfo<Activity> activityList = service.uncheckActivity(paramBean);
            QueryResult<Activity> result = new QueryResult<>();
            result.setData(activityList);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error(e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("历史记录")
    @GetMapping("/check/history")
    public QueryResponseResult getCheckHistory(ParamBean paramBean){
        PageInfo<Activity> farms = service.checkHistory(paramBean);
        QueryResult result = new QueryResult();
        result.setData(farms);
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

    @ApiOperation("一键审核通过活动")
    @PostMapping("/check/{operation}")
    public QueryResponseResult check(@PathVariable String operation, @RequestBody CheckParam checkParam){
        try {
            String login = SecurityContextHolder.getContext().getAuthentication().getName();
            service.checkActivity( checkParam.getIds(),operation,login,checkParam.getPassComment());
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
           log.error("活动审核失败"+e);
           return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("查询未确认的活动")
    @GetMapping("/unconfirmed/{id}")
    public QueryResponseResult unconfirmedActivity(ParamBean paramBean,@PathVariable String id){
        try {
            PageInfo<JoinActivityUser> resultList = service.getUserByActivityId(paramBean,id);
            QueryResult<JoinActivityUser> result = new QueryResult<>();
            result.setData(resultList);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error("获取未确认的活动失败"+e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("批量签到 【请传入活动id集合】")
    @PutMapping("/sign")
    public QueryResponseResult signMany(@RequestBody List<String> ids){
        try {
            service.signMany(ids);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            log.error("活动批量签到失败");
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("参加次数统计 【传入分页参数】")
    @PostMapping("/statistics")
    public QueryResponseResult ActivityStatistics(@RequestBody TimeParam timeParam){

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = sdf.parse(timeParam.getStartTime());
            Date endTime = sdf.parse(timeParam.getEndTime());
            System.err.println(startTime+"---"+endTime);
            PageInfo<ActivityStatistics> resultList = service.ActivityStatistics(timeParam,startTime,endTime);
            QueryResult<ActivityStatistics> result = new QueryResult<>();
            result.setData(resultList);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error("活动统计出错"+e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("活动搜索")
    @PostMapping("/search")
    public QueryResponseResult searchActivity(ParamBean paramBean){
        PageInfo<Activity> results = service.searchActivity(paramBean);
        QueryResult<Activity> result = new QueryResult<>();
        result.setData(results);
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }


    @ApiOperation("导出Excel")
    @GetMapping("/export/{id}")
    @ApiImplicitParam(name = "id",value = "活动的id")
    public void exportExcel(HttpServletResponse response,HttpServletRequest request,@PathVariable String id) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("活动报名信息表");
        ActivityVo activityVo = service.getActivityVoById(id);
        String name = activityVo.getName();

        // 设置要导出的文件的名字
        String fileName = name+".xls";

        //根据不同浏览器进行编码
        String encodeFileName = encodeFileName(fileName, request);

        // 新增数据行，并且设置单元格数据
        int rowNum = 1;

        // headers表示excel表中第一行的表头 在excel表中添加表头
        String[] headers = { "序号", "姓名", "手机", "报名时间"};

        HSSFRow row = sheet.createRow(0);
        sheet.setColumnWidth(0, 20*256);
        sheet.setColumnWidth(1, 20*256);
        sheet.setColumnWidth(2, 20*256);
        sheet.setColumnWidth(3, 20*256);

        // 创建样式
        HSSFCellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true); // 粗体
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER); // 水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER); // 垂直居中
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setWrapText(true);

        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        List<JoinActivityUser> resultList = service.getUserByActivityId(id);

        int index = 1;
        //在表中存放查询到的数据放入对应的列
        for (JoinActivityUser item : resultList) {
            HSSFRow row1 = sheet.createRow(rowNum);
            Date applyTime = item.getApplyTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String applyTimeStr = format.format(applyTime);
            row1.createCell(0).setCellValue(index);
            row1.createCell(1).setCellValue(item.getName());
            row1.createCell(2).setCellValue(item.getPhone());
            row1.createCell(3).setCellValue(applyTimeStr);
            rowNum++;
            index ++;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + encodeFileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    /**
     * @param   fileNames
     * @param   request
     * @Description: 导出文件转换文件名称编码
     */
    public static String encodeFileName(String fileNames, HttpServletRequest request) {
        String codedFilename = null;
        try {
            String agent = request.getHeader("USER-AGENT");
            if (null != agent && -1 != agent.indexOf("MSIE") || null != agent && -1 != agent.indexOf("Trident") || null != agent && -1 != agent.indexOf("Edge")) {// ie浏览器及Edge浏览器
                String name = java.net.URLEncoder.encode(fileNames, "gb2312");
                codedFilename = name;
            } else if (null != agent && -1 != agent.indexOf("Mozilla")) {
                // 火狐,Chrome等浏览器
                codedFilename = new String(fileNames.getBytes("gb2312"), "iso-8859-1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codedFilename;
    }
}

