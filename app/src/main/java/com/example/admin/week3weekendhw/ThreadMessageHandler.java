package com.example.admin.week3weekendhw;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.example.admin.week3weekendhw.model.MyResponse;
import com.google.gson.Gson;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Admin on 9/17/2017.
 */

public class ThreadMessageHandler extends Thread{
    String loginUser;
    private static final String TAG = "Handler" ;
    android.os.Handler handler;
    public static String BASE_URL = "https://api.github.com/users/";
    private OkHttpClient client;
String a;
    MyResponse myResponse;

    public ThreadMessageHandler(android.os.Handler handler, String loginUser){
        this.handler = handler;
        this.loginUser = loginUser;
    }

    @Override
    public void run(){
        //super.run();
        client = new OkHttpClient();
        BASE_URL += loginUser;
        Log.d(TAG, "baseUrl: "+ BASE_URL);
        //----------------------------------------
        final Request request = new Request.Builder().url(BASE_URL).build();
                try {
                    String response = client
                            .newCall(request)
                            .execute()
                            .body()
                            .string();
                    Log.d("MainActOkSyncTag", "run: " + response);
                    Gson gson = new Gson();
                    myResponse = gson.fromJson(response, MyResponse.class);
                    Log.d(TAG, "log get name: " + myResponse.getLogin());


                    Bundle bundle = new Bundle();
                    //      bundle.putString("data", data);
                    bundle.putSerializable("profileSeri", myResponse);
                    //cteate the messagee object and add bundle objecct
                    Message message = new Message();
                    message.setData(bundle);
                    //send that bundle object in the
                    handler.sendMessage(message);

                } catch (IOException e) {
                    e.printStackTrace();
                }

        //----------------------------------------


    }

}
