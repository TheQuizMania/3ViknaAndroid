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
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class SelectionActivityTest {


    private ViewInteraction difficulty;

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
    }

    @Test
    public void categorySpinnerTest() {
        difficulty.perform(click());
        onData(is("Medium")).perform(click());
        difficulty.check(matches(withSpinnerText(containsString("Medium"))));
        assertEquals(mActivity.selectedDifficulty, "&difficulty=Medium");


        difficulty.perform(click());
        onData(is("Hard")).perform(click());
        difficulty.check(matches(withSpinnerText(containsString("Hard"))));
        assertEquals(mActivity.selectedDifficulty, "&difficulty=hard");
    }



    @After
    public void tearDown() throws Exception {
    }
}