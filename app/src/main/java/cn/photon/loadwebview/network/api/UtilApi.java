// (c)2016 Flipboard Inc, All Rights Reserved.

package cn.photon.loadwebview.network.api;



import com.google.gson.JsonObject;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UtilApi {
    //上传 数据
    @GET(" ")
    Observable<JsonObject> getUrl();

}
