package pens.lab.app.belajaractivity.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class UtilProvider {
    private static SharedPreferencesUtil sharedPreferencesUtil;

    public static void initialize(Context context) {
        initSharedPreferencesUtil(context);
    }

    private static void initSharedPreferencesUtil(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("todolist", Context.MODE_PRIVATE);
        sharedPreferencesUtil = new SharedPreferencesUtil(sharedPreferences);
    }

    public static SharedPreferencesUtil getSharedPreferencesUtil() {
        return sharedPreferencesUtil;
    }
}