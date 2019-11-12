package com.hy.robot.activitys;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.com1075.library.base.BaseActivity2;
import com.google.gson.Gson;
import com.hy.robot.R;
import com.hy.robot.adapter.VideoAdapter;
import com.hy.robot.bean.MessageWrap;
import com.hy.robot.bean.XianPaoBean;
import com.hy.robot.contract.IVideoContract;
import com.hy.robot.presenter.VideoPresenter;
import com.orhanobut.logger.Logger;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends BaseActivity2 implements IVideoContract {
    private static final int VIDEO_MAX_VALUE = 5;
    private ViewPager mVpVideo;
    private TXCloudVideoView mVideo;
    private TXVodPlayer mLivePlayer;
    private VideoPresenter mVideoPresenter = new VideoPresenter(this, this);
    private List<XianPaoBean.ResultBean> mData;
    private RelativeLayout mRlRootView;

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
        return R.layout.activity_video;
    }

    @Override
    protected void initView() {
        mRlRootView = findViewById(R.id.rl_toot_view);
        mVpVideo = findViewById(R.id.vp_video);
        mVideo = findViewById(R.id.video_view);

        RelativeLayout.LayoutParams Params = (RelativeLayout.LayoutParams) mVpVideo.getLayoutParams();
        Params.height = mVpVideo.getWidth();
        Params.width = mVpVideo.getHeight();
        mVpVideo.setLayoutParams(Params);

    }

    @Override
    protected void initData() {
        mVideoPresenter.HttpXianPao();

        //创建 player 对象
        mLivePlayer = new TXVodPlayer(VideoActivity.this);
        //关键 player 对象与界面 view
        mLivePlayer.setPlayerView(mVideo);
        //设置填充模式
        mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
        // 设置画面渲染方向
        mLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);


    }

    @Override
    protected void setListener() {

        mVpVideo.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                String flvUrl = mData.get(i).getVideopath() + "";
//                mLivePlayer.startPlay(flvUrl, TXLivePlayer.PLAY_TYPE_VOD_MP4);
                mLivePlayer.startPlay(flvUrl);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


        mLivePlayer.setPlayListener(new ITXLivePlayListener() {
            @Override
            public void onPlayEvent(int event, Bundle param) {
                Logger.i(event + "");
                if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {
                    Logger.e("[AnswerRoom] 拉流失败：网络断开");
                    Logger.e("网络断开，拉流失败");

                } else if (event == TXLiveConstants.PLAY_EVT_PLAY_LOADING) {//视频播放进入缓冲状态，缓冲结束之后会有 PLAY_BEGIN 事件


                } else if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {//视频播放开始，如果您自己做 loading，会需要它
                    findViewById(R.id.image).setVisibility(View.GONE);
                    mRlRootView.setBackgroundColor(Color.BLACK);

                } else if (event == TXLiveConstants.PLAY_EVT_PLAY_END) {//播放结束，HTTP-FLV 的直播流是不抛这个事件的
                    if (mVpVideo.getCurrentItem() >= VIDEO_MAX_VALUE) finish();
                    mVpVideo.setCurrentItem(mVpVideo.getCurrentItem() + 1);
                }
            }

            @Override
            public void onNetStatus(Bundle status) {

            }
        });


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageWrap event) {
        if (event.message.contains("语音激活")) {
            finish();
        }
    }

    @Override
    public void LoadingData() {
        findViewById(R.id.image).setVisibility(View.VISIBLE);
    }

    @Override
    public void LoadingDataFail(String result) {

    }

    @Override
    public void LoadingDataSuccess(String result) {
        Logger.e(result + "");
        XianPaoBean xianPaoBean = new Gson().fromJson(result, XianPaoBean.class);
        mData = xianPaoBean.getResult();
        VideoAdapter videoAdapter = new VideoAdapter(VideoActivity.this, this.mData);

        mVpVideo.setAdapter(videoAdapter);
        mVpVideo.setRotation(90);

        String flvUrl = mData.get(0).getVideopath();
        mLivePlayer.startPlay(flvUrl);
    }

}
