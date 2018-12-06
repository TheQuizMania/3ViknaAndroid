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
import static java.util.Collections.sort;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionDisplayFragment extends Fragment {

//    private static Question question;
    private boolean isMul;
    FragmentManager fmanager;
    FragmentTransaction ftransaction;
    private int questionId;

    private BackroundJob task;


    public static Semaphore mutex;

    // Views
    TextView questionTxt;

    public QuestionDisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fmanager = getFragmentManager();
        ftransaction = fmanager.beginTransaction();
        isMul = false;
        return inflater.inflate(R.layout.fragment_question_display, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        questionTxt = getActivity().findViewById(R.id.question);
        if(task == null || task.getStatus() != AsyncTask.Status.RUNNING) {
            for(int i = 0; i < 10; i++) {
                task = createBackroundJob();
                task.execute(i);
                while(task.isCancelled()) {}
            }
        }
    }

//    private void getQuestions() {
//        Ion.with(this)
//                .load(url)
//                .asString()
//                .setCallback(new FutureCallback<String>() {
//                    @Override
//                    public void onCompleted(Exception e, String result) {
//                        Gson gson = new Gson();
//                        question = gson.fromJson(result, Question.class);
//                    }
//                });
//    }

    private void displayQuestion(int i) {
        if(question != null) {
            if(question.getResults()[i].getType().equals("multiple")) {
                String[] answers = getAnswers(i);
                displayMultipleQuestion(answers);
            } else {
                displayTrueFalse();
            }
            questionTxt.setText(StringEscapeUtils.unescapeHtml4(question.getResults()[i].getQuestion()));
        } else {
            questionTxt.setText(getString(R.string.question_null_error_message));
        }
    }

    private void displayMultipleQuestion(String[] answers) {

        if(!isMul) {
            isMul = true;
            MultipleChoiseFragment displayFragment = new MultipleChoiseFragment();
            ftransaction.replace(R.id.answer_fragment, displayFragment).commitNow();
            fmanager.executePendingTransactions();
        }
        MultipleChoiseFragment fragment = (MultipleChoiseFragment) fmanager.findFragmentById(R.id.answer_fragment);
//        fragment.setBtnColors();
        fragment.printAnswers(answers);
    }

    private void displayTrueFalse() {

    }

    private String[] getAnswers(int id) {
        String answer1 = question.getResults()[id].getCorrectAnswer();
        String answer2 = question.getResults()[id].getIncorrectAnswers()[0];
        String answer3 = question.getResults()[id].getIncorrectAnswers()[1];
        String answer4 = question.getResults()[id].getIncorrectAnswers()[2];

        String[] retVal = {
                answer1,
                answer2,
                answer3,
                answer4
        };

        for(int i = 0; i < retVal.length; i++) {
            // StringEscapeUitls library to decode html4 encoded strings
            retVal[i] = StringEscapeUtils.unescapeHtml4(retVal[i]);
        }

        Arrays.sort(retVal);

        return retVal;
    }

    public boolean checkAnswer(String answer) {
        task.cancel(true);
        if(question.getResults()[questionId].getCorrectAnswer().equals(answer)) {
            return true;
        }
        return false;
    }

    private BackroundJob createBackroundJob() {
        return new BackroundJob(new UiCallback<Integer>() {
            @Override
            public void onPreExecute() { }

            @Override
            public void onProgressUpdate(Integer... values) {
                questionId = values[0];
                displayQuestion(values[0]);
            }

            @Override
            public void onPostExecute(Integer integer) {
                Log.d("QUIZ_APP", "Stopped on id: " + integer);
            }

            @Override
            public void onCancelled() {
                Log.d("QUIZ_APP", "Cancelled Thread");
            }
        });
    }

}
