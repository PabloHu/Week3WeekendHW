package com.example.admin.week3weekendhw;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.admin.week3weekendhw.modelrepo.ModelResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RepositoryActivity extends AppCompatActivity {

    private static final String TAG = "RepositoryTag";
    private OkHttpClient client;
    List<ModelResponse> modelResponseList;
    ListView lvCustomVH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository);
        lvCustomVH = (ListView) findViewById(R.id.lvCustomVH);

        client = new OkHttpClient();

        String userSelected = getIntent().getStringExtra("USER_NAME");

        Log.d(TAG, "onCreate: "+ userSelected);
        Toast.makeText(this, userSelected, Toast.LENGTH_SHORT).show();
        working(userSelected);

    }

    public void working(String userSelected) {
        final Request request = new Request.Builder().url("https://api.github.com/users/"+ userSelected+ "/repos").build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TAGAsync", "onFailure: " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseAsync = response.body()
                        .string();
                Gson gson = new Gson();
                Type listType = new TypeToken<List<ModelResponse>>() {
                }.getType();

                modelResponseList = (List<ModelResponse>) gson.fromJson(responseAsync, listType);

                EventBus.getDefault().post(new MessageEvent(modelResponseList));

                Log.d(TAG, "onCreate size: " + modelResponseList.size());


            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEventImage(MessageEvent msg) {

    //    Toast.makeText(this, msg.getMessage().size() + "", Toast.LENGTH_SHORT).show();
        //mImageViewProfile.setImageBitmap(messageEvent.getMessage());
        modelResponseList = msg.getMessage();

        startPage(modelResponseList);
    }

    public void startPage(List<ModelResponse> modelResponseList) {


        CustomListAdaper customVHListAdapter = new CustomListAdaper(
                this, R.layout.custom_listview_item,modelResponseList);
        lvCustomVH.setAdapter(customVHListAdapter);
    }
}