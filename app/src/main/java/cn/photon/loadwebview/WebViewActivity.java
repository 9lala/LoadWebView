package cn.photon.loadwebview;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class WebViewActivity extends AppCompatActivity {

    WebView webView;
    ProgressBar progressBar1;

    private String mUrl;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉窗口标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏顶部的状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_web_view);
        initIntent();
        initView();
        initData();
        initListener();
    }

    private void initIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("URL") != null) {
                mUrl = bundle.getString("URL");
            }
        }
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.web_view);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);

        initVebView();
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO 自动生成的方法存根

                if (newProgress == 100) {
                    progressBar1.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    progressBar1.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar1.setProgress(newProgress);//设置进度值
                }

            }
        });
    }

    private void initVebView() {
        webView.requestFocusFromTouch();
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//支持js，如果不设置本行，html中的js代码都会失效
//        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH); //提高渲染的优先级 设置自适应屏幕，两者合用
//        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置可以缩放
        webSettings.setUseWideViewPort(true); //设置可以缩放
        webSettings.setTextZoom(100);
        webSettings.setDomStorageEnabled(true);
//        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件 //若上面是false，则该WebView不可缩放，这个不管设置什么都不能缩放。
//        webSettings.setTextZoom(2);//设置文本的缩放倍数，默认为 100
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
//        webSettings.supportMultipleWindows(); //多窗口
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
//        webSettings.setAllowFileAccess(true); //设置可以访问文件 webSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口，不明白什么意思
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
//        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
//        webSettings.setStandardFontFamily("");//设置 WebView 的字体，默认字体为 "sans-serif"
//        webSettings.setDefaultFontSize(20);//设置 WebView 字体的大小，默认大小为 16
//        webSettings.setMinimumFontSize(12);//设置 WebView 支持的最小字体大小，默认为 8作者：IT与江总
    }

    private void initData() {
        if (!TextUtils.isEmpty(mUrl)) {
            webView.loadUrl(mUrl);
        }
    }

    private void initListener() {
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
        stopTimerTask();
    }

    private void stopTimerTask() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }


    //设置返回键动作（防止按返回键直接退出程序)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO 自动生成的方法存根
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {//当webview不是处于第一页面时，返回上一个页面
                webView.goBack();
                return true;
            } else {//当webview处于第一页面时,直接退出程序
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
