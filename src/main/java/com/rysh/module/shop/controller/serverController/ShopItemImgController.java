package com.rysh.module.shop.controller.serverController;

import com.rysh.module.commonService.service.ImageUploadService;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/server/shopItemImg")

@Api(description = "自营商城图片上传接口")
public class ShopItemImgController {

    @Autowired
    private ImageUploadService imageUploadService;

    @ApiOperation("图片上传")
    @PostMapping("/upload")
    public QueryResponseResult uploadImg(MultipartFile img) {
        String uploadUrl = imageUploadService.upload(img);
        if (uploadUrl == null){
            return new QueryResponseResult(CommonCode.UPLOAD_ERROR);
        }else {
            QueryResult<String> result = new QueryResult<>();
            result.setData(uploadUrl);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }
    }
}
