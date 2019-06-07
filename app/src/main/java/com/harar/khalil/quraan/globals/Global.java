package com.harar.khalil.quraan.globals;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.harar.khalil.quraan.R;

public class Global {
    public static void saveState(Activity activity, String key, String value) {

        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();

    }

    public static String getState(Activity activity) {
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPreferences.getString("lang", String.valueOf(R.id.oromo));

    }

}
