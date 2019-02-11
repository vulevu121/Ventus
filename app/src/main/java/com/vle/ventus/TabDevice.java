package com.vle.ventus;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TabDevice extends Fragment {
    List<Device> DeviceList;
    RecyclerViewAdapter myAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_device, container, false);
//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

        DeviceList = new ArrayList<>();
        DeviceList.add(new Device("Vent 1", 75, 72, "92%"));
        DeviceList.add(new Device("Vent 2", 77, 72, "88%"));
        DeviceList.add(new Device("Vent 3", 67, 72, "75%"));
        DeviceList.add(new Device("Vent 4", 66, 75, "97%"));
        DeviceList.add(new Device("Vent 5", 69, 75, "67%"));
        DeviceList.add(new Device("Vent 6", 65, 72, "87%"));
        DeviceList.add(new Device("Vent 7", 68, 77, "91%"));

        RecyclerView myrv = (RecyclerView) rootView.findViewById(R.id.fragment_device_recyclerview);
        myAdapter = new RecyclerViewAdapter(getActivity(), DeviceList);
        myrv.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrv.setAdapter(myAdapter);
//            myrv.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

        private Context mContext;
        private List<Device> mData;

        public RecyclerViewAdapter(Context mContext, List<Device> mData) {
            this.mContext = mContext;
            this.mData = mData;
        }

        @Override
        public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            View view = mInflater.inflate(R.layout.cardview_device, parent, false);
            return new RecyclerViewAdapter.MyViewHolder(view);
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView device_name;
            TextView device_target_temp;
            TextView device_curent_temp;
            TextView device_battery;
            LinearLayout device_layout;
            ImageButton device_temp_up;
            ImageButton device_temp_down;


            public MyViewHolder(View itemView) {
                super(itemView);

                device_layout = (LinearLayout) itemView.findViewById(R.id.device_layout);
                device_name = (TextView) itemView.findViewById(R.id.device_name);
                device_target_temp = (TextView) itemView.findViewById(R.id.device_target_temp);
                device_curent_temp = (TextView) itemView.findViewById(R.id.device_current_temp);
                device_battery = (TextView) itemView.findViewById(R.id.device_battery);
                device_temp_up = (ImageButton) itemView.findViewById(R.id.device_temp_up);
                device_temp_down = (ImageButton) itemView.findViewById(R.id.device_temp_down);

            }
        }

        @Override
        public void onBindViewHolder(RecyclerViewAdapter.MyViewHolder holder, final int position) {
            holder.device_name.setText(mData.get(position).getName());
            holder.device_target_temp.setText(mData.get(position).getTargetTemp().toString());
            holder.device_curent_temp.setText(mData.get(position).getCurrentTemp().toString() + "°F");
            holder.device_battery.setText(mData.get(position).getBattery().toString());

            holder.device_temp_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getContext(), "Temp Up", Toast.LENGTH_LONG).show();
                    DeviceList.get(position).TargetTemp++;
                    myAdapter.notifyDataSetChanged();
                }
            });

            holder.device_temp_down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getContext(), "Temp Down", Toast.LENGTH_LONG).show();
                    DeviceList.get(position).TargetTemp--;
                    myAdapter.notifyDataSetChanged();
                }
            });

//        if (position % 2 == 0)
//            holder.device_layout.setBackgroundColor(Color.RED);
//        else
//            holder.device_layout.setBackgroundColor(Color.BLUE);

        }

        @Override
        public int getItemCount() {
            return mData.size();
        }


    }


}
