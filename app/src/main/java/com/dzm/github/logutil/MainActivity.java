package com.dzm.github.logutil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dzm.github.logutil.utils.LogUtil;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printLog();
            }
        });
    }

    private void printLog() {
        for (int i = 0; i < 5; i ++) {
            LogUtil.v(this, "this is a verbose");
            LogUtil.d(this, "this is a debug");
            LogUtil.i(this, "this is a info");
            LogUtil.w(this, "this is a warn");
            LogUtil.e(this, "this is a error");
        }
    }
}
