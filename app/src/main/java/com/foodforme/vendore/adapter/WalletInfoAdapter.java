package com.foodforme.vendore.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foodforme.vendore.R;
import com.foodforme.vendore.SetterGatter.WalletInfo;
import com.foodforme.vendore.customwidget.CustomTextView;
import com.foodforme.vendore.utils.TransparentProgressDialog;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class WalletInfoAdapter extends RecyclerView.Adapter<WalletInfoAdapter.ViewHolder> {
    private final List<WalletInfo> walletInfoList;
    TransparentProgressDialog progressDialog;
    Context context;
    private static final String TAG = WalletInfoAdapter.class.getSimpleName();

    SharedPreferences preferences;

    public WalletInfoAdapter(Context context, ArrayList<WalletInfo> walletInfos) {
        this.context = context;
        this.walletInfoList = walletInfos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.transation_item, parent, false);
        progressDialog = TransparentProgressDialog.getInstance();
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        preferences = context.getSharedPreferences("Vendor", MODE_PRIVATE);
        WalletInfo item = walletInfoList.get(position);

        String str = item.getTxn_method();
        String status = item.getTransfer_status();

        if (str.equalsIgnoreCase("wallet")) {
            holder.player_name.setText(context.getResources().getString(R.string.Wallet_transaction));
            holder.user_name.setText(context.getResources().getString(R.string.Paid_by_admin));

        } else if (str.equalsIgnoreCase("bank")) {
            holder.player_name.setText(context.getResources().getString(R.string.Bank_transaction));

        }
        if (status.equalsIgnoreCase("0")) {
            holder.user_name.setText(context.getResources().getString(R.string.Transfer_request_is_panding));

        } else if (status.equalsIgnoreCase("1")) {
            holder.user_name.setText(context.getResources().getString(R.string.Transfer_request_rejected));
        } else if (status.equalsIgnoreCase("2")) {
            holder.user_name.setText(context.getResources().getString(R.string.Transfer_completed));
        }
        holder.transaction_date_txt.setText(item.getCreated_at());
        holder.transaction_amount.setText(context.getResources().getString(R.string.price) + " " + item.getVendor_amount());


    }

    @Override
    public int getItemCount() {
        return walletInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CustomTextView player_name, user_name, transaction_date_txt, transaction_amount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            player_name = itemView.findViewById(R.id.player_name);
            user_name = itemView.findViewById(R.id.user_name);
            transaction_date_txt = itemView.findViewById(R.id.transaction_date_txt);
            transaction_amount = itemView.findViewById(R.id.transaction_amount);
        }
    }
}
