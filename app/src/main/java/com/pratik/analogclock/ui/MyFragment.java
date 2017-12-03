package com.pratik.analogclock.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;


import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.pratik.analogclock.R;
import com.pratik.analogclock.widget.ClockViewSurface;

import java.util.Calendar;


public class MyFragment extends Fragment implements ColorPickerDialogListener {
    private View returnView;
    private ClockViewSurface mClockViewSurface;
    private Button alarmButton;
    private Button cancelAlarm;
//    private Button color1;
//    private Button color2;
    private Button colorPicker;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;
    private String[] value = {"Color Preference 1","Color Preference 2","Color Preference 3"};
    final static int RQS_1 = 1;
    int hour_x;
    int minute_x;


    public MyFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        returnView = inflater.inflate(R.layout.fragment_my, container, false);
        alarmButton = (Button)returnView.findViewById(R.id.alarmButton);
        cancelAlarm = (Button)returnView.findViewById(R.id.unsetAlarm);
        //changeColor = (Button)returnView.findViewById(R.id.changeColor);
//        color1 = (Button)returnView.findViewById(R.id.color1);
//        color2 = (Button)returnView.findViewById(R.id.color2);
        colorPicker = (Button)returnView.findViewById(R.id.colorPickerButton);

        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mCurrentTime = Calendar.getInstance();
                int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mCurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                       Calendar calNow = Calendar.getInstance();
                       Calendar calset = (Calendar)calNow.clone();

                       calset.set(Calendar.HOUR_OF_DAY,hourOfDay);
                       calset.set(Calendar.MINUTE,minute);
                       calset.set(Calendar.SECOND,0);
                       calset.set(Calendar.MILLISECOND,0);

                        if(calset.compareTo(calNow) <= 0){
                            calset.add(Calendar.DATE, 1);
                        }
                        Toast.makeText(getContext(),"Alarm has been set",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getContext(),AlarmReceiver.class);
                        pendingIntent = PendingIntent.getBroadcast(getContext(),RQS_1,intent,0);
                        alarmManager = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calset.getTimeInMillis(),pendingIntent);
                        cancelAlarm.setVisibility(View.VISIBLE);
                    }
                },hour,minute,false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmManager.cancel(pendingIntent);
                Intent stopIntent = new Intent(getContext(),RingtonePlayingService.class);
                getActivity().stopService(stopIntent);
                Toast.makeText(getContext(),"Alarm Cancelled",Toast.LENGTH_SHORT).show();
            }
        });


//        color1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mClockViewSurface.changeColor1();
//            }
//        });
//
//        color2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mClockViewSurface.changeColor2();
//            }
//        });

        colorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialog colorPickerDialog =  ColorPickerDialog.newBuilder().setColor(Color.BLUE).create();
                colorPickerDialog.setColorPickerDialogListener(new ColorPickerDialogListener() {
                    @Override
                    public void onColorSelected(int dialogId, int color) {
                        mClockViewSurface.changeColor(color);
                    }

                    @Override
                    public void onDialogDismissed(int dialogId) {

                    }
                });
                colorPickerDialog.show(getFragmentManager(),"ColorPicker");
            }

        });

        return returnView;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initializeContent();
    }

    private void initializeContent(){
        mClockViewSurface = (ClockViewSurface) returnView.findViewById(R.id.clockView);
    }


    @Override
    public void onColorSelected(int dialogId, int color) {

    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }
}
