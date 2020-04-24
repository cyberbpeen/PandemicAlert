package com.mahato.covid_19pandemic.adapter;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mahato.covid_19pandemic.Interface.ItemClickListener;
import com.mahato.covid_19pandemic.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class COVIDItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView stateName;
    TextView confirm;
    TextView active;
    TextView recovered;
    TextView deaths;
    CircleImageView image;

    RecyclerView recyclerView;

    ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public COVIDItemViewHolder(@NonNull View view) {
        super(view);

        stateName = view.findViewById(R.id.covid_item_title);
        confirm = view.findViewById(R.id.covid_item_confirm);
        active = view.findViewById(R.id.covid_item_active);
        recovered = view.findViewById(R.id.covid_item_recovered);
        deaths = view.findViewById(R.id.covid_item_deaths);
        image = view.findViewById(R.id.covid_item_image);

        recyclerView = view.findViewById(R.id.covid_item_recycler_view);
        recyclerView.setVisibility(View.GONE);

        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition());
    }
}