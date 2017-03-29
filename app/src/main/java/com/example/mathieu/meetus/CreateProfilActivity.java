package com.example.mathieu.meetus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class CreateProfilActivity extends AppCompatActivity {



    Button buttonProfil;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profil);




        buttonProfil = (Button) findViewById(R.id.buttonProfil);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextTechno = (EditText) findViewById(R.id.editTextTechno);
        editTextWild = (EditText) findViewById(R.id.editTextWild);
        editTextVille = (EditText) findViewById(R.id.editTextVille);
        toast = Toast.makeText(context, getString(R.string.toastCreateProfil), Toastduration);
        context = getApplicationContext();
        Toastduration = Toast.LENGTH_SHORT;
        imageViewProfil = (ImageView) findViewById(R.id.PhotoProfil);


        buttonProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextName.length() != 0 && editTextAge.length() != 0 && editTextTechno.length() != 0 && editTextWild.length() != 0 && editTextVille.length() != 0) {


                    String userNameString = editTextName.getText().toString();


                    UserProfileName myName = new UserProfileName(userNameString);
                    Intent myIntent = new Intent(CreateProfilActivity.this, ProfilWelcome.class);

                    myIntent.putExtra(CreateProfilActivity.EXTRA_REQUEST, myName);
                    startActivity(myIntent);

                }
                else {
                    Context context = getApplicationContext();
                    CharSequence text = getString(R.string.toastCreateProfil);
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
    }



}


