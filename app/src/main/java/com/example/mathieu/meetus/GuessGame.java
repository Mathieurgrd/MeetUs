package com.example.mathieu.meetus;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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


    Button Try ;
    ImageView PhotoProfil;




    //ConstructeurPrivéVide
    public GuessGame() {}




        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_guess_game, container, false);

             PhotoProfil = (ImageView) v.findViewById(R.id.PhotoProfil);

            final Activity activity = getActivity();
            final View content = activity.findViewById(android.R.id.content).getRootView();
            if (content.getWidth() > 0) {
                Bitmap image = BlurBuilder.blur(content);
                PhotoProfil.setImageDrawable(new BitmapDrawable(activity.getResources(), image));


            }









            Button Try = (Button) v.findViewById(R.id.buttonTry);

            Try.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(GuessGame.this.getContext(), ScoreResultsActivity.class));
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