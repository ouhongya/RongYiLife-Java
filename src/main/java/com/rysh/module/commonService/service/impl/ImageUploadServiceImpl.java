package com.rysh.module.commonService.service.impl;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.rysh.module.commonService.service.ImageUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Log4j2
@Api(description = "公共图片上传服务")
@Service
public class ImageUploadServiceImpl implements ImageUploadService {

    @Value("${result.accessKeyId}")
    private String accessKeyIdValue;

    @Value("${result.accessKeySecret}")
    private String accessKeySecretValue;

    @Value("${result.bucket}")
    private String bucket;

    @Value("${result.folder}")
    private String folderName;

    /**
     * 图片上传服务(公共服务)
     * @param file
     * @return java.lang.String
     * @author Hsiang Sun
     * @date 2019/9/11 17:49
     */
    @ApiOperation("图片上传之后返回图片URL")
    public String upload(@RequestParam("file") MultipartFile file) {

        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000);// socket超时，默认15秒
        conf.setMaxConnections(8);// 最大并发请求书，默认8个
        conf.setMaxErrorRetry(2);// 失败后最大重试次数

        if (file.isEmpty()) {
            return null;
        }
        String imgName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        OSS ossClient = null;
        String URL = null;
        try {
            InputStream inputStream = file.getInputStream();
            /*  **************Aliyun OSS**********************/
            String endpoint = "http://oss-cn-chengdu.aliyuncs.com";
            String accessKeyId = accessKeyIdValue;
            String accessKeySecret = accessKeySecretValue;
            String bucketName = bucket;
            //上传到哪个文件夹
            String folder = folderName;
            String fileName = folder + uuid + imgName;
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret,conf);
            ossClient.putObject(bucketName, fileName, inputStream);
            URL = "https://"+bucketName+".oss-cn-chengdu.aliyuncs.com/" + fileName;
            ossClient.shutdown();
            //https://remote.qingwaw.com/
        } catch (IOException e) {
            log.error("==图片上传时出现错误==",e);
            return null;
        } finally {
            ossClient.shutdown();
        }
        return URL;
    }
}
