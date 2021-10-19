package io.dtchain.service;

import io.dtchain.utils.Result;

public interface ChartService {
	/**
	 * 获取图表数据
	 * @return
	 */
	public Result<Object> getChartData();
}
