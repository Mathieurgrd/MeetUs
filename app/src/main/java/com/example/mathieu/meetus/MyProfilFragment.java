package com.example.mathieu.meetus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link //ScreenSlidePageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link //ScreenSlidePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyProfilFragment extends Fragment {
    //ConstructeurPrivéVide
    public MyProfilFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_screen_slide_page, container, false);

        TextView tv = (TextView) v.findViewById(R.id.Random);
        tv.setText(getArguments().getString("Votre Profil"));

        return v;
    }

    public static MyProfilFragment newInstance(String text) {

        MyProfilFragment fProfil = new MyProfilFragment();
        Bundle b = new Bundle();
        b.putString("Votre Profil", text);

        fProfil.setArguments(b);

        return fProfil;
    }
}