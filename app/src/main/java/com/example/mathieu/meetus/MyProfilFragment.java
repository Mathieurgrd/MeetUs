package com.example.mathieu.meetus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link //ScreenSlidePageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link //ScreenSlidePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyProfilFragment extends Fragment implements ChildEventListener, View.OnClickListener {

    DatabaseReference mRef;
    public TextView pWild;
    public TextView pVille;
    public TextView pTechno;
    public TextView pAge;
    public TextView pName;


    //ConstructeurPrivéVide
    public MyProfilFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_screen_slide_page, container, false);

        v.findViewById(R.id.buttonEditProfil).setOnClickListener(this);
        final TextView pName = (TextView) v.findViewById(R.id.editTextName);
        final TextView pAge = (TextView) v.findViewById(R.id.editTextAge);
        final TextView pTechno = (TextView) v.findViewById(R.id.editTextTechno);
        final TextView pVille = (TextView) v.findViewById(R.id.editTextVille);
        final TextView pWild = (TextView) v.findViewById(R.id.editTextWild);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        mRef = database.child("users/" + userId);


        // Attach a listener to read the data at our profile reference
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                    ProfilModel userProfile = dataSnapshot.getValue(ProfilModel.class);

                    pName.setText(userProfile.getName());
                    pVille.setText(userProfile.getCity());
                    pWild.setText(userProfile.getWild());
                    pTechno.setText(userProfile.getTechno());
                    pAge.setText(String.valueOf(userProfile.getAge()));


                }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MyProfilFragment.this.getContext(), "Your Database is fucked up m8 ! ", Toast.LENGTH_LONG)
                        .show();
            }
        });


        v.findViewById(R.id.SignOutButton).setOnClickListener(this);
        return v;


    }

    private void signOut() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
    }

    public static MyProfilFragment newInstance(String text) {

        MyProfilFragment fProfil = new MyProfilFragment();
        Bundle b = new Bundle();
        b.putString("Votre Profil", text);

        fProfil.setArguments(b);

        return fProfil;
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.SignOutButton) {
            signOut();
            startActivity(new Intent(MyProfilFragment.this.getContext(), LoginActivity.class));
            this.getActivity().finish();

        }
        if (i == R.id.buttonEditProfil) {
            startActivity(new Intent(MyProfilFragment.this.getContext(), ModifyProfilUser.class));
            this.getActivity().finish();
        }

    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        ProfilModel userProfile = dataSnapshot.getValue(ProfilModel.class);
        pAge.setText(userProfile.getAge());
        pName.setText(userProfile.getName());
        pVille.setText(userProfile.getCity());
        pWild.setText(userProfile.getWild());
        pTechno.setText(userProfile.getTechno());


    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}