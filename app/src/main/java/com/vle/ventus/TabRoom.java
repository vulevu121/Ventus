package com.vle.ventus;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;



public class TabRoom extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    List<Room> RoomList;
    RecyclerViewAdapter myAdapter;
    FirebaseUser user;
    private ProgressBar progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_room, container, false);
//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        mAuth = FirebaseAuth.getInstance();
        RoomList = new ArrayList<>();

        // get current user and all rooms from user
        user = mAuth.getCurrentUser();
        if (user != null) {
            final String userEmail = user.getEmail();

            getAllRoomsFromUser(userEmail);
        }


        progress = (ProgressBar) rootView.findViewById(R.id.tabProgressBar);
        showProgress();

//        RoomList.add(new Room("Living Room", 75, 72));
//        RoomList.add(new Room("Dining Room", 77, 72));
//        RoomList.add(new Room("Office", 67, 72));
//        RoomList.add(new Room("Tri's Room", 66, 75));
//        RoomList.add(new Room("Khanh's Room", 69, 75));
//        RoomList.add(new Room("John's Room", 65, 72));
//        RoomList.add(new Room("Vu's Room", 68, 77));

        RecyclerView myrv = rootView.findViewById(R.id.fragment_room_recyclerview);
        myAdapter = new RecyclerViewAdapter(getActivity(), RoomList);
        myrv.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrv.setAdapter(myAdapter);
