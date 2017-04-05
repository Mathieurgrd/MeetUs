package com.example.mathieu.meetus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link //ScreenSlidePageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link //ScreenSlidePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyProfilFragment extends Fragment implements View.OnClickListener {

    //ConstructeurPrivéVide
    public MyProfilFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_screen_slide_page, container, false);





        v.findViewById(R.id.SignOutButton);
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