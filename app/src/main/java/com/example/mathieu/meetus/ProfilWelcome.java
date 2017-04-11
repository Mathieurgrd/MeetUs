package com.example.mathieu.meetus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilWelcome extends AppCompatActivity implements View.OnClickListener {


    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_welcome);

        findViewById(R.id.buttonnext).setOnClickListener(this);

        final TextView userName = (TextView) findViewById(R.id.UserName);
        ImageView photoProfil = (ImageView) findViewById(R.id.ivPhotoProfil);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        mRef = database.child(userId);


        String uId = user.getUid();





        // Attach a listener to read the data at our profile reference
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {


                    ProfilModel userProfile = postSnapshot.getValue(ProfilModel.class);

                    final String userNameString = String.format(" %s !" , userProfile.getName() );
                    userName.setText(getString(R.string.welcomeprofilstring) + userNameString);







                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ProfilWelcome.this, "Your Database is fucked up m8 ! ",Toast.LENGTH_LONG )
                        .show();            }
        });




     }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.buttonnext){
            startActivity(new Intent(ProfilWelcome.this, ScreenSlideActivity.class));
        }
    }
}
