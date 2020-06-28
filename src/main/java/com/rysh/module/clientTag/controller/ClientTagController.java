package com.rysh.module.clientTag.controller;

import com.rysh.module.clientTag.beans.ClientTag;
import com.rysh.module.clientTag.service.ClientTagService;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Api(description = "移动端标签接口")
@Log4j2
@RestController
@RequestMapping(value = "/client/tag")
public class ClientTagController {
    private final SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private ClientTagService tagService;

    @ApiOperation(value = "查询所有标签",httpMethod = "POST",response = ClientTag.class)
    @PostMapping(value = "/findAllTag")
    public QueryResponseResult findAllTag(){
        try {
            List<ClientTag> clientTags=tagService.findAllTag();
            QueryResult<Object> result = new QueryResult<>();
            result.setData(clientTags);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            log.error("查询所有标签异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getMp3")
    public void getMp3(HttpServletResponse response) throws IOException {
        File file = new File("D:\\mp3\\妞儿 - Young for you.mp3");
        FileInputStream fileInputStream = new FileInputStream(file);
        ServletOutputStream outputStream = response.getOutputStream();
        int a = 0;
        byte[] buffer = new byte[1024 * 10];
        while ((a=fileInputStream.read(buffer))!=-1){
            outputStream.write(buffer,0,a);
        }
        outputStream.flush();
        outputStream.close();
        fileInputStream.close();
    }
}
