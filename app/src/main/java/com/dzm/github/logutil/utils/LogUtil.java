package com.dzm.github.logutil.utils;

import android.content.Context;
import android.util.Log;

import com.dzm.github.logutil.BuildConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Class For 日志打印类：支持切换是否打印和写入文件
 * Package Name com.dzm.github.logutil.utils
 * Created by dengzm on 2018/6/6.
 */

public class LogUtil {
    private static final String TAG = "LogUtil";

    private static final int LOG_FILES_MAX_NUM = 15;  //文件最多有5个
    private static boolean ALLOW_TYPE_OUT = true;     //是否允许日志打印
    private static boolean NEED_WRITE_FILE = true;    //是否需要写入文件
    private static int LEVEL;                         //log等级
    private static String FILE_PATH;                  //log文件夹路径
    private static String FILE_NAME;                  //当前log文件名称
    private static File logFile;                      //当前log文件
    private static LogHandlerThread readQueueThread;            //读取log队列信息

    /***************************  初始化init、整理log文档  *******************************/

    /** app启动时进行初始化*/
    public static void init() {
        FILE_PATH = FileUtils.getLogPath() + "diyLog" + File.separator;
        FILE_NAME = "log_" + DateTimeUtil.formatTodayDate1() + ".txt";

        logFile = getNewLogFile();

        if (BuildConfig.DEBUG) {
            LEVEL = Level.DEBUG.value;
        } else {
            LEVEL = Level.WARN.value;
        }

        //循环读取消息
        readQueueThread = LogHandlerThread.getInstance();
        readQueueThread.start();
    }

    /** 整理log文档*/
    private static File getNewLogFile() {
        File dir = new File(FILE_PATH);
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.contains("log_");
            }
        });
        if (files == null || files.length == 0) {
            return createNewFile();
        }
        if (files.length >= LOG_FILES_MAX_NUM) {
            List<File> list = new ArrayList<>();
            list.addAll(Arrays.asList(files));
            list.sort(new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    return o2.getName().compareTo(o1.getName());
                }
            });
            FileUtils.deleteFile(list.get(0));
        }
        return createNewFile();
    }

    /** 创建log文档*/
    private static File createNewFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            boolean result = file.mkdirs();
            if (!result) {
                Log.e("LogUtil", "创建文件夹失败");
            }
        }
        File txtFile = new File(FILE_PATH + FILE_NAME);
        if (!txtFile.exists()) {
            try {
                txtFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("LogUtil", "创建文件失败");
            }
        }
        return txtFile;
    }

    /******************************** set方法  **************************************/

    /** 设置允许日志打印的等级*/
    public static void setLevel(int level) {
        LEVEL = level;
    }

    /** 设置是否允许日志打印*/
    public static void setAllowTypeOut(boolean flag) {
        ALLOW_TYPE_OUT = flag;
    }

    /** 设置是否需要写入文件*/
    public static void setNeedWriteFile(boolean flag) {
        NEED_WRITE_FILE = flag;
    }

    /********************************  log方法  **************************************/

    /** verbose */
    public static void v(Context context, String msg) {
        if (!ALLOW_TYPE_OUT || LEVEL >= Level.VERBOSE.value) return;
        String tag = context.getClass().getPackage().getName() + context.getClass().getSimpleName();
        Log.v(tag, msg);
        if (NEED_WRITE_FILE) {
            writeToQueue(tag, msg, "Verbose");
        }
    }

    /** debug */
    public static void d(Context context, String msg) {
        if (!ALLOW_TYPE_OUT || LEVEL >= Level.DEBUG.value) return;
        String tag = context.getClass().getPackage().getName() + context.getClass().getSimpleName();
        Log.d(tag, msg);
        if (NEED_WRITE_FILE) {
            writeToQueue(tag, msg, "Debug");
        }
    }

    /** info */
    public static void i(Context context, String msg) {
        if (!ALLOW_TYPE_OUT || LEVEL >= Level.INFO.value) return;
        String tag = context.getClass().getPackage().getName() + context.getClass().getSimpleName();
        Log.i(tag, msg);
        if (NEED_WRITE_FILE) {
            writeToQueue(tag, msg, "Info");
        }
    }

    /** warn */
    public static void w(Context context, String msg) {
        if (!ALLOW_TYPE_OUT || LEVEL >= Level.WARN.value) return;
        String tag = context.getClass().getPackage().getName() + context.getClass().getSimpleName();
        Log.w(tag, msg);
        if (NEED_WRITE_FILE) {
            writeToQueue(tag, msg, "Warning");
        }
    }

    /** error */
    public static void e(Context context, String msg) {
        if (!ALLOW_TYPE_OUT || LEVEL >= Level.ERROR.value) return;
        String tag = context.getClass().getPackage().getName() + context.getClass().getSimpleName();
        Log.e(tag, msg);
        if (NEED_WRITE_FILE) {
            writeToQueue(tag, msg, "Error");
        }
    }

    /** assert */
    public static void wtf(Context context, String msg) {
        if (!ALLOW_TYPE_OUT || LEVEL >= Level.ASSERT.value) return;
        String tag = context.getClass().getPackage().getName() + context.getClass().getSimpleName();
        Log.wtf(tag, msg);
        if (NEED_WRITE_FILE) {
            writeToQueue(tag, msg, "Assert");
        }
    }

    /********************************  写入文件方法  **************************************/

    /** 将log写入队列 */
    private static void writeToQueue(String tag, String msg, String level) {
        String builder = DateTimeUtil.formatTodayDateTime() + " " + level + ": " +
                "TAG=" + tag + ",msg=" + msg + "\n";
        readQueueThread.writeIntoQueue(builder);
    }

    /** 将log写入文件 */
    public static void writeToFile(String cache) {
        try {
            FileWriter fileWriter = new FileWriter(logFile, true);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write(cache);
            writer.flush();
            writer.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "log写入失败->e=" + e + ",msg=" + cache);
        }
    }

    /** level */
    public enum Level {
        VERBOSE(Log.VERBOSE),
        DEBUG(Log.DEBUG),
        INFO(Log.INFO),
        WARN(Log.WARN),
        ERROR(Log.ERROR),
        ASSERT(Log.ASSERT);

        int value;

        Level(int value) {
            this.value = value;
        }
    }

}