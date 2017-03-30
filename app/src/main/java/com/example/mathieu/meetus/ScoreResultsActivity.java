package com.example.mathieu.meetus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class ScoreResultsActivity extends AppCompatActivity {

    EditText Ville;
    EditText Age;
    EditText WildSide;
    EditText Spécialité;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_activity);

        EditText Ville = (EditText) findViewById(R.id.editTextVille);
        EditText Spécialité = (EditText) findViewById(R.id.editTextSpé);
        EditText WildSide = (EditText) findViewById(R.id.editTextWild);
        EditText Age = (EditText) findViewById(R.id.editTextAge);




    }
}
