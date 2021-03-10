package com.foodforme.vendore.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.foodforme.vendore.R;
import com.foodforme.vendore.SetterGatter.SetGet;
import com.foodforme.vendore.activity.OrderStatusActivity;
import com.foodforme.vendore.customwidget.CustomTextView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class InProgressAdapter extends RecyclerView.Adapter<InProgressAdapter.ViewHolder> {

    private static final String TAG = NewOrderAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<SetGet> Inprogress_orderlist;
    SharedPreferences preferences;

    public InProgressAdapter(Context context, ArrayList<SetGet> inprogress_orderlist) {
        mContext = context;
        Inprogress_orderlist = inprogress_orderlist;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inprogress_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {

        preferences = mContext.getSharedPreferences("Vendor", MODE_PRIVATE);

        viewHolder.restrorent_name.setText(Inprogress_orderlist.get(position).getRest_name());
        viewHolder.order_delivery_time.setText(Inprogress_orderlist.get(position).getOrder_date_time());
        viewHolder.order_id.setText("#" + Inprogress_orderlist.get(position).getUnique_order_id());
        viewHolder.order_amount.setText(mContext.getResources().getString(R.string.price)  + Inprogress_orderlist.get(position).getOrder_total());
        viewHolder.order_payment_type.setText(Inprogress_orderlist.get(position).getPayment_type());
        viewHolder.order_delivery_address.setText(Inprogress_orderlist.get(position).getOrder_deliveryadd1());

        int OrderStatus = Integer.parseInt(Inprogress_orderlist.get(position).getOrder_status());

        if (OrderStatus == 4) {
            viewHolder.order_status.setText(mContext.getResources().getString(R.string.confirm_order));
        } else if (OrderStatus == 5) {
            viewHolder.order_status.setText((mContext.getResources().getString(R.string.ready_order)));
        } else if (OrderStatus == 7) {
            viewHolder.order_status.setText((mContext.getResources().getString(R.string.ready_order)));
        } else if (OrderStatus == 8) {
            viewHolder.order_status.setText(mContext.getResources().getString(R.string.Order_acceped_by_driver));
        } else if (OrderStatus == 9) {
            viewHolder.order_status.setText(mContext.getResources().getString(R.string.Order_picked_by_driver));
        } else if (OrderStatus == 10) {
            viewHolder.order_status.setText(mContext.getResources().getString(R.string.Order_On_the_way));
        }

        viewHolder.orderlist_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, OrderStatusActivity.class);
                intent.putExtra("order_id", Inprogress_orderlist.get(position).getOrder_id());
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return Inprogress_orderlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CustomTextView restrorent_name, order_delivery_time, order_id, order_amount,
                order_payment_type, order_delivery_address, order_status;

        CardView orderlist_relative;

        ViewHolder(View view) {
            super(view);
            restrorent_name = view.findViewById(R.id.restrorent_name);
            order_delivery_time = view.findViewById(R.id.order_delivery_time);
            order_id = view.findViewById(R.id.order_id);
            order_amount = view.findViewById(R.id.order_amount);
            order_payment_type = view.findViewById(R.id.order_payment_type);
            order_delivery_address = view.findViewById(R.id.order_delivery_address);
            order_status = view.findViewById(R.id.order_status);
            orderlist_relative = view.findViewById(R.id.orderlist_relative);
        }
    }
}
