package com.example.mohit.threadhandlerdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btnclick;
    private TextView txtview;
    private Long endtime;


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            txtview.setText("Hello user!!");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnclick = findViewById(R.id.btnsub);
        txtview = findViewById(R.id.textView);

        //this is main thread where when we tap on a button then text will show after 10 seconds
        /*btnclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endtime = System.currentTimeMillis()+10000;
                while(System.currentTimeMillis() < endtime){
                    synchronized (this){
                        try {
                            wait(endtime - System.currentTimeMillis());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    txtview.setText("Button Pressed");
                    }
                }
        });*/

        //now we are going to create separate thred so that main thread will not get disturbed
        btnclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable runnable =new Runnable() {
                    @Override
                    public void run() {
                        endtime = System.currentTimeMillis()+10000;
                        while(System.currentTimeMillis() < endtime){
                            synchronized (this){
                                try {
                                    wait(endtime - System.currentTimeMillis());
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            //handler.sendEmptyMessage(0);
                            Message msg = handler.obtainMessage();
                        }
                    }
                };
                Thread thread =new Thread(runnable);
                thread.start();
            }
        });


    }
}
