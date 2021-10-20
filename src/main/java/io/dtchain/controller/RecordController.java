package io.dtchain.controller;

import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.dtchain.service.RecordService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import io.dtchain.entity.QueryRecord;
import io.dtchain.entity.RecordTable;
import io.dtchain.service.QueryRecordService;
import io.dtchain.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/record")
@Api(value = "/record", description = "考勤记录相关接口")
@CrossOrigin
public class RecordController {
    @Resource
    private QueryRecordService queryRecordService;
    @Resource
    RecordService recordService;

    @ApiOperation(value = "查询迟到早退加班明细")
    @GetMapping(value = "/detailedInfo.io")
    @ResponseBody
    public Result<List<RecordTable>> queryDetailedInfo(@ApiParam(value = "名字", required = true) @RequestParam(value = "empName") String empName,
                                                       @ApiParam(value = "开始日期", required = true) @RequestParam(value = "start") String start,
                                                       @ApiParam(value = "结束日期", required = true) @RequestParam(value = "end") String end) {
        QueryRecord qr = new QueryRecord();
        qr.setEmpName(empName);
        qr.setEnd(end);
        qr.setStart(start);
        return queryRecordService.queryDetailedInfo(qr);
    }

    @ApiOperation(value = "查询符合查询条件的记录总数")
    @GetMapping(value = "/queryCount.io")
    @ResponseBody
    public Result<Object> queryCount(@ApiParam(value = "名字", required = false) @RequestParam(value = "empName") String empName,
                                     @ApiParam(value = "部门", required = true) @RequestParam(value = "empDept") String empDept,
                                     @ApiParam(value = "开始日期", required = true) @RequestParam(value = "start") String start,
                                     @ApiParam(value = "结束日期", required = true) @RequestParam(value = "end") String end) {
        QueryRecord qr = new QueryRecord();
        qr.setEmpDept(empDept);
        qr.setEmpName(empName);
        qr.setEnd(end);
        qr.setStart(start);
        return queryRecordService.quertCount(qr);
    }

    @ApiOperation(value = "点击分页，查询记录")
    @GetMapping(value = "/otherPage.io")
    @ResponseBody
    public Result<List<RecordTable>> queryOtherPage(@ApiParam(value = "名字", required = false) @RequestParam(value = "empName") String empName,
                                                    @ApiParam(value = "部门", required = true) @RequestParam(value = "empDept") String empDept,
                                                    @ApiParam(value = "开始日期", required = true) @RequestParam(value = "start") String start,
                                                    @ApiParam(value = "结束日期", required = true) @RequestParam(value = "end") String end,
                                                    @ApiParam(value = "当前页数", required = true) @RequestParam(value = "page") int page) {
        QueryRecord qr = new QueryRecord();
        qr.setEmpDept(empDept);
        qr.setEmpName(empName);
        qr.setEnd(end);
        qr.setStart(start);
        qr.setPage(page);
        return queryRecordService.queryOtherPage(qr);
    }

    @ApiOperation(value = "导出考勤记录")
    @GetMapping(value = "/downloadExcel.io")
    public void downloadExcel(HttpServletRequest req, HttpServletResponse res) throws Exception {
        QueryRecord qr = new QueryRecord();
        qr.setEmpName(req.getParameter("name"));
        qr.setEmpDept(req.getParameter("dept"));
        qr.setEnd(req.getParameter("end"));
        qr.setStart(req.getParameter("start"));
        HSSFWorkbook wb = queryRecordService.download(qr);
        res.setContentType("application/vnd.ms-excel");
        res.setHeader("Content-disposition", "attachment;filename=" + new String("考勤记录表".getBytes(), "iso-8859-1") + ".xls");
        OutputStream ouputStream = res.getOutputStream();
        wb.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();
    }

    private String empName;
    private String dates;
    private String workMorn;
    private String atNoon;
    private String workAfter;
    private String atNight;
    private String dept;
    private String conBin;

    @ApiOperation(value = "员工打卡")
    @PostMapping(value = "/addPunchInfo")
    @ResponseBody
    public Result<RecordTable> addPunchInfo(@ApiParam(value = "名字", required = true) @RequestParam(value = "empName") String empName,
                                            @ApiParam(value = "打卡日期", required = true) @RequestParam(value = "dates") String dates,
                                            @ApiParam(value = "早上打卡", required = true) @RequestParam(value = "workMorn") String workMorn,
                                            @ApiParam(value = "中午打卡", required = true) @RequestParam(value = "atNoon") String atNoon,
                                            @ApiParam(value = "午休打卡", required = true) @RequestParam(value = "workAfter") String workAfter,
                                            @ApiParam(value = "下班打卡", required = true) @RequestParam(value = "atNight") String atNight,
                                            @ApiParam(value = "所属部门", required = true) @RequestParam(value = "dept") String dept,
                                            @ApiParam(value = "conBin", required = true) @RequestParam(value = "conBin") String conBin) {
        return recordService.addPunchInfo(new RecordTable(empName,dates,workMorn,atNoon,workAfter,atNight,dept,conBin));
    }
}