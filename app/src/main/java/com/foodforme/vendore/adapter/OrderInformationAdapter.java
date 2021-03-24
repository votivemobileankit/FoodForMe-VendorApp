package com.foodforme.vendore.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodforme.vendore.R;
import com.foodforme.vendore.SetterGatter.OrderCarditem;
import com.foodforme.vendore.SetterGatter.SetGet;
import com.foodforme.vendore.customwidget.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class OrderInformationAdapter extends RecyclerView.Adapter<OrderInformationAdapter.ViewHolder> {

    private static final String TAG = OrderInformationAdapter.class.getSimpleName();
    private final Context mContext;
    private final List<OrderCarditem> mOrderCarditemList;
    SharedPreferences preferences;
    AddonAdapter mAddonAdapter;

    public OrderInformationAdapter(Context context, List<OrderCarditem> cartItemlist) {
        mContext = context;
        mOrderCarditemList = cartItemlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_information_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        preferences = mContext.getSharedPreferences("Vendor", MODE_PRIVATE);
        viewHolder.order_name.setText(mOrderCarditemList.get(position).getName());
        viewHolder.order_quantity.setText(mContext.getResources().getString(R.string.quantity) + mOrderCarditemList.get(position).getQty());
        viewHolder.order_price.setText(mContext.getResources().getString(R.string.price) +" "+ mOrderCarditemList.get(position).getPrice());
        viewHolder.specail_add_tv.setText("" + mOrderCarditemList.get(position).getInstruction());

        if (!mOrderCarditemList.get(position).getFoodSizeDetail().isEmpty()) {
            viewHolder.itme_size_tv.setText(mContext.getResources().getString(R.string.size) + mOrderCarditemList.get(position).getFoodSizeDetail().get(0).getMenuItemTitle());

        }

        if (mOrderCarditemList.get(position).getOptions().getAddonData().size() > 0) {
            viewHolder.mAddonList.setHasFixedSize(true);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
            viewHolder.mAddonList.setLayoutManager(mLayoutManager);
            mAddonAdapter = new AddonAdapter(mContext, mOrderCarditemList.get(position).getOptions().getAddonData());
            viewHolder.mAddonList.setAdapter(mAddonAdapter);
            mAddonAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return mOrderCarditemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CustomTextView order_name, order_quantity, order_price;
        TextView specail_add_tv, itme_size_tv;
        RecyclerView mAddonList;

        ViewHolder(View view) {
            super(view);

            order_name = view.findViewById(R.id.order_name);
            order_price = view.findViewById(R.id.order_price);
            order_quantity = view.findViewById(R.id.order_quantity);
            specail_add_tv = view.findViewById(R.id.specail_add_tv);
            itme_size_tv = view.findViewById(R.id.itme_size_tv);
            mAddonList = view.findViewById(R.id.mAddonList);

        }
    }
}
