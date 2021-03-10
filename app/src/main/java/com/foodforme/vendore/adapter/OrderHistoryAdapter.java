package com.foodforme.vendore.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foodforme.vendore.R;
import com.foodforme.vendore.SetterGatter.SetGet;
import com.foodforme.vendore.activity.OrderStatusActivity;
import com.foodforme.vendore.customwidget.CustomTextView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {

    private static final String TAG = OrderHistoryAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<SetGet.OrderHistory> HistoryOrderList;
    SharedPreferences preferences;

    public OrderHistoryAdapter(Context context, ArrayList<SetGet.OrderHistory> cartItemlist) {
        mContext = context;
        HistoryOrderList = cartItemlist;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_order_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {

        preferences = mContext.getSharedPreferences("Vendor", MODE_PRIVATE);

        viewHolder.restrorent_name.setText(HistoryOrderList.get(position).getRest_name());
        viewHolder.order_delivery_time.setText(HistoryOrderList.get(position).getOrder_date_time());
        viewHolder.order_id.setText("# " + HistoryOrderList.get(position).getUnique_order_id());

        Log.e("Payment_type", HistoryOrderList.get(position).getPayment_type());

        if (HistoryOrderList.get(position).getPayment_type().equals("Card")) {
            viewHolder.payment_type_txt.setText(HistoryOrderList.get(position).getPayment_type());
        } else if (HistoryOrderList.get(position).getPayment_type().equals("Wallet")) {
            viewHolder.payment_type_txt.setText(HistoryOrderList.get(position).getPayment_type());
        } else {
            viewHolder.payment_type_txt.setText(HistoryOrderList.get(position).getPayment_type() + " " + mContext.getResources().getString(R.string.onDelivery));
        }

        int OrderStatus = Integer.parseInt(HistoryOrderList.get(position).getOrder_status());

        if (OrderStatus == 6) {
            viewHolder.order_status.setText(mContext.getResources().getString(R.string.cancelled));
        } else if (OrderStatus == 11) {
            viewHolder.order_status.setText(mContext.getResources().getString(R.string.delivered));
        }


        viewHolder.view_detail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, OrderStatusActivity.class);
                intent.putExtra("order_id", HistoryOrderList.get(position).getOrder_id());
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return HistoryOrderList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CustomTextView restrorent_name, order_delivery_time, order_id, order_status, payment_type_txt;
        Button view_detail_btn;


        ViewHolder(View view) {
            super(view);

            restrorent_name = view.findViewById(R.id.restrorent_name);
            order_delivery_time = view.findViewById(R.id.order_delivery_time);
            order_id = view.findViewById(R.id.order_id);
            order_status = view.findViewById(R.id.order_status);
            payment_type_txt = view.findViewById(R.id.payment_type_txt);
            view_detail_btn = view.findViewById(R.id.view_detail_btn);


        }
    }
}
