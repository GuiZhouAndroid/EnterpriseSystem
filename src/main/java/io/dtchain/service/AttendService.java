package io.dtchain.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import io.dtchain.entity.QueryRecord;
import io.dtchain.entity.ResultProce;
import io.dtchain.utils.Result;

public interface AttendService
{
	/**
	 * 查询公式统计表
	 * 
	 * @param 			检索考勤记录信息
	 * @return
	 */
	public Result<List<ResultProce>> statistic(QueryRecord qr);
	
	/**
	 * 导出工时统计
	 */
	public HSSFWorkbook download(QueryRecord qr) throws Exception;
}
