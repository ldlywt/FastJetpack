package com.fastaac;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.fastaac.base.base.AbsViewModel;
import com.fastaac.base.base.BaseResult;
import com.google.gson.reflect.TypeToken;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.io.IOException;
import java.util.List;

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
    static final String EVENT_DATA = "EVENT_DATA";

    private MutableLiveData<BaseResult<List<TestBean>>> mResult = new MutableLiveData<>();
    private OkHttpClient client = new OkHttpClient();

    public MainVm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<BaseResult<List<TestBean>>> getResultLiveData() {
        return mResult;
    }

    public void requestNet() {
        try {
            String result = run(url);
            BaseResult<List<TestBean>> data = GsonUtils.fromJson(result, new TypeToken<BaseResult<List<TestBean>>>() {
            }.getType());
            //自动根据状态设置页面的模式：空、网络异常、成功
            postPageState(data);
            mResult.postValue(data);
            LiveEventBus.get().with(MainVm.EVENT_DATA, TestBean.class).post(data.getData().get(1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public void clickNoNet() {
        postPageState(new BaseResult(-1));
    }

    public void clickNoData() {
        BaseResult<Object> baseResult = new BaseResult<>();
        baseResult.setErrorCode(0);
        baseResult.setData(null);
        postPageState(baseResult);
    }
}
