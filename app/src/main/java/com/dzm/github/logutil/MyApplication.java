package com.dzm.github.logutil;

import android.app.Application;

import com.dzm.github.logutil.utils.FileUtils;
import com.dzm.github.logutil.utils.LogUtil;

/**
 * Class For
 * Package Name com.dzm.github.logutil
 * Created by dengzm on 2018/6/11.
 */

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        FileUtils.init(this);
        LogUtil.init();
    }
}
