package app.nosleep.com.twocolorball;

import java.io.Serializable;

/**
 * Created by nosleep on 16-12-2.
 * 日期：16-12-2下午3:48
 * author:zzh
 * override:
 */

public class NoteModle implements Serializable {
    private int id;
    private int multiple;     //本注备注
    private String numone;
    private String numtwo;
    private String numthree;
    private String numfour;
    private String numfive;
    private String numsix;
    private String numseven;

    public String getNumone() {
        return numone;
    }

    public void setNumone(String numone) {
        this.numone = StrToInt(numone);
    }

    public String getNumtwo() {
        return numtwo;
    }

    public void setNumtwo(String numtwo) {
        this.numtwo = StrToInt(numtwo);
    }

    public String getNumthree() {
        return numthree;
    }

    public void setNumthree(String numthree) {
        this.numthree = StrToInt(numthree);
    }

    public String getNumfour() {
        return numfour;
    }

    public void setNumfour(String numfour) {
        this.numfour = StrToInt(numfour);
    }

    public String getNumfive() {
        return numfive;
    }

    public void setNumfive(String numfive) {
        this.numfive = StrToInt(numfive);
    }

    public String getNumsix() {
        return numsix;
    }

    public void setNumsix(String numsix) {
        this.numsix = StrToInt(numsix);
    }

    public String getNumseven() {
        return numseven;
    }

    public void setNumseven(String numseven) {
        this.numseven = StrToInt(numseven);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getMultiple() {
        return multiple;
    }
    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }

    private String StrToInt(String number){
        int _int =Integer.parseInt(number);
        if(_int<10){
            number="0"+_int;
        }
        return number;
    }

}
