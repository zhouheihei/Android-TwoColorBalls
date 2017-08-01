package app.nosleep.com.twocolorball;

import java.io.Serializable;
import java.util.List;

/**
 * Created by nosleep on 16-12-2.
 * 日期：16-12-2下午3:41
 * author:zzh
 * override:
 */

public class NumberModle implements Serializable {
    private int id;
    private String openDay;//期号
    private String openDate;//日期
    private String openWeek;//周几
    private int bingoMoney;//金额
    private String openNumbers;//开奖号码

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpenNumbers() {
        return openNumbers;
    }

    public void setOpenNumbers(String openNumbers) {
        this.openNumbers = openNumbers;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getOpenWeek() {
        return openWeek;
    }

    public void setOpenWeek(String openWeek) {
        this.openWeek = openWeek;
    }

    public int getBingoMoney() {
        return bingoMoney;
    }

    public void setBingoMoney(int bingoMoney) {
        this.bingoMoney = bingoMoney;
    }

    public String getOpenDay() {
        return openDay;
    }

    public void setOpenDay(String openDay) {
        this.openDay = openDay;
    }

}
