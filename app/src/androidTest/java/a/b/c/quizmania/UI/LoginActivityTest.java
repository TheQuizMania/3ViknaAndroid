package a.b.c.quizmania.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import a.b.c.quizmania.R;

import static android.content.Context.MODE_PRIVATE;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public final ActivityTestRule<LoginActivity> activityTestRule
            = new ActivityTestRule<>(LoginActivity.class, true, false);

    private ViewInteraction email;
    private ViewInteraction passW;
    private ViewInteraction subBtn;


    @Before
    public void setUp() throws Exception{
        subBtn = onView(withId(R.id.sign_in));
        email = onView(withId(R.id.email_sign_in));
        passW = onView(withId(R.id.password_sign_in));
        SharedPreferences spMock = mock(SharedPreferences.class);
        when(spMock.getBoolean("logged", false)).thenReturn(false);
        LoginActivity.setSharedPreferencesInstance(spMock);
        activityTestRule.launchActivity(new Intent());
        activityTestRule.getActivity();
    }

    @After
    public void tearDown() {
        LoginActivity.setSharedPreferencesInstance(null);
    }

    @Test
    public void noInputTest(){
        subBtn.perform(click());
        email.check(matches(hasFocus()));
        email.check(matches(hasErrorText(activityTestRule.getActivity().getString(R.string.email_not_valid))));
    }

    @Test
    public void wrongEmailFormatTest(){
        email.perform(typeText("ballioli"), closeSoftKeyboard());
        subBtn.perform(click());
        email.check(matches(hasFocus()));
        email.check(matches(hasErrorText(activityTestRule.getActivity().getString(R.string.email_not_valid))));
    }
}