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
 * {@link //GuessGame.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GuessGame#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuessGame extends Fragment {


    //ConstructeurPrivéVide
    public GuessGame() {}



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_guess_game, container, false);

            TextView tv = (TextView) v.findViewById(R.id.tvFragSecond);
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