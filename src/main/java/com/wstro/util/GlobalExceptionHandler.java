package com.wstro.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理器
 * 
 * @author Joey
 * @Email 2434387555@qq.com
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	// public static final String DEFAULT_404_VIEW = "/pages/404";
	private Logger logger = LoggerFactory.getLogger(getClass());

	// 添加全局异常处理流程，根据需要设置需要处理的异常
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Object MethodArgumentNotValidHandler(HttpServletRequest request, Exception exception) throws Exception {
		R r = new R();
		try {
			if (exception instanceof RRException) {
				r.put("code", ((RRException) exception).getCode());
				r.put("msg", ((RRException) exception).getMessage());
			} else if (exception instanceof DuplicateKeyException) {
				r = R.error("数据库中已存在该记录");
			} else if (exception instanceof AuthorizationException) {
				r = R.error("没有权限，请联系管理员授权");
			} else {
				r = R.error();
			}
			// 记录异常日志
			logger.error(exception.getMessage(), exception);
		} catch (Exception e) {
			logger.error("GlobalExceptionHandler 异常处理失败", e);
		}
		return r;
	}

	// @ExceptionHandler(value = NoHandlerFoundException.class)
	// @ResponseStatus(HttpStatus.NOT_FOUND)
	// public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception
	// e) throws Exception {
	// logger.info("找不到页面...");
	// ModelAndView mav = new ModelAndView();
	// mav.addObject("exception", e);
	// mav.addObject("url", req.getRequestURL());
	// mav.setViewName(DEFAULT_404_VIEW);
	// return mav;
	// }

}