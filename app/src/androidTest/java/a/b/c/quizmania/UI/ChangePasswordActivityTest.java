package a.b.c.quizmania.UI;

import android.content.Intent;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

import a.b.c.quizmania.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
@RunWith(AndroidJUnit4.class)
public class ChangePasswordActivityTest {

    @Rule
    public ActivityTestRule<ChangePasswordActivity> mActivityTestRule
            = new ActivityTestRule<>(ChangePasswordActivity.class,
            false, false);

    private ViewInteraction password;
    private ViewInteraction passwordConfirm;
    private ViewInteraction submitBtn;

    private ChangePasswordActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mActivityTestRule.launchActivity(new Intent());
        mActivity = mActivityTestRule.getActivity();


        password = onView(withId(R.id.new_pass));
        passwordConfirm = onView(withId(R.id.confirm_new_pass));
        submitBtn = onView(withId(R.id.submitBtnChangePassword));
    }

    @Test
    public void checkInvalidPWLength() {
        password.perform(typeText(" "), closeSoftKeyboard());
        passwordConfirm.perform(typeText(" "), closeSoftKeyboard());
        submitBtn.perform(click());
        password.check(matches(hasFocus()));
        password.check(matches(hasErrorText("Invalid password")));
        password.perform(typeText(""), closeSoftKeyboard());
        passwordConfirm.perform(typeText(""), closeSoftKeyboard());
        submitBtn.perform(click());
        password.check(matches(hasFocus()));
        password.check(matches(hasErrorText("Invalid password")));
    }

    @Test
    public void checkInvalidPW() {
        password.perform(clearText(), typeText("Ballioli"), closeSoftKeyboard());
        passwordConfirm.perform(clearText(), typeText("Ballioli"), closeSoftKeyboard());
        submitBtn.perform(click());
        password.check(matches(hasFocus()));
        password.check(matches(hasErrorText("Invalid password")));


        password.perform(clearText(), typeText("12345"), closeSoftKeyboard());
        passwordConfirm.perform(clearText(), typeText("12345"), closeSoftKeyboard());
        submitBtn.perform(click());
        password.check(matches(hasFocus()));
        password.check(matches(hasErrorText("Invalid password")));


        password.perform(clearText(), typeText("Balli 123"), closeSoftKeyboard());
        passwordConfirm.perform(clearText(), typeText("Balli 123"), closeSoftKeyboard());
        submitBtn.perform(click());
        password.check(matches(hasFocus()));
        password.check(matches(hasErrorText("Invalid password")));
    }

    @After
    public void tearDown() throws Exception {
    }
}