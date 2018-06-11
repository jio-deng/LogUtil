package com.dzm.github.logutil.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Class For
 * Package Name com.dzm.github.logutil.utils
 * Created by dengzm on 2018/6/7.
 */

public class LogHandlerThread extends HandlerThread {

    private Queue<String> logMsgQueue;        //保存log消息
    private Timer timer;                      //定时器
    private TimerTask timerTask;              //定时任务
    private StringBuilder sb;                 //cache
    private int cacheSize = 0;                //cache size
    private int MAX_CACHE_SIZE = 30;          //max cache size
    private int MAX_CACHE_TIME = 1000 * 5;    //max cache time
    private Handler handler;

    private static LogHandlerThread instance;

    private LogHandlerThread(String name){
        super(name);
        handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Message message = handler.obtainMessage();
                if (!logMsgQueue.isEmpty()) {
                    String logMsg = logMsgQueue.poll();
                    sb.append(logMsg);
                    cacheSize ++;
                    if (cacheSize >= MAX_CACHE_SIZE) {
                        writeIntoFile();
                    }
                    handler.sendMessageDelayed(message, 0);
                } else {
                    handler.sendMessageDelayed(message, 1000);
                }
            }
        };

        logMsgQueue = new LinkedBlockingQueue<>();
        sb = new StringBuilder();

        //开启计时器
        timer = new Timer();
        timerTask = new LogTimerTask();
        timer.schedule(timerTask, MAX_CACHE_TIME);
    }

    public static LogHandlerThread getInstance() {
        if (instance == null)
            instance = new LogHandlerThread("log_handler");
        return instance;
    }

    /** 核心方法：无限循环；当log数缓存到 */
    @Override
    public void run() {
        handler.sendMessageDelayed(handler.obtainMessage(), 0);
    }

    /** 将cache中的log写入文件 */
    private void writeIntoFile() {
        LogUtil.writeToFile(sb.toString());
        cacheSize = 0;
        sb = new StringBuilder();
        timer = new Timer();
        timerTask = new LogTimerTask();
        timer.schedule(timerTask, MAX_CACHE_TIME);
    }

    /** LogUtil调用：将log写入Queue */
    public void writeIntoQueue(String msg) {
        logMsgQueue.add(msg);
    }

    //TimerTask就像穿破的袜子 只能扔 不能复用
    class LogTimerTask extends TimerTask {
        @Override
        public void run() {
            writeIntoFile();
        }
    }
}
