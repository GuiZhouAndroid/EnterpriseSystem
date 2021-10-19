package io.dtchain.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class MyExceptionHandler {
	
	
	/*
	 * 全局异常处理
	 */
	@ExceptionHandler(Exception.class)
	public ModelAndView myException(HttpServletRequest request, Throwable ex) {
		HttpStatus statueCode=getStatus(request);
		ModelAndView model=new ModelAndView();
		model.addObject("code", statueCode.value());
		model.addObject("msg", "服务器出现异常，请联系管理员");
		model.setViewName("error");
		System.out.println(ex.getMessage());
		return model;
	}
	/*
	 * 自定义异常处理
	 */
	@ExceptionHandler(CustomException.class)
	public ModelAndView customException(CustomException ex) {
		ModelAndView model=new ModelAndView();
		model.addObject("code", ex.getCode());
		model.addObject("msg", ex.getMsg());
		model.setViewName("error");
		return model;
	}
	
	private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
