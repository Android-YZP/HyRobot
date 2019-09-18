package com.hy.robot.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class VideoAdapter extends PagerAdapter {
    private Context mContext;
    private List<String> mData;

    public VideoAdapter(Context context , List<String> list) {
        mContext = context;
        mData = list;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        View view = View.inflate(mContext, R.layout.item_base,null);
//        TextView tv = (TextView) view.findViewById(R.id.tv);
        TextView tv = new TextView(mContext);
        tv.setText(mData.get(position));

        container.addView(tv);
        return tv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container,position,object); 这一句要删除，否则报错
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
