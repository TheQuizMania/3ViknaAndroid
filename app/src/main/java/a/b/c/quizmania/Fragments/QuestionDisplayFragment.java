package a.b.c.quizmania.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import a.b.c.quizmania.Entities.QuestionStats;
import a.b.c.quizmania.Entities.Score;
import a.b.c.quizmania.Entities.StaticVariables;
import a.b.c.quizmania.Jobs.BackgroundJob;
import a.b.c.quizmania.Jobs.MessageSender;
import a.b.c.quizmania.Jobs.UiCallback;
import a.b.c.quizmania.R;
import a.b.c.quizmania.UI.MultiPlayerResultsActivity;
import a.b.c.quizmania.UI.SinglePlayerResultsActivity;
import a.b.c.quizmania.utilities.Utility;

import static a.b.c.quizmania.Entities.StaticVariables.currChallenge;
import static a.b.c.quizmania.Entities.StaticVariables.pendingChallenge;
import static a.b.c.quizmania.Entities.StaticVariables.question;
import static a.b.c.quizmania.UI.QuestionActivity.category;
import static a.b.c.quizmania.UI.QuestionActivity.difficulty;

/**
 * A fragment that shows the questions as well as the progress bar and question counter.
 */
public class QuestionDisplayFragment extends Fragment {

    // Fragment manager and transaction to Control the
    private FragmentManager fManager;
    private FragmentTransaction fTransaction;
    // Hold the id at the current question
    private int questionId;
    private boolean isRunning;
    // Array of AsyncTasks
    private BackgroundJob[] task;
    public Score score;
    private List<QuestionStats> questionsList;
    // Views
    private TextView questionTxt;
    private TextView questionCountTxt;
    private ProgressBar progressBar;
    private MessageSender msgSender;
    // Required empty public constructor
    public QuestionDisplayFragment() {}

    @SuppressLint("CommitTransaction")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initiates the fragment manager ant transaction
        fManager = getFragmentManager();
        fTransaction = fManager.beginTransaction();


