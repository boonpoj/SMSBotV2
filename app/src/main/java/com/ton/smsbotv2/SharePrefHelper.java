package com.ton.smsbotv2;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharePrefHelper {
    public void saveToSharePref(Context context, String name, String data) {
        SharedPreferences pref = context.getSharedPreferences("SharePref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(name, data);
        editor.commit();
    }

    public String getFromSharePref(Context context, String name) {
        SharedPreferences pref = context.getSharedPreferences("SharePref", MODE_PRIVATE);
        String data = pref.getString(name, null);
        return data;
    }
}
