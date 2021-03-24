package com.foodforme.vendore.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foodforme.vendore.R;
import com.foodforme.vendore.SetterGatter.SetGet;
import com.foodforme.vendore.customwidget.CustomTextView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder> {

    private static final String TAG = TransactionHistoryAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<SetGet.orderdetail> CartItemlist;

    SharedPreferences preferences;

    public TransactionHistoryAdapter(Context context, ArrayList<SetGet.orderdetail> cartItemlist) {
        mContext = context;
        CartItemlist = cartItemlist;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transation_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        preferences = mContext.getSharedPreferences("Vendor", MODE_PRIVATE);

        viewHolder.player_name.setText("FoodForMe");
        viewHolder.user_name.setText("vendor");
        viewHolder.transaction_amount.setText(mContext.getResources().getString(R.string.price) );
        viewHolder.transaction_date_txt.setText("22-10-2019");

    }

    @Override
    public int getItemCount() {
        return CartItemlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CustomTextView player_name, user_name, transaction_date_txt, transaction_amount;
        ImageView player_image;

        ViewHolder(View view) {
            super(view);

            player_name = view.findViewById(R.id.player_name);
            user_name = view.findViewById(R.id.user_name);
            transaction_date_txt = view.findViewById(R.id.transaction_date_txt);
            transaction_amount = view.findViewById(R.id.transaction_amount);

            player_image = view.findViewById(R.id.player_image);


        }
    }
}
