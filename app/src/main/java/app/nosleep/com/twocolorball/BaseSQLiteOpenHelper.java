package app.nosleep.com.twocolorball;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by nosleep on 16-12-6.
 * 日期：16-12-6下午1:20
 * author:zzh
 * override:
 */

public class BaseSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "twocolorball.db";
    private static final int DB_VERSION = 1;

    public BaseSQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v("数据库","数据库初始化");
        db.execSQL("CREATE TABLE tb_numbers(" +
                "n_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "n_openday TEXT," +
                "n_opendate TEXT," +
                "n_openweek TEXT," +
                "n_opennumber TEXT," +
                "n_bingomoney TEXT)");

        db.execSQL("CREATE TABLE tb_notes(" +
                "no_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "no_multiple TEXT," +
                "no_nid TEXT," +
                "no_num1 TEXT," +
                "no_num2 TEXT," +
                "no_num3 TEXT," +
                "no_num4 TEXT," +
                "no_num5 TEXT," +
                "no_num6 TEXT," +
                "no_num7 TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /**
     * 查询所有期
     *
     * @return
     */
    public List<NumberModle> getNumberAll() {
        String sql = "select * from tb_numbers order by n_id desc";
        return getNumberBySql(sql);
    }

    /**
     * 查询指定ID期
     * @param id 一期 ID
     * @return
     */
    public List<NumberModle> getNumberById(String id) {
        String sql = "select * from tb_numbers where n_id=" + id+" order by n_id desc";
        return getNumberBySql(sql);
    }
    /**
     * 获取最新一条记录
     * @return
     */
    public NumberModle getNumberByFirst() {
        String sql = "select * from tb_numbers order by n_id desc";
        List<NumberModle> list = getNumberBySql(sql);
        Log.v("database","返回第一条记录："+list.size());
        if(null!=list&&list.size()>=1){
            Log.v("database","返回第一条记录："+list.get(0).getOpenDay());
            return list.get(0);
        }
        return null;
    }

    /**
     * 获取最新一条记录
     * @param sql
     * @return
     */
    public List<NumberModle> getNumberBySql(String sql) {
        Cursor c=getWritableDatabase().rawQuery(sql, null);
        List<NumberModle> list = new ArrayList<>();
        while (c.moveToNext()){
            NumberModle numberModle = new NumberModle();
            numberModle.setId(c.getInt(c.getColumnIndex("n_id")));
            numberModle.setOpenDay(c.getString(c.getColumnIndex("n_openday")));
            numberModle.setOpenDate(c.getString(c.getColumnIndex("n_opendate")));
            numberModle.setOpenWeek(c.getString(c.getColumnIndex("n_openweek")));
            numberModle.setBingoMoney(c.getInt(c.getColumnIndex("n_bingomoney")));
            numberModle.setOpenNumbers(c.getString(c.getColumnIndex("n_opennumber")));
            list.add(numberModle);
        }
        return list;
    }

    /**
     * 查询指定ID期
     * @param openday 一期 ID
     * @return
     */
    public List<NumberModle> getNumberByOpenDay(String openday) {
        String sql = "select * from tb_numbers where n_openday=" + openday+" order by desc";
        Cursor c=getWritableDatabase().rawQuery(sql, null);
        List<NumberModle> list = new ArrayList<>();
        while (c.moveToNext()){
            NumberModle numberModle = new NumberModle();
            numberModle.setId(c.getInt(c.getColumnIndex("n_id")));
            numberModle.setOpenDay(c.getString(c.getColumnIndex("n_openday")));
            numberModle.setOpenDate(c.getString(c.getColumnIndex("n_opendate")));
            numberModle.setOpenWeek(c.getString(c.getColumnIndex("n_openweek")));
            numberModle.setBingoMoney(c.getInt(c.getColumnIndex("n_bingomoney")));
            numberModle.setOpenNumbers(c.getString(c.getColumnIndex("n_opennumber")));
            list.add(numberModle);
        }
        return list;
    }

    /**
     * 查询
     *
     * @param id 期ID
     * @return
     */
    public List<NoteModle> getNoteAll(String id) {
        String sql = "select * from tb_notes where no_nid=" + id;
        Log.v("database",""+sql);
        Cursor c=getWritableDatabase().rawQuery(sql, null);
        List<NoteModle> list = new ArrayList<>();
        while (c.moveToNext()){
            NoteModle noteModle = new NoteModle();
            noteModle.setId(c.getInt(c.getColumnIndex("no_id")));
            noteModle.setNumone(c.getString(c.getColumnIndex("no_num1")));
            noteModle.setNumtwo(c.getString(c.getColumnIndex("no_num2")));
            noteModle.setNumthree(c.getString(c.getColumnIndex("no_num3")));
            noteModle.setNumfour(c.getString(c.getColumnIndex("no_num4")));
            noteModle.setNumfive(c.getString(c.getColumnIndex("no_num5")));
            noteModle.setNumsix(c.getString(c.getColumnIndex("no_num6")));
            noteModle.setNumseven(c.getString(c.getColumnIndex("no_num7")));
            noteModle.setMultiple(c.getInt(c.getColumnIndex("no_multiple")));
            list.add(noteModle);
        }
        return list;
    }

    /**
     * 添加一注信息
     * @param pOpenDay      期号(20161108)
     * @param pOpenDate     本期日期(2016年12月08日)
     * @param pOpenWeek     周几(星期一)
     * @param pBingoMoney   奖金(300)
     * @param pOpenNumber   开奖号码
     */
    public void insertToNumbers(String pOpenDay, String pOpenDate, String pOpenWeek, String pBingoMoney,String pOpenNumber) {
        ContentValues cv=new ContentValues();
        cv.put("n_openday", pOpenDay);
        cv.put("n_opendate", pOpenDate);
        cv.put("n_openweek", pOpenWeek);
        cv.put("n_bingomoney", pBingoMoney);
        cv.put("n_opennumber",pOpenNumber);
        Log.v("database","数据库操作");
        getWritableDatabase().insert("tb_numbers","",cv);
    }

    /**
     * 添加一注信息
     * @param pMultiple     倍数
     * @param numbers       号码组合
     * @param pUID          对应期号ID
     */
    public void insertToNote(String pMultiple,String[] numbers,String pUID){
        ContentValues cv=new ContentValues();
        cv.put("no_multiple", pMultiple);
        cv.put("no_nid", pUID);
        for(int i =0;i<numbers.length;i++){
           if(i<=6) cv.put("no_num"+(i+1),numbers[i]);
        }
        getWritableDatabase().insert("tb_notes","",cv);
    }

    /**
     * 更新一个号码
     * @param number    更新值
     * @param id        注ID
     * @param colname   列名称
     */
    public void updateByNote(String number,String id,String colname){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "update tb_notes set "+colname+"="+number+" where no_id="+id;
        Log.v("database",sql);
        db.execSQL(sql);
        db.close();
    }

    public void updateByNumber(String id, String[] key,String[] value){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "update tb_numbers set ";
        if(key.length==value.length){
            for(int i =0; i<key.length;i++){
                sql=sql+key[i]+"='"+value[i]+"'";
                if(i!=key.length-1){
                    sql=sql+ ",";
                }
            }
        }
        sql=sql +" where n_id="+id;
        Log.v("database",sql);
        db.execSQL(sql);
        db.close();
    }
    public void updateByNumber(String number, String newnumber){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "update tb_numbers set n_openday='"+newnumber+"' where n_openday='"+number+"'";
        db.execSQL(sql);
        db.close();
    }


    /**
     * 删除一注
     * @param id    删除ID
     */
    public void deleteByNoteID(String id){
        SQLiteDatabase db = getWritableDatabase();
        String sql ="delete from tb_notes where no_id="+id;
        db.execSQL(sql);
        db.close();
    }
}
