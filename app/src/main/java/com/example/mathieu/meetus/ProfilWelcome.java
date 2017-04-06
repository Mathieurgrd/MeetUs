package com.example.mathieu.meetus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfilWelcome extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_welcome);

        findViewById(R.id.buttonnext).setOnClickListener(this);

        TextView userName = (TextView) findViewById(R.id.UserName);
        ImageView photoProfil = (ImageView) findViewById(R.id.ivPhotoProfil);

        Intent myIntent = getIntent();
        UserProfileName myName = getIntent().getExtras().getParcelable(CreateProfilActivity.EXTRA_REQUEST);

        String userNameString = String.format(" %s" , myName.getmName());

        userName.setText(getString(R.string.welcomeprofilstring) + userNameString);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.buttonnext){
            startActivity(new Intent(ProfilWelcome.this, ScreenSlideActivity.class));
        }
    }
}
