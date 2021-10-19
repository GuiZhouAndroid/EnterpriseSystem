package io.dtchain.serviceImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import io.dtchain.dao.DataProceDao;
import io.dtchain.dao.MangageDao;
import io.dtchain.dao.RecordDao;
import io.dtchain.dao.ResourceDao;
import io.dtchain.dao.UpLoadDao;
import io.dtchain.entity.AttendTable;
import io.dtchain.entity.DataProceTable;
import io.dtchain.entity.EnterTable;
import io.dtchain.entity.OutTable;
import io.dtchain.entity.QueryRecord;
import io.dtchain.entity.RecordTable;
import io.dtchain.entity.ResultProce;
import io.dtchain.service.UpLoadService;
import io.dtchain.utils.Result;

@Service("UpLoadService")
public class UpLoadServiceImpl implements UpLoadService {
	@Autowired
	private UpLoadDao uploadDao;
	@Autowired
	private RecordDao recordDao;
	@Autowired
	private MangageDao mangageDao;
	@Autowired
	private DataProceDao dataProceDao;
	@Autowired
	private ResourceDao resourcesDao;
	private Map<String, String> map = new HashMap<String, String>();
	private Map<String, String> overMap = new HashMap<String, String>();// 储存加班信息
	private Map<Integer, String> dataNum = new HashMap<Integer, String>();

	/**
	 * 上传文件
	 */
	public void upLoad(MultipartFile file,HttpServletRequest req, HttpServletResponse res) throws Exception {
		String userId = (String) SecurityUtils.getSubject().getSession().getAttribute("userSessionId");

		// 从数据库获取，该权限
		List<String> list = resourcesDao.queryResourceName(userId);
		if (list == null || list.size() < 1 || !list.contains("/dataImport")) {
			req.getSession().setAttribute("state", -1);
			res.sendRedirect("../index");
			return;
		}

		String savePath = ResourceUtils.getURL("src\\main\\resources\\upload").getPath();
		savePath = savePath.substring(1).replace('/', '\\');
		String name = file.getOriginalFilename();
		String fileName = savePath + name;
		File fileSave=new File(fileName);
		String dateS=req.getParameter("dateS").replace('-', '/');
		String dateE=req.getParameter("dateE").replace('-', '/');
		String fileExtName = name.substring(name.lastIndexOf(".") + 1);
		if(null!=dateS && null!=dateE && !"".equals(dateS) && !"".equals(dateE) && (fileExtName.equals("xlsx") || fileExtName.equals("xltx"))) {
			file.transferTo(fileSave);
			System.out.println("文件上传成功");
			List<Map<String, Object>> listNum = mangageDao.queryNum();
			if (!listNum.isEmpty()) {
				for (Map<String, Object> m : listNum) {
					dataNum.put(Integer.parseInt(m.get("empNum").toString()), m.get("empName").toString());

				}
			}
			//插入考勤记录
			insertAttendRecord(fileName, dateS, dateE);
			// 读取完删除文件
			if (fileSave.exists()) {
				fileSave.delete();
			}
			res.sendRedirect("../index");
			return;
		}else {
			res.sendRedirect("../dataImport");
			return;
		}
		
	}

