package io.dtchain.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * created by on 2021/10/20
 * 描述：
 *
 * @author ZSAndroid
 * @create 2021-10-20-20:42
 */
public class DateUtil {

    public static void main(String[] args) throws ParseException {

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String beginTime = dateFormat.format(date);

        System.out.println("当前打卡时间=="+beginTime);

        String endTime="2021/10/20 21:56:00";

        System.out.println("结束打卡时间=="+endTime);

        SimpleDateFormat now = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        Date beginDate=now.parse(beginTime);

        Date endDate=now.parse(endTime);
        System.out.println("endDate=="+endDate);

        System.out.println("当前>结束=="+beginDate.before(endDate));

        System.out.println("当前<结束=="+beginDate.after(endDate));

    }
    public static String nowDate(){
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(date);
    }

    public static String nowTime(){
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(date);
    }

    public static String nowDateAndTime(){
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(date);
    }
}
