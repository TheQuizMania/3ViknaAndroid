package a.b.c.quizmania.Fragments;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import a.b.c.quizmania.Entities.QuestionStats;
import a.b.c.quizmania.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MultipleChoiceFragment extends Fragment {

    // Views
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private TextView question;

    //Strings
    String questionTxt;

    public MultipleChoiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_multiple_choise, container, false);
    }


    public void printAnswers(String[] answers) {
        // Get the activity
        Activity activity = getActivity();

        question = activity.findViewById(R.id.question);
        questionTxt = question.getText().toString();

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
        // Setting default colors on the buttons
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
        // When clicked you can't click another answer
        disableButtons();
        // Get the question fragment to call functions within
        QuestionDisplayFragment questionFragment = (QuestionDisplayFragment) getFragmentManager().findFragmentById(R.id.question_fragment);


        QuestionStats stats;
        // Checks whether it was the right answer and changes the color of the button accordingly
        switch (view.getId()) {
            case R.id.answer_1:
                if(questionFragment.checkAnswer(b1.getText().toString())) {
                    //TODO: add new questionstats
                    stats = new QuestionStats(0, questionTxt, "", "", new String[]{"", ""}, true);
//                    Toast.makeText(getActivity(), "Correct Answer", Toast.LENGTH_SHORT).show();
                    b1.setBackgroundColor(Color.parseColor("#00FF00"));
                } else {
                    //TODO: add new questionstats
//                    Toast.makeText(getActivity(), "Incorrect Answer", Toast.LENGTH_SHORT).show();
                    b1.setBackgroundColor(Color.parseColor("#FF0000"));
                }
                break;
            case R.id.answer_2:
                if(questionFragment.checkAnswer(b2.getText().toString())) {
                    //TODO: increment correct answers and add new questionstats
//                    Toast.makeText(getActivity(), "Correct Answer", Toast.LENGTH_SHORT).show();
                    b2.setBackgroundColor(Color.parseColor("#00FF00"));
                } else {
                    //TODO: add new questionstats
//                    Toast.makeText(getActivity(), "Incorrect Answer", Toast.LENGTH_SHORT).show();
                    b2.setBackgroundColor(Color.parseColor("#FF0000"));
                }
                break;
            case R.id.answer_3:
                if(questionFragment.checkAnswer(b3.getText().toString())) {
                    //TODO: increment correct answers and add new questionstats
//                    Toast.makeText(getActivity(), "Correct Answer", Toast.LENGTH_SHORT).show();
                    b3.setBackgroundColor(Color.parseColor("#00FF00"));
                } else {
                    //TODO: add new questionstats
//                    Toast.makeText(getActivity(), "Incorrect Answer", Toast.LENGTH_SHORT).show();
                    b3.setBackgroundColor(Color.parseColor("#FF0000"));
                }
                break;
            case R.id.answer_4:
                if(questionFragment.checkAnswer(b4.getText().toString())) {
                    //TODO: increment correct answers and add new questionstats
//                    Toast.makeText(getActivity(), "Correct Answer", Toast.LENGTH_SHORT).show();
                    b4.setBackgroundColor(Color.parseColor("#00FF00"));
                } else {
                    //TODO: add new questionstats
//                    Toast.makeText(getActivity(), "Incorrect Answer", Toast.LENGTH_SHORT).show();
                    b4.setBackgroundColor(Color.parseColor("#FF0000"));
                }
                break;
            default:
                Toast.makeText(getActivity(), "Error came up", Toast.LENGTH_SHORT).show();
                break;
        }

        // Colours the right answer green
        printRightAnswer(questionFragment.getRightAnswer());
        // Stops the thread keeping the question on the screen
        questionFragment.stopCurrentTask();
    }

    private void disableButtons() {
        // Disables the button
        b1.setClickable(false);
        b2.setClickable(false);
        b3.setClickable(false);
        b4.setClickable(false);
    }

    private void printRightAnswer(String correctAnswer) {
        // Prints the right button green
        if(b1.getText().toString().matches(correctAnswer)) {
            b1.setBackgroundColor(Color.parseColor("#00FF00"));
        } else if(b2.getText().toString().matches(correctAnswer)) {
            b2.setBackgroundColor(Color.parseColor("#00FF00"));
        } else if(b3.getText().toString().matches(correctAnswer)) {
            b3.setBackgroundColor(Color.parseColor("#00FF00"));
        } else {
            b4.setBackgroundColor(Color.parseColor("#00FF00"));
        }
    }
}
