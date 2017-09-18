package com.example.admin.week3weekendhw;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import com.example.admin.week3weekendhw.model.MyResponse;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import okhttp3.OkHttpClient;

/**
 * Created by Admin on 9/17/2017.
 */

public class ThreadPhotoHandler extends Thread{
    String photoUrl;byte[] byteArray;
    Bitmap bmp;
    private static final String TAG = "Handler" ;
    android.os.Handler handler;
    public static String BASE_URL = "https://api.github.com/users/";
    private OkHttpClient client;
    String a;
    MyResponse myResponse;

    public ThreadPhotoHandler(android.os.Handler handler, String photoUrl){
        this.handler = handler;
        this.photoUrl = photoUrl;
    }
    @Override
    public void run(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Log.d(TAG, "runPhotoUrl: "+photoUrl);
                    URL url = new URL(photoUrl);

                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    //ivUserPick.setImageBitmap(bmp);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byteArray = stream.toByteArray();
                } catch (Exception e) {
                    Log.d(TAG, "run pic err: " + e.toString());
                }
                //super.run();
                Bundle bundle = new Bundle();
                //      bundle.putString("data", data);
                bundle.putByteArray("profilePhoto", byteArray);
                //cteate the messagee object and add bundle objecct
                Message message = new Message();
                message.setData(bundle);
                //send that bundle object in the
                handler.sendMessage(message);
            }
        }).start();

    }

}
