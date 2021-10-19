package io.dtchain.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import io.dtchain.service.UpLoadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value="/File")
@Api(value = "/File", description = "导入excel考勤记录相关接口")
@CrossOrigin
public class UpLoadController
{
	@Resource
	private UpLoadService upLoadService;
	
	
	@ApiOperation(value = "上传excel文件")
	@PostMapping(value="/upload.io")
	public void upLoad(@ApiParam(value = "上传excel", required = true) @RequestParam(value = "file") MultipartFile file,HttpServletRequest req,HttpServletResponse res)throws Exception{
		upLoadService.upLoad(file,req, res);
	}	
}
