package io.dtchain.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import io.dtchain.entity.Notice;
import io.dtchain.utils.Result;

public interface NoticeService {
	
	/**
	 * 公告内容中上传图片
	 * 
	 * @param file				上传图片
	 * @return
	 * @throws Exception
	 */
	public String uploadImage(MultipartFile file) throws Exception;

	/**
	 * 发布公告
	 * 
	 * @param notice			公告信息
	 * @return
	 */
	public String noticeContent(Notice notice);

	/**
	 * 获取公告标题列表
	 * 
	 * @param page				当前页数
	 * @return
	 */
	public Result<List<Notice>> queryNoticeTitle(Integer page);

	/**
	 * 查询公告内容
	 * 
	 * @param id				公告id
	 * @return
	 */
	public Result<Notice> queryNoticeContent(String id);

	/**
	 * 获取公告总数
	 * 
	 * @return
	 */
	public Result<Object> queryNoticeCount();
	
	/**
	 * 删除公告
	 * 
	 * @param id				公告id
	 * @return
	 */
	public Result<Object> delNotice(String id);
	
	/**
	 * 修改公告详情
	 * 
	 * @param notice			公告详情
	 * @return
	 */
	public Result<Object> updateNotice(Notice notice);
}
