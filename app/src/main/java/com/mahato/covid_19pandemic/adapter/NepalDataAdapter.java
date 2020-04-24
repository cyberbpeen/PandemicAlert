package com.mahato.covid_19pandemic.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mahato.covid_19pandemic.Interface.ItemClickListener;
import com.mahato.covid_19pandemic.R;
import com.mahato.covid_19pandemic.model.DistrictModel;
import com.mahato.covid_19pandemic.model.StateModel;
import com.mahato.covid_19pandemic.requests.responses.DistrictResponse;

import java.util.ArrayList;
import java.util.List;

public class NepalDataAdapter extends RecyclerView.Adapter<COVIDItemViewHolder>{

    private Context mContext;
    private List<StateModel> stateList;
    private List<DistrictResponse> districtResponses;
    private DistrictAdapter districtAdapter;

    int row_index = -1;

    public NepalDataAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setStateList(List<StateModel> stateList) {
        this.stateList = stateList;
    }

    public void setDistrictResponses(List<DistrictResponse> districtResponses) {
        this.districtResponses = districtResponses;
    }

    @NonNull
    @Override
    public COVIDItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_covid_single_item, parent, false);
        return new COVIDItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final COVIDItemViewHolder holder, int position) {

        String confirmedData = String.valueOf(stateList.get(position).getConfirmed());
        String activeData = String.valueOf(stateList.get(position).getActive());
        String recoveredData = String.valueOf(stateList.get(position).getRecovered());
        String deathsData = String.valueOf(stateList.get(position).getDeaths());
        final String stateNameString = stateList.get(position).getState();

        holder.stateName.setText(stateNameString);
        holder.confirm.setText("Confirmed: " + confirmedData);
        holder.active.setText("Active: " + activeData);
        holder.recovered.setText("Recovered: " + recoveredData);
        holder.deaths.setText("Deaths: " + deathsData);


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                row_index = position;
                notifyDataSetChanged();
                for (DistrictResponse response : districtResponses) {
                    List<DistrictModel> districtModels = new ArrayList<>();
                    if (stateNameString.equals(response.getStateName())){
                        districtModels = response.getDistricts();
                        //init recycler view
                        holder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                        holder.recyclerView.setHasFixedSize(true);
                        districtAdapter = new DistrictAdapter(mContext, districtModels);

                        holder.recyclerView.setAdapter(districtAdapter);
                        notifyDataSetChanged();
                    }
                }
            }
        });

        //high light
        if (row_index == position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#000000"));
            holder.recyclerView.setVisibility(View.VISIBLE);
        }else {
            holder.itemView.setBackgroundColor(Color.parseColor("#222222"));
            holder.recyclerView.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        if (stateList == null)
            return 0;
        else
            return stateList.size();
    }
}
