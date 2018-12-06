package a.b.c.quizmania.Fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import a.b.c.quizmania.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MultipleChoiseFragment extends Fragment {



    public MultipleChoiseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_multiple_choise, container, false);
    }


    public void printAnswers(String[] answers) {
        Activity activity = getActivity();

        Button b1 = activity.findViewById(R.id.answer_1);
        Button b2 = activity.findViewById(R.id.answer_2);
        Button b3 = activity.findViewById(R.id.answer_3);
        Button b4 = activity.findViewById(R.id.answer_4);

        b1.setText(answers[0]);
        b2.setText(answers[1]);
        b3.setText(answers[2]);
        b4.setText(answers[3]);

    }

}
