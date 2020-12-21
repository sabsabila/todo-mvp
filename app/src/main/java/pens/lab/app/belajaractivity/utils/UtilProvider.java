package pens.lab.app.belajaractivity.utils;

import android.content.Context;
import android.content.SharedPreferences;
import pens.lab.app.belajaractivity.constant.SqliteConstant;

public class UtilProvider {
    private static SharedPreferencesUtil sharedPreferencesUtil;
    private static SqliteUtil sqliteUtil;

    public static void initialize(Context context){
        initSharedPreferencesUtil(context);
        initSqliteUtil(context);
    }

    private static void initSharedPreferencesUtil(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("example", Context.MODE_PRIVATE);
        sharedPreferencesUtil = new SharedPreferencesUtil(sharedPreferences);
    }

    private static void initSqliteUtil(Context context){
        sqliteUtil = new SqliteUtil(context, SqliteConstant.DATABASE_NAME, null, SqliteConstant.DATABASE_VERSION);
    }

    public static SharedPreferencesUtil getSharedPreferencesUtil(){
        return sharedPreferencesUtil;
    }

    public static SqliteUtil getSqliteUtil(){
        return sqliteUtil;
    }
}
