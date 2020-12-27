package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.lang.Math;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private static HashMap<Integer, String> timeMap = null;
    private String instructionPrompt = "说明书：五不遇时又称竹篮打水一场空，凡事不顺，" +
            "时机不遇，日月无光；重要事情要避开这个时间段；若无意中遇到，则考虑退出，当然祸福相依" +
            "，不顺也是相对的，调整好心态，多行善事，则自然多福；";
    private Intent openBookIntent;
    private TextView prompt;
    private TextView timeView;
    private TextView instru;
    private Button bookEntry;
    private CalendarView calendar;

    private static int timeMapIndex;
    public static String time;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        refreshMagicTime();

        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                .add(R.id.fragment_container_view, BlankFragment.class, null)
                .commit();


    }


//    private void setTimeZone() {
//        locale = new Locale("zh", "CN");  //地区
//        Locale.setDefault(locale);
//        Configuration config = new Configuration();
//        config.locale = locale;
//        getBaseContext().getResources().updateConfiguration(config,
//                getBaseContext().getResources().getDisplayMetrics());
//
//
//    }

    private void init() {
        getRes();

        openBookIntent= new Intent(this, Book.class);
        bookEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(openBookIntent);
//                finish();
            }
        });

        setText(prompt, instru);
        timeMap = buildTimeMap();

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                switchCalendarDate(view, year, month, dayOfMonth);
            }
        } );
    }



    private void getRes() {
        prompt = findViewById(R.id.prompt);
        timeView = findViewById(R.id.time);
        instru = findViewById(R.id.instru);
        bookEntry = findViewById(R.id.bookEntry);
        calendar = findViewById(R.id.calendarView);
    }


    private void setText(TextView prompt, TextView instru){
        instru.setText(instructionPrompt);
        prompt.setText("五不遇时(北京时间)：");
    }


    private HashMap buildTimeMap(){
        HashMap<Integer, String> timeMap = new HashMap<Integer, String>();
        timeMap.put(0, "7:00 - 9:00");
        timeMap.put(1, "5:00 - 7:00");
        timeMap.put(2, "3:00 - 5:00");
        timeMap.put(3, "1:00 - 3:00,  21:00 - 24:00");
        timeMap.put(4, "0:00 - 1:00,  19:00 - 21:00");
        timeMap.put(5, "17:00 - 19:00");
        timeMap.put(6, "15:00 - 17:00");
        timeMap.put(7, "13:00 - 15:00");
        timeMap.put(8, "11:00 - 13:00");
        timeMap.put(9, "9:00 - 11:00");
        return timeMap;
    }


    public static String calculateMagicTime() {
        Date date1 = null;
        Date date2 = null;
        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
        try {
            date1 = myFormat.parse("03 01 1900");
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            date2 = cal.getTime();
            timeMapIndex = (int) initDifferenceDays(date1, date2) % 10;
            time = timeMap.get(timeMapIndex);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return time;

    }

    public void refreshMagicTime() {
        time = calculateMagicTime();
        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
        try{ Date date1 = myFormat.parse("03 01 1900");
            calendar.setMinDate(date1.getTime());
            calendar.setDate((new Date()).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        timeView.setText(time);

    }



    private void switchCalendarDate(CalendarView view, int year, int month, int dayOfMonth){
        Date date1;
        Date date2;
        try {
            SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
            String inputString1 = "03 01 1900";
            String inputString2 = dayOfMonth + " " +(month+1) + " " + year;
            date1 = myFormat.parse(inputString1);

            Calendar cal = Calendar.getInstance();
            cal.setTime(myFormat.parse(inputString2));
            date2 = cal.getTime();
            int index = (int) getDifferenceDays(date1, date2) % 10;
            timeView.setText(timeMap.get(index));
        }catch (ParseException e) {
            e.printStackTrace();
        }

    }


    private static long initDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - 1000 * 60 * 60 * 4  - d1.getTime();
        double a = diff / (1000*60*60*24.0);
        return Math.round(a);
    }


    private long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime()  - 1000 * 60 * 60 * 4  - d1.getTime();
        double a = diff / (1000*60*60*24.0);
        return Math.round(a);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "land", Toast.LENGTH_SHORT).show();
        } if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }
}
