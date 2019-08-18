package com.fastaac.mall;

import android.app.Application;
import android.util.Log;

import com.fastaac.base.base.AbsViewModel;

import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <pre>
 *     @author : wutao
 *     e-mail : 670831931@qq.com
 *     time   : 2019/08/17
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class MainVm extends AbsViewModel {

    private String url = "https://wanandroid.com/wxarticle/chapters/json";

    public MainVm(@NonNull Application application) {
        super(application);
    }

    private OkHttpClient client = new OkHttpClient();

    private String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public void requestNet() {
        try {
            String result = run(url);
            Log.i("wutao", "requestNet: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
