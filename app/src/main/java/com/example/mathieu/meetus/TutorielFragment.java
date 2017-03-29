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
 * {@link //TutorielFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TutorielFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TutorielFragment extends Fragment {
    //ConstructeurPrivéVide
    public TutorielFragment() {}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tutoriel, container, false);

        TextView tv = (TextView) v.findViewById(R.id.tvFragTuto);
        tv.setText(getArguments().getString("msglol"));

        return v;
    }

    public static TutorielFragment newInstance(String text) {

        TutorielFragment fTuto = new TutorielFragment();
        Bundle b = new Bundle();
        b.putString("msglol", text);

        fTuto.setArguments(b);

        return fTuto;
    }
}