package com.vle.ventus;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
    private FirebaseAuth mAuth;
    private ProgressBar progress;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user;
    List<Room> RoomList;
    RecyclerViewAdapter myAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_room, container, false);
        // get firebase auth instance
        mAuth = FirebaseAuth.getInstance();

        // list for all rooms
        RoomList = new ArrayList<>();

        // get current user and all rooms from user
        user = mAuth.getCurrentUser();
        if (user != null) {
            final String userEmail = user.getEmail();

            getAllRoomsFromUser(userEmail);
        }

        // spinning progress while loading all rooms and data
        progress = (ProgressBar) rootView.findViewById(R.id.tabProgressBar);
        showProgress();

        // create a scrollable list of elements based on large data sets or data
        // that frequently changes
        RecyclerView myrv = rootView.findViewById(R.id.fragment_room_recyclerview);

        // RecyclerView adapter that provides a binding from an app specific data set
        // to views that are displayed within a RecyclerView
        myAdapter = new RecyclerViewAdapter(getActivity(), RoomList);

        // set the RecyclerView layout manager
        myrv.setLayoutManager(new LinearLayoutManager(getActivity()));

        // set the RecyclerView adapter to the RecyclerView itself
        myrv.setAdapter(myAdapter);

        return rootView;
    }

    // get all rooms from user account and grab all data fields under each room
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

                                Integer currentTemp;
                                Integer currentHumidity;
                                Integer targetTemp;
                                Integer batteryPercent;

                                try {
                                    currentTemp = Integer.valueOf(doc.get("current_temp").toString());
                                    currentHumidity = Integer.valueOf(doc.get("current_humidity").toString());
                                    targetTemp = Integer.valueOf(doc.get("target_temp").toString());
                                    batteryPercent = Integer.valueOf(doc.get("battery_percent").toString());
                                } catch (Exception e) {
                                    currentTemp = null;
                                    currentHumidity = null;
                                    targetTemp = null;
                                    batteryPercent = null;
                                }
                                // add each room with their corresponding data fields
                                addRoom(roomId, currentTemp, currentHumidity, targetTemp, batteryPercent);

                                // add listeners to update app when data fields are changed
                                addRoomListener(userEmail, roomId, i++);
                                hideProgress();
                            }
                        }
                    }
                });
    }

    // add a snapshot listener for data fields of each room that may change like temp and humidity
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

                            // notify the view to update the displayed data
                            myAdapter.notifyItemChanged(position);
                        }
                    }
                });
    }

    // grab all devices from each room and corresponding data fields
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

    // update the target temp on firestore
    private void updateRoomTargetTempOnFirestore(String userEmail, String room, Integer targetTemp) {
        String path = "/user/" + userEmail + "/room/" + room;
        db.document(path).update("target_temp", targetTemp);
    }

    // add the room into the room list and notify the data set has changed
    private void addRoom(String room, Integer currentTemp, Integer currentHumidity, Integer targetTemp, Integer batteryPercent) {
        RoomList.add(new Room(room, currentTemp, currentHumidity, targetTemp, batteryPercent));
        myAdapter.notifyDataSetChanged();
    }

    // RecyclerView adapter, data set, and holder
    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
        private Context mContext;
        private List<Room> mData;

        public RecyclerViewAdapter(Context mContext, List<Room> mData) {
            this.mContext = mContext;
            this.mData = mData;
        }

        @Override
        public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // instantiates a layout into its corresponding view object
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            // instantiates a view for each room cardview
            View view = mInflater.inflate(R.layout.cardview_room, parent, false);
            return new RecyclerViewAdapter.MyViewHolder(view);
        }

        // describes an item view and metadata about its place within the RecyclerView
        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView room_name;
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
            Integer currentTemp = mData.get(position).getCurrentTemp();
            Integer currentHumidity = mData.get(position).getCurrentHumidity();
            Integer targetTemp = mData.get(position).getTargetTemp();
            Integer batteryPercent = mData.get(position).getBatteryPercent();

            if (currentTemp != null) {
                holder.room_current_temp.setText(getString(R.string.tempf_fmt, currentTemp.toString()));
            } else {
                holder.room_current_temp.setText(R.string.no_data);
            }

            if (currentHumidity != null) {
                holder.room_current_humidity.setText(getString(R.string.percent_fmt, currentHumidity.toString()));
            } else {
                holder.room_current_humidity.setText(R.string.no_data);
            }

            if (targetTemp != null) {
                holder.room_target_temp.setText(getString(R.string.tempf_fmt, targetTemp.toString()));
            } else {
                holder.room_target_temp.setText(R.string.no_data);
            }

            if (batteryPercent != null) {
                holder.room_battery_percent.setText(getString(R.string.percent_fmt, batteryPercent.toString()));
            } else {
                holder.room_battery_percent.setText(R.string.no_data);
            }

            // on click listeners for up and down buttons
            holder.room_temp_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RoomList.get(position).TargetTemp++;
                    updateRoomTargetTempOnFirestore(user.getEmail(), RoomList.get(position).getName(), RoomList.get(position).TargetTemp);
                    myAdapter.notifyItemChanged(position);
                }
            });

            holder.room_temp_down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RoomList.get(position).TargetTemp--;
                    updateRoomTargetTempOnFirestore(user.getEmail(), RoomList.get(position).getName(), RoomList.get(position).TargetTemp);
                    myAdapter.notifyItemChanged(position);
                }
            });
        // create striped pattern for each element
//        if (position % 2 == 0)
//            holder.room_layout.setBackgroundColor(Color.RED);
//        else
//            holder.room_layout.setBackgroundColor(Color.BLUE);

        }

        // get total number of rooms
        @Override
        public int getItemCount() {
            return mData.size();
        }

    }

    // show the progress circle
    private void showProgress() {
        if (progress != null) {
            progress.setVisibility(View.VISIBLE);
        }

    }

    // hide the progress circle
    private void hideProgress() {
        if (progress != null) {
            progress.setVisibility(View.GONE);
        }

    }

}