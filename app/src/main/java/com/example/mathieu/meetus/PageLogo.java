package com.example.mathieu.meetus;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PageLogo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(PageLogo.this, LoginActivity.class);
                PageLogo.this.startActivity(mainIntent);
                PageLogo.this.finish();
            }
        }, 7000);




    }
}
