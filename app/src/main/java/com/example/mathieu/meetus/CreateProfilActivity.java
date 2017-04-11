package com.example.mathieu.meetus;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateProfilActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText editTextName;
    private EditText editTextAge;
    private EditText editTextTechno;
    private EditText editTextWild;
    private EditText editTextVille;
    private Toast toast;
    private FirebaseDatabase database;
    private DatabaseReference refProfil;
    private int Toastduration;
    private Context context;
    private ImageView imageViewProfil;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profil);


        //Buttons
        findViewById(R.id.buttonProfil).setOnClickListener(this);
        findViewById(R.id.buttonAddYourPhoto).setOnClickListener(this);
        //EditText
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextTechno = (EditText) findViewById(R.id.editTextTechno);
        editTextWild = (EditText) findViewById(R.id.editTextWild);
        editTextVille = (EditText) findViewById(R.id.editTextVille);

        toast = Toast.makeText(this, getString(R.string.toastCreateProfil), Toastduration);
        context = getApplicationContext();
        Toastduration = Toast.LENGTH_SHORT;
        imageViewProfil = (ImageView) findViewById(R.id.PhotoProfil);

        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.buttonAddYourPhoto) {
        }
        if (i == R.id.buttonProfil) {

            try {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userId = user.getUid();
                String name = editTextName.getText().toString();
                int age = Integer.parseInt(editTextAge.getText().toString());
                String techno = editTextTechno.getText().toString();
                String wild = editTextWild.getText().toString();
                String city = editTextVille.getText().toString();

                if (TextUtils.isEmpty(String.valueOf(age)) && editTextAge.length() == 0 ) {
                    editTextAge.setError("Required");
                }
                if (TextUtils.isEmpty(city)) {
                    editTextVille.setError("Required");
                }
                if (TextUtils.isEmpty(techno)) {
                    editTextTechno.setError("Required");
                }
                if (TextUtils.isEmpty(name)) {
                    editTextName.setError("Required");
                }
                if (TextUtils.isEmpty(wild)) {
                    editTextWild.setError("Required");
                } else {

                    // Envoi sur la Database

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference(userId);
                    database = FirebaseDatabase.getInstance(); //APPELLE LA BASE DE DONNEES
                    refProfil = database.getReference(userId);



                    ProfilModel userProfile = new ProfilModel(name, age, techno, wild, city, userId);
                    refProfil.push().setValue(userProfile);

                    startActivity(new Intent(CreateProfilActivity.this, ProfilWelcome.class));
                    finish();
                }





            } catch (NumberFormatException e) {
                AlertDialog.Builder a1 = new AlertDialog.Builder(CreateProfilActivity.this);


                // Setting Dialog Title
                a1.setTitle("That's embarassing ... ");

                // Setting Dialog Message
                a1.setMessage("Please fill in all the fields to create your profile");

                a1.setPositiveButton("Got it",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int button1) {
                                // if this button is clicked, close
                                // current activity
                                dialog.cancel();
                            }

                        });

                // Showing Alert Message
                AlertDialog alertDialog = a1.create();
                a1.show();
            }







        }

    }
}


