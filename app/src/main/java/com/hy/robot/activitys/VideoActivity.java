package com.hy.robot.activitys;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import com.hy.robot.R;
import com.hy.robot.adapter.VideoAdapter;
import com.hy.robot.bean.MessageWrap;
import com.orhanobut.logger.Logger;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class VideoActivity extends Activity {
    private static final int VIDEO_MAX_VALUE = 5;
    private ViewPager mVpVideo;
    ArrayList<String> mData = new ArrayList<>();
    private TXCloudVideoView mVideo;
    private TXVodPlayer mLivePlayer;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_video);
        EventBus.getDefault().register(this);
        init();
        initListenter();
    }


    private void initListenter() {

    }

    private void init() {

        mData.add("fjiorjngioreog");
        mData.add("fjiorjngioreog");
        mData.add("fjiorjngioreog");
        mData.add("fjiorjngioreog");
        mData.add("fjiorjngioreog");
        mData.add("fjiorjngioreog");
        mData.add("fjiorjngioreog");

        mVpVideo = findViewById(R.id.vp_video);
        VideoAdapter videoAdapter = new VideoAdapter(VideoActivity.this, mData);
        mVpVideo.setAdapter(videoAdapter);
        mVpVideo.setRotation(90);
        mVpVideo.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                String flvUrl = "http://1253520711.vod2.myqcloud.com/3035299avodgzp1253520711/a521da7d5285890789069346663/6QCSEoJDCOIA.mp4";
//                mLivePlayer.startPlay(flvUrl, TXLivePlayer.PLAY_TYPE_VOD_MP4);
                mLivePlayer.startPlay(flvUrl);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });



        mVideo = findViewById(R.id.video_view);
        //创建 player 对象
        mLivePlayer = new TXVodPlayer(VideoActivity.this);
        //关键 player 对象与界面 view
        mLivePlayer.setPlayerView(mVideo);
        String flvUrl = "http://1253520711.vod2.myqcloud.com/3035299avodgzp1253520711/a521da7d5285890789069346663/6QCSEoJDCOIA.mp4";
        mLivePlayer.startPlay(flvUrl);
        //Android 示例代码

        // 设置填充模式
        mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
        // 设置画面渲染方向
        mLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
        mLivePlayer.setPlayListener(new ITXLivePlayListener() {
            @Override
            public void onPlayEvent(int event, Bundle param) {
                Logger.e(event+"");
                if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {
                    Logger.e("[AnswerRoom] 拉流失败：网络断开");
                    Logger.e("网络断开，拉流失败");

                } else if (event == TXLiveConstants.PLAY_EVT_PLAY_LOADING) {//视频播放进入缓冲状态，缓冲结束之后会有 PLAY_BEGIN 事件

                } else if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {//视频播放开始，如果您自己做 loading，会需要它

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mLivePlayer.stopPlay(true); // true 代表清除最后一帧画面
        mVideo.onDestroy();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageWrap event) {
        if (event.message.contains("语音激活")) {
            finish();
        }
    }
}
