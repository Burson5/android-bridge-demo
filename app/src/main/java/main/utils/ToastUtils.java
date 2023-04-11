package main.utils;

import android.app.Activity;
import android.widget.Toast;

public class ToastUtils {
    private static Toast toast;

    public static Activity mActivity = null;

    public static void init(Activity activity) {
        mActivity = activity;
    }


    /**
     * Android原生Toast的显示，主要解决点多少就提示多少次的问题
     */
    public static void showToast(String content){

        if (toast == null){
            toast = Toast.makeText(mActivity, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

}
