package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 通用接口
 */
@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    // 如果要测试文件上传 只能用postman或者前后端联调
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传：{}", file);
        // 防止重名覆盖
        try {
            // 原始文件名
            String filename = file.getOriginalFilename();
            // 截取原始文件名的后缀
            String extension = filename.substring(filename.lastIndexOf("."));
            // 构造新文件名UUID
            String objectName = UUID.randomUUID().toString() + extension;

            // 文件的请求路径
            String filepath = aliOssUtil.upload(file.getBytes(), objectName);
            log.info("文件上传成功，访问路径：{}", filepath);
            return Result.success(filepath);
        } catch (IOException e) {
            log.error("文件上传失败：{}", e);
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
