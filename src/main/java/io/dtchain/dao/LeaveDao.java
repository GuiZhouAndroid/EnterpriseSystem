package io.dtchain.dao;

import java.util.List;

import io.dtchain.entity.LeaveTable;
import io.dtchain.entity.QueryLeave;

public interface LeaveDao {
	// 添加请假信息
	public int addLeave(LeaveTable leave);

	// 查询全部请假信息
	public List<LeaveTable> searchAllLeave(QueryLeave ql);

	// 查询个人请假信息
	public List<LeaveTable> searchLeave(QueryLeave ql);
}
