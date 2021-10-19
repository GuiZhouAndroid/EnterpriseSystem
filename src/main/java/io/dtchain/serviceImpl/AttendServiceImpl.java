package io.dtchain.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import io.dtchain.dao.AttendDao;
import io.dtchain.dao.RecordDao;
import io.dtchain.dao.UpLoadDao;
import io.dtchain.entity.DataProceTable;
import io.dtchain.entity.QueryRecord;
import io.dtchain.entity.RecordTable;
import io.dtchain.entity.ResultProce;
import io.dtchain.service.AttendService;
import io.dtchain.utils.Result;
import io.dtchain.utils.Utils;

@Service("AttendService")
public class AttendServiceImpl implements AttendService {
	@Resource
	private AttendDao attendDao;
	@Resource
	private UpLoadDao uploadDao;
	@Resource
	private RecordDao recordDao;

	private String[] rpHead = { "姓名", "部门", "上班天数", "小时数", "迟到", "早退", "加班", "总工时" };
	private String[] dbHead = { "姓名", "部门", "小时数", "迟到", "早退", "加班", "日期", "星期" };
	private String[] rtHead = { "姓名", "日期", "早上上班", "中午下班", "下午上班", "晚上下班", "部门" };

	/**
	 * 查询工时统计表
	 */
	public Result<List<ResultProce>> statistic(QueryRecord qr) {
		Result<List<ResultProce>> result = new Result<List<ResultProce>>();
		List<DataProceTable> list = new ArrayList<DataProceTable>();
		List<ResultProce> rp = new ArrayList<ResultProce>();
		if (qr.getEmpName() == "") {
			// 查询部门
			list = attendDao.searchDeptProce(qr);
			rp = proce(list);
		} else {
			// 查询个人
			list = attendDao.searchProce(qr);
			rp = proce(list);
		}
		if (!rp.isEmpty()) {
			result.setData(rp);
			result.setMsg("查询成功");
			result.setState(1);
		} else {
			result.setMsg("没有查询到该数据");
			result.setState(0);
		}
		return result;
	}

	/**
	 * 处理员工工时统计表
	 */
	private List<ResultProce> proce(List<DataProceTable> list) {
		List<ResultProce> rpList = new ArrayList<ResultProce>();
		ResultProce rp;
		DataProceTable dp1;
		DataProceTable dp2;
		for (int i = list.size() - 1; i >= 0;) {
			int index = 1;
			rp = new ResultProce();
			dp1 = list.get(i);
			float days = dp1.getDays();
			int hours = dp1.getHours();
			int late = dp1.getLate();
			int earlyRetr = dp1.getEarlyRetr();
			int overTime = dp1.getOverTime();
			for (int j = list.size() - 2; j >= 0; j--) {
				dp2 = list.get(j);
				// 名字相同,日期不同
				if (dp1.getEmpName().equals(dp2.getEmpName()) && !dp1.getDates().equals(dp2.getDates())) {
					days += dp2.getDays();
					hours += dp2.getHours();
					late += dp2.getLate();
					earlyRetr += dp2.getEarlyRetr();
					overTime += dp2.getOverTime();
					index++;
					list.remove(j);
				}
			}
			rp.setDays(days);
			rp.setDept(dp1.getDept());
			rp.setEarlyRetr(earlyRetr);
			rp.setEmpName(dp1.getEmpName());
			rp.setHours(hours);
			rp.setLate(late);
			rp.setOverTime(overTime);
			rpList.add(rp);
			i -= index;
		}
		return rpList;
	}

