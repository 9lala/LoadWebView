// (c)2016 Flipboard Inc, All Rights Reserved.

package cn.photon.loadwebview.network;


import cn.photon.loadwebview.network.api.UtilApi;
import cn.photon.loadwebview.utils.Constants;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {
    private static UtilApi utilApi;
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();


    public static UtilApi getUtilApi() {
        if (utilApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            utilApi = retrofit.create(UtilApi.class);
        }
        return utilApi;
    }
}