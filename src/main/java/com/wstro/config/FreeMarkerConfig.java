package com.wstro.config;

import com.jagregory.shiro.freemarker.ShiroTags;
import com.wstro.util.freemaker.FormatTimeFTLHelper;
import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Freemaker
 *
 * @author Joey
 * @Email 2434387555@qq.com
 */
@org.springframework.context.annotation.Configuration
public class FreeMarkerConfig {

    @Value("${seo.author}")
    private String author;

    @Value("${seo.keywords}")
    private String keywords;

    @Value("${seo.description}")
    private String description;

    @Resource
    private Configuration configuration;
    @Resource
    protected org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver resolver;
    @Resource
    protected org.springframework.web.servlet.view.InternalResourceViewResolver springResolver;

    @PostConstruct
    public void setSharedVariable() throws TemplateModelException {
        configuration.setSharedVariable("author", author);
        configuration.setSharedVariable("keywords", keywords);
        configuration.setSharedVariable("description", description);
        configuration.setTagSyntax(freemarker.template.Configuration.AUTO_DETECT_TAG_SYNTAX);
        configuration.setSharedVariable("formatTime", new FormatTimeFTLHelper());
        configuration.setSharedVariable("shiro", new ShiroTags());
    }

    @PostConstruct
    public void freeMarkerConfigurer() {
    }

}
