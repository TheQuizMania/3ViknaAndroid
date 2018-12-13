package a.b.c.quizmania.Fragments;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import a.b.c.quizmania.R;

/**
 * A fragment that shows the buttons for the answers
 */
public class MultipleChoiceFragment extends Fragment {

    // Views
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    //Strings
    private String guess;
    // Required empty public constructor
    public MultipleChoiceFragment(){}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return Objects.requireNonNull(inflater).inflate(R.layout.fragment_multiple_choice, container, false);
    }


    public void printAnswers(List<String> answers) {
        // Get the activity
        Activity activity = getActivity();

        // Finding views
        b1 = activity.findViewById(R.id.answer_1);
        b2 = activity.findViewById(R.id.answer_2);
        b3 = activity.findViewById(R.id.answer_3);
        b4 = activity.findViewById(R.id.answer_4);

        //Setting default button drawable settings
        b1.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        b2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        b3.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        b4.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);


        // Setting the answers
        b1.setText(answers.get(0));
        b2.setText(answers.get(1));
        b3.setText(answers.get(2));
        b4.setText(answers.get(3));

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
        b1.setBackgroundColor(Color.parseColor("#64686d"));
        b2.setTextColor(Color.parseColor("#FFFFFF"));
        b2.setBackgroundColor(Color.parseColor("#64686d"));
        b3.setTextColor(Color.parseColor("#FFFFFF"));
        b3.setBackgroundColor(Color.parseColor("#64686d"));
        b4.setTextColor(Color.parseColor("#FFFFFF"));
        b4.setBackgroundColor(Color.parseColor("#64686d"));
    }

    public void btnClicked(View view) {
        // When clicked you can't click another answer
        disableButtons();
        // Get the question fragment to call functions within
        QuestionDisplayFragment questionFragment = (QuestionDisplayFragment) getFragmentManager().findFragmentById(R.id.question_fragment);

        // Checks whether it was the right answer and changes the color of the button accordingly
        switch (view.getId()) {
            case R.id.answer_1:
                guess = b1.getText().toString();
                if(questionFragment.checkAnswer(b1.getText().toString())) {
                    b1.setBackgroundColor(Color.parseColor("#08ad08"));
                    b1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.round_done_white_18dp, 0, 0);
                } else {
                    b1.setBackgroundColor(Color.parseColor("#e51010"));
                    b1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.round_clear_white_18dp, 0, 0);
                }
                break;
            case R.id.answer_2:
                guess = b2.getText().toString();
                if(questionFragment.checkAnswer(b2.getText().toString())) {
                    b2.setBackgroundColor(Color.parseColor("#08ad08"));
                    b2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.round_done_white_18dp, 0, 0);
                } else {
                    b2.setBackgroundColor(Color.parseColor("#e51010"));
                    b2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.round_clear_white_18dp, 0, 0);
                }
                break;
            case R.id.answer_3:
                guess = b3.getText().toString();
                if(questionFragment.checkAnswer(b3.getText().toString())) {
                    b3.setBackgroundColor(Color.parseColor("#08ad08"));
                    b3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.round_done_white_18dp, 0, 0);
                } else {
                    b3.setBackgroundColor(Color.parseColor("#e51010"));
                    b3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.round_clear_white_18dp, 0, 0);
                }
                break;
            case R.id.answer_4:
                guess = b4.getText().toString();
                if(questionFragment.checkAnswer(b4.getText().toString())) {
                    b4.setBackgroundColor(Color.parseColor("#08ad08"));
                    b4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.round_done_white_18dp, 0, 0);
                } else {
                    b4.setBackgroundColor(Color.parseColor("#e51010"));
                    b4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.round_clear_white_18dp, 0, 0);
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
        // Disables the buttons
        b1.setClickable(false);
        b2.setClickable(false);
        b3.setClickable(false);
        b4.setClickable(false);
    }

    private void printRightAnswer(String correctAnswer) {
        // Prints the right button green
        if(correctAnswer.equals(b1.getText().toString())) {
            b1.setBackgroundColor(Color.parseColor("#08ad08"));
            b1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.round_done_white_18dp, 0, 0);
        } else if(correctAnswer.equals(b2.getText().toString())) {
            b2.setBackgroundColor(Color.parseColor("#08ad08"));
            b2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.round_done_white_18dp, 0, 0);
        } else if(correctAnswer.equals(b3.getText().toString())) {
            b3.setBackgroundColor(Color.parseColor("#08ad08"));
            b3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.round_done_white_18dp, 0, 0);
        } else if (correctAnswer.equals(b4.getText().toString())) {
            b4.setBackgroundColor(Color.parseColor("#08ad08"));
            b4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.round_done_white_18dp, 0, 0);
        }
    }
    //A getter for the other fragment
    public String playerGuess(){
        return guess;
    }
}
