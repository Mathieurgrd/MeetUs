package com.example.mathieu.meetus;

/**
 * Created by mathieu on 07/04/17.
 */

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.Query;


public class ProfileAdapter extends Firebaseadapter<ProfilModel> {

    TextView Name;
    TextView Age;
    TextView Ville;
    TextView Techno;
    TextView Wild;


    public ProfileAdapter(Query ref, Activity activity, int layout) {
        super(ref, ProfilModel.class, layout, activity);

    }


    @Override
    protected void populateView(View view, ProfilModel userProfile) {

        Name = (TextView) view.findViewById(R.id.editTextName);
        Age = (TextView) view.findViewById(R.id.editTextAge);
        Ville = (TextView) view.findViewById(R.id.editTextVille);
        Techno = (TextView) view.findViewById(R.id.editTextTechno);
        Wild = (TextView) view.findViewById(R.id.editTextWild);

        Name.setText(String.valueOf(userProfile.getName()));
        Age.setText(String.valueOf(userProfile.getAge()));
        Ville.setText(String.valueOf(userProfile.getCity()));
        Wild.setText(String.valueOf(userProfile.getWild()));
        Techno.setText(String.valueOf(userProfile.getTechno()));


    }
}