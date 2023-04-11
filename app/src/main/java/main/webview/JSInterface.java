package main.webview;

import android.webkit.JavascriptInterface;
import main.utils.ToastUtils;

public class JSInterface {

    /**
     * 短暂气泡提醒
     *
     浏览器
     * @param message 提示信息
     */
    @JavascriptInterface
    public static void toast( String message) {
        ToastUtils.showToast(message);
    }


}
