package com.wstro.controller;

import com.wstro.util.R;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.Model;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * 邮件发送接收
 *
 * @author Joey
 * @Email 2434387555@qq.com
 */
@RestController
@RequestMapping("/mail")
public class MailController extends AbstractController {

    @Resource
    private JavaMailSender mailSender;

    /**
     * 发送
     */
    @RequestMapping("/send")
    public R send(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("2434387555@qq.com");// 发送者.
        message.setTo(email);// 接收者.
        message.setSubject("测试邮件（邮件主题）");// 邮件主题.
        message.setText("这是邮件内容");// 邮件内容.
        mailSender.send(message);// 发送邮件
        return R.ok();
    }

    /**
     * 发送HTML模版文件
     *
     * @throws TemplateException
     * @throws IOException
     */
    @RequestMapping("/sendTemplate")
    public R sendTemplateMail(String email, Model model, HttpServletRequest request) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("2434387555@qq.com");// 发送者.
        helper.setTo(email);// 接收者.
        helper.setSubject("模板邮件（邮件主题）");// 邮件主题.
        model.addAttribute("username", "Joey");
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        // 设定去哪里读取相应的ftl模板
        cfg.setClassForTemplateLoading(this.getClass(), "/templates");
        // 在模板文件目录中寻找名称为name的模板文件
        Template template = cfg.getTemplate("email.ftl");

        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        helper.setText(html, true);
        // 嵌入图片
        FileSystemResource file = new FileSystemResource(
                new File(request.getServletContext().getRealPath("/statics/images/kgc.jpg")));
        // testphoto和img标签的src对应cid:
        helper.addInline("testphoto", file);

        mailSender.send(mimeMessage);
        return R.ok();
    }

}
