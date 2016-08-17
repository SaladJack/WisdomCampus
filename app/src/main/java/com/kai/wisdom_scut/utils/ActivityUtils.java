package com.kai.wisdom_scut.utils;

import android.app.Activity;
import android.content.Intent;

import com.kai.wisdom_scut.R;

/**
 * Created by tusm on 16/8/16.
 */

public class ActivityUtils {
    public static void  parseToActivity(Activity activity, Intent intent){
        activity.startActivity(intent);
//        activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

    public static void finishActivity(Activity activity){
        activity.finish();
//        activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }
}
