package com.foodforme.vendore.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foodforme.vendore.R;
import com.foodforme.vendore.SetterGatter.SetGet;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class NotificationAdapter extends RecyclerView.Adapter
        <NotificationAdapter.ViewHolder> {

    private static final String TAG = NotificationAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<SetGet.NotificationList> OrderNotificationList;
    SharedPreferences preferences;

    public NotificationAdapter(Context context, ArrayList<SetGet.NotificationList> cartItemlist) {
        mContext = context;
        OrderNotificationList = cartItemlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        preferences = mContext.getSharedPreferences("Vendor", MODE_PRIVATE);
        viewHolder.notification_title.setText(OrderNotificationList.get(position).getNoti_title());
        viewHolder.notification_desc.setText(OrderNotificationList.get(position).getNoti_desc());
        viewHolder.notification_time.setText(OrderNotificationList.get(position).getCreated_at());

    }

    @Override
    public int getItemCount() {
        return OrderNotificationList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView notification_title, notification_desc, notification_time;

        ViewHolder(View view) {
            super(view);
            notification_title = view.findViewById(R.id.notification_title);
            notification_desc = view.findViewById(R.id.notification_desc);
            notification_time = view.findViewById(R.id.notification_time);
        }
    }
}
