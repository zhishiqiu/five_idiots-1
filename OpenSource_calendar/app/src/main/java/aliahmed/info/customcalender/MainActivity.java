package aliahmed.info.customcalender;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.content.Intent; // 화면전환 용도


public class MainActivity extends AppCompatActivity {
    List<Date> selectedDates;
    Date start, end;
    LinearLayout layoutCalender;
    View custom_view;
    Date initialDate, lastDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setInitializations();
        setCalenderView();
    }

    private void setInitializations() {
        custom_view = (View) findViewById(R.id.custom_view);
        layoutCalender = (LinearLayout) findViewById(R.id.layoutCalender);
    }

    public void setCalenderView() {

        //Custom Events
        EventObjects eventObjects = new EventObjects(1, "Birth", new Date());
        eventObjects.setColor(R.color.colorPrimary);
        List<EventObjects> mEvents = new ArrayList<>();
        mEvents.add(eventObjects);

        ViewGroup parent = (ViewGroup) custom_view.getParent();
        parent.removeView(custom_view);
        layoutCalender.removeAllViews();
        layoutCalender.setOrientation(LinearLayout.VERTICAL);

        final CalendarCustomView calendarCustomView = new CalendarCustomView(this, mEvents);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        calendarCustomView.setLayoutParams(layoutParams);
        layoutCalender.addView(calendarCustomView);

        calendarCustomView.calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getAdapter().getView((int) l, null, null).getAlpha() == 0.4f) {
                    Log.d("hello", "hello");
                } else {
                    Calendar today = Calendar.getInstance();
                    today.setTime(new java.util.Date());

                    Calendar tapedDay = Calendar.getInstance();
                    tapedDay.setTime((Date) adapterView.getAdapter().getItem((int) l));
//                    boolean sameDay = tapedDay.get(Calendar.YEAR) == tapedDay.get(Calendar.YEAR) &&
//                            today.get(Calendar.DAY_OF_YEAR) == tapedDay.get(Calendar.DAY_OF_YEAR);
//                    if (today.after(tapedDay) && !sameDay) { // 이전 날짜 선택시 메세지 출력하는 부분
//                        Toast.makeText(MainActivity.this, "이전 날짜는 선택하실 수 없습니다.", Toast.LENGTH_LONG).show();
////                    } else {
//                        if (initialDate == null && lastDate == null) {
//                            initialDate = lastDate = (Date) adapterView.getAdapter().getItem((int) l);
//                        } else {
//                            initialDate = lastDate;
                            lastDate = (Date) adapterView.getAdapter().getItem((int) l);
//                        }
//                        if (initialDate != null && lastDate != null) // 날짜 범위 화면에 표시해주는 부분
//                            calendarCustomView.setRangesOfDate(makeDateRanges());
//                        }
                }
                try {
                    Toast.makeText(MainActivity.this, "선택한 날짜: " + lastDate.toString(), Toast.LENGTH_LONG).show();

                    // 날짜 클릭 시 Memo 화면으로 화면전환
                    Intent intent = new Intent(getApplicationContext(), Memo.class);
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public List<EventObjects> makeDateRanges() {
        if (lastDate.after(initialDate)) {
            start = initialDate;
            end = lastDate;
        } else {
            start = lastDate;
            end = initialDate;
        }
        List<EventObjects> eventObjectses = new ArrayList<>();
        GregorianCalendar gcal = new GregorianCalendar();
        gcal.setTime(start);

        while (!gcal.getTime().after(end)) {
            Date d = gcal.getTime();
            EventObjects eventObject = new EventObjects("", d);
            eventObject.setColor(getResources().getColor(R.color.colorAccent));
            eventObjectses.add(eventObject);
            gcal.add(Calendar.DATE, 1);
        }
        return eventObjectses;
    }
}
