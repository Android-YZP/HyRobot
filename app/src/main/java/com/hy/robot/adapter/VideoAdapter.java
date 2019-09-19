package com.hy.robot.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.hy.robot.App;
import com.hy.robot.R;
import com.hy.robot.bean.XianPaoBean;
import com.hy.robot.utils.RotateTransformation;

import java.util.List;

import static com.hy.robot.utils.RotateTransformation.getRotateOptions;

public class VideoAdapter extends PagerAdapter {
    private Context mContext;
    List<XianPaoBean.ResultBean> mData;

    public VideoAdapter(Context context, List<XianPaoBean.ResultBean> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(mContext, R.layout.item_video,null);
        ImageView imageView = view.findViewById(R.id.iv);
//        Glide.with(App.getContext()).load(mData.get(position).getVideocover()).into(imageView);

        Glide.with(App.getContext()).load(mData.get(position).getVideocover()).apply(getRotateOptions(mContext)).into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container,position,object); 这一句要删除，否则报错
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}
