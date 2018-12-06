package a.b.c.quizmania.Fragments;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import a.b.c.quizmania.Entities.Question;
import a.b.c.quizmania.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MultipleChoiseFragment extends Fragment {

    Button b1;
    Button b2;
    Button b3;
    Button b4;

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

        // Finding views
        b1 = activity.findViewById(R.id.answer_1);
        b2 = activity.findViewById(R.id.answer_2);
        b3 = activity.findViewById(R.id.answer_3);
        b4 = activity.findViewById(R.id.answer_4);

        // Setting the answers
        b1.setText(answers[0]);
        b2.setText(answers[1]);
        b3.setText(answers[2]);
        b4.setText(answers[3]);

        // Changing the color of the buttons
        setBtnColors();

        // Set Click event
        b1.setOnClickListener(v -> btnClicked(v));
        b2.setOnClickListener(v -> btnClicked(v));
        b3.setOnClickListener(v -> btnClicked(v));
        b4.setOnClickListener(v -> btnClicked(v));
    }

    public void setBtnColors() {
        b1.setTextColor(Color.parseColor("#FFFFFF"));
        b1.setBackgroundColor(Color.parseColor("#000000"));
        b2.setTextColor(Color.parseColor("#FFFFFF"));
        b2.setBackgroundColor(Color.parseColor("#000000"));
        b3.setTextColor(Color.parseColor("#FFFFFF"));
        b3.setBackgroundColor(Color.parseColor("#000000"));
        b4.setTextColor(Color.parseColor("#FFFFFF"));
        b4.setBackgroundColor(Color.parseColor("#000000"));
    }

    public void btnClicked(View view) {
        disableButtons();
        QuestionDisplayFragment question = (QuestionDisplayFragment) getFragmentManager().findFragmentById(R.id.question_fragment);

        switch (view.getId()) {
            case R.id.answer_1:
                if(question.checkAnswer(b1.getText().toString())) {
                    Toast.makeText(getActivity(), "Correct Answer", Toast.LENGTH_SHORT).show();
                    b1.setBackgroundColor(Color.parseColor("#00FF00"));
                } else {
                    Toast.makeText(getActivity(), "Incorrect Answer", Toast.LENGTH_SHORT).show();
                    b1.setBackgroundColor(Color.parseColor("#FF0000"));
                }
                break;
            case R.id.answer_2:
                if(question.checkAnswer(b2.getText().toString())) {
                    Toast.makeText(getActivity(), "Correct Answer", Toast.LENGTH_SHORT).show();
                    b2.setBackgroundColor(Color.parseColor("#00FF00"));
                } else {
                    Toast.makeText(getActivity(), "Incorrect Answer", Toast.LENGTH_SHORT).show();
                    b2.setBackgroundColor(Color.parseColor("#FF0000"));
                }
                break;
            case R.id.answer_3:
                if(question.checkAnswer(b3.getText().toString())) {
                    Toast.makeText(getActivity(), "Correct Answer", Toast.LENGTH_SHORT).show();
                    b3.setBackgroundColor(Color.parseColor("#00FF00"));
                } else {
                    Toast.makeText(getActivity(), "Incorrect Answer", Toast.LENGTH_SHORT).show();
                    b3.setBackgroundColor(Color.parseColor("#FF0000"));
                }
                break;
            case R.id.answer_4:
                if(question.checkAnswer(b4.getText().toString())) {
                    Toast.makeText(getActivity(), "Correct Answer", Toast.LENGTH_SHORT).show();
                    b4.setBackgroundColor(Color.parseColor("#00FF00"));
                } else {
                    Toast.makeText(getActivity(), "Incorrect Answer", Toast.LENGTH_SHORT).show();
                    b4.setBackgroundColor(Color.parseColor("#FF0000"));
                }
                break;
            default:
                Toast.makeText(getActivity(), "Error came up", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void disableButtons() {
        b1.setClickable(false);
        b2.setClickable(false);
        b3.setClickable(false);
        b4.setClickable(false);
    }

}
