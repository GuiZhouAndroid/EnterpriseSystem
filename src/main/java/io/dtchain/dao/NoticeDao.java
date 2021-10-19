package io.dtchain.dao;

import java.util.List;
import java.util.Map;

import io.dtchain.entity.Notice;

public interface NoticeDao {
	
	/**
	 * 发布公告
	 * 
	 * @param notice		公告信息
	 */
	public void saveNotice(Notice notice);

	/**
	 * 获取公告标题列表
	 * 
	 * @param map			当前页面
	 * @return
	 */
	public List<Notice> queryNoticeTitle(Map<String, Object> map);

	/**
	 * 获取公告内容
	 * 
	 * @param id			公告id
	 * @return
	 */
	public Notice queryNoticeContent(String id);

	/**
	 * 获取公告总数
	 * 
	 * @return
	 */
	public int queryNoticeCount();
	
	/**
	 * 删除公告
	 * 
	 * @param id			公告id
	 * @return
	 */
	public int delNotice(String id);
	
	/**
	 * 修改公告详情
	 * 
	 * @param notice		公告详情
	 * @return
	 */
	public int updataNotice(Notice notice);
}
