package com.hy.robot.utils;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hy.robot.App;
import com.hy.robot.R;

public class SwitchBGUtils {
    private ImageView imageView;

    public static SwitchBGUtils getInstance(ImageView imageView) {
        return new SwitchBGUtils(imageView);
    }

    private SwitchBGUtils(ImageView imageView) {
        this.imageView = imageView;
    }

    public void switchBg(String dialogueParameter) {
        switch (dialogueParameter) {
            case "weather": //天气

                Glide.with(App.getContext()).load(R.mipmap.zhuanqq).into(imageView);

                break;
            case "joke": //笑话

                Glide.with(App.getContext()).load(R.mipmap.zuoz).into(imageView);

                break;
            case "musicX": //戏曲

                break;
            case "百科": //百科

                break;
            case "闲泡视频": //闲泡视频

                break;
            case "飞鸽短信": //飞鸽短信

                break;
            case "飞鸽通话": //飞鸽通话

                break;
            case "三眼蛙资讯": //三眼蛙资讯

                break;
            case "相声小品": //相声小品

                break;
            case "翻译": //翻译

                break;
            case "跳舞": //跳舞

                break;
            case "待机": //待机

                break;
            case "长时间待机": //长时间待机

                break;
            case "更换角色": //更换角色

                break;
            default:
                Glide.with(App.getContext()).load(R.mipmap.huis).into(imageView);
                break;
        }

        ObjectAnimator animator7 = ObjectAnimator.ofFloat(imageView, "alpha", 0.3f, 1f);
        AnimatorSet set2 = new AnimatorSet();
        set2.setDuration(1000);
        set2.play(animator7);
        set2.start();
    }


}
