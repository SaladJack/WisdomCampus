package com.kai.wisdom_scut.network.api;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by tusm on 16/8/19.
 */

public interface SimsimiApi {

    //http://www.xiaodoubi.com/simsimiapi.php?msg=你好
    @GET("simsimiapi.php")
    Observable<ResponseBody> getResponse(@Query("msg") String msg);

}
