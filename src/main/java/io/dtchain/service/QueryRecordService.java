package io.dtchain.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import io.dtchain.entity.QueryRecord;
import io.dtchain.entity.RecordTable;
import io.dtchain.utils.Result;

public interface QueryRecordService {


	/**
	 * 查询迟到早退加班明细
	 * 
	 * @param qr			检索信息
	 * @return
	 */
	public Result<List<RecordTable>> queryDetailedInfo(QueryRecord qr);

	/**
	 * 查询记录总数
	 * 
	 * @param qr			检索信息
	 * @return
	 */
	public Result<Object> quertCount(QueryRecord qr);

	/**
	 * 获取分页记录 			检索信息
	 * 
	 * @param qr
	 * @return
	 */
	public Result<List<RecordTable>> queryOtherPage(QueryRecord qr);

	/**
	 * 下载excel表格
	 * 
	 * @param qr			检索信息
	 * @return
	 */
	public HSSFWorkbook download(QueryRecord qr);

}
