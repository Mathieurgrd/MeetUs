package com.example.mathieu.meetus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfilWelcome extends AppCompatActivity implements View.OnClickListener {


     DatabaseReference mRef;
    StorageReference mPhotoStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_welcome);

        findViewById(R.id.buttonnext).setOnClickListener(this);

        final TextView userName = (TextView) findViewById(R.id.UserName);
        final ImageView photoProfil = (ImageView) findViewById(R.id.ivPhotoProfil);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        mPhotoStorage = FirebaseStorage.getInstance().getReference();
        StorageReference photoRef = mPhotoStorage.child("userPics/" + userId);
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        mRef = database.child("users/" + userId);


        photoProfil.setImageBitmap(null);


// Load the image using Glide
            Glide.with(ProfilWelcome.this).using(new FirebaseImageLoader()).load(photoRef).asBitmap().centerCrop()
                    .into(new BitmapImageViewTarget(photoProfil) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(ProfilWelcome.this.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            photoProfil.setImageDrawable(circularBitmapDrawable);
                        }
                    });


        // Attach a listener to read the data at our profile reference
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                ProfilModel userProfile = dataSnapshot.getValue(ProfilModel.class);

                final String userNameString = String.format(" %s !", userProfile.getName());
                userName.setText(getString(R.string.welcomeprofilstring) + userNameString);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ProfilWelcome.this, "Your Database is fucked up m8 ! ", Toast.LENGTH_LONG)
                        .show();
            }
        });


    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.buttonnext) {
            startActivity(new Intent(ProfilWelcome.this, ScreenSlideActivity.class));
        }
    }
}
