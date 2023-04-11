package main.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;

import main.config.Constants;
import main.webview.JSInterface;
import main.webview.inter.JavascriptCallback;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class WebViewUtil {

    private static String TAG = WebViewUtil.class.getName();

    private static WebView webView;

    private static String currentUrl;

    public static Activity mActivity = null;

    public static void init(Activity activity) {
        mActivity = activity;
        webView = new WebView(activity);
        activity.setContentView(webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        // 设置是否允许 WebView 使用 File 协议
        webSettings.setAllowFileAccess(true);
        // 设置是否允许通过 file url 加载的 Js代码读取其他的本地文件
        webSettings.setAllowFileAccessFromFileURLs(true);
        // 设置是否允许通过 file url 加载的 Javascript 可以访问其他的源(包括http、https等源)
        webSettings.setAllowUniversalAccessFromFileURLs(false);

        webView.addJavascriptInterface(new JSInterface(), "JSInterface");

        // chrome 调试
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        currentUrl = Constants.ENTER_PHONE_URL;
        webView.loadUrl(Constants.ENTER_PHONE_URL);
    }

    public static void reload() {
        webView.reload();
    }


    /**
     * 使用 killProcess 杀死自身，系统会恢复应用
     * https://gist.github.com/imhet/378a751826196e64f24f1c5784e6be51
     */
    public static void relanch()
    {
        Intent intent = new Intent(mActivity, mActivity.getClass());
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        mActivity.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }


    public static void loadUrl() {
        webView.loadUrl(currentUrl);
    }

    public static boolean canGoBack() {
        return webView.canGoBack();
    }

    public static void goBack() {
        webView.goBack();
    }


    public static void evaluateJavascript(final String parameter, final JavascriptCallback javascriptCallback) {
        webView.post(new Runnable() {
            @Override
            public void run() {
                String encodeStr = "{\"code\":-1,\"action\":\"error\",\"desc\":\"data encode err\",\"data\":null}";
                try {
                    encodeStr = URLEncoder.encode(parameter, "UTF-8");
                    encodeStr=encodeStr.replaceAll("\\+",  "%20");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                webView.evaluateJavascript("javascript:callJS('"+encodeStr+"')", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Log.d(TAG, "evaluateJavascript onReceiveValue:" + value);
                        if(null != javascriptCallback) {
                            javascriptCallback.onReceive(value);
                        }
                    }
                });
            }
        });
    }
}