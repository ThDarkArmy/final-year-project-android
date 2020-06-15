package com.tda.finalyear.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.tda.finalyear.R;
import com.tda.finalyear.activities.fee.EditFeeActivity;
import com.tda.finalyear.activities.fee.FeeActivity;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.FeeList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeeAdapter extends RecyclerView.Adapter<FeeAdapter.FeeViewHolder> {
    Context context;
    private FeeList feeList;
    ConstraintLayout mainLayout;

    public FeeAdapter(Context context, FeeList feeList) {
        this.context = context;
        this.feeList = feeList;
    }
    @NonNull
    @Override
    public FeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fee_card_view, parent, false);
        return new FeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeeViewHolder holder, int position) {
        holder.std.setText(feeList.getFees().get(position).getStd());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EditFeeActivity.class);
                i.putExtra("FEE_ID", feeList.getFees().get(position).getId());
                i.putExtra("FEE_STD", feeList.getFees().get(position).getStd());
                i.putExtra("ADMISSION_FEE", feeList.getFees().get(position).getAdmissionFee());
                i.putExtra("EXAM_FEE", feeList.getFees().get(position).getExamFee());
                i.putExtra("TUITION_FEE", feeList.getFees().get(position).getTuitionFee());
                i.putExtra("MONTH", feeList.getFees().get(position).getMonth());
                i.putExtra("YEAR", feeList.getFees().get(position).getYear());
                context.startActivity(i);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitClient.getInstance().getFeeService().deleteFee(feeList.getFees().get(position).getId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            feeList.getFees().remove(position);
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, "Delete Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, FeeActivity.class);
                i.putExtra("FEE_ID", feeList.getFees().get(position).getId());
                i.putExtra("FEE_STD", feeList.getFees().get(position).getStd());
                i.putExtra("ADMISSION_FEE", feeList.getFees().get(position).getAdmissionFee());
                i.putExtra("EXAM_FEE", feeList.getFees().get(position).getExamFee());
                i.putExtra("TUITION_FEE", feeList.getFees().get(position).getTuitionFee());
                i.putExtra("MONTH", feeList.getFees().get(position).getMonth());
                i.putExtra("YEAR", feeList.getFees().get(position).getYear());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return feeList.getFees().size();
    }

    public class FeeViewHolder extends RecyclerView.ViewHolder{
        TextView std;
        Button edit, delete;
        public FeeViewHolder(@NonNull View itemView) {
            super(itemView);
            std = itemView.findViewById(R.id.std);
            edit = itemView.findViewById(R.id.edit_fee_card);
            delete = itemView.findViewById(R.id.delete_fee_card);
            mainLayout = itemView.findViewById(R.id.fee_card);
        }
    }
}
