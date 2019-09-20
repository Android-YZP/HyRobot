package com.hy.robot.activitys;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.view.WindowManager;
import androidx.annotation.RequiresApi;
import com.com1075.library.base.BaseActivity2;
import com.google.gson.Gson;
import com.hy.robot.App;
import com.hy.robot.R;
import com.hy.robot.bean.MessageWrap;
import com.hy.robot.bean.NewsBean;
import com.hy.robot.bean.NewsListBean;
import com.hy.robot.contract.INewsContract;
import com.hy.robot.presenter.NewsPresenter;
import com.hy.robot.utils.SharedPreferencesUtils;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.orhanobut.logger.Logger;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;

public class TrieyeNewsActivity extends BaseActivity2 implements INewsContract {

    private NewsPresenter mRobotPresenter = new NewsPresenter(this, this);

    // 语音合成对象
    private SpeechSynthesizer mTts;
    // 默认发音人
    private String voicer = "xiaoyan";
    private List<NewsListBean.ResultBean> mResultBeans = new ArrayList<>();
    private int i = 0;
    private int mPage = 1;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected int getContentViewId() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        return R.layout.activity_trieye_news;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (null != mTts) {
            mTts.stopSpeaking();
            // 退出时释放连接
            mTts.destroy();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageWrap event) {
        // 更新界面
        Logger.e(event.message);
        if (event.message.contains("语音激活")) {
            finish();
        }
    }


    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        int newsTotlePage = (int) SharedPreferencesUtils.getParam(TrieyeNewsActivity.this, "totalPage", 1);
        int newsPage = (int) SharedPreferencesUtils.getParam(TrieyeNewsActivity.this, "NewsPage", 1);
        Logger.e(newsTotlePage + "," + newsPage);
        if (newsPage < newsTotlePage) {
            mPage = newsPage + 1;
        } else {
            mPage = 1;
        }


        mRobotPresenter.HttpRandomGetInformationByContentType(mPage + "");
        startSpeaker();
    }

    @Override
    protected void setListener() {

    }


    @Override
    public void LoadingData() {

    }

    @Override
    public void LoadingDataFail(String result) {

    }

    @Override
    public void LoadingDataSuccess(String result) {
        Logger.e(result);
        NewsListBean newsListBean = new Gson().fromJson(result, NewsListBean.class);
        mRobotPresenter.getInformationById(newsListBean.getResult().get(i++).getId() + "");

        SharedPreferencesUtils.setParam(App.getContext(), "totalPage", newsListBean.getPage().getTotalPage());
        SharedPreferencesUtils.setParam(App.getContext(), "NewsPage", newsListBean.getPage().getPage());

    }

    @Override
    public void LoadingNewsDataSuccess(String result) {
        NewsBean newsBean = new Gson().fromJson(result, NewsBean.class);
        if (i == 1) {
            speachText("以下内容由三眼蛙资讯栏目提供。。。。" + newsBean.getResult().getTitle() + "。。。。" + Html.fromHtml(newsBean.getResult().getText()) + "");
        } else {
            speachText(newsBean.getResult().getTitle() + "。。。。" + Html.fromHtml(newsBean.getResult().getText()) + "");
        }

    }


    //语音播报配置
    public void startSpeaker() {
        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(TrieyeNewsActivity.this, mTtsInitListener);
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 设置在线合成发音人
        mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
        //设置合成语速
        mTts.setParameter(SpeechConstant.SPEED, "50");
        //设置合成音调
        mTts.setParameter(SpeechConstant.PITCH, "50");
        //设置合成音量
        mTts.setParameter(SpeechConstant.VOLUME, "50");
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "pcm");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/tts.pcm");
    }

    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Logger.e("InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Logger.e("初始化失败,错误码：" + code + ",请点击网址https://www.xfyun.cn/document/error-code查询解决方案");
            } else {
            }
        }
    };

    public void speachText(String vodString) {
        // 设置参数
        int code = mTts.startSpeaking(vodString, mTtsListener);
        if (code != ErrorCode.SUCCESS) {
            Logger.e("语音合成失败,错误码: " + code + ",请点击网址https://www.xfyun.cn/document/error-code查询解决方案");
        }
    }


    /**
     * 语音合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
        }

        @Override
        public void onSpeakPaused() {
        }

        @Override
        public void onSpeakResumed() {
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (i == 4) TrieyeNewsActivity.this.finish();
            mRobotPresenter.getInformationById(mResultBeans.get(i++).getId() + "");
            Logger.e("onCompleted" + mResultBeans.get(i).getId());
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        }
    };


}
