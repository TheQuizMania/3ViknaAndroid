package a.b.c.quizmania.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.Arrays;

import a.b.c.quizmania.Entities.Question;
import a.b.c.quizmania.Jobs.BackroundJob;
import a.b.c.quizmania.Jobs.UiCallback;
import a.b.c.quizmania.R;

import static java.util.Collections.sort;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionDisplayFragment extends Fragment {

    private Question question;
    private boolean isMul;
    FragmentManager fmanager;
    FragmentTransaction ftransaction;
    public String url = "https://opentdb.com/api.php?amount=10&category=9&difficulty=easy&type=multiple";

    private BackroundJob task;

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
            task = createBackroundJob();
            task.execute(1);
        }
    }

    private void getQuestions() {
        Ion.with(this)
                .load(url)
                .as(new TypeToken<Question>(){ })
                .setCallback(new FutureCallback<Question>() {
                    @Override
                    public void onCompleted(Exception e, Question result) {
//                        StringEscapeUtils.unescapeHtml4()
                        question = result;
                    }
                });
    }

    private void displayQuestion(int i) {
        if(question.getResults()[i].getType().equals("multiple")) {
            String[] answers = getAnswers(i);
            displayMultipleQuestion(answers);
        } else {
            displayTrueFalse();
        }
        // StringEscapeUitls library to decode html4 encoded strings
        questionTxt.setText(StringEscapeUtils.unescapeHtml4(question.getResults()[i].getQuestion()));
    }

    private void displayMultipleQuestion(String[] answers) {

        if(!isMul) {
            isMul = true;
            MultipleChoiseFragment displayFragment = new MultipleChoiseFragment();
            ftransaction.replace(R.id.answer_fragment, displayFragment).commitNow();
            fmanager.executePendingTransactions();
        }
        MultipleChoiseFragment fragment = (MultipleChoiseFragment) fmanager.findFragmentById(R.id.answer_fragment);
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

    private BackroundJob createBackroundJob() {
        return new BackroundJob(new UiCallback() {
            @Override
            public void onPreExecute() {
                getQuestions();
            }

            @Override
            public void onProgressUpdate(Integer... values) {
                displayQuestion(values[0]);
            }

            @Override
            public void onPostExecute() {

            }

            @Override
            public void onCancelled() {

            }
        });
    }

}
