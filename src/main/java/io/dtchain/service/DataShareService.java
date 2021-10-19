package io.dtchain.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import io.dtchain.entity.DataShare;
import io.dtchain.utils.Result;

public interface DataShareService {
	/**
	 * 资料上传
	 * @param file					资料文件
	 * @param docTitle				文档标题
	 * @param docContent			文档描述
	 * @return
	 */
	public Result<Object> uploadDataFile(MultipartFile file,String docTitle,String docContent) throws Exception ;
	
	/**
	 * 文件预览
	 * @param id					文档id
	 */
	public Result<Object>  preview(String id,HttpServletRequest req,HttpServletResponse res);
	
	/**
	 * 获取个人发布文档信息
	 * @param page					当前页数
	 * @return
	 */
	public Result<List<DataShare>> getReleaseDocument(int page);
	
	/**
	 * 获取个人发布文档总数
	 * @return
	 */
	public Result<Object> getReleaseDocumentCount();
	
	/**
	 * 获取文档内容详情
	 * @param id					文档id
	 * @return
	 */
	public Result<DataShare> getDocumentContent(String id);
	
	/**
	 * 获取所有文档总数
	 * @return
	 */
	public Result<Object> getAllDocumentCount(String value,int state);
	
	/**
	 * 初始化发布文档
	 * @param page
	 * @return
	 */
	public Result<List<DataShare>> initDocument(int page,String value,int state);
	
	/**
	 * 删除文档
	 * @param id
	 * @return
	 */
	public Result<Object> delDoc(String id);
}								
