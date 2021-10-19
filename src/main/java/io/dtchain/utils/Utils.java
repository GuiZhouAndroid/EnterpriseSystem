package io.dtchain.utils;

import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class Utils {
	/*
	 * 利用UUID算法生成主键
	 */
	public static String createId() {
		UUID uuid = UUID.randomUUID();
		String id = uuid.toString();
		return id.replace("-", "");
	}
	
	/**
	 * shiro密码加密
	 * @param userName		员工名字
	 * @param num			编号
	 * @return
	 */
	public static String Md5(String userName,String num) {
		return new SimpleHash("MD5", num,  ByteSource.Util.bytes(userName), 2).toHex();
	}
	public static void excelFormat(HSSFWorkbook wb, HSSFSheet sheet) {
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		for (int j = 1; j <= sheet.getLastRowNum(); j++) {
			HSSFRow r = sheet.getRow(j);
			if (r != null) {
				for (int l = 0; l < r.getLastCellNum(); l++) {
					HSSFCell c = r.getCell(l);
					c.setCellStyle(style);
					sheet.setColumnWidth(l, 20 * 300);
				}
			}
		}
	}
	
}
