package com.mahato.covid_19pandemic.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mahato.covid_19pandemic.Interface.ItemClickListener;
import com.mahato.covid_19pandemic.PatientDetailsActivity;
import com.mahato.covid_19pandemic.R;
import com.mahato.covid_19pandemic.model.InfectionModel;

import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientViewHolder> {

    private Context mContext;
    private List<InfectionModel> infectionPatients;

    int row_index = -1;

    public PatientAdapter(Context context, List<InfectionModel> infectionPatients) {
        this.mContext = context;
        this.infectionPatients = infectionPatients;
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_patient_single_item, parent, false);
        return new PatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {

        String patientNum = infectionPatients.get(position).getPatientnumber();

        holder.patientNumber.setText("Patient " + patientNum);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                row_index = position;
                notifyDataSetChanged();

                Intent intent = new Intent(mContext, PatientDetailsActivity.class);
                intent.putExtra("Patient", infectionPatients.get(position));
                mContext.startActivity(intent);

            }
        });

        //high light
        if (row_index == position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#000000"));
        }else {
            holder.itemView.setBackgroundColor(Color.parseColor("#222222"));
        }
    }

    @Override
    public int getItemCount() {
        return infectionPatients.size();
    }
}
class PatientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView patientNumber;

    ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public PatientViewHolder(@NonNull View itemView) {
        super(itemView);

        patientNumber = itemView.findViewById(R.id.item_patient_number);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition());
    }
}