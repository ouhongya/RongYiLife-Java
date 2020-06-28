package com.rysh.module.farm.controller.serverController;

import com.rysh.module.commonService.service.ImageUploadService;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/server/farmitemimg")

@Api(description = "农场商品图片上传接口")
public class FarmItemImgController {

    @Autowired
    private ImageUploadService imageUploadService;

    @ApiOperation("农场图片上传")
    @PostMapping("/upload")
    public QueryResponseResult uploadImg(@RequestParam MultipartFile file) {
        String url  = imageUploadService.upload(file);
        if (url == null){
            return new QueryResponseResult(CommonCode.UPLOAD_ERROR);
        }else {
            QueryResult<String> result = new QueryResult<>();
            result.setData(url);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }
    }

}
