package com.wstro.controller;

import com.wstro.util.*;
import com.wstro.util.Constant.UploadType;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;

/**
 * 文件上传下载控制器
 *
 * @author Joey
 * @Email 2434387555@qq.com
 */
@Controller
@RequestMapping("/file")
// @Scope("prototype")
public class SysFileController extends AbstractController {

    /**
     * 上传文件保存的路径
     */
    protected String uploadPath;

    /**
     * 存放路径上下文
     */
    protected String fileContextPath;

    /**
     * 上传文件类型
     */
    protected String fileType;

    /**
     * 上传文件名称
     */
    protected String fileName;

    /**
     * 文件上传(上传后返回保存的相对路径)
     *
     * @param flag       上传文件的name属性
     * @param uploadType 上传文件类型(不同保存的文件夹就不同)
     * @param request    HttpServletRequest
     * @return Map
     * @throws Exception
     */
    // 此处没有做权限验证
    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public R upload(Integer uploadType, HttpServletRequest request) throws Exception {
        uploadPath = constant.uploadPath; // 上传文件保存的路径
        fileContextPath = constant.fileContextPath; // 存放路径上下文
        MultipartHttpServletRequest multipartRequest = null;
        if (ServletFileUpload.isMultipartContent(request)) { // 判断request是否有文件上传
            multipartRequest = (MultipartHttpServletRequest) request;
        } else {
            return R.error("请先选择上传的文件");
        }
        Iterator<String> ite = multipartRequest.getFileNames();
        while (ite.hasNext()) {
            MultipartFile file = multipartRequest.getFile(ite.next());
            if (file == null)
                return R.error("上传文件为空"); // 判断上传的文件是否为空
            uploadPath = request.getServletContext().getRealPath(uploadPath) + File.separator;
            fileName = file.getOriginalFilename();
            logger.info("上传的文件原名称:" + fileName);
            // 上传文件类型
            fileType = fileName.indexOf(".") != -1
                    ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;

            logger.info("上传文件类型:" + fileType);
            // 自定义的文件名称
            String trueFileName = getTrueFileName(fileName, uploadType);
            fileContextPath += File.separator + trueFileName;
            // 防止火狐等浏览器不显示图片
            fileContextPath = fileContextPath.replace("\\", "/");
            uploadPath += trueFileName;
            File fileUpload = new File(uploadPath); // 上传文件后的保存路径

            // 创建父级目录
            FileUtil.createParentPath(fileUpload);

            logger.info("存放文件的路径:" + uploadPath);
            file.transferTo(fileUpload);
            // 进行文件处理
            fileHandle(fileUpload);
            break; // 这里暂时只能上传一个文件
        }
        return R.ok().put("filePath", fileContextPath);
    }

    /**
     * 进行文件处理
     *
     * @param file File
     * @throws IOException
     */
    private void fileHandle(File file) throws IOException {
        try {
            BufferedImage bufreader = ImageIO.read(file);
            int width = bufreader.getWidth();
            if (width > 800) {
                logger.info("进行图片压缩处理...");
                Thumbnails.of(file).height(800).toFile(file);
                logger.info("图片压缩处理完毕...");
            }
        } catch (Exception e) {
            logger.info("上传的文件不是图片");
        }
    }

    /**
     * 自定义上传文件的名称
     *
     * @param file_Name  上传文件的名称
     * @param uploadType 上传文件类型(不同保存的文件夹就不同)
     * @return String
     */
    private String getTrueFileName(String file_Name, Integer uploadType) {
        StringBuffer bf = new StringBuffer();
        if (null == uploadType) {
        } else if (uploadType == UploadType.adminAvatar.getValue()) {
            bf.append("adminAvatar" + File.separator);
        } else if (uploadType == UploadType.other.getValue()) {
            bf.append("other" + File.separator);
        } else {
        }
        return bf.append(DateUtils.format(new Date(), Constant.uploadSavePathFormat) + File.separator
                + String.valueOf(System.currentTimeMillis()) + fileName).toString();
    }

    /**
     * 下载文件
     *
     * @param fileName 文件路径
     * @param real     是否是绝对路径(如果为True就不转换)
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException
     */
    @RequestMapping("/download")
    public void download(@RequestParam("name") String fileName, Boolean real, HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        if (null == fileName) {
            throw new RRException("未找到资源");
        }
        request.setCharacterEncoding("UTF-8");
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        fileName = URLDecoder.decode(fileName, "UTF-8");
        logger.info("下载文件的名称" + fileName);
        if (null == real || !real) {
            fileName = request.getServletContext().getRealPath(fileName);
        }
        logger.info("下载文件的绝对路径" + fileName);
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            // 获取文件的长度
            long fileLength = file.length();

            // 设置文件输出类型
            try {
                response.setContentType("application/octet-stream");
                String name = file.getName();
                if (null == real || !real) {
                    name = name.length() > 13 ? name.substring(13) : name;
                }
                response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(name, "utf-8"));
                // 设置输出长度
                response.setHeader("Content-Length", String.valueOf(fileLength));
                // 获取输入流
                bis = new BufferedInputStream(new FileInputStream(file));
                // 输出流
                bos = new BufferedOutputStream(response.getOutputStream());
                byte[] buff = new byte[2048];
                int bytesRead;
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
                // 关闭流
            } catch (Exception e) {
                throw new RRException("下载错误,请重试!");
            } finally {
                if (null != bis) {
                    bis.close();
                }
                if (null != bos) {
                    bos.close();
                }
            }
        } else {
            throw new RRException("未找到资源");
        }
    }
}
