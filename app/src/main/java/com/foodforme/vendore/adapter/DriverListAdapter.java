package com.foodforme.vendore.adapter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.foodforme.vendore.R;
import com.foodforme.vendore.SetterGatter.AddonDatum;
import com.foodforme.vendore.SetterGatter.Example;
import com.foodforme.vendore.SetterGatter.OrderCarditem;
import com.foodforme.vendore.SetterGatter.OrderDetail;
import com.foodforme.vendore.serverintegration.Constant;
import com.foodforme.vendore.utils.ApplicationController;
import com.foodforme.vendore.utils.Utility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class DriverListAdapter extends RecyclerView.Adapter<DriverListAdapter.RecyclerViewViewHolder> {

    List<AddonDatum> mFoodAddonList;
    private View mView;
    Context mContext;
    JSONObject jsonObject;
    String requestBody;

    @NonNull
    @Override
    public RecyclerViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_list, parent, false);
        RecyclerViewViewHolder viewHolder = new RecyclerViewViewHolder(mView);
        return viewHolder;
    }


    public DriverListAdapter(Context m) {
        //  this.mFoodAddonList = mList;
        this.mContext = m;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewViewHolder holder, int position) {


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignOrderMethod();
            }
        });
    }


    @Override
    public int getItemCount() {
        return 5;
    }

    public class RecyclerViewViewHolder extends RecyclerView.ViewHolder {

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }

    private void assignOrderMethod() {

        try {
            jsonObject.put("email", "");
            jsonObject.put("lang_id", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestBody = jsonObject.toString();
        StringRequest signinRequest = new StringRequest(Request.Method.POST, Constant.ForgotPasswordAPI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("", "onResponse: " + response);
                        try {
                            JSONObject jObject = new JSONObject(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", "onErrorResponse: " + error.getMessage());


                    }
                }) {


            @Override
            public String getBodyContentType() {
                return String.format("application/json; charset=utf-8");
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return requestBody == null ? null : requestBody.getBytes(StandardCharsets.UTF_8);
            }
        };
        requestQueue.add(signinRequest);


    }
}

