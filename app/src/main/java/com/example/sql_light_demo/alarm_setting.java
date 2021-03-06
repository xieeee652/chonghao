package com.example.sql_light_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.String;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.amap.api.mapcore2d.q.i;


public class alarm_setting extends AppCompatActivity implements View.OnClickListener{
    private sql_activities helper;
    private TextView current_time;
    private SQLiteDatabase sql_db;
    private Button daka_cc,back_bot;
    private List name;
    private List<Activity> act_list;
    private ListView listView;
    private Button button1,button2,button3,button4;
    private String tt[]=new String[10];
    private int tt1=1;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_setting);

        listView = findViewById(R.id.activity_list_3);
        daka_cc=findViewById(R.id.bot_3);
        back_bot=findViewById(R.id.back_menu);
        spinner=findViewById(R.id.spinner);
        listView.setAdapter(null);

        //init();
        run();


        back_bot.setOnClickListener(this);
        daka_cc.setOnClickListener(this);


    }

    private void createAlarm(String message,int day,int hour,int minutes) {

        String packageName = getApplication().getPackageName();
        System.out.println(day+hour+minutes);
        //action???AlarmClock.ACTION_SET_ALARM
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)

               .putExtra(AlarmClock.EXTRA_DAYS,day)
                //???????????????
                .putExtra(AlarmClock.EXTRA_HOUR, hour)
                //????????????????????????
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                //???????????????
                .putExtra(AlarmClock.EXTRA_MINUTES, minutes)
                //??????????????????????????????????????????
                .putExtra(AlarmClock.EXTRA_VIBRATE, true)

                //?????? ArrayList?????????????????????????????????????????????????????????
                // ???????????????????????? Calendar ?????????????????????????????? MONDAY??????????????????
                //?????????true????????????startActivity()???????????????????????????????????????
                .putExtra(AlarmClock.EXTRA_SKIP_UI, true);
            startActivity(intent);
            Toast.makeText(this,"?????????",Toast.LENGTH_SHORT).show();

    }

    private void createAlarm1(String message, int hour, int minutes, int resId) {
        ArrayList<Integer> testDays = new ArrayList<>();
        testDays.add(Calendar.MONDAY);//??????
        testDays.add(Calendar.TUESDAY);//??????
        testDays.add(Calendar.FRIDAY);//??????

        String packageName = getApplication().getPackageName();
        Uri ringtoneUri = Uri.parse("android.resource://" + packageName + "/" + resId);
        //action???AlarmClock.ACTION_SET_ALARM
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                //???????????????
                .putExtra(AlarmClock.EXTRA_HOUR, hour)
                //???????????????
                .putExtra(AlarmClock.EXTRA_MINUTES, minutes)
                //????????????????????????
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                //??????????????????????????????????????????
                .putExtra(AlarmClock.EXTRA_VIBRATE, true)
                //?????? content: URI??????????????????????????????????????????????????? VALUE_RINGTONE_SILENT ?????????????????????
                //????????????????????????????????????????????? extra???
               // .putExtra(AlarmClock.EXTRA_RINGTONE, ringtoneUri)
                //?????? ArrayList?????????????????????????????????????????????????????????
                // ???????????????????????? Calendar ?????????????????????????????? MONDAY??????????????????
                //??????????????????????????????????????? extra
                .putExtra(AlarmClock.EXTRA_DAYS, testDays)
                //?????????true????????????startActivity()???????????????????????????????????????
                .putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void startTimer(String message, int seconds) {
        //action???AlarmClock.ACTION_SET_TIMER
        Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER)
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                //?????????????????????????????????
                .putExtra(AlarmClock.EXTRA_LENGTH, seconds)
                //?????????????????????????????????????????????
                .putExtra(AlarmClock.EXTRA_SKIP_UI, false);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {

        }
    };


    @Override
    public void onClick (View v)  {
        switch (v.getId()) {
            case R.id.bot_3:
                //String a = editText.getText().toString().trim();
                if (spinner.getCount()==0){
                    Toast.makeText(this,"???????????????",Toast.LENGTH_SHORT).show();
                }
                else {
                    int b = spinner.getSelectedItemPosition();
                    /*int c = a.length();*/
                    //int e=Integer.parseInt(a);
                    //System.out.println(e);
                    String timee=tt[b+1];
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    System.out.println(timee);
                    String dd;
                    String HH;
                    String mm;

                    dd = timee.substring(8, 10);
                    HH = timee.substring(11, 13);
                    mm = timee.substring(14, 16);
                    int dd1;
                    dd1=Integer.parseInt(dd);
                    int kk=Integer.parseInt(HH);

                    kk=kk-1;
                    if(kk<0){
                        kk=23;
                        dd1--;
                    }

                    System.out.println(kk);
                    createAlarm(spinner.getSelectedItem().toString(),dd1, kk, Integer.parseInt(mm));
                    Toast.makeText(this,"??????????????????",Toast.LENGTH_SHORT).show();

                }

                break;

            case R.id.back_menu:
                Intent intent = new Intent();
                intent.setClass(this,Main_menu.class);     //Main_menu????????????class
                startActivity(intent);
                break;


        }

    }


        public void run(){
            try{
                while (true){
                    SimpleDateFormat   formatter   =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
                    Date curDate =  new Date(System.currentTimeMillis());
                    //??????????????????
                    String str = formatter.format(curDate);
                    handler.sendMessage(handler.obtainMessage(100,str));
                    Thread.sleep(1000);//????????????????????????


                    //??????????????????????????????listView???
                    sql_activities helper=new sql_activities(alarm_setting.this);
                    sql_db = helper.getReadableDatabase();
                    Cursor cursor = sql_db.query("activitys_arrangement",null,null,null,null,null,null);
                    if(cursor.getCount() == 0) {
                        Intent intent = new Intent();
                        intent.setClass(this,Main_menu.class);     //Main_menu????????????class
                        startActivity(intent);
                        break;
                    }
                    else {
                        act_list = new ArrayList<Activity>();
                        name = new ArrayList();
                        cursor.moveToFirst();



                        do {
                            String num = cursor.getString(0);
                            String names = cursor.getString(1);
                            String time = cursor.getString(2);
                            String adress = cursor.getString(3);
                            String priority = cursor.getString(4);
                            /*String record = cursor.getString(5);*/

                          try{
                                //???????????? (??????????????????????????????????????????????????????)
                                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                //??????????????????
                                Date date = new Date(System.currentTimeMillis());
                                String date3=sdf.format(date);

                                Calendar calendar = Calendar.getInstance();
                                calendar.add(Calendar.HOUR,+24);
                                Date date24 = calendar.getTime();
                                String date4 = sdf.format(date24);
                              if((time.compareTo(String.valueOf(date3)))>0&&time.compareTo(date4)<0&&priority.equals("???")){
                                  Activity activityOBJ = new Activity(num,names,time,adress,priority/*,null*/);
                                  act_list.add(activityOBJ);
                                  name.add(names+" "+time);
                                  tt[tt1]=time;
                                  tt1++;

                              }

                            }catch(Exception e){
                                e.printStackTrace();
                            }

                        }while (cursor.moveToNext());


                        listView.setAdapter(new ActivityAdapter());
                        String[] strArray = (String[]) name.toArray(new String[name.size()]);
                        ArrayAdapter<String> namelist = new ArrayAdapter<>(alarm_setting.this, android.R.layout.simple_spinner_dropdown_item,strArray);
                        spinner.setAdapter(namelist);
                        cursor.close();
                        sql_db.close();
                        break;
                    }
                }
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }



    //?????????????????? https://xnfood.com.tw/android-listview-baseadapter/
    class ActivityAdapter extends BaseAdapter {

        public ActivityAdapter(){
            super();
        }

        @Override
        public int getCount() {//??????????????????
            return act_list.size();
        }

        @Override
        public Object getItem(int position) {
            return act_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {//???position????????????????????????ListView???
            View view = View.inflate(alarm_setting.this,R.layout.ningbao,null);

            Activity activity = act_list.get(position);

            TextView tv1 = view.findViewById(R.id.num);
            tv1.setText(activity.getNum());

            TextView tv2 = view.findViewById(R.id.name);
            tv2.setText(activity.getName());

            TextView tv3 = view.findViewById(R.id.date);
            tv3.setText(activity.getTime());

            TextView tv4 = view.findViewById(R.id.address);
            tv4.setText(activity.getAdress());

            TextView tv5 = view.findViewById(R.id.priority);
            tv5.setText(activity.getPriority());

            return view;
        }
    }


}


