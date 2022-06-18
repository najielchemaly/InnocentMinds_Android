package apploads.com.innocentminds.events;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apploads.com.innocentminds.BaseActivity;
import apploads.com.innocentminds.R;
import apploads.com.innocentminds.helpers.LoadingIndicator;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.services.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chchidiac on 4/2/19.
 */

public class EventsActivity extends BaseActivity {

    TextView txtWeek, txtMonth, txtCalendar, lblNoData;
    ImageButton btnMinusWeek, btnPlusWeek;
    LoadingIndicator indicatorView;
    EventsAdapter eventsAdapter;
    FoodAdapter foodAdapter;
    ListView lstEvents;
    Button btnClose;
    Map<Integer,List<Event>> eventMap;
    Map<Integer,List<Food>> foodMap;

    String[] monthName;

    //weeks variables
    int numOfWeeks = 1;
    int maxWeeks;

    //event types variables
    private boolean isFoodCalendar;

    @Override
    public int getContentViewId() {
        return R.layout.events_activity;
    }

    @Override
    public void doOnCreate() throws Exception {
        initView();
        setFonts();
        initListeners();
        callEventApiSortedBy();
    }

    private void initView()
    {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            isFoodCalendar = bundle.getBoolean("isFoodCalendar");
        }

        monthName = new String[]{getResources().getString(R.string.january),
                getResources().getString(R.string.february),
                getResources().getString(R.string.march),
                getResources().getString(R.string.april),
                getResources().getString(R.string.may),
                getResources().getString(R.string.july),
                getResources().getString(R.string.july),
                getResources().getString(R.string.august),
                getResources().getString(R.string.september),
                getResources().getString(R.string.october),
                getResources().getString(R.string.november),
                getResources().getString(R.string.december)};

        btnClose  = _findViewById(R.id.btnClose);
        lstEvents  = _findViewById(R.id.lstEvents);
        indicatorView = _findViewById(R.id.indicatorView);
        txtWeek       = _findViewById(R.id.txtWeek);
        txtMonth      = _findViewById(R.id.txtMonth);
        lblNoData     = _findViewById(R.id.lblNoData);
        txtCalendar   = _findViewById(R.id.txtCalendar);
        btnMinusWeek  = _findViewById(R.id.btnMinusWeek);
        btnPlusWeek   = _findViewById(R.id.btnPlusWeek);
        lblNoData.setText(isFoodCalendar?getResources().getString(R.string.no_food_available):getResources().getString(R.string.no_events_available));
        maxWeeks = AppUtils.getWeeksOfMonth();
        AppUtils.isWeekDate(1,new Date());

        Calendar cal = Calendar.getInstance();
        String month = monthName[cal.get(Calendar.MONTH)];
        txtMonth.setText(month);
        txtWeek.setText(getResources().getString(R.string.week) + " 1");
    }

    private void setFonts()
    {
        txtWeek.setTypeface(AppUtils.getBoldTypeface(getApplicationContext()));
        lblNoData.setTypeface(AppUtils.getBoldTypeface(getApplicationContext()));
        txtMonth.setTypeface(AppUtils.getRegularTypeface(getApplicationContext()));
        btnClose.setTypeface(AppUtils.getRegularTypeface(getApplicationContext()));
        txtCalendar.setTypeface(AppUtils.getTitleRegularFont(getApplicationContext()));
    }

    private void initListeners()
    {
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnMinusWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                addWeek(false);
                if(isFoodCalendar)
                {
                    foodAdapter.reloadData(foodMap.get(numOfWeeks));
                    lblNoData.setVisibility(View.GONE);
                    if(foodMap.get(numOfWeeks).size()==0)
                    {
                        lblNoData.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    eventsAdapter.reloadData(eventMap.get(numOfWeeks));
                    lblNoData.setVisibility(View.GONE);
                    if(eventMap.get(numOfWeeks).size()==0)
                    {
                        lblNoData.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        btnPlusWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                addWeek(true);
                if(isFoodCalendar){
                    foodAdapter.reloadData(foodMap.get(numOfWeeks));
                    lblNoData.setVisibility(View.GONE);
                    if(foodMap.get(numOfWeeks).size()==0)
                    {
                        lblNoData.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    eventsAdapter.reloadData(eventMap.get(numOfWeeks));
                    lblNoData.setVisibility(View.GONE);
                    if(eventMap.get(numOfWeeks).size()==0)
                    {
                        lblNoData.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    /**
     * for adding and subtracting weeks
     * @param toAdd for knowing when to add and when to subtract week
     */
    private void addWeek(boolean toAdd)
    {
        if(toAdd)
        {
            if (numOfWeeks<maxWeeks)
            {
                txtWeek.setText(getResources().getString(R.string.week)+ " " + ++numOfWeeks);
            }
        }
        else
        {
            if(numOfWeeks>1)
            {
                txtWeek.setText(getResources().getString(R.string.week)+ " " + --numOfWeeks);
            }
        }
    }

    /**
     * calling the calendar api depending on the type
     */
    private void callEventApiSortedBy()
    {
        indicatorView.show();
        String userId = StaticData.user.getId() == null ? "0" : StaticData.user.getId().toString();

        if(!isFoodCalendar)
        {
            ApiManager.getService(true).getCalendar(userId,AppUtils.getLang(EventsActivity.this)).enqueue(new Callback<EventResponse>()
            {
                @Override
                public void onResponse(Call<EventResponse> call, Response<EventResponse> response)
                {
                    if(response.isSuccessful() && response.body() != null)
                    {
                        List<Event> eventsList = response.body().getEvents();
                        eventMap = new HashMap<>();

                        int maxWeeks = AppUtils.getWeeksOfMonth();
                        for(int i=1;i<=maxWeeks;i++)
                        {
                            eventMap.put(i,new ArrayList<Event>());
                            for(Event event : eventsList)
                            {
                                 if(AppUtils.isWeekDate(i,event.getDate()))
                                 {
                                     eventMap.get(i).add(event);
                                 }
                            }
                            Collections.sort(eventMap.get(i));
                        }
                        eventsAdapter = new EventsAdapter(eventMap.get(1), EventsActivity.this);
                        lstEvents.setAdapter(eventsAdapter);
                        indicatorView.hide();
                    }
                }

                @Override
                public void onFailure(Call<EventResponse> call, Throwable t)
                {
                    indicatorView.hide();
                }
            });
        }
        else
        {
            ApiManager.getService(true).getFoodCalendar(userId,AppUtils.getLang(EventsActivity.this)).enqueue(new Callback<EventResponse>()
            {
                @Override
                public void onResponse(Call<EventResponse> call, Response<EventResponse> response)
                {
                    if(response.isSuccessful() && response.body() != null)
                    {
                        List<Food> foodList = response.body().getFoodCalendar();
                        foodMap = new HashMap<>();

                        int maxWeeks = AppUtils.getWeeksOfMonth();
                        for(int i=1;i<=maxWeeks;i++)
                        {
                            foodMap.put(i,new ArrayList<Food>());
                            for(Food food : foodList)
                            {
                                if(AppUtils.isWeekDate(i,food.getDate()))
                                {
                                    foodMap.get(i).add(food);
                                }
                            }
                            Collections.sort(foodMap.get(i));
                        }
                        foodAdapter = new FoodAdapter(foodMap.get(1), EventsActivity.this);
                        lstEvents.setAdapter(foodAdapter);
                        indicatorView.hide();
                    }
                }

                @Override
                public void onFailure(Call<EventResponse> call, Throwable t)
                {
                    indicatorView.hide();
                }
            });
        }
    }
}
