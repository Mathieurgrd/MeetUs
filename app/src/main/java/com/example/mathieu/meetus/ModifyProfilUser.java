package com.example.mathieu.meetus;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ModifyProfilUser extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "TAG";

    private EditText editTextName;
    private EditText editTextAge;
    private EditText editTextTechno;
    private EditText editTextWild;
    private EditText editTextVille;

    private Toast toast;
    private int Toastduration;
    private Context context;

    private FirebaseDatabase database;
    private DatabaseReference refProfil;
    DatabaseReference mRef;
    private StorageReference mPhotoStorage;


    private ImageView imageViewProfil;

    final static int cameraData = 0;
    Bitmap bmp;

    private static final int RESULT_LOAD_IMAGE = 1;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private StorageReference photoRef;

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
        mPhotoStorage = FirebaseStorage.getInstance().getReference();
        photoRef = FirebaseStorage.getInstance().getReference();



        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {


                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    final String userId = user.getUid();
                    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

                    mRef = database.child("users/" + userId);
                    mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {

                            ProfilModel userProfile = snapshot.getValue(ProfilModel.class);

                            editTextName.setText(userProfile.getName());
                            editTextVille.setText(userProfile.getCity());
                            editTextWild.setText(userProfile.getWild());
                            editTextTechno.setText(userProfile.getTechno());
                            editTextAge.setText(String.valueOf(userProfile.getAge()));

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

            AlertDialog.Builder a1 = new AlertDialog.Builder(ModifyProfilUser.this);


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


                    startActivity(new Intent(ModifyProfilUser.this, ProfilWelcome.class));
                    finish();
                }


            } catch (NumberFormatException e) {
                AlertDialog.Builder a1 = new AlertDialog.Builder(ModifyProfilUser.this);


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

    private void uploadPhoto(Uri imageUri) {


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        StorageReference photoRef = mPhotoStorage.child("userPics/" + userId);

        photoRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                System.out.println("Upload is " + progress + "% done");
            }
        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                System.out.println("Upload is paused");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Handle successful uploads on complete
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getMetadata().getDownloadUrl();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageViewProfil.setImageURI(selectedImage);

                    if (selectedImage != null) {
                      /**  Picasso.with(getApplicationContext())
                                .load(selectedImage)
                                .placeholder(R.drawable.flou)
                                .resize(200, 200)
                                .centerCrop()
                                .into(imageViewProfil);*/

                        Glide.with(ModifyProfilUser.this).load(selectedImage).asBitmap().centerCrop()
                                .into(new BitmapImageViewTarget(imageViewProfil) {
                                    @Override
                                    protected void setResource(Bitmap resource) {
                                        RoundedBitmapDrawable circularBitmapDrawable =
                                                RoundedBitmapDrawableFactory.create(ModifyProfilUser.this.getResources(), resource);
                                        circularBitmapDrawable.setCircular(true);
                                        imageViewProfil.setImageDrawable(circularBitmapDrawable);
                                    }
                                });

                        uploadPhoto(selectedImage);

                    }

                    //SetProfilePhoto


                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(editTextName.getText().toString())
                            .setPhotoUri(Uri.parse(selectedImage.getPath()))
                            .build();


                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "User profile updated.");
                                    } else {
                                        Toast.makeText(ModifyProfilUser.this, "Echec du chargement de la photo de profil", Toast.LENGTH_LONG)
                                                .show();
                                    }
                                }
                            });
                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageViewProfil.setImageURI(selectedImage);

                    if (selectedImage != null) {


                        Glide.with(ModifyProfilUser.this).load(selectedImage).asBitmap().centerCrop()
                                .into(new BitmapImageViewTarget(imageViewProfil) {
                                    @Override
                                    protected void setResource(Bitmap resource) {
                                        RoundedBitmapDrawable circularBitmapDrawable =
                                                RoundedBitmapDrawableFactory.create(ModifyProfilUser.this.getResources(), resource);
                                        circularBitmapDrawable.setCircular(true);
                                        imageViewProfil.setImageDrawable(circularBitmapDrawable);
                                    }
                                });


                        uploadPhoto(selectedImage);

                    }


                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(editTextName.getText().toString())
                            .setPhotoUri(Uri.parse(selectedImage.getPath()))
                            .build();


                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "User profile updated.");
                                    } else {
                                        Toast.makeText(ModifyProfilUser.this, "Echec du chargement de la photo de profil",
                                                Toast.LENGTH_LONG)
                                                .show();
                                    }
                                }
                            });

                }
        }
    }



}