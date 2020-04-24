package com.mahato.covid_19pandemic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mahato.covid_19pandemic.R;
import com.mahato.covid_19pandemic.model.DistrictModel;

import java.util.List;

public class DistrictAdapter extends RecyclerView.Adapter<DistrictViewHolder>{

    private Context mContext;
    private List<DistrictModel> districts;

    public DistrictAdapter(Context mContext, List<DistrictModel> districts) {
        this.mContext = mContext;
        this.districts = districts;
    }

    @NonNull
    @Override
    public DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_district_item, parent, false);
        return new DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistrictViewHolder holder, int position) {

        String confirmed = String.valueOf(districts.get(position).getConfirmed());
        String name = districts.get(position).getDistrict();

        holder.districtName.setText("District : " + name);
        holder.districtConfirmed.setText("Confirmed : " + confirmed);
    }

    @Override
    public int getItemCount() {
        return districts.size();
    }
}
class DistrictViewHolder extends RecyclerView.ViewHolder{

    TextView districtName;
    TextView districtConfirmed;

    public DistrictViewHolder(@NonNull View itemView) {
        super(itemView);

        districtConfirmed = itemView.findViewById(R.id.item_district_confirmed);
        districtName = itemView.findViewById(R.id.item_district_name);
    }
}