	/**
	 * 导出工时统计
	 * 
	 * @throws Exception
	 */
	@Override
	public HSSFWorkbook download(QueryRecord qr) throws Exception {
		List<DataProceTable> rpList = new ArrayList<DataProceTable>();
		List<ResultProce> rp = new ArrayList<ResultProce>();
		List<ResultProce> dbWeek = new ArrayList<ResultProce>();
		List<RecordTable> rtList = new ArrayList<RecordTable>();
		// 工时统计
		if (qr.getEmpName() == "") {
			// 查询部门
			rpList = attendDao.searchDeptProce(qr);
			rp = proce(rpList); // 工时统计
			dbWeek = uploadDao.queryDeptWeekInfo(qr); // 双休
			rtList = recordDao.queryDeptDetailed(qr); // 迟到早退
		} else {
			// 查询个人
			rpList = attendDao.searchProce(qr);
			rp = proce(rpList);
			dbWeek = uploadDao.queryWeekInfo(qr);
			rtList = recordDao.queryDetailed(qr);
		}
		HSSFWorkbook wb = new HSSFWorkbook(); // 工作蒲
		HSSFSheet sheet = wb.createSheet("token"); // 工作表
		HSSFRow row = sheet.createRow((int) 0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框

		for (int i = 0; i < rpHead.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(rpHead[i]);
			cell.setCellStyle(style);
			sheet.setColumnWidth(i, 20 * 256);
		}
		for (int i = 0; i < rp.size(); i++) {
			row = sheet.createRow(i + 1);
			ResultProce r = rp.get(i);

			row.createCell(0).setCellValue(r.getEmpName());
			row.createCell(1).setCellValue(r.getDept());
			row.createCell(2).setCellValue(r.getDays());
			row.createCell(3).setCellValue(r.getHours());
			row.createCell(4).setCellValue(r.getLate());
			row.createCell(5).setCellValue(r.getEarlyRetr());
			row.createCell(6).setCellValue(r.getOverTime());
			row.createCell(7).setCellValue(r.getHours() + r.getOverTime());
		}
		row = sheet.createRow(rp.size() + 1);
		for (int i = 0; i < dbHead.length; i++) {

			HSSFCell cell = row.createCell(i);
			cell.setCellValue(dbHead[i]);
			cell.setCellStyle(style);
			sheet.setColumnWidth(i, 20 * 256);
		}
		for (int i = rp.size() + 1, k = 0; k < dbWeek.size(); i++, k++) {
			row = sheet.createRow(i + 1);
			ResultProce r = dbWeek.get(k);

			row.createCell(0).setCellValue(r.getEmpName());
			row.createCell(1).setCellValue(r.getDept());
			row.createCell(2).setCellValue(r.getHours());
			row.createCell(3).setCellValue(r.getLate());
			row.createCell(4).setCellValue(r.getEarlyRetr());
			row.createCell(5).setCellValue(r.getOverTime());
			row.createCell(6).setCellValue(r.getDates());

			Calendar cale = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			cale.setTime(sdf.parse(r.getDates()));
			if ((cale.get(Calendar.DAY_OF_WEEK) - 1) == 0) {
				row.createCell(7).setCellValue("星期日");
			}
			if (cale.get(Calendar.DAY_OF_WEEK) - 1 == 6) {
				row.createCell(7).setCellValue("星期六");
			}
		}
		row = sheet.createRow(rp.size() + dbWeek.size() + 2);
		for (int i = 0; i < rtHead.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(rtHead[i]);
			cell.setCellStyle(style);
			sheet.setColumnWidth(i, 20 * 256);
		}
		for (int i = (rp.size() + dbWeek.size() + 2), k = 0; k < rtList.size(); i++, k++) {
			row = sheet.createRow(i + 1);
			RecordTable r = rtList.get(k);

			row.createCell(0).setCellValue(r.getEmpName());
			row.createCell(1).setCellValue(r.getDates());
			row.createCell(2).setCellValue(r.getWorkMorn());
			row.createCell(3).setCellValue(r.getAtNoon());
			row.createCell(4).setCellValue(r.getWorkAfter());
			row.createCell(5).setCellValue(r.getAtNight());
			row.createCell(6).setCellValue(r.getDept());
		}
		Utils.excelFormat(wb, sheet);
		return wb;
	}
}
