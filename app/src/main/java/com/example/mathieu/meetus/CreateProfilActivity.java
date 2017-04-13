package com.example.mathieu.meetus;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.IOException;

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
    DatabaseReference mRef;
    String userId;
    final static int cameraData=0;
    Bitmap bmp;

    public final static int RESULT_LOAD_IMAGE = 1;


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

        mAuth = FirebaseAuth.getInstance();



        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {

                    final String userId = user.getUid();

                    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

                    mRef = database.child("users/" +userId);

                    mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.hasChild("userId")) {


                                ProfilModel userProfile = snapshot.getValue(ProfilModel.class);
                                String checkIfUserHasProfile = userProfile.getUserId();


                                if (userId.equals(checkIfUserHasProfile)) {
                                    startActivity(new Intent(CreateProfilActivity.this, ScreenSlideActivity.class));
                                    CreateProfilActivity.this.finish();
                                }
                            } else{
                                    Toast.makeText(getApplicationContext(), "You don't have a profile", Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }


            }
        });


    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.buttonAddYourPhoto) {

            AlertDialog.Builder a1 = new AlertDialog.Builder(CreateProfilActivity.this);


            // Setting Dialog Title
            a1.setTitle("Choose an option");

            // Setting Dialog Message
            a1.setMessage("Choose whether your prefer upload a photo by taking a picture with the camera , or uploading an existing from the gallery");

            a1.setPositiveButton("Take a new one",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int button1) {
                            // if this button is clicked, close
                            // current activity
                            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(takePicture, 0);//zero can be replaced with any action code

                            dialog.cancel();
                        }


                    });
            a1.setNeutralButton("From Gallery",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int button2) {
                            openGallery();
                            dialog.cancel();

                        }
                    });

            // Showing Alert Message
            AlertDialog alertDialog = a1.create();
            a1.show();


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

                if (TextUtils.isEmpty(String.valueOf(age)) && editTextAge.length() == 0 && "".equals(age)) {
                    editTextAge.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(city) && editTextVille.length() == 0 && "".equals(city)) {
                    editTextVille.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(techno) && editTextTechno.length() == 0 && "".equals(techno)) {
                    editTextTechno.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(name) && editTextName.length() == 0 && "".equals(name)) {
                    editTextName.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(wild) && editTextWild.length() == 0 && "".equals(wild)) {
                    editTextWild.setError("Required");
                    return;
                } else {


                    // Envoi sur la Database

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference mRef = database.getReference("users");
                    database = FirebaseDatabase.getInstance(); //APPELLE LA BASE DE DONNEES
                    refProfil = database.getReference("users/" + userId);


                    ProfilModel userProfile = new ProfilModel(name, age, techno, wild, city, userId);
                    refProfil.setValue(userProfile);

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


    private void openGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);//
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageViewProfil.setImageURI(selectedImage);
                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageViewProfil.setImageURI(selectedImage);


                    // Get and resize profile image
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = CreateProfilActivity.this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap loadedBitmap = BitmapFactory.decodeFile(picturePath);

                    ExifInterface exif = null;
                    try {
                        File pictureFile = new File(picturePath);
                        exif = new ExifInterface(pictureFile.getAbsolutePath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    int orientation = ExifInterface.ORIENTATION_NORMAL;

                    if (exif != null)
                        orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            loadedBitmap = rotate(loadedBitmap, 90);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            loadedBitmap = rotate(loadedBitmap, 180);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_270:
                            loadedBitmap = rotate(loadedBitmap, 270);
                            break;
                    }

                }
        }
    }


    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}


