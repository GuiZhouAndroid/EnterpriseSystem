package io.dtchain.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import io.dtchain.dao.RecordDao;
import io.dtchain.entity.QueryRecord;
import io.dtchain.entity.RecordTable;
import io.dtchain.service.QueryRecordService;
import io.dtchain.utils.Result;
import io.dtchain.utils.Utils;

@Service("queryRecordService")
public class QueryRecordServiceImpl implements QueryRecordService {
	@Resource
	private RecordDao recordDao;
	private String[] excelHead = { "姓名", "部门", "日期", "早上上班", "中午下班", "下午上班", "晚上下班" };

	/*
	 * 查询迟到早退，加班信息明细
	 * 
	 */
	public Result<List<RecordTable>> queryDetailedInfo(QueryRecord qr) {
		Result<List<RecordTable>> result = new Result<List<RecordTable>>();
		List<RecordTable> list = new ArrayList<RecordTable>();
		list = recordDao.queryDetailedInfo(qr);
		if (!list.isEmpty()) {
			result.setData(list);
			result.setMsg("查询成功");
			result.setState(1);
		} else {
			result.setMsg("查询失败");
		}
		return result;
	}

	public Result<Object> quertCount(QueryRecord qr) {
		Result<Object> result = new Result<Object>();
		int n = 0;
		 if (qr.getEmpName() == "") { // 按部门搜索
			n = recordDao.queryDeptCount(qr);
		}else {
			n = recordDao.queryCount(qr); // 搜索个人
		}
		result.setData(n);
		return result;
	}

	public Result<List<RecordTable>> queryOtherPage(QueryRecord qr) {
		if (qr.getPage() <= 0) {
			qr.setPage(1);
		}
		int page = (qr.getPage() - 1) * 10;
		qr.setPage(page);
		Result<List<RecordTable>> result = new Result<List<RecordTable>>();
		List<RecordTable> list = new ArrayList<RecordTable>();
		if (qr.getEmpName().equals("")) {

			list = recordDao.queryPage(qr);
		} else {
			list = recordDao.queryOtherPage(qr);
		}
		result.setData(list);
		result.setState(1);
		return result;
	}

	public HSSFWorkbook download(QueryRecord qr) {
		List<RecordTable> list = new ArrayList<RecordTable>();
		if (qr.getEmpName().equals("")) {
			list = recordDao.queryAllRecord(qr);
		} else {
			list = recordDao.queryRecord(qr);
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

		for (int i = 0; i < excelHead.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(excelHead[i]);
			cell.setCellStyle(style);
			sheet.setColumnWidth(i, 20 * 256);
		}
		for (int i = 0, k = 0; i < list.size(); i++, k++) {

			row = sheet.createRow(k + 1);
			RecordTable record = list.get(i);

			row.createCell(0).setCellValue(record.getEmpName());
			row.createCell(1).setCellValue(record.getDept());
			row.createCell(2).setCellValue(record.getDates());
			row.createCell(3).setCellValue(record.getWorkMorn());
			row.createCell(4).setCellValue(record.getAtNoon());
			row.createCell(5).setCellValue(record.getWorkAfter());
			row.createCell(6).setCellValue(record.getAtNight());

		}
		Utils.excelFormat(wb, sheet);
		return wb;
	}


}
