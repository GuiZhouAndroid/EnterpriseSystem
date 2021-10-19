package io.dtchain.dao;

import java.util.List;

import io.dtchain.entity.EnterTable;
import io.dtchain.entity.OutTable;
import io.dtchain.entity.QueryRecord;
import io.dtchain.entity.RecordTable;
import io.dtchain.utils.Result;

public interface RecordDao {
	
	/**
	 * 将上下班时间写入worktimetable
	 * 
	 * @param list		上下班记录集合
	 * @return
	 */
	public int workTable(List<RecordTable> list);

	/**
	 * 清空都未打卡的记录
	 * 
	 * @return
	 */
	public int emptyNull();

	/**
	 * 查询迟到早退明细
	 * 
	 * @param qr		检索信息
	 * @return
	 */
	public List<RecordTable> queryDetailed(QueryRecord qr);

	/**
	 * 查询部门早退明细
	 * 
	 * @param qr		检索信息
	 * @return
	 */
	public List<RecordTable> queryDeptDetailed(QueryRecord qr);

	/**
	 * 查询全部员工的早退明细
	 * 
	 * @param qr
	 * @return
	
	public List<RecordTable> queryAllDetailed(QueryRecord qr);
	 */
	
	
	/**
	 * 插入迟到早退记录
	 * 
	 * @param list
	 * @return
	 */
	public int detailed(List<RecordTable> list);

	/**
	 * 个人早退，迟到，加班
	 * 
	 * @param qr
	 * @return
	 */
	public List<RecordTable> queryDetailedInfo(QueryRecord qr);

	/**
	 * 查询总员工记录总数
	 * 
	 * @param qr
	 * @return
	
	public int queryAllCount(QueryRecord qr);
	 */
	
	
	/**
	 * 查询部门员工记录数
	 * 
	 * @param qr
	 * @return
	 */
	public int queryDeptCount(QueryRecord qr);

	/**
	 * 查询个人记录数
	 * 
	 * @param qr
	 * @return
	 */
	public int queryCount(QueryRecord qr);

	/**
	 * 个人分页的记录数
	 * 
	 * @param qr
	 * @return
	 */
	public List<RecordTable> queryOtherPage(QueryRecord qr);

	/**
	 * 部门分页的记录数
	 * 
	 * @param qr
	 * @return
	 */
	public List<RecordTable> queryPage(QueryRecord qr);

	/**
	 * 查询总记录用于下载
	 * 
	 * @param qr
	 * @return
	 */
	public List<RecordTable> queryAllRecord(QueryRecord qr);

	
	// public List<RecordTable> queryDeptPage(QueryRecord qr);

	/**
	 * 查询个人记录数用于下载
	 * 
	 * @param qr
	 * @return
	 */
	public List<RecordTable> queryRecord(QueryRecord qr);
}
