package com.mahato.covid_19pandemic.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.mahato.covid_19pandemic.R;
import com.mahato.covid_19pandemic.model.SlideItem;

import java.util.List;

public class SliderAdapter extends PagerAdapter {
    private Context mContext;
    private List<SlideItem> contentItems;

    public SliderAdapter(Context mContext, List<SlideItem> mListItems){
        this.mContext = mContext;
        this.contentItems = mListItems;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        @SuppressLint("InflateParams")View layoutItems = layoutInflater.inflate(R.layout.layout_splash_slide,null);

        TextView title = layoutItems.findViewById(R.id.slide_item_title);
        TextView description = layoutItems.findViewById(R.id.slide_item_description);
        ImageView images = layoutItems.findViewById(R.id.slide_item_image);

        title.setText(contentItems.get(position).getTitle());
        description.setText(contentItems.get(position).getDescription());
        images.setImageResource(contentItems.get(position).getImage());
        container.addView(layoutItems);
        return layoutItems;

    }

    @Override
    public int getCount(){
        return contentItems.size();
    }


    @Override
    public boolean isViewFromObject(@NonNull View v, @NonNull Object a){
        return v == a;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object){
        container.removeView((View)object);
    }
}
