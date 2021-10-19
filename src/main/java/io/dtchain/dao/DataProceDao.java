package io.dtchain.dao;

import java.util.List;

import io.dtchain.entity.DataProceTable;

public interface DataProceDao {
	
	/**
	 * 数据处理结果写入数据库工时统计表
	 * 
	 * @param list		数据处理集合
	 * @return
	 */
	public int dataProce(List<DataProceTable> list);

	/**
	 * 清空数据处理表工时统计为0的记录
	 * 
	 * @return
	 */
	public int emptyZero();

}
