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
        SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//定义字符串格式化为日期格式
        String strMySetEndDate = endYear + "/" + endMonth + "/" + endDay + " " + endHours + ":" + endMinute + ":" + endSecond; //自定义设置结束打开时间 2021/10/21 09:10:00
        String strNowBeginDate = DateUtil.nowDateAndTime();//当前服务器日期时间 例如：2021/10/21 09:09:00
        try {
            Date endDate = sd.parse(strMySetEndDate);// 格式化"自定设置"结束打开时间--->日期格式 2021/10/21 09:10:00

            Date beginDate = sd.parse(strNowBeginDate);//格式后"服务器当前"时间--->日期格式 2021/10/21 09:09:00

            if (endDate.before(beginDate)) {//  结束时间超过当前时间，不满足设置打卡结束时间的条件
                return new Result(0, "时间错误", "当前系统时间：" + strNowBeginDate + " > 结束打卡时间" + strMySetEndDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //第一步：获取session
        HttpSession session = request.getSession();
        //第二步：将想要保存到数据存入session中
        session.setAttribute("MySetEndDate", strMySetEndDate);//打卡结束时间--->日期格式 2021/10/21 09:10:00
        session.setAttribute("PlayNowDate", endYear + "/" + endMonth + "/" + endDay);//管理员自定义打卡结束日期 2021/11/21
        session.setAttribute("PlayNowTime", endHours + ":" + endMinute + ":" + endSecond);//管理员自定义打卡结束时间 09:10:00
        System.out.println(strMySetEndDate);
        System.out.println(endYear + "/" + endMonth + "/" + endDay);
        System.out.println(endHours + ":" + endMinute + ":" + endSecond);
        //这样就完成了用户名和密码保存到session的操作
        return new Result(1, "设置结束打卡时间成功", strMySetEndDate);
    }

    @ApiOperation(value = "员工打卡")
    @RequestMapping(value = "addPunchInfo")
    public Result addPunchInfo(@ApiParam(value = "名字", required = true) @RequestParam(value = "empName") String empName,
                               @ApiParam(value = "所属部门", required = true) @RequestParam(value = "dept") String dept) {

        String MySetEndDate = (String) request.getSession().getAttribute("MySetEndDate");//获取Session保存的打卡结束时间 2021/10/21 09:10:00
        System.out.println("管理设置的打卡时间====" + MySetEndDate);
        String PlayNowDate = (String) request.getSession().getAttribute("PlayNowDate");//获取Session保存的开始打卡日期 2021/11/21
        String PlayNowTime = (String) request.getSession().getAttribute("PlayNowTime");//获取Session保存的开始打卡时间  09:10:00

        if (MySetEndDate != null) {
            System.out.println("进入打卡判断");
            SimpleDateFormat sdDateAndTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//用于字符串格式化为日期时间格式
            SimpleDateFormat sdDate = new SimpleDateFormat("yyyy/MM/dd");//用于字符串格式化为日期格式
            SimpleDateFormat sdTime = new SimpleDateFormat("HH:mm:ss");//用于字符串格式化为时间格式

            String strUserPlayDateAndTime = DateUtil.nowDateAndTime();//用户当前打卡服务器日期时间
            String userPlayNowDate = DateUtil.nowDate(); //用户当前打卡服务器日期
            String userPlayNowTime = DateUtil.nowTime();//用户当前打卡服务器时间

            try {
                Date endDate = sdDateAndTime.parse(MySetEndDate);//格式化管理员设置的结束打卡日期时间--->日期格式 2021/10/21 09:10:00
                System.out.println("管理员设置结束打卡服务器日期时间===" + endDate);
                Date DateUserPlayDateAndTime = sdDateAndTime.parse(strUserPlayDateAndTime);//用户当前打卡服务器日期时间
                System.out.println("用户当前打卡服务器日期时间===" + DateUserPlayDateAndTime);
                if (!DateUserPlayDateAndTime.before(endDate)) { //用户打卡时间超过管理员设置的打卡时间
                    return new Result(0, "本次打卡已结束,下次要提前哟~！", "结束打卡时间：" + MySetEndDate + "，当前打卡时间：" + userPlayNowDate + " " + strUserPlayDateAndTime);
                }
                //管理员设置的结束打卡时间
//                Date endPlayDate = sdDate.parse(PlayNowDate);//格式化管理员设置的结束打卡日期
                Date DateEndPlayTime = sdTime.parse(PlayNowTime);//格式化管理员设置的结束打卡时间
                //员工当前打卡时间
//              Date userPlayDate = sdDate.parse(userPlayNowDate);//格式化用户当前打卡服务器日期
                Date DateUserPlayNowTime = sdTime.parse(userPlayNowTime);//格式化用户当前打卡服务器时间
                System.out.println("用户当前打卡时间==="+DateUserPlayNowTime);
                //早上
                String strMorning = "09:00:00";
                Date dateMorning = sdTime.parse(strMorning);
                System.out.println("格式化后的早上9点=="+dateMorning);
                String strSetMorningTimer = "09:30:00";//定义打卡时间
                Date dateSetMorningTimer = sdTime.parse(strSetMorningTimer);
                //中午
                String strNoon = "12:00:00";
                Date dateNoon = sdTime.parse(strNoon);
                System.out.println("格式化后的中午12点=="+dateNoon);
                //下午
                String strAfternoon = "10:00:00";
                Date dateAfternoon = sdTime.parse(strAfternoon);
                //晚上
                String strNight = "18:00:00";
                Date dateNight = sdTime.parse(strNight);
                String strPlayCardError = "未打卡";
                String strPlayCardNotBegin = "————";//未开始
                if (DateUserPlayNowTime.after(dateMorning) && DateUserPlayNowTime.before(dateSetMorningTimer)) {//早上打卡：员工打卡时在 09:00 - 09:30  之间打卡有效
                    WorkTimeBean workTimeBean = new WorkTimeBean(empName,dept,userPlayNowDate,userPlayNowTime,strPlayCardNotBegin,strPlayCardNotBegin,strPlayCardNotBegin);
                    int intState = workTimeService.addPunchInfo(workTimeBean);
//                    return intState !=0 ? new Result(1,"09:30之前结束，本次成功打卡",workTimeBean):new Result(1,"打卡失败",workTimeBean);
                    return new Result(0, "目前在09:30之前，本次成功打卡", "早3上：" +DateUserPlayNowTime.after(dateMorning)  + "，早上结束打卡时间：" +  DateUserPlayNowTime.before(dateSetMorningTimer));
                }else { //超过早上 09:30 未打卡
                    WorkTimeBean workTimeBean = new WorkTimeBean(empName,dept,userPlayNowDate,strPlayCardError,strPlayCardNotBegin,strPlayCardNotBegin,strPlayCardNotBegin);
                    int intState = workTimeService.addPunchInfo(workTimeBean);
                    return new Result(0, "目前在09:30之后，本次成功失败", "早3上：" +DateUserPlayNowTime.after(dateMorning)  + "，早上结束打卡时间：" +  DateUserPlayNowTime.before(dateSetMorningTimer));
                }
//                if (!DateUserPlayNowTime.before(dateMorning) && !DateUserPlayNowTime.after(dateNoon)) {//员工打卡时间超过 9 - 12点
//                    return new Result(0, "早上And中午", "早上：" + DateUserPlayNowTime.before(dateMorning) + "，中午：" + DateUserPlayNowTime.after(dateNoon));
//                }

                //比较打卡日期，超过日期就提示打卡已结束

//            if (userPlayDate.after(endPlayDate)){
//                WorkTimeBean workTimeBean = new WorkTimeBean(empName,dept,userPlayDate,nowTime,nowTime,nowTime,nowTime);
//                int intState = workTimeService.addPunchInfo(workTimeBean);
//                return intState !=0 ? new Result(1,"打卡成功",workTimeBean):new Result(1,"打卡失败",workTimeBean);
//            }

//            if (userPlayDate.after(endPlayDate)){ //用户打卡时间超过管理员设置的日期
//                return new Result(0,"打卡失败，打卡已经结束！","结束打卡日期："+endPlayDate+"，当前打卡日期："+userPlayDate);
//                System.out.println(userPlayDate.after(endPlayDate));
//            }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
//        return new Result(0,"管理员未启用打卡功能",null);
        return new Result(0, "打卡有效", null);
//        System.out.println("Date===="+MySetEndDate+PlayNowDate+PlayNowTime);

//        WorkTimeBean workTimeBean = new WorkTimeBean(empName,dept,userPlayNowDate,userPlayNowDate,userPlayNowDate,userPlayNowDate,userPlayNowDate);
//        System.out.println(workTimeBean);
//
////        http://localhost:8083/work/addPunchInfo?empName=Android&dept=测试部
//        int intState = workTimeService.addPunchInfo(workTimeBean);
//        System.out.println(intState);
//        return intState !=0 ? new Result(1,"打卡成功",workTimeBean):new Result(1,"打卡失败",workTimeBean);

    }
}
