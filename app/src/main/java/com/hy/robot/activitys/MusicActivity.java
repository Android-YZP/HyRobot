package com.hy.robot.activitys;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.com1075.library.base.BaseActivity2;
import com.google.gson.Gson;
import com.hy.robot.R;
import com.hy.robot.bean.CrossTalkBean;
import com.hy.robot.bean.MessageWrap;
import com.orhanobut.logger.Logger;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MusicActivity extends BaseActivity2 {
    private TXCloudVideoView mVideo;
    private TXVodPlayer mLivePlayer;
    private int i = 0;
    private CrossTalkBean mCrossTalkBean;

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mLivePlayer.stopPlay(true); // true 代表清除最后一帧画面
        mVideo.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected int getContentViewId() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        EventBus.getDefault().register(this);
        return R.layout.activity_music;
    }

    @Override
    protected void initView() {
        mVideo = findViewById(R.id.video_view);

    }

    @Override
    protected void initData() {
        //"http://od.open.qingting.fm/m4a/5800f3f87cb891101aeb0c1a_6108239_64.m4a?u=786&channelId=187352&programId=5640081"

        String data = getIntent().getStringExtra("data");
        Logger.e(data);
        mCrossTalkBean = new Gson().fromJson(data, CrossTalkBean.class);


        //创建 player 对象
        mLivePlayer = new TXVodPlayer(MusicActivity.this);
        //关键 player 对象与界面 view
        mLivePlayer.setPlayerView(mVideo);

        String flvUrl = mCrossTalkBean.getResult().get(i++).getUrl();
        Logger.e(flvUrl);
        mLivePlayer.startPlay(flvUrl);
        // 设置填充模式
        mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
        // 设置画面渲染方向
        mLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);


    }

    @Override
    protected void setListener() {
        mLivePlayer.setPlayListener(new ITXLivePlayListener() {
            @Override
            public void onPlayEvent(int event, Bundle param) {

                if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {
                    Logger.e("[AnswerRoom] 拉流失败：网络断开");
                    Logger.e("网络断开，拉流失败");

                } else if (event == TXLiveConstants.PLAY_EVT_PLAY_LOADING) {//视频播放进入缓冲状态，缓冲结束之后会有 PLAY_BEGIN 事件

                    findViewById(R.id.image).setVisibility(View.VISIBLE);


                } else if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {//视频播放开始，如果您自己做 loading，会需要它


                    findViewById(R.id.image).setVisibility(View.GONE);

                } else if (event == TXLiveConstants.PLAY_EVT_PLAY_END) {//播放结束，HTTP-FLV 的直播流是不抛这个事件的
                    Logger.e("TXLiveConstants.PLAY_EVT_PLAY_END");
                    if (i == mCrossTalkBean.getResult().size()) MusicActivity.this.finish();
                    String flvUrl = mCrossTalkBean.getResult().get(i++).getUrl();
                    mLivePlayer.startPlay(flvUrl);
                }
            }

            @Override
            public void onNetStatus(Bundle status) {

            }
        });


        findViewById(R.id.image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (type.equals("crossTalk")){
//
//
//                    if (i == mCrossTalkBean.getResult().size()-1) MusicActivity.this.finish();
//
//                    String flvUrl = mCrossTalkBean.getResult().get(i++).getUrl();
//                    Logger.e(i+"======"+flvUrl);
//                    mLivePlayer.startPlay(flvUrl);
//                }
                mLivePlayer.seek(mLivePlayer.getDuration() - 10.0f);
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageWrap event) {
        // 更新界面
        Logger.e(event.message);
        if (event.message.contains("语音激活")) {
            finish();
        }
    }


}