//        myrv.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }

    private void getAllRoomsFromUser(final String userEmail) {
        String path = "/user/" + userEmail + "/room";
        db.collection(path)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Integer i = 0;
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                String roomId = doc.getId();

                                Integer currentTemp = 0;
                                Integer currentHumidity = 0;
                                Integer targetTemp = 0;
                                Integer batteryPercent = 0;

                                try {
                                    currentTemp = Integer.valueOf(doc.get("current_temp").toString());
                                    currentHumidity = Integer.valueOf(doc.get("current_humidity").toString());
                                    targetTemp = Integer.valueOf(doc.get("target_temp").toString());
                                    batteryPercent = Integer.valueOf(doc.get("battery_percent").toString());
                                } catch (Exception e) {
                                    currentTemp = 9999;
                                    currentHumidity = 9999;
                                    targetTemp = 9999;
                                    batteryPercent = 9999;
                                }

                                addRoom(roomId, currentTemp, currentHumidity, targetTemp, batteryPercent);
                                addRoomListener(userEmail, roomId, i++);
                                hideProgress();
                            }
                        }
                    }
                });
    }

    private void addRoomListener(String userEmail, String room, final Integer position) {
        String path = "/user/" + userEmail + "/room/" + room;
        db.document(path)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                        if (snapshot != null && snapshot.exists()) {
                            // set all parameters in room list from snapshot data and notify the adapter to update the values on view
                            RoomList.get(position).setCurrentTemp(Integer.parseInt(snapshot.get("current_temp").toString()));
                            RoomList.get(position).setCurrentHumidity(Integer.parseInt(snapshot.get("current_humidity").toString()));
//                            RoomList.get(position).setTargetTemp(Integer.parseInt(snapshot.get("target_temp").toString()));
                            RoomList.get(position).setBatteryPercent(Integer.parseInt(snapshot.get("battery_percent").toString()));
                            myAdapter.notifyItemChanged(position);
                        }
                    }
                });
    }

    private void getAllDevicesFromRoom(String userEmail, String room) {
        String path = "/user/" + userEmail + "/room/" + room + "/device";
        db.collection(path)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                String deviceId = doc.getId();
                            }
                        }
                    }
                });
    }

    private void setRoomTargetTemp(String userEmail, String room, Integer targetTemp) {
        String path = "/user/" + userEmail + "/room/" + room;
        db.document(path).update("target_temp", targetTemp);
    }

    private void addRoom(String room, Integer currentTemp, Integer currentHumidity, Integer targetTemp, Integer batteryPercent) {
        RoomList.add(new Room(room, currentTemp, currentHumidity, targetTemp, batteryPercent));
        myAdapter.notifyDataSetChanged();
    }


    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

        private Context mContext;
        private List<Room> mData;

        public RecyclerViewAdapter(Context mContext, List<Room> mData) {
            this.mContext = mContext;
            this.mData = mData;
        }

        @Override
        public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            View view = mInflater.inflate(R.layout.cardview_room, parent, false);
            return new RecyclerViewAdapter.MyViewHolder(view);
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView room_name;
//            ImageView room_img_thumbnail;
            TextView room_current_temp;
            TextView room_current_humidity;
            TextView room_target_temp;
            LinearLayout room_layout;
            ImageButton room_temp_up;
            ImageButton room_temp_down;
            TextView room_battery_percent;


            public MyViewHolder(View itemView) {
                super(itemView);

                room_name = (TextView) itemView.findViewById(R.id.room_name);
                room_current_temp = (TextView) itemView.findViewById(R.id.room_current_temp);
                room_current_humidity = (TextView) itemView.findViewById(R.id.room_current_humidity);
                room_target_temp = (TextView) itemView.findViewById(R.id.room_target_temp);
                room_temp_up = (ImageButton) itemView.findViewById(R.id.room_temp_up);
                room_temp_down = (ImageButton) itemView.findViewById(R.id.room_temp_down);
                room_battery_percent = (TextView) itemView.findViewById(R.id.room_battery_percent);

            }
        }

        @Override
        public void onBindViewHolder(final RecyclerViewAdapter.MyViewHolder holder, final int position) {
            holder.room_name.setText(mData.get(position).getName());
//            holder.room_img_thumbnail.setImageResource(mData.get(position).getThumbnail());

            Integer currentTemp = mData.get(position).getCurrentTemp();
            Integer currentHumidity = mData.get(position).getCurrentHumidity();
            Integer targetTemp = mData.get(position).getTargetTemp();
            Integer batteryPercent = mData.get(position).getBatteryPercent();

            if (currentTemp != 9999) {
                holder.room_current_temp.setText(getString(R.string.tempf_fmt, currentTemp.toString()));
            } else {
                holder.room_current_temp.setText(R.string.no_data);
            }

            if (currentHumidity != 9999) {
                holder.room_current_humidity.setText(getString(R.string.percent_fmt, currentHumidity.toString()));
            } else {
                holder.room_current_humidity.setText(R.string.no_data);
            }

            if (targetTemp != 9999) {
                holder.room_target_temp.setText(getString(R.string.tempf_fmt, targetTemp.toString()));
            } else {
                holder.room_target_temp.setText(R.string.no_data);
            }

            if (batteryPercent != 9999) {
                holder.room_battery_percent.setText(getString(R.string.percent_fmt, batteryPercent.toString()));
            } else {
                holder.room_battery_percent.setText("N/A");
            }


            holder.room_temp_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RoomList.get(position).TargetTemp++;
                    setRoomTargetTemp(user.getEmail(), RoomList.get(position).getName(), RoomList.get(position).TargetTemp);
                    myAdapter.notifyItemChanged(position);
                }
            });

            holder.room_temp_down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RoomList.get(position).TargetTemp--;
                    setRoomTargetTemp(user.getEmail(), RoomList.get(position).getName(), RoomList.get(position).TargetTemp);
                    myAdapter.notifyItemChanged(position);
                }
            });

//        if (position % 2 == 0)
//            holder.room_layout.setBackgroundColor(Color.RED);
//        else
//            holder.room_layout.setBackgroundColor(Color.BLUE);

        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

    }

    private void showProgress() {
        if (progress != null) {
            progress.setVisibility(View.VISIBLE);
        }

    }

    private void hideProgress() {
        if (progress != null) {
            progress.setVisibility(View.GONE);
        }

    }

}