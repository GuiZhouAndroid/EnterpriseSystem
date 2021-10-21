package io.dtchain.controller;

import io.dtchain.entity.WorkTimeBean;
import io.dtchain.service.WorkTimeService;
import io.dtchain.utils.DateUtil;
import io.dtchain.utils.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        /** 1.获取 Session 数据--->管理员设置的结束打卡日期信息*/
        String MySetEndDate = (String) request.getSession().getAttribute("MySetEndDate");//获取Session保存的打卡结束日期+时间 2021/10/21 09:10:00
        String PlayNowDate = (String) request.getSession().getAttribute("PlayNowDate");//获取Session保存的开始打卡日期 2021/11/21
        String PlayNowTime = (String) request.getSession().getAttribute("PlayNowTime");//获取Session保存的开始打卡时间  09:10:00
        if (MySetEndDate != null) {
            /** 2.日期格式转换 */
            SimpleDateFormat sdDateAndTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//用于字符串格式化为日期时间格式
            SimpleDateFormat sdDate = new SimpleDateFormat("yyyy/MM/dd");//用于字符串格式化为日期格式
            SimpleDateFormat sdTime = new SimpleDateFormat("HH:mm:ss");//用于字符串格式化为时间格式
            /** 3.员工打卡当前服务器时间 */
            String strUserPlayDateAndTime = DateUtil.nowDateAndTime();//用户当前打卡服务器日期时间
            String userPlayNowDate = DateUtil.nowDate(); //用户当前打卡服务器日期
            String userPlayNowTime = DateUtil.nowTime();//用户当前打卡服务器时间
            /** 4.员工日期格式开始转换 */
            try {
                Date endDate = sdDateAndTime.parse(MySetEndDate);//格式化管理员设置的结束打卡日期时间
                System.out.println("管理员设置结束打卡服务器日期时间===" + endDate);
                Date DateUserPlayDateAndTime = sdDateAndTime.parse(strUserPlayDateAndTime);//用户当前打卡服务器日期时间
                System.out.println("用户当前打卡服务器日期时间===" + DateUserPlayDateAndTime);
                /** 5.开始动态判断打卡时间 */
                if (!DateUserPlayDateAndTime.before(endDate)) { //用户打卡时间超过管理员设置的打卡时间
                    return new Result(0, "本次打卡已结束,下次要提前哟~！", "结束打卡时间：" + MySetEndDate + "，当前打卡时间：" + userPlayNowDate + " " + strUserPlayDateAndTime);
                }
                //管理员设置的结束打卡时间
//                Date endPlayDate = sdDate.parse(PlayNowDate);//格式化管理员设置的结束打卡日期
//                Date DateEndPlayTime = sdTime.parse(PlayNowTime);//格式化管理员设置的结束打卡时间

                //员工当前打卡时间
//              Date userPlayDate = sdDate.parse(userPlayNowDate);//格式化用户当前打卡服务器日期
                Date DateUserPlayNowTime = sdTime.parse(userPlayNowTime);//格式化用户当前打卡服务器时间
                System.out.println("用户当前打卡时间===" + DateUserPlayNowTime);
                /** 6.设置打卡对比参数 */
                //早上：9点整开始打卡
                String strMorning = "09:00:00";
                Date dateMorning = sdTime.parse(strMorning);
                System.out.println("格式化后的上午9点==" + dateMorning);
                //定义早上打卡期限时间--->期限时间为管理员设置时间
                Date dateSetMorningTimer = sdTime.parse(PlayNowTime);
                System.out.println("上午9点打卡时间期限==" + dateSetMorningTimer);

                //中午：12点整开始打卡
                String strNoon = "12:00:00";
                Date dateNoon = sdTime.parse(strNoon);
                System.out.println("格式化后的中午12点==" + dateNoon);
                //定义中午打卡时间期限--->期限时间为管理员设置时间
                Date dateSetNoonTimer = sdTime.parse(PlayNowTime);
                System.out.println("中午12点打卡时间期限==" + dateSetNoonTimer);

                //下午：15点整开始打卡
                String strAfternoon = "15:00:00";
                Date dateAfternoon = sdTime.parse(strAfternoon);
                System.out.println("格式化后的下午15点==" + dateAfternoon);
                //定义下午打卡期限时间--->期限时间为管理员设置时间
                Date dateSetAfternoonTimer = sdTime.parse(PlayNowTime);
                System.out.println("下午15点打卡时间期限==" + dateSetAfternoonTimer);

                //晚上：18点整开始打卡
                String strNight = "18:00:00";
                Date dateNight = sdTime.parse(strNight);
                System.out.println("格式化后的晚上18点==" + dateNight);
                //定义下午打卡期限时间--->期限时间为管理员设置时间
                Date dateSetNightTimer = sdTime.parse(PlayNowTime);
                System.out.println("晚上18点打卡时间期限==" + dateSetNightTimer);


                //打卡状态
                String strPlayCardError = "未打卡";
                String strPlayCardNotBegin = "————";//未开始

                //当天-->打卡有效期内可以多次打卡(解决不了)，超过打卡有效期,即使当天的某时间段未打卡--->那么当天同时间段，只能打卡一次，并插入未打卡，生效一次添加数据库操作

                /** 7.员工开始打卡 */
                //比较早上员工打卡时间---> 09:00:00
                if ((DateUserPlayNowTime.after(dateMorning) && DateUserPlayNowTime.before(sdTime.parse("09:30:00"))) && (dateSetMorningTimer.after(DateUserPlayNowTime) && dateSetMorningTimer.before(dateNoon))) {// 09:00:00 < 员工打卡时间 < 09:30:00 , 员工打卡时间 < 管理员设置时间 < 12:00:00
                    WorkTimeBean workTimeBean = new WorkTimeBean(empName, dept, userPlayNowDate, userPlayNowTime, strPlayCardNotBegin, strPlayCardNotBegin, strPlayCardNotBegin);
                    workTimeService.addPunchInfo(workTimeBean);
                    return new Result(1, "本次成功打卡", "早上：" + DateUserPlayNowTime.after(dateMorning) + "，早上结束打卡时间：" + DateUserPlayNowTime.before(dateSetMorningTimer));
                } else {//超过中午 09:00:00 未打卡
                    if ((DateUserPlayNowTime.after(dateMorning) && DateUserPlayNowTime.after(sdTime.parse("09:30:00"))) && (DateUserPlayNowTime.after(dateSetMorningTimer) && dateSetMorningTimer.before(dateNoon))) { // 09:00:00 < 员工打卡时间 > 09:30:00 , 员工打卡时间 > 管理员设置时间 < 12:00:00
                        WorkTimeBean workTimeBean = new WorkTimeBean(empName, dept, userPlayNowDate, strPlayCardError, strPlayCardNotBegin, strPlayCardNotBegin, strPlayCardNotBegin);
                        workTimeService.addPunchInfo(workTimeBean);
                        return new Result(0, "本次打卡失败", "早上：" + DateUserPlayNowTime.after(dateMorning) + "，早上结束打卡时间：" + DateUserPlayNowTime.before(dateSetMorningTimer));
                    }
                }
                //比较中午员工打卡时间---> 12:00:00
                if ((DateUserPlayNowTime.after(dateNoon) && DateUserPlayNowTime.before(sdTime.parse("12:30:00"))) && (dateSetNoonTimer.after(DateUserPlayNowTime) && dateSetNoonTimer.before(dateAfternoon))) { // 12:00:00 < 员工打卡时间 < 12:30:00 , 员工打卡时间 < 管理员设置时间 < 15:00:00
                    WorkTimeBean workTimeBean = new WorkTimeBean(empName, dept, userPlayNowDate, strPlayCardNotBegin, userPlayNowTime, strPlayCardNotBegin, strPlayCardNotBegin);
                    workTimeService.addPunchInfo(workTimeBean);
                    return new Result(1, "本次成功打卡", "中午：" + DateUserPlayNowTime.after(dateNoon) + "，中午结束打卡时间：" + DateUserPlayNowTime.before(dateSetNoonTimer));

                } else { //超过中午 12:00:00 未打卡
                    if ((DateUserPlayNowTime.after(dateNoon) && DateUserPlayNowTime.after(sdTime.parse("12:30:00"))) && (DateUserPlayNowTime.after(dateSetNoonTimer) && dateSetNoonTimer.before(dateAfternoon))) {  // 12:00:00 < 员工打卡时间 > 12:30:00 , 员工打卡时间 > 管理员设置时间 < 15:00:00
                        WorkTimeBean workTimeBean = new WorkTimeBean(empName, dept, userPlayNowDate, strPlayCardNotBegin, strPlayCardError, strPlayCardNotBegin, strPlayCardNotBegin);
                        workTimeService.addPunchInfo(workTimeBean);
                        return new Result(0, "本次打卡失败", "中午：" + DateUserPlayNowTime.after(dateNoon) + "，中午结束打卡时间：" + DateUserPlayNowTime.before(dateSetNoonTimer));
                    }
                }

                //比较下午员工打卡时间---> 15:00:00
                if ((DateUserPlayNowTime.after(dateAfternoon) && DateUserPlayNowTime.before(sdTime.parse("15:30:00"))) && (dateSetAfternoonTimer.after(DateUserPlayNowTime) && dateSetAfternoonTimer.before(dateNight))) { // 15:00:00 < 员工打卡时间 < 15:30:00 , 员工打卡时间 < 管理员设置时间 < 18:00:00
                    WorkTimeBean workTimeBean = new WorkTimeBean(empName, dept, userPlayNowDate, strPlayCardNotBegin, strPlayCardNotBegin, userPlayNowTime, strPlayCardNotBegin);
                    workTimeService.addPunchInfo(workTimeBean);
                    return new Result(1, "本次成功打卡", "下午：" + DateUserPlayNowTime.after(dateAfternoon) + "，下午结束打卡时间：" + DateUserPlayNowTime.before(dateSetAfternoonTimer));

                } else { //超过下午 15:00:00 未打卡
                    if ((DateUserPlayNowTime.after(dateAfternoon) && DateUserPlayNowTime.after(sdTime.parse("15:30:00"))) && (DateUserPlayNowTime.after(dateSetAfternoonTimer) && dateSetAfternoonTimer.before(dateNight))) {  // 15:00:00 < 员工打卡时间 > 15:30:00 , 员工打卡时间 > 管理员设置时间 < 18:00:00
                        WorkTimeBean workTimeBean = new WorkTimeBean(empName, dept, userPlayNowDate, strPlayCardNotBegin, strPlayCardNotBegin, strPlayCardError, strPlayCardNotBegin);
                        workTimeService.addPunchInfo(workTimeBean);
                        return new Result(0, "本次打卡失败", "下午：" + DateUserPlayNowTime.after(dateAfternoon) + "，下午结束打卡时间：" + DateUserPlayNowTime.before(dateSetAfternoonTimer));
                    }
                }

                //比较晚上员工打卡时间---> 18:00:00
                if ((DateUserPlayNowTime.after(dateNight) && DateUserPlayNowTime.before(sdTime.parse("18:30:00"))) && (dateSetNightTimer.after(DateUserPlayNowTime) && dateSetNightTimer.before(sdTime.parse("19:00:00")))) { // 15:00:00 < 员工打卡时间 < 15:30:00 , 员工打卡时间 < 管理员设置时间 < 18:00:00
                    WorkTimeBean workTimeBean = new WorkTimeBean(empName, dept, userPlayNowDate, strPlayCardNotBegin, strPlayCardNotBegin, strPlayCardNotBegin, userPlayNowTime);
                    workTimeService.addPunchInfo(workTimeBean);
                    return new Result(1, "本次成功打卡", "晚上：" + DateUserPlayNowTime.after(dateNight) + "，晚上结束打卡时间：" + DateUserPlayNowTime.before(dateSetNightTimer));

                } else { //超过晚上 18:00:00 未打卡
                    if ((DateUserPlayNowTime.after(dateNight) && DateUserPlayNowTime.after(sdTime.parse("18:30:00"))) && (DateUserPlayNowTime.after(dateSetNightTimer) && dateSetNightTimer.before(sdTime.parse("19:00:00")))) {  // 18:00:00 < 员工打卡时间 > 18:30:00 , 员工打卡时间 > 管理员设置时间 < 19:00:00
                        WorkTimeBean workTimeBean = new WorkTimeBean(empName, dept, userPlayNowDate, strPlayCardNotBegin, strPlayCardNotBegin, strPlayCardNotBegin, strPlayCardError);
                        workTimeService.addPunchInfo(workTimeBean);
                        return new Result(0, "本次打卡失败", "晚上：" + DateUserPlayNowTime.after(dateNight) + "，晚上结束打卡时间：" + DateUserPlayNowTime.before(dateSetNightTimer));
                    }
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return new Result(0, "管理员未启用打卡功能", null);
//        return intState !=0 ? new Result(1,"打卡成功",workTimeBean):new Result(1,"打卡失败",workTimeBean);

    }
}
