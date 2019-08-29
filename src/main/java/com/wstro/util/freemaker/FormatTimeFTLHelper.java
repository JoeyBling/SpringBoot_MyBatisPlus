package com.wstro.util.freemaker;

import com.wstro.util.DateUtils;
import com.wstro.util.JoeyUtil;
import freemarker.core.Environment;
import freemarker.template.*;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * Freemaker格式化时间戳为指定日期
 * 使用:[#-- 自定义标签格式化时间戳 ?c防止时间戳有,逗号 --]
 * [@formatTime unix="${entity.createTime?c}" pattern="yyyy-MM-dd HH:mm:ss"] [/@formatTime]
 *
 * @author Joey
 * @Email 2434387555@qq.com
 */
public class FormatTimeFTLHelper implements TemplateDirectiveModel {

    @Override
    public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body) throws TemplateException, IOException {
        if (body == null) {// 自定义标签必须有内容，即自定义 开始标签与结束标签之间必须有 内容
            throw new TemplateModelException("自定义标签必须有内容，即自定义开始标签与结束标签之间必须有内容");
        }
        Writer out = env.getOut();
        TemplateScalarModel scalarModel = (TemplateScalarModel) params.get("unix");
        if (null == scalarModel) {
            return;
        }
        TemplateScalarModel pattern = (TemplateScalarModel) params.get("pattern");
        String asString;
        if (pattern == null) {
            asString = DateUtils.DATE_TIME_PATTERN;
        } else {
            asString = pattern.getAsString();
        }
        long flag = Long.valueOf(scalarModel.getAsString());
        out.write(DateUtils.format(JoeyUtil.fomartDate(flag * 1000), asString));
        body.render(out);
    }

}
