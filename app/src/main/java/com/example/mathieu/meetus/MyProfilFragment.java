package com.example.mathieu.meetus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link //ScreenSlidePageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link //ScreenSlidePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyProfilFragment extends Fragment implements View.OnClickListener {

    FirebaseDatabase database ;
    DatabaseReference myRef;

    //ConstructeurPrivéVide
    public MyProfilFragment() {}


     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_screen_slide_page, container, false);

        final TextView pName = (TextView) v.findViewById(R.id.editTextName);
        TextView pAge = (TextView) v.findViewById(R.id.editTextAge);
        TextView pTechno = (TextView) v.findViewById(R.id.editTextTechno);
        TextView pVille = (TextView) v.findViewById(R.id.editTextVille);
        TextView pWild = (TextView) v.findViewById(R.id.editTextWild);
/**
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("Info");

        ProfileAdapter mProfileAdapter = new ProfileAdapter(myRef, MyProfilFragment.this.getActivity(), R.layout.fragment_screen_slide_page);


        */






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
        if(i == R.id.SignOutButton){
            signOut();
            startActivity(new Intent(MyProfilFragment.this.getContext() , LoginActivity.class));

        }

    }
}