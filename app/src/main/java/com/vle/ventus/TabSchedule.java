package com.vle.ventus;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TabSchedule extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);
//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

        List<Schedule> ScheduleList;
        ScheduleList = new ArrayList<>();
        ScheduleList.add(new Schedule("Schedule 1", "8:00AM", "10:00AM"));
        ScheduleList.add(new Schedule("Schedule 2", "10:00AM", "12:00PM"));
        ScheduleList.add(new Schedule("Schedule 3", "12:00PM", "2:00PM"));
        ScheduleList.add(new Schedule("Schedule 4", "2:00PM", "4:00PM"));
        ScheduleList.add(new Schedule("Schedule 5", "4:00PM", "6:00PM"));
        ScheduleList.add(new Schedule("Schedule 6", "6:00PM", "8:00PM"));
        ScheduleList.add(new Schedule("Schedule 7", "8:00PM", "10:00PM"));

        RecyclerView myrv = (RecyclerView) rootView.findViewById(R.id.fragment_schedule_recyclerview);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getActivity(), ScheduleList);
        myrv.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrv.setAdapter(myAdapter);
//            myrv.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

        private Context mContext;
        private List<Schedule> mData;

        public RecyclerViewAdapter(Context mContext, List<Schedule> mData) {
            this.mContext = mContext;
            this.mData = mData;
        }

        @Override
        public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            View view = mInflater.inflate(R.layout.cardview_schedule, parent, false);
            return new RecyclerViewAdapter.MyViewHolder(view);
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView schedule_name;
            TextView schedule_time_start;
            TextView schedule_time_end;


            public MyViewHolder(View itemView) {
                super(itemView);

                schedule_name = (TextView) itemView.findViewById(R.id.schedule_name);
                schedule_time_start = (TextView) itemView.findViewById(R.id.schedule_time_start);
                schedule_time_end = (TextView) itemView.findViewById(R.id.schedule_time_end);

            }
        }

        @Override
        public void onBindViewHolder(RecyclerViewAdapter.MyViewHolder holder, int position) {
            holder.schedule_name.setText(mData.get(position).getName());
            holder.schedule_time_start.setText(mData.get(position).getTimeStart());
            holder.schedule_time_end.setText(mData.get(position).getTimeEnd());


//        if (position % 2 == 0)
//            holder.schedule_layout.setBackgroundColor(Color.RED);
//        else
//            holder.schedule_layout.setBackgroundColor(Color.BLUE);

        }

        @Override
        public int getItemCount() {
            return mData.size();
        }


    }


}
