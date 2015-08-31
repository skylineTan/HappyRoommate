package com.tzy.happyroommate.app;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.jude.beam.Beam;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;
import com.tzy.happyroommate.BuildConfig;
import com.tzy.happyroommate.R;
import com.tzy.happyroommate.config.Config;
import com.tzy.happyroommate.config.Dir;
import com.jude.http.RequestManager;
import com.jude.utils.JFileManager;
import com.jude.utils.JUtils;

import com.bmob.BmobConfiguration;
import com.bmob.BmobPro;


import cn.bmob.v3.Bmob;

public class APP extends Application{
    private static APP mAPP;
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        mAPP = this;
        init();

    }

    public static APP getInstance(){
        return mAPP;
    }

    private void init(){
        JUtils.initialize(this);
        JUtils.setDebug(BuildConfig.DEBUG, "RoommateLog");
        JFileManager.getInstance().init(this, Dir.values());
        Fresco.initialize(this);//初始化fresco
        RequestManager.getInstance().init(this);
        RequestManager.getInstance().setDebugMode(BuildConfig.DEBUG, "DateNet");
        Beam.init(this);
        //下载抽屉的头像
        DrawerImageLoader.init(new DrawerImageLoader.IDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }

            @Override
            public Drawable placeholder(Context ctx) {
                return null;
            }
        });
        Bmob.initialize(this, Config.applicationId);
        initConfig(getApplicationContext());
        JUtils.Log("初始化完毕");
    }
    /**
     * 初始化文件配置
     * @param context
     */
    public static void initConfig(Context context) {
        BmobConfiguration config = new BmobConfiguration.Builder(context).customExternalCacheDir("Smile").build();
        BmobPro.getInstance(context).initConfig(config);
    }

    public static Context getContext() {
        return context;
    }


}