        // Initiate the isMul as false because multiple choice is not visible
        return inflater.inflate(R.layout.fragment_question_display, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Finds Views
        questionTxt = getActivity().findViewById(R.id.question);
        questionCountTxt = getActivity().findViewById(R.id.question_count);
        progressBar = getActivity().findViewById(R.id.progress_bar_question_fragment);
        questionsList = new ArrayList<>();
        score = new Score();

        MultipleChoiceFragment displayFragment = new MultipleChoiceFragment();
        fTransaction.replace(R.id.answer_fragment, displayFragment).commit();

        isRunning = true;

        // Initiate questionid as 0
        questionId = 0;
        //Get game settings from the activity

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
    //stops the background tasks if a user quits early
    public void stopFragment() {
        isRunning = false;
        for(AsyncTask instance: task) {
            if(instance.getStatus() == AsyncTask.Status.RUNNING) {
                instance.cancel(true);
            }
        }
    }
    //display questions
    private void displayQuestion(int i) {
        Log.d("QUIZ_APP", "displayQuestion() called");

        // Checks whether the question variable initiated in Selection Activity was initialized
        if(question != null) {
            // Checks if the question type is multiple choice
            // Gets all the answers and displays them in the MultipleChoiceFragment
            List<String> answers = getAnswers(i);
            displayMultipleQuestion(answers);
            // Displays the question
            questionTxt.setText(StringEscapeUtils.unescapeHtml4(question.getResults().get(i).getQuestion()));
            questionCountTxt.setText(String.format(getString(R.string.out_of_10), questionId + 1));

        } else {
            // If the question variable was null, display error message
            questionTxt.setText(getString(R.string.question_null_error_message));
        }
    }
    //displays multiple choice question. (Earlier versions also supported true/false questions)
    private void displayMultipleQuestion(List<String> answers) {
        Log.d("QUIZ_APP", "MultipleChoice() Called");
        // Checks if Multiple Choice Fragment is already displayed on the screen

        // Finds the fragment and prints the answers on the buttons
        MultipleChoiceFragment fragment = (MultipleChoiceFragment) fManager.findFragmentById(R.id.answer_fragment);
        fragment.printAnswers(answers);
    }
    //returns a list of answers to the current question
    private List<String> getAnswers(int id) {
        Log.d("QUIZ_APP", "getAnswers() called");

        // Gets all the answers and stores them in variables
        String answer1 = question.getResults().get(id).getCorrectAnswer();
        String answer2 = question.getResults().get(id).getIncorrectAnswers().get(0);
        String answer3 = question.getResults().get(id).getIncorrectAnswers().get(1);
        String answer4 = question.getResults().get(id).getIncorrectAnswers().get(2);

        // Make an array out of the question
        List<String> retVal = new ArrayList<>();
        retVal.add(answer1);
        retVal.add(answer2);
        retVal.add(answer3);
        retVal.add(answer4);
        // Decode the strings in the array
        for(int i = 0; i < 4; i++) {
            // StringEscapeUtils library to decode html4 encoded strings
            retVal.set(i, StringEscapeUtils.unescapeHtml4(retVal.get(i)));
        }

        Collections.shuffle(retVal);
        return retVal;
    }

    public boolean checkAnswer(String answer) {
        // Returns true if the answer matches the answer asked
        return StringEscapeUtils.unescapeHtml4(question.getResults().get(questionId).getCorrectAnswer()).equals(answer);
    }

    public void stopCurrentTask() {
        // Cancel the current task
        task[questionId].cancel(true);
    }
    //After all the questions this function prepares for the result view
    private void showResults() {
        Log.d("QUIZ_APP", "showResults() called");
        String mode = getActivity().getIntent().getStringExtra("MODE");
        //Initialize and write data to Firebase
        initScore();
        writeScoreToDatabase();
        //determines what game mode you are playing
        if(mode.matches("CHALLENGER")) {
            //If you are challenging someone, initializes challenge, lets the other player know
            //and sends you to the results view
            initChallenge();
            msgSender = new MessageSender();
            msgSender.sendChallenge(pendingChallenge.getChallengee().getPushToken());
            Intent i = new Intent(getActivity(), SinglePlayerResultsActivity.class);
            i.putExtra("MODE", "MULTI");
            startActivity(i);
            getActivity().finish();
        } else if (mode.matches("CHALLENGEE")) {
            //If you have been challenged, informs other player that you've completed the challenge
            // and sends you to the results screen
            updateChallenge();
            startMPResults();
        } else {
            //If you're playing single player, sends you to the results screen
            Intent i = new Intent(getActivity(), SinglePlayerResultsActivity.class);
            i.putExtra("MODE", "SINGLE");
            startActivity(i);
            getActivity().finish();
        }
    }

    private void startMPResults() {
        //Initializes next activity with intent and extras
        Intent intent = new Intent(getActivity(), MultiPlayerResultsActivity.class);
        intent.putExtra("challengeID", currChallenge.getId());
        startActivity(intent);
        getActivity().finish();
    }

    private void updateChallenge() {
        // sets the boolean variable false that indicates that the challenge is finish
        currChallenge.setActive(false);
        // Sets the score of the one that was challenged
        currChallenge.setChallengeeScore(score);
        // Writes the new data to the database
        Utility.updateChallenge(currChallenge);
    }

    private void initChallenge() {
        //  Sets the category and difficulty of the challenge
        if(category.equals("")){
            pendingChallenge.setCategory("Random");
        } else {
            pendingChallenge.setCategory(category);
        }
        pendingChallenge.setDifficulty(difficulty);

        // Sets the score of the challenger in
        pendingChallenge.setChallengerScore(score);

        // Writes down the challenge to the database
        Utility.addChallenge(pendingChallenge);
    }

    private void writeScoreToDatabase() {
        //Writes score to database after the game
        Utility.addScore(score);
    }

    private void initScore(){
        //initializes the score before it is written to the database
        List<QuestionStats> retString = questionsList;
        score.setQuestionStats(retString);
        if(category.equals("")){
                score.setCategory("Random");
        } else {
            score.setCategory(category);
        }
        score.setDifficulty(difficulty);

        int count = 0;
        for(QuestionStats q : questionsList){
            if(q.isWasCorrect()){
                count++;
            }
        }
        score.setCorrectAnswers(count);
        StaticVariables.setCurrScore(score);
    }


    public String getRightAnswer() {
        // Returns the right answer
        return StringEscapeUtils.unescapeHtml4(question.getResults().get(questionId).getCorrectAnswer());
    }

    public List<String> getWrongAnswers(int id){
        //returns a list of answers with the correct answer removed
        Log.d("QUIZ_APP", "getWrongAnswers() called");
        List<String> answersArr = getAnswers(id);
        answersArr.remove(getRightAnswer());
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
                // If the thread hasn't displayed on the board display the board and make the variable true
                if(!isDisplayed) {
                    Log.d("QUIZ_APP", "DisplayOn() task[" + questionId + "]");
                    // Shows the progress bar
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                    displayQuestion(questionId);
                    isDisplayed = true;
                    //sets question parameters for each question
                    Log.d("QUIZ_APP", "Setting currQuest parameters");
                    currQuest.setRightAnswer(getRightAnswer());
                    List<String> answers = getWrongAnswers(questionId);
                    currQuest.setWrongAnswers(answers);
                    currQuest.setStatsQuestion(question.getResults().get(questionId).getQuestion());
                    currQuest.setQuestionCategory(question.getResults().get(questionId).getCategory());
                    currQuest.setQuestionDifficulty(question.getResults().get(questionId).getDifficulty());
                }
                //updates the progress bar
                progressBar.setProgress(values[0]);
                //updates until you have answered
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
                if(questionId == 10 && isRunning) {
                    Log.d("QUIZ`_APP", "10 questions answered. Exiting");
                    showResults();
                }
            }

            @Override
            public void onCancelled() {
                Log.d("QUIZ_APP", "onCancelled() task[" + questionId + "]");
                if(!isRunning) {
                    return;
                }
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
                if(questionId == 10 && isRunning) {
                    Log.d("QUIZ_APP", "10 questions answered. Exiting");
                    showResults();
                }
            }
        });
    }




}