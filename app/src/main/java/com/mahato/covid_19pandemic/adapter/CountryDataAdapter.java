package com.mahato.covid_19pandemic.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mahato.covid_19pandemic.Interface.ItemClickListener;
import com.mahato.covid_19pandemic.R;
import com.mahato.covid_19pandemic.model.CountryModel;

import java.util.List;

public class CountryDataAdapter extends RecyclerView.Adapter<COVIDItemViewHolder> {

    private static final String TAG = "CountryDataAdapter";

    private Context mContext;
    private List<CountryModel> countryData;
    int row_index = -1;

    public CountryDataAdapter(Context mContext, List<CountryModel> countryData) {
        this.mContext = mContext;
        this.countryData = countryData;
    }

    public void setFilteredCountry(List<CountryModel> filteredCountry) {
        this.countryData = filteredCountry;
    }

    @NonNull
    @Override
    public COVIDItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_covid_single_item, parent, false);
        return new COVIDItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull COVIDItemViewHolder holder, int position) {

        String confirmedData = String.valueOf(countryData.get(position).getCases());
        String activeData = String.valueOf(countryData.get(position).getActive());
        String recoveredData = String.valueOf(countryData.get(position).getRecovered());
        String deathsData = String.valueOf(countryData.get(position).getDeaths());

        String countryName = countryData.get(position).getCountry();

        holder.stateName.setText(countryName);
        holder.confirm.setText("Confirmed: " + confirmedData);
        holder.active.setText("Active: " + activeData);
        holder.recovered.setText("Recovered: " + recoveredData);
        holder.deaths.setText("Deaths: " + deathsData);



        //set country flag
        String countryFlag = countryData.get(position).getCountryInfo().getFlag();

        Log.d(TAG, "onBindViewHolder: " + countryFlag);

        if (countryFlag != null) {
            Glide
                    .with(mContext)
                    .load(countryFlag)
                    .placeholder(R.drawable.ic_world_icon)
                    .into(holder.image);
        }

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                row_index = position;
                notifyDataSetChanged();
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
        return countryData.size();
    }

}
