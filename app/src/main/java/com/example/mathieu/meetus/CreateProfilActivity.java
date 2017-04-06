package com.example.mathieu.meetus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class CreateProfilActivity extends AppCompatActivity implements View.OnClickListener {



    EditText editTextName;
    EditText editTextAge;
    EditText editTextTechno;
    EditText editTextWild;
    EditText editTextVille;
    Toast toast;
    int Toastduration;
    Context context;
    ImageView imageViewProfil;

    public final static String EXTRA_REQUEST = "Bienvenue";

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

    }




    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i == R.id.buttonAddYourPhoto){}
        if (i == R.id.buttonProfil){

            String userNameString = editTextName.getText().toString();
            String userNameAge  = editTextAge.getText().toString();
            String userNameCity = editTextVille.getText().toString();
            String userNameTech = editTextTechno.getText().toString();
            String userNameWild = editTextWild.getText().toString();

            if (TextUtils.isEmpty(userNameAge)) {
                editTextAge.setError("Required");
            }
            if (TextUtils.isEmpty(userNameCity)) {
                editTextVille.setError("Required");
            }
            if (TextUtils.isEmpty(userNameTech)) {
                editTextTechno.setError("Required");
            }
            if (TextUtils.isEmpty(userNameString)) {
                editTextName.setError("Required");
            }
            if (TextUtils.isEmpty(userNameWild)) {
                editTextWild.setError("Required");
            }
            else{
                // Envoi sur la Database
                UserProfileName myName = new UserProfileName(userNameString);
                Intent myIntent = new Intent(CreateProfilActivity.this, ProfilWelcome.class);

                myIntent.putExtra(CreateProfilActivity.EXTRA_REQUEST, myName);
                startActivity(myIntent);
            }


        }

    }
}


