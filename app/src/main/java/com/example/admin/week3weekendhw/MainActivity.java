package com.example.admin.week3weekendhw;

import android.os.Handler;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.admin.week3weekendhw.model.MyResponse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainViewTag";
    private OkHttpClient client;
    TextView tvName, tvLocation,tvCompany;
    ImageView ivUserPick;
    SearchView searchView;
    Button btnRepository;
    MyResponse myResponse = null;

    android.os.Handler handler, handler1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        client = new OkHttpClient();

        tvName = (TextView) findViewById(R.id.tvName);
        tvLocation =  (TextView) findViewById(R.id.tvLocation);
        tvCompany =  (TextView) findViewById(R.id.tvCompany);

        ivUserPick = (ImageView) findViewById(R.id.ivUserPick);
        searchView = (SearchView) findViewById(R.id.searchView);
        btnRepository = (Button) findViewById(R.id.btnRepository);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }


            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() != 0) {
                    System.out.println("--->" + query);
                    // handle search here
                    Toast.makeText(MainActivity.this, query.toString(), Toast.LENGTH_SHORT).show();
                    test(getSearchWithoutspaces());
                    return true;
                }
                return false;
            }
        });
    }

    public String getSearchWithoutspaces() {
        String s = searchView.getQuery().toString();
        Pattern trimmer = Pattern.compile("^\\s+|\\s+$");
        Matcher m = trimmer.matcher(s);
        StringBuffer out = new StringBuffer();
        while (m.find())
            m.appendReplacement(out, "");
        m.appendTail(out);
        return out.toString();

    }

    public void userClicks(View view) {
        switch (view.getId()) {

            case R.id.btnRepository:
                if (!searchView.getQuery().toString().equals("")) {
                    Intent intent = new Intent(this, RepositoryActivity.class);
                    intent.putExtra("USER_NAME", getSearchWithoutspaces());
                    startActivity(intent);
                }
                break;
        }

    }

    public void test(String searchProfile) {

        //===========================================================
        handler1 = new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                byte[] byteArray = msg.getData().getByteArray("profilePhoto");
                Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                ivUserPick.setImageBitmap(bmp);
                return false;
            }
        });
        //=====================================
        handler = new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                Log.d(TAG, "handleMessage: ht" + Thread.currentThread());
                myResponse = (MyResponse) msg.getData().getSerializable("profileSeri");
                Log.d(TAG, "handleMessage login: " + myResponse.getLogin());
                if (myResponse != null) {

                    if (myResponse.getName() != null)
                        tvName.setText(myResponse.getName().toString());
                    else
                        tvName.setText(myResponse.getLogin());

                    if (myResponse.getLocation() != null)
                        tvLocation.setText(myResponse.getLocation().toString());
                    else
                        tvLocation.setText("N/A");

                    if (myResponse.getCompany() != null)
                        tvCompany.setText(myResponse.getCompany().toString());
                    else
                        tvCompany.setText("N/A");


                    if (myResponse.getPublicRepos() != null) {
                        btnRepository.setText("Repos (" + myResponse.getPublicRepos().toString()+")");
                    }

                    if (myResponse.getAvatarUrl().toString() != null) {
                        ThreadPhotoHandler photoHandler = new ThreadPhotoHandler(handler1, myResponse.getAvatarUrl().toString());
                        photoHandler.start();
                    }

                }
                return false;
            }
        });
        ThreadMessageHandler testThreadHandlerMessage = new ThreadMessageHandler(handler, searchProfile);
        testThreadHandlerMessage.start();
    }
}
