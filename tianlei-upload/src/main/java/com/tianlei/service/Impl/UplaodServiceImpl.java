package com.tianlei.service.Impl;

import com.tianlei.controller.UploadController;
import com.tianlei.service.itef.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.List;

@Service
public class UplaodServiceImpl implements UploadService {
    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    // 支持的文件类型
    private static final List<String> suffixes = Arrays.asList("image/png", "image/jpeg");

    @Override
    public String upload(MultipartFile file) {
        try {        // 验证图片信息，以及文件类型
            String type = file.getContentType();
            if (!suffixes.contains(type)) {
                logger.info("上传失败，文件类型不匹配！{}", type);
                return null;
            }

            // 图片内容校验
            BufferedImage image = ImageIO.read(file.getInputStream());

            if (image == null) {
                logger.info("文件上传失败，文件不符合要求");
                return null;
            }

            // 保存图片，生成目录
            File dir = new File("/Users/tianlongzhang/image/tianlei_project/");

            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 开始保存照片
            file.transferTo(new File(dir, file.getOriginalFilename()));

            // 得到图片的地址
            String url = "http://image.tianlei,com/upload" + file.getOriginalFilename();

            return url;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
