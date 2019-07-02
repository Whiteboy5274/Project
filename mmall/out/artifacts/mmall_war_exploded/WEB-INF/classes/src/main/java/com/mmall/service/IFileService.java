package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件处理服务
 * Created by Administrator on 2019/6/26.
 */
public interface IFileService {

    String upload(MultipartFile file, String path);
}
