package a.b.c.quizmania.UI;

import android.content.Intent;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;


import a.b.c.quizmania.Entities.StaticVariables;
import a.b.c.quizmania.R;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

public class SelectionActivityTest {


    private ViewInteraction difficulty;
    private ViewInteraction category;
    private ViewInteraction startBtn;

    @Rule
    public ActivityTestRule<SelectionActivity> mActivityTestRule
            = new ActivityTestRule<>(SelectionActivity.class,
            false, false);

    private SelectionActivity mActivity = null;

     @Mock
     private FirebaseAuth fbAuth;

    @Before
    public void setUp() throws Exception {
        mActivityTestRule.launchActivity(new Intent());
        mActivity = mActivityTestRule.getActivity();
        difficulty = onView(withId(R.id.difficulty_dropdown));
        category = onView(withId(R.id.category_dropdown));
        startBtn = onView(withId(R.id.sp_play_btn));
    }

    @Test
    public void difficultySpinnerTest() {
        clickDifficultySpinner("Medium");
        assertEquals(mActivity.selectedDifficulty, "medium");


        clickDifficultySpinner("Hard");
        assertEquals(mActivity.selectedDifficulty, "hard");

        clickDifficultySpinner("Easy");
        assertEquals(mActivity.selectedDifficulty, "easy");
    }

    @Test
    public void categorySpinnerTest() {
        clickCategorySpinner("Random");
        assertEquals(mActivity.selectedCategory, "");

        clickCategorySpinner("Film");
        assertEquals(mActivity.selectedCategory, "11");

        clickCategorySpinner("Science & nature");
        assertEquals(mActivity.selectedCategory, "17");

        clickCategorySpinner("General Knowledge");
        assertEquals(mActivity.selectedCategory, "9");

        clickCategorySpinner("Sports");
        assertEquals(mActivity.selectedCategory, "21");

        clickCategorySpinner("Mythology");
        assertEquals(mActivity.selectedCategory, "20");

        clickCategorySpinner("Politics");
        assertEquals(mActivity.selectedCategory, "24");

        clickCategorySpinner("Geography");
        assertEquals(mActivity.selectedCategory, "22");

        clickCategorySpinner("Video Games");
        assertEquals(mActivity.selectedCategory, "15");

        clickCategorySpinner("Music");
        assertEquals(mActivity.selectedCategory, "12");
    }

    @Test
    public void questionAmountTest() {
        clickDifficultySpinner("Medium");
        clickCategorySpinner("Mythology");
        startBtn.perform(click());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(StaticVariables.question.getResults().size(), 10);

    }

    @Test
    public void correctCategoryTest() {
        clickDifficultySpinner("Medium");
        clickCategorySpinner("Mythology");
        startBtn.perform(click());


        //Sleep to wait for the api request
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //loop through all the questions and check whether they are all in
        //the correct category and difficulty
        for(int i = 0; i < 10; i++){
            assertEquals(StaticVariables.question.getResults().get(i).getCategory(), "Mythology");
            assertEquals(StaticVariables.question.getResults().get(i).getDifficulty(), "medium");
        }

    }

    private void clickCategorySpinner(String categoryString) {
        //Click the category dropdown, then click the dropdown that matcher the string
        category.perform(click());
        onData(is(categoryString)).perform(click());
        category.check(matches(withSpinnerText(containsString(categoryString))));
    }

    private void clickDifficultySpinner(String difficultyString) {
        //Click the difficulty dropdown, then click the dropdown that matcher the string
        difficulty.perform(click());
        onData(is(difficultyString)).perform(click());
        difficulty.check(matches(withSpinnerText(containsString(difficultyString))));
    }

    @After
    public void tearDown() throws Exception {
    }
}
