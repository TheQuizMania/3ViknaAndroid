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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import a.b.c.quizmania.Entities.QuestionStats;
import a.b.c.quizmania.Entities.Score;
import a.b.c.quizmania.Jobs.BackgroundJob;
import a.b.c.quizmania.Jobs.UiCallback;
import a.b.c.quizmania.R;
import a.b.c.quizmania.UI.QuestionActivity;

import static a.b.c.quizmania.UI.QuestionActivity.category;
import static a.b.c.quizmania.UI.QuestionActivity.difficulty;
import static a.b.c.quizmania.UI.SelectionActivity.question;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionDisplayFragment extends Fragment {

    // Boolean variable that is true when Multiple Choice fragment is displayed
    private boolean isMul;

    // Fragment manager and transaction to Control the
    private FragmentManager fManager;
    private FragmentTransaction fTransaction;

    // Hold the id at the current question
    private int questionId;

    // Array of AsyncTasks
    private BackgroundJob[] task;

    private Score score;

    private ArrayList<QuestionStats> questionsList;

    // Views
    private TextView questionTxt;
    private ProgressBar progressBar;

    public QuestionDisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initiates the fragment manager ant transaction
        fManager = getFragmentManager();
        fTransaction = fManager.beginTransaction();


        // Initiate the isMul as false because multiple choice is not visible
        isMul = false;
        return inflater.inflate(R.layout.fragment_question_display, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Finds Views
        questionTxt = getActivity().findViewById(R.id.question);
        progressBar = (ProgressBar)getActivity().findViewById(R.id.progress_bar_question_fragment);
        questionsList = new ArrayList<>();


        // Initiate questionid as 0
        questionId = 0;
        //Get game settings from the activity
        QuestionActivity a = (QuestionActivity) getActivity();

        // Makes 10 AsyncTasks
        task = new BackgroundJob[10];
        for(int i = 0; i < 10; i++) {
            if(task[i] == null || task[i].getStatus() != AsyncTask.Status.RUNNING) {
                // Creates the backgroundJobs and executes them with the right id ranging 0 - 9
                task[i] = createBackgroundJob();
                task[i].execute(i);
            }
        }
    }

    private void displayQuestion(int i) {

        // Checks whether the question variable initiated in Selection Activity was initialized
        if(question != null) {
            // Checks if the question type is multiple choice
            // Gets all the answers and displays them in the MultipleChoiceFragment
            String[] answers = getAnswers(i);
            displayMultipleQuestion(answers);
            // Displays the question
            questionTxt.setText(StringEscapeUtils.unescapeHtml4(question.getResults()[i].getQuestion()));
        } else {
            // If the question variable was null, display error message
            questionTxt.setText(getString(R.string.question_null_error_message));
        }
    }

    private void displayMultipleQuestion(String[] answers) {
        Log.d("QUIZ_APP", "MultipleChoice() Called");
        // Checks if Multiple Choice Fragment is already displayed on the screen
        if(!isMul) {
            // Makes boolean true and replaces the previous view with a multiple choice fragment
            isMul = true;
            MultipleChoiceFragment displayFragment = new MultipleChoiceFragment();
            fTransaction.replace(R.id.answer_fragment, displayFragment).commitNow();
            fManager.executePendingTransactions();
        }
        // Finds the fragment and prints the answers on the buttons
        MultipleChoiceFragment fragment = (MultipleChoiceFragment) fManager.findFragmentById(R.id.answer_fragment);
        fragment.printAnswers(answers);
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

        if(retVal.length < 4){
            Toast.makeText(getActivity(), "this dude", Toast.LENGTH_SHORT).show();
        }

        // Decode the strings in the array
        for(int i = 0; i < retVal.length; i++) {
            // StringEscapeUtils library to decode html4 encoded strings
            retVal[i] = StringEscapeUtils.unescapeHtml4(retVal[i]);
        }

        Arrays.sort(retVal);


        return retVal;
    }

    public boolean checkAnswer(String answer) {
        // Returns true if the answer matches the answer asked
        return StringEscapeUtils.unescapeHtml4(question.getResults()[questionId].getCorrectAnswer()).equals(answer);
    }

    public void stopCurrentTask() {
        // Cancel the current task
        task[questionId].cancel(true);
    }

    private void showResults() {
        Log.d("QUIZ_APP", "showResults() called");
        initScore();
        getActivity().finish();
    }

    private void initScore(){
        QuestionStats[] retString = questionsList.toArray(new QuestionStats[10]);
        score.setQuestionStats(retString);
        score.setCategory(category);
        score.setDifficulty(difficulty);
        int count = 0;
        for(QuestionStats q : questionsList){
            if(q.isWasCorrect()){
                count++;
            }
        }
        score.setCorrectAnswers(count);
    }


    public String getRightAnswer() {
        // Returns the right answer
        return StringEscapeUtils.unescapeHtml4(question.getResults()[questionId].getCorrectAnswer());
    }

    public String[] getWrongAnswers(int id){
        Log.d("QUIZ_APP", "getWrongAnswers() called");
        String[] answersArr = getAnswers(id);
        ArrayList<String> arrList = new ArrayList<>();
        Collections.addAll(arrList, answersArr);
        arrList.remove(getRightAnswer());

        answersArr = arrList.toArray(new String[3]);

        if(answersArr.length < 3){
            Toast.makeText(getActivity(), "FUCKER", Toast.LENGTH_SHORT).show();
        }

        return answersArr;
    }

    private BackgroundJob createBackgroundJob() {
        return new BackgroundJob(new UiCallback<Integer>() {
            // Boolean to check if the answers have been displayed on the board
            private boolean isDisplayed;
            private QuestionStats currQuest;
            @Override
            public void onPreExecute() {
                Log.d("QUIZ_APP", "onPreExecute() task[" + questionId + "]");
                // Initialize the boolean as false
                isDisplayed = false;
                currQuest = new QuestionStats();
            }

            @Override
            public void onProgressUpdate(Integer... values) {
//                Log.d("QUIZ_APP", "onProgressUpdate() task[" + values[0] + "]");
                // If the thread hasn't displayed on the board display the board and make the variable true
                if(!isDisplayed) {
                    Log.d("QUIZ_APP", "DisplayOn() task[" + questionId + "]");
                    // Shows the progress bar
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                    displayQuestion(questionId);
                    isDisplayed = true;
                    if(currQuest.getRightAnswer() == null || currQuest.getWrongAnswers() == null
                            || currQuest.getStatsQuestion() == null) {
                        Log.d("QUIZ_APP", "Setting currQuest parameters");
                        currQuest.setRightAnswer(getRightAnswer());
                        String[] answers = getWrongAnswers(questionId);
                        currQuest.setWrongAnswers(answers);
                        currQuest.setStatsQuestion(question.getResults()[questionId].getQuestion());
                    }
                }
                progressBar.setProgress(values[0]);
                currQuest.setTimeToAnswer(20000 - values[0]);
            }

            @Override
            public void onPostExecute(Integer integer) {
                Log.d("QUIZ_APP", "onPostExecute() task[" + questionId + "]");
                // If the time runs out make the boolean variable false and increment the questionId

                currQuest.setWasCorrect(false);
                currQuest.setTimeToAnswer(20000);
                isDisplayed = false;
                questionId++;
                questionsList.add(currQuest);
                if(questionId == 10) {
                    Log.d("QUIZ_APP", "10 questions answered. Exiting");
                    showResults();
                }
            }

            @Override
            public void onCancelled() {
                Log.d("QUIZ_APP", "onCancelled() task[" + questionId + "]");
                MultipleChoiceFragment fragment
                        = (MultipleChoiceFragment) fManager.findFragmentById(R.id.answer_fragment);
                String pGuess = fragment.playerGuess();
                if(pGuess.equals(getRightAnswer())){
                    currQuest.setWasCorrect(true);
                } else {
                    currQuest.setWasCorrect(false);
                }
                // If an answer was selected make the boolean variable false and increment questionId
                // Sleep for 1 sec to show the right answer and if all questions have been asked call showResult();
                isDisplayed = false;
                SystemClock.sleep(1000);
                questionId++;
                questionsList.add(currQuest);
                if(questionId == 10) {
                    Log.d("QUIZ_APP", "10 questions answered. Exiting");
                    showResults();
                }
            }
        });
    }




}