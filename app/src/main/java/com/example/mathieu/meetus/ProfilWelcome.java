package com.example.mathieu.meetus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfilWelcome extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_welcome);

        TextView userName = (TextView) findViewById(R.id.UserName);
        ImageView photoProfil = (ImageView) findViewById(R.id.ivPhotoProfil);

        Intent myIntent = getIntent();
        UserProfileName myName = getIntent().getExtras().getParcelable(CreateProfilActivity.EXTRA_REQUEST);

        userName.setText("Welcome on Meetus " + myName.getmName());
    }

}
