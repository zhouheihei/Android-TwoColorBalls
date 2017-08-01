package app.nosleep.com.twocolorball;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nosleep on 16-12-8.
 * 日期：16-12-8下午9:06
 * author:zzh
 * override:
 */

public class HTMLUtils {
    public HTMLUtils() throws IOException {
    }
    public List<String[]> getAll(String body){
        List<String[]> list = new ArrayList<>();
        Document doc = Jsoup.parseBodyFragment(body);
        Elements links = doc.select("tbody > tr"); // a with href
        for (Element link : links) {
            boolean isadd=true;
            String[] item=new String[4];
            String linkText = link.select("td > a").text();
            if(null!=linkText&&!linkText.equals("")){
                item[0]=linkText;
            }else
            {
                isadd=false;
            }
            linkText = link.select("td").eq(3).text();
            if(null!=linkText&&!linkText.equals("")){
                item[1]=linkText;
            }else
            {
                isadd=false;
            }
            linkText = link.select("td").eq(4).text();
            if(null!=linkText&&!linkText.equals("")){
                item[2]=linkText;
            }else
            {
                isadd=false;
            }
            linkText = link.select("td").eq(1).text();
            if(linkText.indexOf("-")>0){
                item[3]=linkText;
            }else
            {
                isadd=false;
            }
            if(isadd){
                list.add(item);
            }

        }
        return converTo(list);
    }

    public String[] getFirst(String body){
        List<String[]> list = this.getAll(body);
        if(list.size()>=1){
//            String[] str = new String[]{"2016150","01,02,03,04,05,06,07","四","2016-12-22"};
//            return str;
            return list.get(0);
        }
        return null;
    }

    /**
     *
     * @param list
     * @return
     */
    public List<String[]> converTo(List<String[]> list ){
        for(int i =0;i<list.size();i++){
            String[] arr = list.get(i);
            char[] strarr=arr[1].toCharArray();
            String srtto ="";
            int _index=1;
            for(int j =0;j<strarr.length;j++){
                srtto=srtto+strarr[j];
                if(_index==2){
                    srtto=srtto+",";
                    _index=1;
                }else{
                    _index++;
                }
            }
            arr[1]=srtto+arr[2];
            arr[2] =arr[3].substring(arr[3].lastIndexOf("-")+3).replaceAll("（","").replaceAll("）","");
            arr[3] =arr[3].substring(0,arr[3].lastIndexOf("-")+3);
        }
        return list;
    }

    public String[] getNewNumnber(String html){
        Log.v("html","getnewnumber");
        String[] ret ;
        Document doc = Jsoup.parseBodyFragment(html);
        Elements links = doc.select("[class=c-border c-gap-bottom-small]"); // a with href
//        Elements elnumber=links.select("[class=c-icon c-icon-ball-red op_caipiao_ball_red c-gap-right-small]");
//        for (Element el : elnumber) {
//            Log.v("html",el.text());
//        }
//        Elements elbluenumber=links.select("[class=c-icon c-icon-ball-blue op_caipiao_ball_blue c-gap-right-small]");
//        for (Element el : elbluenumber) {
//            Log.v("html",el.text());
//        }
//        Elements eldatetime=links.select("b");
//        for (Element el : eldatetime) {
//            Log.v("html",el.text());
//        }
        String strret = "";
        Elements eldateweek=links.select("div");
        for (Element el : eldateweek) {
            Log.v("html1",el.text());
            strret=el.text();
            break;
        }
        String[] apa =strret.split(" ");
        for (String a:
             apa) {
                Log.v("html2",a);
        }
        return apa;
    }
}
