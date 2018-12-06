package a.b.c.quizmania.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.Arrays;
import java.util.concurrent.Semaphore;

import a.b.c.quizmania.Entities.Question;
import a.b.c.quizmania.Jobs.BackroundJob;
import a.b.c.quizmania.Jobs.UiCallback;
import a.b.c.quizmania.R;

import static a.b.c.quizmania.UI.SelectionActivity.question;
import static java.sql.Types.NULL;
import static java.util.Collections.sort;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionDisplayFragment extends Fragment {

    // Boolean variable that is true when Multiple Choice fragment is displayed
    private boolean isMul;

    // Fragment manager and transaction to Control the
    private FragmentManager fmanager;
    private FragmentTransaction ftransaction;

    // Hold the id at the current question
    private int questionId;

    // Array of AsyncTasks
    private BackroundJob[] task;

    // Views
    TextView questionTxt;

    public QuestionDisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initiates the fragment manager ant transaction
        fmanager = getFragmentManager();
        ftransaction = fmanager.beginTransaction();

        // Initiate the isMul as false because multiple choice is not visible
        isMul = false;
        return inflater.inflate(R.layout.fragment_question_display, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Finds Views
        questionTxt = getActivity().findViewById(R.id.question);
        // Initiate questionid as 0
        questionId = 0;
        // Makes 10 AsyncTasks
        task = new BackroundJob[10];
        for(int i = 0; i < 10; i++) {
            if(task[i] == null || task[i].getStatus() != AsyncTask.Status.RUNNING) {
                // Creates the backroundJobs and executes them with the right id ranging 0 - 9
                task[i] = createBackroundJob();
                task[i].execute(i);
            }
        }
    }

    private void displayQuestion(int i) {
        // Checks whether the question variable initiated in Selection Activity was initialized
        if(question != null) {
            // Checks if the question type is multiplie choice
            if(question.getResults()[i].getType().equals("multiple")) {
                // Gets all the answers and displays them in the MultipleChoiceFragment
                String[] answers = getAnswers(i);
                displayMultipleQuestion(answers);
            } else {
                displayTrueFalse();
            }
            // Displays the question
            questionTxt.setText(StringEscapeUtils.unescapeHtml4(question.getResults()[i].getQuestion()));
        } else {
            // If the question variable was null, display error message
            questionTxt.setText(getString(R.string.question_null_error_message));
        }
    }

    private void displayMultipleQuestion(String[] answers) {
        Log.d("QUIZ_APP", "MultipleChoise() Called");
        // Checks if Multiple Choice Fragment is already displayed on the screen
        if(!isMul) {
            // Makes boolean true and replaces the previous view with a multiple choice fragment
            isMul = true;
            MultipleChoiseFragment displayFragment = new MultipleChoiseFragment();
            ftransaction.replace(R.id.answer_fragment, displayFragment).commitNow();
            fmanager.executePendingTransactions();
        }
        // Finds the fragment and prints the answers on the buttons
        MultipleChoiseFragment fragment = (MultipleChoiseFragment) fmanager.findFragmentById(R.id.answer_fragment);
        fragment.printAnswers(answers);
    }

    private void displayTrueFalse() {
        Log.d("QUIZ_APP", "True&False() Called");
    }

    private String[] getAnswers(int id) {
        // Gets all the answers and stores them in variables
        String answer1 = question.getResults()[id].getCorrectAnswer();
        String answer2 = question.getResults()[id].getIncorrectAnswers()[0];
        String answer3 = question.getResults()[id].getIncorrectAnswers()[1];
        String answer4 = question.getResults()[id].getIncorrectAnswers()[2];

        // Make an array out of the question
        String[] retVal = {
                answer1,
                answer2,
                answer3,
                answer4
        };

        // Decode the strings in the array
        for(int i = 0; i < retVal.length; i++) {
            // StringEscapeUitls library to decode html4 encoded strings
            retVal[i] = StringEscapeUtils.unescapeHtml4(retVal[i]);
        }

        Arrays.sort(retVal);

        return retVal;
    }

    public boolean checkAnswer(String answer) {
        // Returns true if the answer matches the answer asked
        if(StringEscapeUtils.unescapeHtml4(question.getResults()[questionId].getCorrectAnswer()).equals(answer)) {
            return true;
        }
        return false;
    }

    public void stopCurrentTask() {
        // Cancel the current task
        task[questionId].cancel(true);
    }

    private void showResults() {
        Log.d("QUIZ_APP", "showResults() called");
    }


    public String getRightAnswer() {
        // Returns the right answer
        return StringEscapeUtils.unescapeHtml4(question.getResults()[questionId].getCorrectAnswer());
    }

    private BackroundJob createBackroundJob() {
        return new BackroundJob(new UiCallback<Integer>() {
            // Boolean to check if the answers have been displayed on the board
            private boolean isDisplayed;
            @Override
            public void onPreExecute() {
                Log.d("QUIZ_APP", "onPreExecute() task[" + questionId + "]");
                // Initialize the boolean as false
                isDisplayed = false;

            }

            @Override
            public void onProgressUpdate(Integer... values) {
//                Log.d("QUIZ_APP", "onProgressUpdate() task[" + values[0] + "]");
                // If the thread hasn't displayed on the board display the board and make the variable true
                if(!isDisplayed) {
                    Log.d("QUIZ_APP", "DisplayOn() task[" + values[0] + "]");
                    displayQuestion(values[0]);
                    isDisplayed = true;
                }
            }

            @Override
            public void onPostExecute(Integer integer) {
                Log.d("QUIZ_APP", "onPostExecute() task[" + questionId + "]");
                // If the time runs out make the boolean variable false and increment the questionId
                isDisplayed = false;
                questionId++;
            }

            @Override
            public void onCancelled() {
                Log.d("QUIZ_APP", "onCancelled() task[" + questionId + "]");
                // If an answer was selected make the boolean variable false and increment questionId
                // Sleep for 1 sec to show the right answer and if all questions have been asked call showResult();
                isDisplayed = false;
                SystemClock.sleep(1000);
                questionId++;
                if(questionId == 10) {
                    showResults();
                }
            }
        });
    }
}