	/**
	 * 插入考勤记录到数据库
	 */
	private void insertAttendRecord(String realfileName, String dateS, String dateE) throws ParseException {
		try {
			// 用于存储原始数据
			List<AttendTable> list = new ArrayList<AttendTable>();
			// 用于存储进门信息
			List<AttendTable> enter = new ArrayList<AttendTable>();
			// 用于存储出门信息
			List<AttendTable> out = new ArrayList<AttendTable>();
			AttendTable record;
			// 获取到工作薄
			XSSFWorkbook workbook = new XSSFWorkbook(realfileName);
			
			
			
			int num = workbook.getNumberOfSheets(); // 获取工作表格数
			XSSFSheet sheet;
			int rowNum = 0;
			for (int i = 0; i < num; i++) {
				sheet = workbook.getSheetAt(i); // 获取第i个工作表格
				rowNum = sheet.getLastRowNum(); // 获取最后一行行号，即行数
	
				SimpleDateFormat sdf = null;
				Cell[] cell = new Cell[7];
				for (int j = 1; j <= rowNum; j++) { // 循环取行
					Row row = sheet.getRow(j);
					if (row != null) {
						record = new AttendTable();
						for (int l = 0; l < row.getLastCellNum(); l++) {
							cell[l] = row.getCell(l);

						}

						// 封装类值
						record.setUserName(judge(cellNull(cell[0]))); // 用户名
						record.setEmpNum((int) cell[1].getNumericCellValue()); // 工号
						record.setCardNum(judge(cellNull(cell[2])));// 卡号
						sdf = new SimpleDateFormat("yyyy/MM/dd");
						String cale = sdf.format(cell[3].getDateCellValue());
						record.setDates(cale);// 日期
						sdf = new SimpleDateFormat("HH:mm:ss");
						String cale1 = sdf.format(cell[4].getDateCellValue());
						record.setTimes(cale1); // 时间
						record.setDirection(cellNull(cell[5])); // 进出门
						record.setSourceEvent(((int) cell[6].getNumericCellValue())); // 事件码

						if (dataNum.get((int)cell[1].getNumericCellValue()) != null) {
						
							record.setUserName(dataNum.get((int) cell[1].getNumericCellValue()));
						} else {
							continue;
						}

						//限定上传指定日期范围
						if (dateS.compareTo(cale) <= 0 && dateE.compareTo(cale) >= 0) {
							if (cellNull(cell[5]).equals("进门")) {
						
								enter.add(record);
							} else {
							
								out.add(record);
							}
							// 添加到集合
							list.add(record);

						}

					}
					
		
					// 批量处理数据写入数据库，每次200条记录
					if ((list.size() + 1) % 201 == 0) {
			
						uploadDao.insertAttendRecord(list);
						list.clear();
					}
				}
			}

			if (list.size() != 0) {
				uploadDao.insertAttendRecord(list);
			}

			
			workbook.close();
			// 判断上下班时间信息
			List<EnterTable> enterList = new ArrayList<EnterTable>();
			List<OutTable> outList = new ArrayList<OutTable>();
			if (!enter.isEmpty()) {
				enterList = workTime(enter);
			}
			if (!out.isEmpty()) {
				outList = offWork(out);
			}
			// 调用搜索日期相同，进行数据合并
			searchDate(enterList, outList);

		} catch (FileNotFoundException e) {
			System.out.println("文件获取异常");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 过滤逗号 
	 */
	private String judge(String userName) {
		int n = userName.indexOf("'");
		String str;
		if (n == 0) {
			str = userName.substring(1);
		} else {
			str = userName.substring(0);
		}
		return str;
	}

	/**
	 * 判断cell 是否为空值
	 */
	private String cellNull(Cell cell) {
		if (cell == null) {
			return "";
		} else {
			return cell.getStringCellValue();
		}

	}

	/**
	 * 通过进门打卡信息判断上班时间信息
	 */
	private List<EnterTable> workTime(List<AttendTable> enter) {
		List<EnterTable> enterList = new ArrayList<EnterTable>();

		/*
		 * 进门表 日期相同，早上上班 -12：0：0取时间最小 (若无即早上没上班) 下午上班
		 * 12：0：0-14：0：0取时间最大（若无则取大于14：0：0的时间最小）
		 * 
		 */

		// 非空，执行进门时间判断
		Time t1 = Time.valueOf("11:59:59");
		Time t2 = Time.valueOf("13:59:59");
		Time t3 = Time.valueOf("7:59:59");
		Time t4 = Time.valueOf("15:00:01");
		EnterTable en;
		for (int i = enter.size() - 1; i >= 0;) {
			// 用于更新i值
			int index = 1;
			AttendTable att = enter.get(i);
			en = new EnterTable();
			en.setDates(att.getDates());
			en.setEmpName(att.getUserName());
			en.setMinTime(att.getTimes());
			// 早上上班8:00:00-12:00:00
			if (Time.valueOf(att.getTimes()).before(t1) && Time.valueOf(att.getTimes()).after(t3)) {
				en.setWorkMorn(att.getTimes());
			} else if (Time.valueOf(att.getTimes()).after(t2)) { // 否则下午上班
																	// 14：00：00-
				en.setWorkAfter(att.getTimes());
			}
			StringBuilder str = new StringBuilder();
			// 12:00:0-14:00:00
			if (Time.valueOf(att.getTimes()).after(t1) && Time.valueOf(att.getTimes()).before(t2)) {
				str.append(att.getTimes());
			} else {
				str.append("12:00:00");
			}

			for (int j = enter.size() - 2; j >= 0; j--) {
				AttendTable a = enter.get(j);
				// 判断日期是否是同一天
				if (att.getDates().equals(a.getDates()) && att.getUserName().equals(a.getUserName())) {

					index++;
					// 8:00:00-12:00:00
					if (Time.valueOf(a.getTimes()).before(t1) && Time.valueOf(a.getTimes()).after(t3)) {
						// 8:00:00-12:00:00的时间最小值
						if (en.getWorkMorn() != null
								&& Time.valueOf(en.getWorkMorn()).after(Time.valueOf(a.getTimes()))) {
							en.setWorkMorn(a.getTimes());
						} else if (en.getWorkMorn() == null) {
							en.setWorkMorn(a.getTimes());
						}
					} else if (Time.valueOf(a.getTimes()).after(t1) && Time.valueOf(a.getTimes()).before(t2)) {// 12:0:0-14:0:0
						// 12:0:0-14:00:00的时间最大值
						if (Time.valueOf(str.toString()).before(Time.valueOf(a.getTimes()))) {
							str.delete(0, str.length());
							str.append(a.getTimes());
						}
						// 12:00:00-14:00:00的时间最小用于判断下班时间
						if (Time.valueOf(en.getMinTime()).after(Time.valueOf(a.getTimes()))) {
							en.setMinTime(a.getTimes());
						}
					} else { // 14:0:00之后
						// 不为空，14:00:00-15:00:00取时间最小
						if (en.getWorkAfter() != null && Time.valueOf(a.getTimes()).after(t2)
								&& Time.valueOf(a.getTimes()).before(t4)
								&& Time.valueOf(en.getWorkAfter()).after(Time.valueOf(a.getTimes()))) { // -------
							en.setWorkAfter(a.getTimes());
							// 为空且时间在14：00：0-15：00：00
						} else if (en.getWorkAfter() == null && Time.valueOf(a.getTimes()).after(t2)
								&& Time.valueOf(a.getTimes()).before(t4)) {
							en.setWorkAfter(a.getTimes());
						}
					}
					// 判断完成，删除，防止重复判断
					enter.remove(j);
				}
			} // 防止和初始值相同
			if (!str.toString().equals("12:00:00")) {
				//
				if (!str.toString().equals(en.getWorkAfter()) && en.getWorkAfter() != null) {
					en.setWorkAfter(str.toString() + " " + en.getWorkAfter());
				} else {
					en.setWorkAfter(str.toString());
				}
			} // 判断结束，添加到集合
			enterList.add(en);
			// 移除最外层元素
			enter.remove(enter.size() - 1);
			// 更新i值（即减去所移除的次数）
			i -= index;
		}
		return enterList;
	}

	/**
	 * 通过出门打卡信息判断下班时间
	 */
	private List<OutTable> offWork(List<AttendTable> out) {
		/*
		 * 出门表 中午下班8:0:0-12:0:0取时间最大值 (若无则 取12:0:0-14：0：0最小值) 晚上下班 14：0：0-最大值
		 */
		Time t1 = Time.valueOf("11:59:59");
		Time t2 = Time.valueOf("8:00:00");
		Time t3 = Time.valueOf("13:59:59");
		String time = "8:00:00";
		List<OutTable> outlist = new ArrayList<OutTable>();
		OutTable en;
		for (int i = out.size() - 1; i >= 0;) {
			int index = 1;
			AttendTable att = out.get(i);
			en = new OutTable();
			en.setEmpName(att.getUserName());
			en.setDates(att.getDates());

			// 判断昨晚加班00：00：00-05:00:00时间

			if (Time.valueOf(att.getTimes()).after(Time.valueOf("00:00:00"))
					&& Time.valueOf(att.getTimes()).before(Time.valueOf("05:00:00"))) {
				en.setOverTime(att.getTimes());

			} else {
				en.setOverTime("00:00:00");
			}
			// 12:0:0-14:0:0 的一个初始值
			if (Time.valueOf(att.getTimes()).after(t1) && Time.valueOf(att.getTimes()).before(t3)) {
				en.setAtNoon(att.getTimes());
				en.setMaxTime(att.getTimes());
			} else if (Time.valueOf(att.getTimes()).after(t3)) { // 14:0:0-的一个初始值
				en.setAtNight(att.getTimes());
				en.setMaxTime("12:00:00");
			}

			StringBuilder str = new StringBuilder();
			// att>12:00:00
			if (Time.valueOf(att.getTimes()).after(t1)) {
				str.append(time);
			} else {
				str.append(att.getTimes());

			}
			for (int j = out.size() - 2; j >= 0; j--) {
				AttendTable a = out.get(j);
				// 判断名字和日期是否相同
				if (att.getDates().equals(a.getDates()) && att.getUserName().equals(a.getUserName())) {
					index++;
					// 8:00:00-12:00:00
					if (Time.valueOf(a.getTimes()).after(t2) && Time.valueOf(a.getTimes()).before(t1)) {
						// 8:00:00-12:00:00的时间最大值
						if (Time.valueOf(str.toString()).before(Time.valueOf(a.getTimes()))) {
							str.delete(0, str.length());
							str.append(a.getTimes());
						}
					} else if (Time.valueOf(a.getTimes()).after(t1) && Time.valueOf(a.getTimes()).before(t3)) { // 12:0:0-14:0:0
						// 12:00:00-14:00:00的时间最小值
						if (en.getAtNoon() != null && Time.valueOf(en.getAtNoon()).after(Time.valueOf(a.getTimes()))) {
							en.setAtNoon(a.getTimes());
						} else if (en.getAtNoon() == null) {
							en.setAtNoon(a.getTimes());
						}

						// 12:00:00-14:00:00最大时间，用于判断上班时间
						if (en.getMaxTime() != null && a.getTimes() != null)
							if (Time.valueOf(en.getMaxTime()).before(Time.valueOf(a.getTimes()))) {
								en.setMaxTime(a.getTimes());
							}
					} else { // 14:00:00- 且14：00：00之后的最大值
						if (en.getAtNight() != null && Time.valueOf(a.getTimes()).after(t3)
								&& Time.valueOf(a.getTimes()).after(Time.valueOf(en.getAtNight()))) {
							en.setAtNight(a.getTimes());
						} else if (en.getAtNight() == null) {
							en.setAtNight(a.getTimes());
						}
					}
					// 加班到凌晨
					if (Time.valueOf(a.getTimes()).after(Time.valueOf(en.getOverTime()))
							&& Time.valueOf(a.getTimes()).before(Time.valueOf("05:00:01"))) {
						en.setOverTime(a.getTimes());
					}
					out.remove(j);
				}
			}
			if (!str.toString().equals(time)) {
				if (en.getAtNoon() != null) {
					en.setAtNoon(str.toString() + " " + en.getAtNoon());
				} else if (Time.valueOf(str.toString()).after(Time.valueOf("11:00:00"))) {
					en.setAtNoon(str.toString());
				} else {
					en.setAtNoon("未打卡");
				}

			}

			outlist.add(en);
			out.remove(out.size() - 1);
			i -= index;
		}
		return outlist;
	}

	/**
	 * 查找相同的打卡日期
	 */
	private void searchDate(List<EnterTable> enter, List<OutTable> out) throws ParseException {
		List<RecordTable> list = new ArrayList<RecordTable>();
		// 按照日期排序（降序）
		// Collections.sort(out);
		// 加班标识符
		// int over=0;
		// int index=0;
		// 用于存储考勤记录
		RecordTable rt;
		EnterTable en;
		OutTable ou;
		if (!enter.isEmpty() && !out.isEmpty()) {
			for (int i = enter.size() - 1; i >= 0; i--) {
				// 标识符用于判断是否删除最外层循环的集合元素
				boolean flog = false;
				en = enter.get(i);
				rt = new RecordTable();
				for (int j = out.size() - 1; j >= 0; j--) {
					ou = out.get(j); // 0 1 2 3 4 5 6 7 7
					// 进门，出门的名字和日期相同进行合并
					if (en.getEmpName().equals(ou.getEmpName()) && en.getDates().equals(ou.getDates())) {
						flog = true;
						// index=j;
						/*
						 * 进门 获取早上上班时间如果为null,即为未打卡 获取下午上班时间如果只有一个即为上班时间,如果是两个即取最小值
						 * 
						 * 出门 获取中午下班时间如果为null即为未打卡,如果获取到一个极为下班时间(如果下午上班时间为null, 即未出公司设置为null)
						 * 如果只为两个即取最大值,
						 */
						/*
						 * 对数据进行逻辑判别
						 */
						rt = logjudg(en, ou);
						rt.setConBin(en.getEmpName() + en.getDates());
						if (!ou.getOverTime().equals("00:00:00")) {
							overMap.put(ou.getEmpName() + "" + ou.getDates(), ou.getOverTime());
						}
						// if(over==1&&)

						out.remove(j);
						rt.setDept(deptMapOut(ou));
						list.add(rt);
						// 两百写入一次数据库
						if (list.size() % 301 == 0) {
							Collections.sort(list);
							for (int h = 0; h < list.size(); h++) {
								for (Map.Entry<String, String> m : overMap.entrySet()) {
									if ((list.get(h).getConBin()).equals(m.getKey())
											&& list.get(h).getEmpName().equals(list.get(h - 1).getEmpName())) {
										list.get(h - 1).setAtNight(m.getValue());
									}
								}
							}
							recordDao.workTable(list);
							// 调用函数进行数据处理
							dataProce(list);
							overMap.clear();
							list.clear();
						}
						break;
					}
				}
				// 进出都有打卡才删除
				if (flog) {
					enter.remove(i);
				}

			}
		}
		/*
		 * 剩余的集合元素都是只打半天卡 需要重新写入记录
		 */
		if (!enter.isEmpty()) {
			for (int k = 0; k < enter.size(); k++) {

				rt = logjudg(enter.get(k), new OutTable());
				rt.setConBin(enter.get(k).getEmpName() + enter.get(k).getDates());
				rt.setDept(deptMapEnter(enter.get(k)));
				list.add(rt);
			}
		}
		if (!out.isEmpty()) {
			for (int k = 0; k < out.size(); k++) {
				rt = logjudg(new EnterTable(), out.get(k));
				rt.setConBin(out.get(k).getEmpName() + out.get(k).getDates());
				if (!out.get(k).getOverTime().equals("00:00:00")) {
					overMap.put(out.get(k).getEmpName() + "" + out.get(k).getDates(), out.get(k).getOverTime());
				}
				rt.setDept(deptMapOut(out.get(k)));
				list.add(rt);
			}
		}
		if (!list.isEmpty()) {
			Collections.sort(list);

			for (int h = 0; h < list.size(); h++) {
				for (Map.Entry<String, String> m : overMap.entrySet()) {

					if ((list.get(h).getConBin()).equals(m.getKey())
							&& list.get(h).getEmpName().equals(list.get(h - 1).getEmpName())) {
						list.get(h - 1).setAtNight(m.getValue());
					}
				}
			}
			overMap.clear();
			recordDao.workTable(list);
			// 调用该函数进行数据处理
			dataProce(list);
			// 清空都为null的记录
			recordDao.emptyNull();
			// 清空都为0的记录
			dataProceDao.emptyZero();
		}
	}

	/**
	 * 对数据进行逻辑判别以及数据整合
	 */
	private RecordTable logjudg(EnterTable en, OutTable ou) {

		RecordTable rt = new RecordTable();
		if (en.getEmpName() != null) {
			rt.setEmpName(en.getEmpName());
			rt.setDates(en.getDates());
		} else {
			rt.setEmpName(ou.getEmpName());
			rt.setDates(ou.getDates());
		}
		// 早上上班
		if (en.getWorkMorn() != null) {
			rt.setWorkMorn(en.getWorkMorn());
		} else {
			rt.setWorkMorn("未打卡");
		}
		// 晚上下班
		if (ou.getAtNight() != null) {
			rt.setAtNight(ou.getAtNight());
		} else {
			rt.setAtNight("未打卡");
		}
		// 下午上班
		if (en.getWorkAfter() == null) {
			rt.setWorkAfter("未打卡");
		} else if (en.getWorkAfter().indexOf(" ") != -1) { // 有两个值
			String str1 = en.getWorkAfter().substring(0, en.getWorkAfter().indexOf(" "));
			String str2 = en.getWorkAfter().substring(en.getWorkAfter().indexOf(" ") + 1);
			if (Time.valueOf(str1).after(Time.valueOf(str2))) {
				// rt.setWorkAfter(str1);
				String s = str1;
				str1 = str2;
				str2 = s;

			}
			// 与出门的最大时间比较

			if (ou.getMaxTime() != null && Time.valueOf(ou.getMaxTime()).after(Time.valueOf(str1))
					&& Time.valueOf(ou.getMaxTime()).before(Time.valueOf(str2))
					&& Time.valueOf(ou.getMaxTime()).before(Time.valueOf("14:00:01"))) {
				if (Time.valueOf(str1).after(Time.valueOf("13:44:59"))) {
					rt.setWorkAfter(str1);
				} else if (Time.valueOf(str2).before(Time.valueOf("15:00:00"))) { // 修改
					rt.setWorkAfter(str2);
				} else {
					rt.setWorkAfter("未打卡");
				}
			} else if (ou.getMaxTime() != null && Time.valueOf(ou.getMaxTime()).before(Time.valueOf(str1))) { // 进来两次但中间出去没有打卡
				rt.setWorkAfter(str1);
			} else {
				rt.setWorkAfter(str2);
			}
		} else {
			if (Time.valueOf(en.getWorkAfter()).before(Time.valueOf("15:00:00"))) {
				rt.setWorkAfter(en.getWorkAfter());
			} else {
				rt.setWorkAfter("未打卡");
			}
		}

		// 中午下班
		if (ou.getAtNoon() == null) {
			rt.setAtNoon("未打卡");

		} else if (ou.getAtNoon().indexOf(" ") != -1) { // 两个值
			String str1 = ou.getAtNoon().substring(0, ou.getAtNoon().indexOf(" "));
			String str2 = ou.getAtNoon().substring(ou.getAtNoon().indexOf(" ") + 1);

			if (Time.valueOf(str1).after(Time.valueOf(str2))) {
				// rt.setAtNoon(str2);
				String s = str1;
				str1 = str2;
				str2 = s;
			}
			/*
			 * t1<12:00:00 t2>12:00:00存一个最小的进门时间t 1.t>t1,t2则取max(t1,t2),说明t1没出去
			 * 
			 */
			if (en.getMinTime() != null) {

				if (Time.valueOf(en.getMinTime()).after(Time.valueOf(str1))
						&& Time.valueOf(en.getMinTime()).after(Time.valueOf(str2))) {
					rt.setAtNoon(str2);
				}
				// t>12:20:00 && t2>12:20:0则取t1
				else if (Time.valueOf(en.getMinTime()).after(Time.valueOf("12:20:01"))
						&& Time.valueOf(str2).after(Time.valueOf("12:20:01"))) {
					rt.setAtNoon(str1);
				}
				// .t,t2<12:20:00则取t2
				else if (Time.valueOf(en.getMinTime()).before(Time.valueOf("12:20:01"))
						&& Time.valueOf(str2).before(Time.valueOf("12:20:01"))) {
					rt.setAtNoon(str2);
				}
				// t<12:20:00 && t2>12:20:00则取t2
				else if (Time.valueOf(en.getMinTime()).before(Time.valueOf("12:19:59"))
						&& Time.valueOf(str2).after(Time.valueOf("12:20:01"))) {
					rt.setAtNoon(str2);
				}
			} else if (Time.valueOf(str1).after(Time.valueOf("11:55:00"))
					&& Time.valueOf(str2).before(Time.valueOf("12:10:00"))) {
				rt.setAtNoon(str1);
			} else if (Time.valueOf(str2).before(Time.valueOf("13:10:00"))) {
				rt.setAtNoon(str2);
			} else {
				rt.setAtNoon("未打卡");
			}
		} else {
			rt.setAtNoon(ou.getAtNoon());
		}
		return rt;
	}

	/**
	 * 添加部门字段值
	 */
	private String deptMapOut(OutTable out) {
		if (map.get(out.getEmpName()) != null) {
			return map.get(out.getEmpName());
		} else {
			String s = mangageDao.queryDept(out.getEmpName());
			map.put(out.getEmpName(), s);
			return s;
		}
	}

	private String deptMapEnter(EnterTable enter) {
		if (map.get(enter.getEmpName()) != null) {
			return map.get(enter.getEmpName());
		} else {
			String s = mangageDao.queryDept(enter.getEmpName());
			map.put(enter.getEmpName(), s);
			return s;
		}
	}

	/**
	 * 对工作时间表进行处理
	 */
	private void dataProce(List<RecordTable> list) throws ParseException {
		List<DataProceTable> proce = new ArrayList<DataProceTable>();
		//List<DataProceTable> db = new ArrayList<DataProceTable>();
		List<RecordTable> detailed = new ArrayList<RecordTable>();

		DataProceTable data = null;
		for (RecordTable rt : list) {
			StringBuffer sb = new StringBuffer();
			// 迟到早退标记
			boolean fog = false;
			data = new DataProceTable();
			data.setDates(rt.getDates());
			data.setEmpName(rt.getEmpName());
			data.setDept(rt.getDept());

			// 记录迟到
			int late = 0;
			// 记录早退
			int early = 0;
			// 上完一天班
			data.setDays(1);

			// 计算一天上班小时数
			if (!rt.getWorkMorn().equals("未打卡")) { // 早上上班卡已打
				// 9:00:0-18:00:00
				if (Time.valueOf(rt.getWorkMorn()).before(Time.valueOf("09:10:01"))) {
					data.setHours(calcHours(rt, "19:59:59", "18:00:00"));
				} else {// 10:00:00-19:00:00
					data.setHours(calcHours(rt, "20:59:59", "19:00:00"));

				}
			} else if (!rt.getAtNight().equals("未打卡")) { // 晚上下班卡已打
				if (Time.valueOf(rt.getAtNight()).before(Time.valueOf("20:59:59"))
						&& Time.valueOf(rt.getAtNight()).after(Time.valueOf("15:00:00"))) {

					data.setHours(calcAfterTime(rt, rt.getAtNight()));
				} else if (Time.valueOf(rt.getAtNight()).after(Time.valueOf("20:59:59"))
						|| (Time.valueOf(rt.getAtNight()).after(Time.valueOf("00:00:00"))
								&& Time.valueOf(rt.getAtNight()).before(Time.valueOf("05:00:00")))) {
					data.setHours(calcAfterTime(rt, "19:00:00"));

				}
			} else if (!rt.getAtNoon().equals("未打卡")) { //
				if (!rt.getWorkAfter().equals("未打卡")) {
					data.setHours(hoursCale("10:00:00", "19:00:00") - 2);
				}
			}
			// 迟到次数,早退次数
			// 加班，正常下班时间超过一小时属于加班时间
			/*
			 * 9:00:00-18:00:00 迟到时间超过9:15:00即属于10:00:00-19：00：00
			 */
			if (!rt.getWorkMorn().equals("未打卡") && Time.valueOf(rt.getWorkMorn()).before(Time.valueOf("09:10:01"))) {
				// 迟到
				if (!rt.getWorkMorn().equals("未打卡") && Time.valueOf(rt.getWorkMorn()).after(Time.valueOf("09:00:00"))) {
					late++;
					fog = true;
					sb.append("l");
				}
				if (!rt.getAtNight().equals("未打卡")) {
					// 早退
					if (Time.valueOf(rt.getAtNight()).before(Time.valueOf("18:00:00"))
							&& Time.valueOf(rt.getAtNight()).after(Time.valueOf("14:00:00"))) {
						early++;
						fog = true;
						sb.append("e");
					}
					// 加班
					if ((Time.valueOf(rt.getAtNight()).after(Time.valueOf("19:59:59"))
							&& Time.valueOf(rt.getAtNight()).before(Time.valueOf("23:59:59")))
							|| (Time.valueOf(rt.getAtNight()).after(Time.valueOf("00:00:00"))
									&& Time.valueOf(rt.getAtNight()).before(Time.valueOf("05:00:00")))) {
						fog = true;
						sb.append("o");
						data.setOverTime(clacOverTime(rt, "18:00:00"));
					}
				}

			} else { // 10:00:00-19:00:00
				// 迟到
				if (!rt.getWorkMorn().equals("未打卡") && Time.valueOf(rt.getWorkMorn()).after(Time.valueOf("10:00:00"))) {
					late++;
					fog = true;
					sb.append("l");
				}
				if (!rt.getAtNight().equals("未打卡")) {
					// 早退
					if (Time.valueOf(rt.getAtNight()).before(Time.valueOf("19:00:00"))
							&& Time.valueOf(rt.getAtNight()).after(Time.valueOf("14:00:00"))) {
						early++;
						fog = true;
						sb.append("e");
					}
					// 加班
					if ((Time.valueOf(rt.getAtNight()).after(Time.valueOf("20:59:59"))
							&& Time.valueOf(rt.getAtNight()).before(Time.valueOf("23:59:59")))
							|| (Time.valueOf(rt.getAtNight()).after(Time.valueOf("00:00:00"))
									&& Time.valueOf(rt.getAtNight()).before(Time.valueOf("05:00:00")))) {
						fog = true;
						sb.append("o");
						data.setOverTime(clacOverTime(rt, "19:00:00"));
					}

				}

			}
			// 下午上班迟到
			if (!rt.getWorkAfter().equals("未打卡") && Time.valueOf(rt.getWorkAfter()).after(Time.valueOf("14:00:00"))) {
				late++;
				fog = true;
				sb.append("l");
			}
			// 中午下班早退
			if (!rt.getAtNoon().equals("未打卡") && Time.valueOf(rt.getAtNoon()).before(Time.valueOf("12:00:00"))
					&& !rt.getWorkAfter().equals("未打卡")) {

				early++;
				fog = true;
				sb.append("e");
			}
			data.setLate(late);
			data.setEarlyRetr(early);
	
			// 添加到集合
			proce.add(data);
			if (fog) {
				rt.setConBin(sb.toString());
				detailed.add(rt);
			}
		}
		// 写入迟到早退表
		if (!detailed.isEmpty()) {
			recordDao.detailed(detailed);
		}
	
		// 写入工时统计表
		dataProceDao.dataProce(proce);

	}

	/**
	 * 大于半小时则算一小时
	 */
	private int hoursCale(String night, String times) {
		int hours = (int) Math.abs((Time.valueOf(night).getTime() - Time.valueOf(times).getTime()) / (1000 * 60));
		int h = hours % 60;
		if (h >= 30) {
			hours /= 60;
			hours++;
		} else {
			hours /= 60;
		}
		return hours;
	}

	/**
	 * 判断加班过程中出去的时间
	 */
	private int forCale(List<AttendTable> oud, List<AttendTable> end, String time) {
		int n = 0;

		if (!oud.isEmpty() && !end.isEmpty())
			if (Time.valueOf(oud.get(0).getTimes()).before(Time.valueOf(end.get(0).getTimes()))) {
				n += hoursCale(oud.get(0).getTimes(), time);

			}
		for (int i = 0; i < end.size(); i++) {
			for (int j = i; j < oud.size(); j++) {
				if (Time.valueOf(end.get(i).getTimes()).before(Time.valueOf(oud.get(j).getTimes()))) {

					n += hoursCale(end.get(i).getTimes(), oud.get(j).getTimes());
					break;
				}

			}
		}

		return n;
	}

	/**
	 * 计算正常上班过程中出去的时间
	 */
	private int caleOut(List<AttendTable> oud, List<AttendTable> end, String time) {
		int n = 0;

		for (int i = 0; i < end.size(); i++) {
			for (int j = i; j < oud.size(); j++) {
				if (Time.valueOf(end.get(i).getTimes()).before(Time.valueOf(oud.get(j).getTimes()))
						&& Time.valueOf(end.get(i).getTimes()).before(Time.valueOf(time))) {

					n += hoursCale(end.get(i).getTimes(), oud.get(j).getTimes());
					break;

				}

			}
		}
		return n;
	}

	/**
	 * 计算加班时长
	 */
	private int clacOverTime(RecordTable rt, String time) {
		int num = 0;
		if (Time.valueOf(rt.getAtNight()).after(Time.valueOf("20:59:59"))
				&& Time.valueOf(rt.getAtNight()).before(Time.valueOf("23:59:59"))) {
			int hours = hoursCale(rt.getAtNight(), time);
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("userName", rt.getEmpName());
			map1.put("dates", rt.getDates());
			map1.put("direction", "进门");
			map1.put("times", time);
			List<AttendTable> oud = uploadDao.queryOverTime(map1);
			map1.put("direction", "出门");
			List<AttendTable> end = uploadDao.queryOverTime(map1);

			// 判断出去是否超过半小时
			int n = forCale(oud, end, time); // 修改

			if (n <= 2) {
				num = (hours - n);
			} else {
				num = (hours);
			}

		} else if (Time.valueOf(rt.getAtNight()).after(Time.valueOf("00:00:00"))
				&& Time.valueOf(rt.getAtNight()).before(Time.valueOf("05:00:00"))) {
			long hours1 = (Time.valueOf(time).getTime() - Time.valueOf("24:00:00").getTime()) / (1000 * 60 * 60);
			int hours2 = hoursCale(rt.getAtNight(), "00:00:00");
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("userName", rt.getEmpName());
			map1.put("dates", rt.getDates());
			map1.put("direction", "进门");
			map1.put("times", time);
			List<AttendTable> oud = uploadDao.queryOverTime(map1);
			map1.put("direction", "出门");
			List<AttendTable> end = uploadDao.queryOverTime(map1);
			// 判断出去是否超过半小时
			int n = forCale(oud, end, time);
			if (n <= 2) {
				num = ((int) Math.abs(hours1) + (int) Math.abs(hours2) - n);
			} else {
				num = ((int) Math.abs(hours1) + (int) Math.abs(hours2));
			}

		}
		return num;
	}

	/**
	 * 计算正常上班时长
	 */
	private int calcHours(RecordTable rt, String time, String time2) { // time是加班时间的分界点,time2是正常下班时间点18:00:00或者19:00:00
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("userName", rt.getEmpName());
		map1.put("dates", rt.getDates());
		map1.put("direction", "进门");

		if (!rt.getAtNight().equals("未打卡")) {
			if (Time.valueOf(rt.getAtNight()).after(Time.valueOf("20:59:59")) && time2.equals("19:00:00")) {
				map1.put("times", time2);
			} else if (Time.valueOf(rt.getAtNight()).before(Time.valueOf("20:59:59")) && time2.equals("19:00:00")) {
				map1.put("times", rt.getAtNight());

			}
			if (Time.valueOf(rt.getAtNight()).after(Time.valueOf("19:59:59")) && time2.equals("18:00:00")) {
				map1.put("times", time2);
			} else if (Time.valueOf(rt.getAtNight()).before(Time.valueOf("19:59:59")) && time2.equals("18:00:00")) {
				map1.put("times", rt.getAtNight());
			}
			if (Time.valueOf(rt.getAtNight()).after(Time.valueOf("00:00:00"))
					&& Time.valueOf(rt.getAtNight()).before(Time.valueOf("05:00:00"))) {
				map1.put("times", time2);
			}
		}

		List<AttendTable> oud = uploadDao.queryOutTime(map1);
		map1.put("direction", "出门");
		List<AttendTable> end = uploadDao.queryOutTime(map1);
		int n = caleOut(oud, end, time2);

		int num = 0;
		if (!rt.getAtNight().equals("未打卡") && Time.valueOf(rt.getAtNight()).before(Time.valueOf(time)) //
				&& Time.valueOf(rt.getAtNight()).after(Time.valueOf("14:00:00"))) {
			int hours = hoursCale(rt.getAtNight(), rt.getWorkMorn());
			num = ((int) Math.abs(hours) - 2);

			// 处理加班超过00：00：00点的全天时间
		} else if (!rt.getAtNight().equals("未打卡") && (Time.valueOf(rt.getAtNight()).after(Time.valueOf(time))
				|| (Time.valueOf(rt.getAtNight()).after(Time.valueOf("00:00:00"))
						&& Time.valueOf(rt.getAtNight()).before(Time.valueOf("05:00:00"))))) {

			long hours1 = hoursCale(rt.getWorkMorn(), time2);
			num = ((int) Math.abs(hours1) - 2);
		} else if (rt.getAtNight().equals("未打卡") && !rt.getWorkAfter().equals("未打卡")) { // 下午上班卡已打，但未打下班卡
			int hours = hoursCale(time2, rt.getWorkMorn());
			num = ((int) Math.abs(hours) - 2);
		} else if (rt.getAtNight().equals("未打卡") && rt.getWorkAfter().equals("未打卡")) { // 下午未上班
			if (!rt.getAtNoon().equals("未打卡")) { // 中午下班已打卡

				int hours = hoursCale(rt.getAtNoon(), rt.getWorkMorn());
				num = ((int) Math.abs(hours));
			}
		}
		return num - n;

	}

	/**
	 * 早上上班未打卡，计算全天正常上班时长
	 */
	private int calcAfterTime(RecordTable rt, String time) {
		
		int num = 0;
		if (!rt.getAtNoon().equals("未打卡")) {
			// if(!rt.getAtNight().equals("未打卡")){ //晚上下班打卡
			num = (hoursCale(time, "10:00:00") - 2);
		} else { // 早上没有上班
			// if(!rt.getAtNight().equals("未打卡")){
			if (!rt.getWorkAfter().equals("未打卡")) {
				//下午来上班的时间
				if(Time.valueOf(rt.getWorkAfter()).after(Time.valueOf("14:00:00"))) {
					num = (hoursCale(time, rt.getWorkAfter()));
				}else {
					num = (hoursCale(time, "14:00:00"));
				}
				
				
			}
			// }
		}
		return num;
	}
}
