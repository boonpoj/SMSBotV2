package com.ton.smsbotv2;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpHelper {

    public String postHeader(String url, HashMap<String, String> hashMap) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();
        OkHttpClient client = new OkHttpClient();
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .header("content-type", "application/x-www-form-urlencoded")
                .post(body)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful())
                return response.body().string();
            else
                return "{ 'message':'something wrong' }";
        } catch (SocketTimeoutException e) {
            return "{ 'message':'connection fails' }";
        }
    }
}