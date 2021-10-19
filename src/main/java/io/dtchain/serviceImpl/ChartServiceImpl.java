package io.dtchain.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.dtchain.dao.ChartDao;
import io.dtchain.entity.ChartData;
import io.dtchain.service.ChartService;
import io.dtchain.utils.Result;
@Service("ChartService")
public class ChartServiceImpl implements ChartService{

	@Autowired
	private ChartDao chartDao;
	@Override
	public Result<Object> getChartData() {
		//String year=LocalDateTime.now().toLocalDate().getYear()+"";
		String year=2018+"";
		int[] late=new int[12];
		int[] earlyRetr=new int[12];
		int[] overTime=new int[12];
		int[] days=new int[12];
		List<int[]> data=new ArrayList<int[]>();
		String empName=(String)SecurityUtils.getSubject().getPrincipal();
		List<ChartData> list=chartDao.getChartData(empName,year);

		for(ChartData cd:list) {
			late[cd.getMonth()-1]=cd.getLate();
			earlyRetr[cd.getMonth()-1]=cd.getEarlyRetr();
			overTime[cd.getMonth()-1]=cd.getOverTime();
			days[cd.getMonth()-1]=cd.getDays();
		}
		data.add(late);
		data.add(earlyRetr);
		data.add(overTime);
		data.add(days);
		Result<Object> result=new Result<Object>();
		result.setData(data);
		result.setMsg("请求数据成功");
		result.setState(1);
		return result;
	}

}
