package io.dtchain.entity;

import java.io.Serializable;

/**
 * created by on 2021/10/20
 * 描述：
 *
 * @author ZSAndroid
 * @create 2021-10-20-20:22
 */
public class WorkTimeBean implements Serializable {
    private String empName;
    private String dept;
    private String dates;
    private String workMorn;
    private String atNoon;
    private String workAfter;
    private String atNight;

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getWorkMorn() {
        return workMorn;
    }

    public void setWorkMorn(String workMorn) {
        this.workMorn = workMorn;
    }

    public String getAtNoon() {
        return atNoon;
    }

    public void setAtNoon(String atNoon) {
        this.atNoon = atNoon;
    }

    public String getWorkAfter() {
        return workAfter;
    }

    public void setWorkAfter(String workAfter) {
        this.workAfter = workAfter;
    }

    public String getAtNight() {
        return atNight;
    }

    public void setAtNight(String atNight) {
        this.atNight = atNight;
    }

    public WorkTimeBean(String empName, String dept, String dates, String workMorn, String atNoon, String workAfter, String atNight) {
        this.empName = empName;
        this.dept = dept;
        this.dates = dates;
        this.workMorn = workMorn;
        this.atNoon = atNoon;
        this.workAfter = workAfter;
        this.atNight = atNight;
    }

    @Override
    public String toString() {
        return "WorkTimeBean{" +
                "empName='" + empName + '\'' +
                ", dept='" + dept + '\'' +
                ", dates='" + dates + '\'' +
                ", workMorn='" + workMorn + '\'' +
                ", atNoon='" + atNoon + '\'' +
                ", workAfter='" + workAfter + '\'' +
                ", atNight='" + atNight + '\'' +
                '}';
    }
}
