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
        assertEquals(mActivity.selectedDifficulty, "&difficulty=medium");


        clickDifficultySpinner("Hard");
        assertEquals(mActivity.selectedDifficulty, "&difficulty=hard");

        clickDifficultySpinner("Easy");
        assertEquals(mActivity.selectedDifficulty, "&difficulty=easy");
    }

    @Test
    public void categorySpinnerTest() {
        clickCategorySpinner("Random");
        assertEquals(mActivity.selectedCategory, "");

        clickCategorySpinner("Film");
        assertEquals(mActivity.selectedCategory, "&category=11");

        clickCategorySpinner("Science & nature");
        assertEquals(mActivity.selectedCategory, "&category=17");

        clickCategorySpinner("General knowledge");
        assertEquals(mActivity.selectedCategory, "&category=9");

        clickCategorySpinner("Sports");
        assertEquals(mActivity.selectedCategory, "&category=21");

        clickCategorySpinner("Mythology");
        assertEquals(mActivity.selectedCategory, "&category=20");

        clickCategorySpinner("Politics");
        assertEquals(mActivity.selectedCategory, "&category=24");

        clickCategorySpinner("Geography");
        assertEquals(mActivity.selectedCategory, "&category=22");

        clickCategorySpinner("Video games");
        assertEquals(mActivity.selectedCategory, "&category=15");

        clickCategorySpinner("Television");
        assertEquals(mActivity.selectedCategory, "&category=14");

        clickCategorySpinner("Music");
        assertEquals(mActivity.selectedCategory, "&category=12");
    }

    @Test
    public void questionAmountTest() {
        clickDifficultySpinner("Medium");
        clickCategorySpinner("Mythology");
        startBtn.perform(click());

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(mActivity.question.getResults().length, 10);

    }

    @Test
    public void correctCategoryTest() {
        clickDifficultySpinner("Medium");
        clickCategorySpinner("Mythology");
        startBtn.perform(click());


        //Sleep til að bíða eftir api request
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //loopar í gegnum allar spurningarnar og athugar hvort þær séu ekki allar
        //í réttum category
        for(int i = 0; i < 10; i++){
            assertEquals(mActivity.question.getResults()[i].getCategory(), "Mythology");
            assertEquals(mActivity.question.getResults()[i].getDifficulty(), "medium");
        }

    }

    private void clickCategorySpinner(String categoryString) {
        //Clickar á category dropdown, clickar á category sem matchar strengin categoryString
        //athugar hvort spinnerText matchi við categoryString
        category.perform(click());
        onData(is(categoryString)).perform(click());
        category.check(matches(withSpinnerText(containsString(categoryString))));
    }

    private void clickDifficultySpinner(String difficultyString) {
        difficulty.perform(click());
        onData(is(difficultyString)).perform(click());
        difficulty.check(matches(withSpinnerText(containsString(difficultyString))));
    }

    @After
    public void tearDown() throws Exception {
    }
}
