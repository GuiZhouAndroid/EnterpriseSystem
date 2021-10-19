package io.dtchain.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import io.dtchain.entity.ChartData;

public interface ChartDao {
	public List<ChartData> getChartData(@Param(value = "empName") String empName,@Param(value = "year") String year);
}
