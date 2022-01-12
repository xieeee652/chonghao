package com.example.sql_light_demo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class sql_activities extends SQLiteOpenHelper {

    public sql_activities(Context context)      //指定數據庫名稱和版本，用於打開或創建一個數據庫
    {
        super(context,"activities_lite",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql_1="Create TABLE activitys_arrangement(num VARCHAR(100), " +
                "names VARCHAR(100), data_time VARCHAR(100), address VARCHAR(100)," +
                " priority VARCHAR(100), record VARCHAR(100))";
                            //创建一张表格（字段可以任意更改）
        db.execSQL(sql_1);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)//當打開數據庫時，發現版本改變了，要執行的動作
    {

    }
}
