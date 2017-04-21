package com.example.mathieu.meetus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ScoreResultsActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase database;
    private DatabaseReference refProfil;
    private ImageView imageViewProfil;
    private StorageReference mPhotoStorage;
    private StorageReference photoRef;
    private String tryCount;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_activity);

        photoRef = FirebaseStorage.getInstance().getReference();
        mPhotoStorage = FirebaseStorage.getInstance().getReference();
        Button TryAgain = (Button) findViewById(R.id.button2);
        imageViewProfil = (ImageView) findViewById(R.id.imageView2);
        TextView ResultTv = (TextView) findViewById(R.id.textView3);
        TextView bravotv = (TextView) findViewById(R.id.textView5);
        TextView Blablatv = (TextView) findViewById(R.id.textView7);

        Bundle extra = getIntent().getExtras();
        int tryCount = extra.getInt("tryCount", 0);
        String userIdforPic = extra.getString("uid");
        String answer = extra.getString("answer");
        String blabla = String.format(" %s " , answer);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        mRef = database.child("users");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        mPhotoStorage = FirebaseStorage.getInstance().getReference();
        StorageReference photoRef = mPhotoStorage.child("userPics/" + userIdforPic);

        mRef = database.child("users");

        imageViewProfil.setImageDrawable(null);

        Glide.with(ScoreResultsActivity.this).using(new FirebaseImageLoader()).load(photoRef).asBitmap().centerCrop()
                .into(new BitmapImageViewTarget( imageViewProfil) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(ScoreResultsActivity.this.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageViewProfil.setImageDrawable(circularBitmapDrawable);

                    }
                });



        switch (tryCount) {
            case 1:
                ResultTv.setText("20/20 :D");
                bravotv.setText("Perfect ! ");
                Blablatv.setText("You nailed" + blabla + "  ! Great ! ");
                break;
            case 2:
                ResultTv.setText("15/20 :)");
                bravotv.setText("So close ! ");
                Blablatv.setText("Good work but not perfect tho ! You were looking for " + blabla );
                break;
            case 3:
                ResultTv.setText("10/20 :|");
                bravotv.setText(" Close enough ! ");
                Blablatv.setText("Fair enough ! You were looking for " + blabla);
                break;
            case 4:
                ResultTv.setText("0/20 :(");
                bravotv.setText("Nope ! ");
                Blablatv.setText(blabla +" would be so sad that you didn't find about him/her ... Try again !");

                break;
            default:
                ResultTv.setText("15 /20");

        }



        TryAgain.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick (View v){

        startActivity(new Intent(ScoreResultsActivity.this, ScreenSlideActivity.class));
        finish();

    }
    });

}


    @Override
    public void onClick(View v) {

    }
}

