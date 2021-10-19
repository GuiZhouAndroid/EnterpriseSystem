package io.dtchain.controller;

import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.dtchain.entity.QueryRecord;
import io.dtchain.entity.ResultProce;
import io.dtchain.service.AttendService;
import io.dtchain.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/attend")
@Api(value = "/attend", description = "考勤管理相关接口")
@CrossOrigin
public class AttendController {
	@Resource
	private AttendService attendService;

	@ApiOperation(value = "查询工时统计表")
	@GetMapping(value = "/statistic.io")
	@ResponseBody
	public Result<List<ResultProce>> statistic(@ApiParam(value = "员工名字", required = false)  @RequestParam(value = "empName") String empName,
											   @ApiParam(value = "部门名称", required = true)  @RequestParam(value = "empDept") String empDept,
											   @ApiParam(value = "开始日期", required = true)  @RequestParam(value = "start") String start,
											   @ApiParam(value = "结束日期", required = true)  @RequestParam(value = "end") String end) {
		QueryRecord qr=new QueryRecord();
		qr.setEmpDept(empDept);
		qr.setEmpName(empName);
		qr.setEnd(end);
		qr.setStart(start);
		return attendService.statistic(qr);
	}
	
	@ApiOperation(value = "导出工时统计")
	@GetMapping(value = "/downloadExcel.io")
	public void downloadExcel(HttpServletRequest req,HttpServletResponse res) throws Exception{
		QueryRecord qr=new QueryRecord();
		qr.setEmpName(req.getParameter("name"));
		qr.setEmpDept(req.getParameter("dept"));
		qr.setEnd(req.getParameter("end"));
		qr.setStart(req.getParameter("start"));
		HSSFWorkbook wb=attendService.download(qr);
        res.setContentType("application/vnd.ms-excel");
        res.setHeader("Content-disposition", "attachment;filename="+new String("工时统计表".getBytes(),"iso-8859-1")+".xls" );
        OutputStream ouputStream = res.getOutputStream();
        wb.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();
	}
}
