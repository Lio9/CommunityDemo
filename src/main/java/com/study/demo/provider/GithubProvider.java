package com.study.demo.provider;

import com.alibaba.fastjson.JSON;
import com.study.demo.DTO.AccessToken;
import com.study.demo.DTO.GithubUser;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @ClassName: GithubProvider
 * @author: Lio9
 * @create: 2022-6-23
 */
@Component
public class GithubProvider {

    @Value("${Github.userUrl}")
    private String userUrl;
    @Value("${Github.accessUrl}")
    private String accessUrl;

    public String getAccessToken(AccessToken accessToken) {
        MediaType mediaType
                = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessToken));
        Request request = new Request.Builder()
                .url(accessUrl)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            System.out.println("responseBody:"+responseBody);
            String token = responseBody.split("&")[0].split("=")[1];
            System.out.println("token:"+token);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(userUrl)
                .header("Authorization", "token " + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            return JSON.parseObject(string, GithubUser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
