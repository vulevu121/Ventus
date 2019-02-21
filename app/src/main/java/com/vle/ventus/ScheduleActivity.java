package com.vle.ventus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class ScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        final SeekBar seekBarStart = (SeekBar) findViewById(R.id.seekBar_start);
        final SeekBar seekBarEnd = (SeekBar) findViewById(R.id.seekBar_end);
        final TextView time_start = (TextView) findViewById(R.id.time_start);
        final TextView time_end = (TextView) findViewById(R.id.time_end);

        time_start.setText(getHalfHourTime(seekBarStart.getProgress()));
        time_end.setText(getHalfHourTime(seekBarEnd.getProgress()));

        seekBarStart.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Integer seekBarProgress = seekBarStart.getProgress();
                time_start.setText(getHalfHourTime(seekBarProgress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarEnd.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Integer seekBarProgress = seekBarEnd.getProgress();
                time_end.setText(getHalfHourTime(seekBarProgress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private String getHalfHourTime(int x) {
        String timeStartString = "";
        switch (x) {
            case 0: timeStartString = "12:00 AM"; break;
            case 1: timeStartString = "12:30 AM"; break;
            case 2: timeStartString = "1:00 AM"; break;
            case 3: timeStartString = "1:30 AM"; break;
            case 4: timeStartString = "2:00 AM"; break;
            case 5: timeStartString = "2:30 AM"; break;
            case 6: timeStartString = "3:00 AM"; break;
            case 7: timeStartString = "3:30 AM"; break;
            case 8: timeStartString = "4:00 AM"; break;
            case 9: timeStartString = "4:30 AM"; break;
            case 10: timeStartString = "5:00 AM"; break;
            case 11: timeStartString = "5:30 AM"; break;
            case 12: timeStartString = "6:00 AM"; break;
            case 13: timeStartString = "6:30 AM"; break;
            case 14: timeStartString = "7:00 AM"; break;
            case 15: timeStartString = "7:30 AM"; break;
            case 16: timeStartString = "8:00 AM"; break;
            case 17: timeStartString = "8:30 AM"; break;
            case 18: timeStartString = "9:00 AM"; break;
            case 19: timeStartString = "9:30 AM"; break;
            case 20: timeStartString = "10:00 AM"; break;
            case 21: timeStartString = "10:30 AM"; break;
            case 22: timeStartString = "11:00 AM"; break;
            case 23: timeStartString = "11:30 AM"; break;
            case 24: timeStartString = "12:00 PM"; break;
            case 25: timeStartString = "12:30 PM"; break;
            case 26: timeStartString = "1:00 PM"; break;
            case 27: timeStartString = "1:30 PM"; break;
            case 28: timeStartString = "2:00 PM"; break;
            case 29: timeStartString = "2:30 PM"; break;
            case 30: timeStartString = "3:00 PM"; break;
            case 31: timeStartString = "3:30 PM"; break;
            case 32: timeStartString = "4:00 PM"; break;
            case 33: timeStartString = "4:30 PM"; break;
            case 34: timeStartString = "5:00 PM"; break;
            case 35: timeStartString = "5:30 PM"; break;
            case 36: timeStartString = "6:00 PM"; break;
            case 37: timeStartString = "6:30 PM"; break;
            case 38: timeStartString = "7:00 PM"; break;
            case 39: timeStartString = "7:30 PM"; break;
            case 40: timeStartString = "8:00 PM"; break;
            case 41: timeStartString = "8:30 PM"; break;
            case 42: timeStartString = "9:00 PM"; break;
            case 43: timeStartString = "9:30 PM"; break;
            case 44: timeStartString = "10:00 PM"; break;
            case 45: timeStartString = "10:30 PM"; break;
            case 46: timeStartString = "11:00 PM"; break;
            case 47: timeStartString = "11:30 PM"; break;
        }
        return timeStartString;
    }
}
