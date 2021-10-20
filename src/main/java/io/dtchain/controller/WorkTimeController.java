package io.dtchain.controller;

import groovy.util.logging.Slf4j;
import io.dtchain.entity.RecordTable;
import io.dtchain.entity.WorkTimeBean;
import io.dtchain.service.WorkTimeService;
import io.dtchain.utils.DateUtil;
import io.dtchain.utils.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * created by on 2021/10/20
 * 描述：
 *
 * @author ZSAndroid
 * @create 2021-10-20-20:31
 */
@RestController
@RequestMapping("/work/")
public class WorkTimeController {

    @Autowired
    WorkTimeService workTimeService;

    @Autowired
    HttpServletRequest request; //通过注解获取一个request

    @RequestMapping(value = "setPlayCardEndTime")
    public Result setPlayCardEndTime(HttpServletRequest request,
                                     @RequestParam(value = "endYear") String endYear,
                                     @RequestParam(value = "endMonth") String endMonth,
                                     @RequestParam(value = "endDay") String endDay,
                                     @RequestParam(value = "endHours") String endHours,
                                     @RequestParam(value = "endMinute") String endMinute,
                                     @RequestParam(value = "endSecond") String endSecond) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//用于字符串格式化为日期格式

        String strMySetEndDate = endYear +"/"+ endMonth +"/"+ endDay + " " + endHours +":"+endMinute +":"+ endSecond; //自定义设置结束打开时间
        String strNowBeginDate = DateUtil.nowDateAndTime();//当前服务器日期时间 例如：2021/10/21 22:00:0
        try {
            Date endDate = sd.parse(strMySetEndDate);// 格式化"自定设置"结束打开时间--->日期格式

            Date beginDate = sd.parse(strNowBeginDate);//格式后"服务器当前"时间--->日期格式

            if(!endDate.after(beginDate)){//  结束时间 > 当前时间，满足设置打卡结束条件
                return new Result(0,"时间错误","当前系统时间："+strNowBeginDate+" > 结束打卡时间"+strMySetEndDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //第一步：获取session
        HttpSession session = request.getSession();
        //第二步：将想要保存到数据存入session中
        session.setAttribute("MySetEndDate",strMySetEndDate);//打卡结束时间
        session.setAttribute("PlayNowDate",endYear +"/"+ endMonth +"/"+ endDay);//管理员自定义打卡结束日期
        session.setAttribute("PlayNowTime",endHours +":"+endMinute +":"+ endSecond);//管理员自定义打卡结束时间
        System.out.println(strMySetEndDate);
        //这样就完成了用户名和密码保存到session的操作
        return new Result(1,"设置结束打卡时间成功",strMySetEndDate);
    }

    @ApiOperation(value = "员工打卡")
    @RequestMapping(value = "addPunchInfo")
    public Result addPunchInfo(@ApiParam(value = "名字", required = true) @RequestParam(value = "empName") String empName,
                               @ApiParam(value = "所属部门", required = true) @RequestParam(value = "dept") String dept) {

        String MySetEndDate = (String) request.getSession().getAttribute("MySetEndDate");//获取Session保存的打卡结束时间
        String PlayNowDate = (String) request.getSession().getAttribute("PlayNowDate");//获取Session保存的开始打卡日期
        String PlayNowTime = (String) request.getSession().getAttribute("PlayNowTime");//获取Session保存的开始打卡时间

        SimpleDateFormat sdDateAndTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//用于字符串格式化为日期时间格式
        SimpleDateFormat sdDate = new SimpleDateFormat("yyyy/MM/dd");//用于字符串格式化为日期格式
        SimpleDateFormat sdTime = new SimpleDateFormat("HH:mm:ss");//用于字符串格式化为时间格式

        String userPlayNowDate = DateUtil.nowDate(); //用户当前打卡服务器日期
        String userPlayNowTime= DateUtil.nowTime();//用户当前打卡服务器时间
        try {
            Date endDate = sdDate.parse(MySetEndDate);//格式化管理员设置的结束打卡日期时间
            Date userPlayDateAndTime = sdDateAndTime.parse(userPlayNowDate +" "+ userPlayNowTime);//用户当前打卡服务器日期时间

            if (!userPlayDateAndTime.after(endDate)){ //用户打卡时间超过管理员设置的打卡时间
                return new Result(0,"打卡失败，打卡已经结束！","结束打卡时间："+MySetEndDate+"，当前打卡时间："+userPlayNowDate +" "+ userPlayNowTime);
            }
            Date userPlayDate = sdDate.parse(userPlayNowDate);//格式化用户当前打卡服务器日期
            Date endPlayDate = sdDate.parse(PlayNowDate);//格式化管理员设置的结束打卡日期

//            if (userPlayDate.after(endPlayDate)){
//                WorkTimeBean workTimeBean = new WorkTimeBean(empName,dept,userPlayDate,nowTime,nowTime,nowTime,nowTime);
//                int intState = workTimeService.addPunchInfo(workTimeBean);
//                return intState !=0 ? new Result(1,"打卡成功",workTimeBean):new Result(1,"打卡失败",workTimeBean);
//            }

            Date userPlayTime = sdDate.parse(userPlayNowDate);//格式化用户当前打卡服务器时间
            Date endPlayTime = sdDate.parse(PlayNowTime);//格式化管理员设置的结束打卡时间




//            if (userPlayDate.after(endPlayDate)){ //用户打卡时间超过管理员设置的日期
//                return new Result(0,"打卡失败，打卡已经结束！","结束打卡日期："+endPlayDate+"，当前打卡日期："+userPlayDate);
//                System.out.println(userPlayDate.after(endPlayDate));
//            }
        }catch (ParseException e) {
            e.printStackTrace();
        }


        System.out.println("Date===="+MySetEndDate+PlayNowDate+PlayNowTime);




        WorkTimeBean workTimeBean = new WorkTimeBean(empName,dept,userPlayNowDate,userPlayNowDate,userPlayNowDate,userPlayNowDate,userPlayNowDate);
        System.out.println(workTimeBean);

//        http://localhost:8083/work/addPunchInfo?empName=Android&dept=测试部
        int intState = workTimeService.addPunchInfo(workTimeBean);
        System.out.println(intState);
        return intState !=0 ? new Result(1,"打卡成功",workTimeBean):new Result(1,"打卡失败",workTimeBean);
    }
}
