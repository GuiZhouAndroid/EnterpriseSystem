package io.dtchain.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import io.dtchain.entity.DataShare;

public interface DataShareDao {
	/**
	 * 上传文档资料
	 * @param ds
	 */
	public void uploadDataFile(DataShare ds);
	
	/**
	 * 获取文档名字
	 * @param id
	 * @return
	 */
	public String getDocumentNameById(String id);
	
	/**
	 * 获取个人发布文档信息
	 * @param map
	 * @return
	 */
	public List<DataShare> getReleaseDocument(Map<String,Object> map);
	
	/**
	 * 获取个人发布文档总数
	 * @param userId
	 * @return
	 */
	public int getReleaseDocumentCount(@Param(value = "userId")String userId);
	
	/**
	 * 获取文档内容详情
	 * @param id
	 * @return
	 */
	public DataShare getDocumentContent(String id);
	
	/**
	 * 获取所有文档总数
	 * @return
	 */
	public int getAllDocumentCount();
	
	/**
	 * 初始化
	 * @param begin
	 * @return
	 */
	public List<DataShare> initDocument(int begin);
	
	/**
	 * 根据搜索条件查询文档总数
	 * @param value
	 * @return
	 */
	public int getSearshDocumentCount(String value);
	
	/**
	 * 根据搜索条件查询文档
	 * @param map
	 * @return
	 */
	public List<DataShare> getSearchDocument(Map<String,Object> map);
	
	/**
	 * 删除文档
	 * @param map
	 * @return
	 */
	public int delDoc(Map<String,Object> map);
	
	/**
	 * 通过id查询后缀
	 * @param id
	 * @return
	 */
	public String getSuffixById(String id);
}
