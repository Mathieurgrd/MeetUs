package com.example.mathieu.meetus;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

import java.util.Iterator;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link //GuessGame.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GuessGame#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuessGame extends Fragment {


    private Button Try;
    private static final String TAG = "EmailPassword";
    private ImageView PhotoProfil;
    private DatabaseReference myRef;
    private StorageReference mPhotoStorage;
    DatabaseReference mRef;
    public TextView pVille, pAge, pTechno, pWild;
    private TextView AnswerName;
    private EditText tryField;
    private int tryCount;


    //ConstructeurPrivéVide
    public GuessGame() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_guess_game, container, false);


        pVille = (TextView) v.findViewById(R.id.editTextVille);
        pAge = (TextView) v.findViewById(R.id.editTextAge);
        pTechno = (TextView) v.findViewById(R.id.editTextSpé);
        pWild = (TextView) v.findViewById(R.id.editTextWild);
        PhotoProfil = (ImageView) v.findViewById(R.id.PhotoProfil);

        tryField = (EditText) v.findViewById(R.id.GuessAnswer);
        AnswerName = (TextView) v.findViewById(R.id.textViewAnswer);

        AnswerName.setEnabled(false);







        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.imgmarineprofil);
        Bitmap blurredImage = bitmap;

        for(int i = 0; i < 20; i++) {
            blurredImage = Blur.blurRenderScript(getContext(), blurredImage, 25);
        }



        PhotoProfil.setImageBitmap(blurredImage);


        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


         //       ProfilModel userProfile = dataSnapshot.getValue(ProfilModel.class);

                long allNum = dataSnapshot.getChildrenCount();
                int maxNum = (int)allNum;
                int min = 0;
                int randomNum = new Random().nextInt(maxNum - min + 1) + min;

                int count = 0;
                Iterable<DataSnapshot> ds = dataSnapshot.getChildren();
                Iterator<DataSnapshot> ids = ds.iterator();

                ProfilModel randomUser = ids.next().getValue(ProfilModel.class);

                pVille.setText(randomUser.getCity());
                pWild.setText(randomUser.getWild());
                pTechno.setText(randomUser.getTechno());
                pAge.setText(String.valueOf(randomUser.getAge()));
                AnswerName.setText(randomUser.getName().toLowerCase());

                String userIdforPic = randomUser.getUserId();

               // String newPost = (String) ids.next().getValue();
                while(ids.hasNext() && count < randomNum) {
                    ids.next();
                    count ++; // used as positioning.
                }


                mRef = FirebaseDatabase.getInstance().getReference("Info");
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userId = user.getUid();
                mPhotoStorage = FirebaseStorage.getInstance().getReference();
                StorageReference photoRef = mPhotoStorage.child("userPics/" + userIdforPic);
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                mRef = database.child("users");


                PhotoProfil.setImageDrawable(null);

                Glide.with(GuessGame.this.getContext()).using(new FirebaseImageLoader()).load(photoRef).asBitmap().centerCrop()
                        .into(new BitmapImageViewTarget(PhotoProfil) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(GuessGame.this.getContext().getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                PhotoProfil.setImageDrawable(circularBitmapDrawable);

                            }
                        });



            }





            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        Button Try = (Button) v.findViewById(R.id.buttonTry);

        Try.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                tryCount++;
                String userAnswer = tryField.getText().toString().toLowerCase();
                String guessAnswer = AnswerName.getText().toString().toLowerCase();

                if (userAnswer.equals(guessAnswer) || tryCount == 4){
                    Intent maxTry = new Intent(GuessGame.this.getContext() , ScoreResultsActivity.class);
                    maxTry.putExtra("tryCount", tryCount);
                    startActivity(maxTry);
                    getActivity().finish();                }

                else{

                    int charCount = 0 ;
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.imgmarineprofil);
                    Bitmap blurredImage = bitmap;

                   for (int i = 0 ; i == AnswerName.length(); i++ ){

                       /** (userAnswer.charAt(i) == guessAnswer.charAt(i))*/
                       if(userAnswer.charAt(i) == guessAnswer.charAt(i))  {
                           blurredImage = Blur.blurRenderScript(getContext(), blurredImage, 25);
                       }

                   }

                   String guessStringforDialog = String
                           .format(" Wrong answer ! You have %d characters that match with the name of that profile ! %d try remaining !", charCount, 4 - tryCount);
                    AlertDialog.Builder a1 = new AlertDialog.Builder(GuessGame.this.getContext());


                    // Setting Dialog Title
                    a1.setTitle("Nope!");

                    // Setting Dialog Message

                    a1.setMessage( guessStringforDialog);

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
            });


        TextView tv = (TextView) v.findViewById(R.id.tvFragGuess);
        tv.setText(getArguments().getString("msg ici ce seras guess"));

        return v;


    }











    public static GuessGame newInstance(String text) {

        GuessGame fGuess = new GuessGame();
        Bundle b = new Bundle();
        b.putString("msg ici ce seras guess", text);

        fGuess.setArguments(b);

        return fGuess;
    }
}