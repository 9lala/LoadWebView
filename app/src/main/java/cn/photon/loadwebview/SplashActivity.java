package cn.photon.loadwebview;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;

import cn.photon.loadwebview.network.NetworkUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉窗口标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏顶部的状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                NetworkUtils.getUtilApi()
                        .getUrl()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<JsonObject>() {
                            @Override
                            public void accept(@NonNull JsonObject obj) throws Exception {
                                String mUrl = (String) obj.get("url").getAsString();
                                Intent intent = new Intent(SplashActivity.this, WebViewActivity.class);
                                intent.putExtra("URL", mUrl);
                                startActivity(intent);
                                finish();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                Toast.makeText(SplashActivity.this,throwable.getMessage().toString(),Toast.LENGTH_LONG).show();
                            }
                        });
            }
        }, 2000);
    }
}
