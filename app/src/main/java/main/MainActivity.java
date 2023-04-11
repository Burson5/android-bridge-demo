package main;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import main.utils.ToastUtils;
import main.utils.WebViewUtil;

public class MainActivity extends AppCompatActivity {

    public String TAG = MainActivity.class.getName();

    public MainActivity instance = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 提示初始化
        ToastUtils.init(this);

        //webview 初始化
        WebViewUtil.init(this);
    }

}