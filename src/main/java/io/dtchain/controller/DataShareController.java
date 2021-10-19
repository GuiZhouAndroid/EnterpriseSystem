package io.dtchain.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import io.dtchain.entity.DataShare;
import io.dtchain.service.DataShareService;
import io.dtchain.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/dataShare")
@Api(value = "/dataShare",description="资料共享")
@CrossOrigin
public class DataShareController {
	
	@Autowired
	private DataShareService dataShareService;
	
	@ApiOperation(value = "资料上传")
	@PostMapping(value = "/uploadDataFile.io")
	@ResponseBody
	public Result<Object> uploadDataFile(@ApiParam(value = "资料文件",required = true) @RequestParam(value = "file") MultipartFile file,
										 @ApiParam(value = "文档标题",required = true) @RequestParam(value = "docTitle") String docTitle,
										 @ApiParam(value = "文档描述",required = true) @RequestParam(value = "docContent") String docContent) throws Exception {
		return dataShareService.uploadDataFile(file, docTitle, docContent);
	}
	
	@ApiOperation(value = "文件预览")
	@GetMapping(value = "/preview.io")
	@ResponseBody
	public Result<Object> preview(@ApiParam(value = "id",required = true) @RequestParam(value = "id") String id,HttpServletRequest req,HttpServletResponse res) {
		 return dataShareService.preview(id,req,res);
	}
	
	@ApiOperation(value = "获取个人发布文档信息")
	@GetMapping(value = "/getReleaseDocument.io")
	@ResponseBody
	public Result<List<DataShare>> getReleaseDocument(@ApiParam(value ="当前页数",required = true) @RequestParam(value ="page") int page){
		return dataShareService.getReleaseDocument(page);
	}
	
	@ApiOperation(value = "获取个人发布文档总数")
	@GetMapping(value = "/getReleaseDocumentCount.io")
	@ResponseBody
	public Result<Object> getReleaseDocumentCount(){
		return dataShareService.getReleaseDocumentCount();
	}
	
	@ApiOperation(value = "获取发布文档内容详情")
	@GetMapping(value = "/getDocumentContent.io")
	@ResponseBody
	public Result<DataShare> getDocumentContent(@ApiParam(value = "文档id",required = true) @RequestParam(value = "id") String id){
		return dataShareService.getDocumentContent(id);
	}
	
	@ApiOperation(value = "获取所有文档总数")
	@GetMapping(value = "/getAllDocumentCount.io")
	@ResponseBody
	public Result<Object> getAllDocumentCount(@ApiParam(value = "搜索值",required = false) @RequestParam(value = "value") String value,
											  @ApiParam(value = "状态  0:初始化   1:搜索",required = true) @RequestParam(value = "state") int state){
		return dataShareService.getAllDocumentCount(value,state);
	}
	
	@ApiOperation(value = "初始化发布文档")
	@GetMapping(value = "/initDocument.io")
	@ResponseBody
	public Result<List<DataShare>> initDocument(@ApiParam(value = "当前页数",required = true) @RequestParam(value = "page") int page,
												@ApiParam(value = "搜索值",required = false) @RequestParam(value = "value") String value,
												@ApiParam(value = "状态  0:初始化   1:搜索",required = true) @RequestParam(value = "state") int state){
		return dataShareService.initDocument(page,value,state);
	}
	
	@ApiOperation(value = "删除文档")
	@DeleteMapping(value = "/delDoc.io")
	@ResponseBody
	public Result<Object> delDoc(@ApiParam(value = "文档id", required = true)  @RequestBody String id){
		return dataShareService.delDoc(id);
	}
}
