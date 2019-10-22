package com.yfr.upload.service.impl;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.yfr.upload.controller.UploadController;
import com.yfr.upload.service.UploadService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UploadServiceImpl implements UploadService {

    //log4j的日志打印类
    private static final Logger logger= LoggerFactory.getLogger(UploadController.class);

    //上传图片的fastClient类
    @Autowired
    private FastFileStorageClient storageClient;
    /**
     * 上传图片
     * @return
     */
    @Override
    public String uploadImage(MultipartFile file) {

        try {
            //设置文件类型白名单
            List<String>type=new ArrayList<>();
            type.add("image/jpeg");
            type.add("image/png");
            type.add("image/bmp");

            //判断文件类型
            if(!type.contains(file.getContentType())){
                logger.info("文件类型异常");
                return null;
            }

            //校验图片内容
            BufferedImage image= ImageIO.read(file.getInputStream());
            if(image==null){
                logger.info("图片内容异常");
                return null;
            }

            //获取文件名
            String originalFilename = file.getOriginalFilename();
            //获取文件拓展名
            String exeNmae= StringUtils.substringAfterLast(originalFilename, ".");
            //上传图片
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), exeNmae, null);
            //获取图片在storage服务器中的路径
            String fullPath = storePath.getFullPath();

            //保存文件
//            File f=new File("D://upload");
//            if(!f.exists()){
//                f.mkdir();
//            }
//            file.transferTo(new File(f,originalFilename));


            //获取文件的保存地址
            String url="http://image.leyou.com/"+fullPath;
            //返回文件的保存地址
            return url;
        }

        catch (IOException e) {
            return null;
        }


    }
}
