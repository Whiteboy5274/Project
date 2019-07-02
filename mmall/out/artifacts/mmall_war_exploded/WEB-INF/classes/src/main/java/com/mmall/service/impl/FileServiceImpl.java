package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传实现类
 * Created by Administrator on 2019/6/26.
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService {

    //声明日志
    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    //返回上传以后的文件名
    //path 上传路径
    public String upload(MultipartFile file,String path){
        String fileName = file.getOriginalFilename();
        //获取扩展名
        //abc.jpg，从后往前查，查到第一个"."，返回的是".jpg"，然后+1把"."去掉
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        //上传文件的名字
        //A:abc.jpg
        //B:abc.jpg,用UUID解决
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}",fileName,path,uploadFileName);

        //声明目录file
        File fileDir = new File(path);
        //判断文件目录是否存在，如果不存在，就创建文件目录
        if(!fileDir.exists()){
            //创建之前先赋予权限，可写
            //打个比方，启动Tomcat的用户权限，不一定会有在tomcat webAPP下发布文件夹的权限，因为发布完以后，我也可以改
            fileDir.setWritable(true);
            //mkdir() 当前这个级别
            //mkdirs() 如果我们上传的文件所在的文件夹是/a、/b、/c，然后直接传到服务器上
            fileDir.mkdirs();
        }
        //创建文件
        File targetFile = new File(path,uploadFileName);


        try {
            file.transferTo(targetFile);
            //文件已经上传成功了

            //将targetFile上传到我们的FTP服务器上
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //已经上传到ftp服务器上

            //上传完之后，删除upload下面的文件，防止tomcat下文件过多过大
            targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件异常", e);
            return null;
        }
        return targetFile.getName();

    }

}
