package com.tda.finalyear.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.tda.finalyear.R;
import com.tda.finalyear.activities.fee.FeePaymentActivity;
import com.tda.finalyear.models.FeeHistory;
import com.tda.finalyear.models.Student;

import java.util.List;

public class DefaulterStudentFeeAdapter extends RecyclerView.Adapter<DefaulterStudentFeeAdapter.DefaulterViewHodler> {
    List<Student> listOfDefaulter;
    Context context;

    public DefaulterStudentFeeAdapter(List<Student> listOfDefaulter, Context context) {
        System.out.println("DefaulterStudentFeeAdapter.DefaulterStudentFeeAdapter Inside the adapter class");
        this.listOfDefaulter = listOfDefaulter;
        this.context = context;
    }

    @NonNull
    @Override
    public DefaulterViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DefaulterViewHodler(LayoutInflater.from(context).inflate(R.layout.defaulter_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DefaulterViewHodler holder, int position) {
        List<FeeHistory> feeHistoryList = listOfDefaulter.get(position).getFeeHistory();
        FeeHistory feeHistory = feeHistoryList.get(feeHistoryList.size() - 1);
        if (!feeHistory.getIsPaid()) {
            holder.defaulterName.setText(listOfDefaulter.get(position).getName());
            Integer examFee = feeHistory.getExamFee();
            Integer admissionFee = feeHistory.getAdmissionFee();
            Integer tutionFee = feeHistory.getTuitionFee();
            Integer total = examFee + admissionFee + tutionFee;

            holder.dueAmount.setText(total.toString());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, FeePaymentActivity.class);
                    i.putExtra("CLASS_TYPE", listOfDefaulter.get(0));
                    context.startActivity(i);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return listOfDefaulter.size();
    }

    public class DefaulterViewHodler extends RecyclerView.ViewHolder {
        private TextView defaulterName, dueAmount;
        private CardView cardView;

        public DefaulterViewHodler(@NonNull View itemView) {
            super(itemView);
            defaulterName = (itemView).findViewById(R.id.defaulterName);
            dueAmount = (itemView).findViewById(R.id.paymentDue);
            cardView = (itemView).findViewById(R.id.defaulter_card_view);
        }
    }

}
