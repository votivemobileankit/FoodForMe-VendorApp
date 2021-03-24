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

public class NewOrderAdapter extends RecyclerView.Adapter<NewOrderAdapter.ViewHolder> {

    private static final String TAG = NewOrderAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<SetGet> Orderlist;
    SharedPreferences preferences;

    public NewOrderAdapter(Context context, ArrayList<SetGet> orderlist) {
        mContext = context;
        Orderlist = orderlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_order_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        preferences = mContext.getSharedPreferences("Vendor", MODE_PRIVATE);

        viewHolder.restrorent_name.setText(Orderlist.get(position).getRest_name());
        viewHolder.order_delivery_time.setText(Orderlist.get(position).getOrder_date_time());
        viewHolder.order_id.setText("#" + Orderlist.get(position).getUnique_order_id());
        viewHolder.order_amount.setText(mContext.getResources().getString(R.string.price) +" " + Orderlist.get(position).getOrder_total());
        viewHolder.order_payment_type.setText(Orderlist.get(position).getPayment_type());
        viewHolder.order_delivery_address.setText(Orderlist.get(position).getOrder_deliveryadd1());

        viewHolder.orderlist_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, OrderStatusActivity.class);
                intent.putExtra("order_id", Orderlist.get(position).getOrder_id());
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return Orderlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CustomTextView restrorent_name, order_delivery_time, order_id, order_amount, order_payment_type, order_delivery_address;
        CardView orderlist_relative;

        ViewHolder(View view) {
            super(view);
            restrorent_name = view.findViewById(R.id.restrorent_name);
            order_delivery_time = view.findViewById(R.id.order_delivery_time);
            order_id = view.findViewById(R.id.order_id);
            order_amount = view.findViewById(R.id.order_amount);
            order_payment_type = view.findViewById(R.id.order_payment_type);
            order_delivery_address = view.findViewById(R.id.order_delivery_address);
            orderlist_relative = view.findViewById(R.id.orderlist_relative);

        }
    }
}
